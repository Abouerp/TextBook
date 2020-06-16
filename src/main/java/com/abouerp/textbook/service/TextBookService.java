package com.abouerp.textbook.service;


import com.abouerp.textbook.config.StorageProperties;
import com.abouerp.textbook.dao.StorageRepository;
import com.abouerp.textbook.dao.TextBookRepository;
import com.abouerp.textbook.domain.ClassInformation;
import com.abouerp.textbook.domain.Storage;
import com.abouerp.textbook.domain.TextBook;
import com.abouerp.textbook.dto.TextBookDTO;
import com.abouerp.textbook.dto.TextBookStatusDTO;
import com.abouerp.textbook.exception.ExcelErrorException;
import com.abouerp.textbook.mapper.TextBookMapper;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.*;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author Abouerp
 */
@Service
public class TextBookService {

    private final TextBookRepository textBookRepository;
    private final Path rootLocation;
    private final StorageRepository storageRepository;
    private final String CONTENT_TYPE = "application/vnd.ms-excel";


    public TextBookService(
            TextBookRepository textBookRepository,
            StorageProperties storageProperties,
            StorageRepository storageRepository) {
        this.textBookRepository = textBookRepository;
        this.rootLocation = Paths.get(storageProperties.getLocation());
        this.storageRepository = storageRepository;
    }

    public TextBook save(TextBook textBook) {
        return textBookRepository.save(textBook);
    }

    public Optional<TextBook> findById(Integer id) {
        return textBookRepository.findById(id);
    }

    public void deleteById(Integer id) {
        textBookRepository.deleteById(id);
    }

    public Page<TextBookDTO> findAll(Pageable pageable, Integer status) {
        List<TextBook> list = textBookRepository.findAll();
        list = list.stream().filter(it -> !it.getStatus().equals(1)).collect(Collectors.toList());
        if (status != null && status > 0) {
            list = list.stream().filter(it -> it.getStatus().equals(status)).collect(Collectors.toList());
        }
        return common(pageable, list);
    }

    public Page<TextBookDTO> findByAdministrator_Id(Integer id, Pageable pageable, Integer status) {
        List<TextBook> list = textBookRepository.findByAdministrator_Id(id, pageable);
        if (status != null && status > 0) {
            list = list.stream().filter(it -> it.getStatus().equals(status)).collect(Collectors.toList());
        }
        return common(pageable, list);
    }

    public Page<TextBookDTO> findByAdministrator_Ids(List<Integer> ids, Pageable pageable, Integer status) {
        List<TextBook> list = textBookRepository.findByAdministratorIdIn(ids, pageable);
        if (status != null && status > 0) {
            list = list.stream().filter(it -> it.getStatus().equals(status)).collect(Collectors.toList());
        }
        list = list.stream().filter(it -> !it.getStatus().equals(1)).collect(Collectors.toList());
        return common(pageable, list);
    }

    private static Page<TextBookDTO> common(Pageable pageable, List<TextBook> list) {
        List<TextBookDTO> dtoList = new ArrayList<>();
        for (TextBook textBook : list) {
            dtoList.add((TextBookMapper.INSTANCE.toDTO(textBook)));
        }

        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > dtoList.size() ? dtoList.size() : (start + pageable.getPageSize());
        return new PageImpl<>(dtoList.subList(start, end), pageable, dtoList.size());
    }

    public List<TextBook> findByIdIn(List<Integer> ids) {
        return textBookRepository.findByIdIn(ids);
    }

    private static byte[] read(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return FileCopyUtils.copyToByteArray(resource.getInputStream());
    }


    public String outPutExcel(TextBook textBook) throws IOException {
        byte[] data = read("excel/template_textbook.xls");
        try {
            InputStream in = new ByteArrayInputStream(data);
            POIFSFileSystem fileSystem = new POIFSFileSystem(in);
            HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
            HSSFSheet sheet = workbook.getSheet("Sheet1");

            HSSFRow row = sheet.getRow(2);
            row.getCell(1).setCellValue(textBook.getCourseName());
            row.getCell(6).setCellValue(textBook.getCourseTime());

            row = sheet.getRow(3);
            row.getCell(1).setCellValue(textBook.getTitleName());
            row.getCell(6).setCellValue(textBook.getPublisher());

            row = sheet.getRow(4);
            row.getCell(1).setCellValue(textBook.getAuthor());
            row.getCell(3).setCellValue(textBook.getTitleDate());
            row.getCell(5).setCellValue(textBook.getVersion());
            row.getCell(7).setCellValue(textBook.getFlag());

            row = sheet.getRow(6);
            row.getCell(4).setCellValue(textBook.getTitleType());

            row = sheet.getRow(7);
            row.getCell(6).setCellValue(textBook.getIsbn());

            List<ClassInformation> classInformations = textBook.getClassList().stream().collect(Collectors.toList());
            int i = 9;
            for (ClassInformation classInformation : classInformations) {
                row = sheet.getRow(i);
                row.getCell(0).setCellValue(classInformation.getGrade());
                row.getCell(1).setCellValue(classInformation.getSubject());
                row.getCell(3).setCellValue(classInformation.getNumber());
                row.getCell(4).setCellValue(classInformation.getDate());
                row.getCell(5).setCellValue(classInformation.getClassType());
                row.getCell(6).setCellValue(classInformation.getSemester());
                i++;
            }

            row = sheet.getRow(14);


            row.getCell(2).setCellValue(textBook.getAdministrator().getRealName());
            row.getCell(6).setCellValue(textBook.getPhone());

            row = sheet.getRow(16);
            row.getCell(0).setCellValue("审核意见： " + textBook.getReviewOpinion());

            row = sheet.getRow(17);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            row.getCell(5).setCellValue("审核时间：" + (textBook.getReviewDate() == null ? "" : format.format(textBook.getReviewDate())));

            row = sheet.getRow(18);
            row.getCell(5).setCellValue("系主任签名：");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
            String sha1 = common(rootLocation, swapStream);

            workbook.close();
            swapStream.close();
            in.close();
            Storage storage = storageRepository.findBySha1(sha1).orElse(null);
            if (storage == null) {
                storage = new Storage().setSha1(sha1)
                        .setContentType(CONTENT_TYPE)
                        .setOriginalFilename(textBook.getCourseName() + ".xls");
                storageRepository.save(storage);
                return sha1;
            }


            return storage.getSha1();
        } catch (Exception e) {
            throw new ExcelErrorException();
        }
    }

    private String common(Path rootLocation, ByteArrayInputStream in) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            Path temp = Files.createTempFile("temp-", null);
            try (
                    OutputStream out = Files.newOutputStream(temp)
            ) {
                byte[] buf = new byte[8192];
                int n;
                while (-1 != (n = in.read(buf, 0, buf.length))) {
                    digest.update(buf, 0, n);
                    out.write(buf, 0, n);
                }
                String sha1 = String.format("%032X", new BigInteger(1, digest.digest()));
                Path dest = rootLocation.resolve(sha1);
                if (dest.toFile().exists()) {
                    return sha1;
                }
                Files.move(temp, dest);
                return sha1;
            } catch (IOException e) {
//                throw new StorageException("Failed to store file " + originalFilename, e);
            }
        } catch (IOException | NoSuchAlgorithmException e) {
//            throw new StorageException("Failed to create temp file ", e);
        }
        return null;
    }


    public TextBookStatusDTO countStatusAndAdminId(Integer id){
        return new TextBookStatusDTO()
                .setUnSubmit(textBookRepository.countByStatusAndAdministrator_Id(1,id))
                .setUnReview(textBookRepository.countByStatusAndAdministrator_Id(2,id))
                .setReview(textBookRepository.countByStatusAndAdministrator_Id(3,id))
                .setCount(textBookRepository.countByAdministrator_Id(id));
    }

    public void deleteByAdministrator_Id(Integer id){
        textBookRepository.deleteByAdministrator_Id(id);
    }
}
