package com.it666.textbook.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it666.textbook.dao.TextBookDao;
import com.it666.textbook.domain.ClassInformation;
import com.it666.textbook.domain.TextBook;
import com.it666.textbook.domain.User;
import com.it666.textbook.entity.StatisticsRep;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @author Abouerp
 */
@Service
public class TextBookService {

    private final TextBookDao textBookDao;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    public TextBookService(TextBookDao textBookDao) {
        this.textBookDao = textBookDao;
    }

    public TextBook save(TextBook textBook) {
        textBookDao.save(textBook);
        return textBook;
    }

    public PageInfo<TextBook> findByTeacherId(int page, int size, Integer id) {
        PageHelper.startPage(page, size);
        PageInfo<TextBook> pageInfo = new PageInfo<>(textBookDao.findByTeacherId(id), size);
        return pageInfo;
    }

    public TextBook findTextBookById(Integer id) {
        return textBookDao.findByTextBookById(id);
    }

    public void deleteByTextBookId(Integer id) {
        textBookDao.deleteById(id);
    }

    public TextBook updateTextBook(TextBook textBook) {
        textBookDao.updateTextbook(textBook);
        return textBook;
    }

    public PageInfo<TextBook> findByTeacherIdAndStatus(int page, int size, Integer teacherId, Integer status) {
        PageHelper.startPage(page, size);
        PageInfo<TextBook> pageInfo = new PageInfo<>(textBookDao.findByTeacherIdAndStatus(teacherId, status), size);
        return pageInfo;
    }

    public PageInfo<TextBook> findByTeacherIdAndOkStatus(int page, int size, Integer teacherId, Integer status) {
        PageHelper.startPage(page, size);
        PageInfo<TextBook> pageInfo = new PageInfo<>(textBookDao.findByTeacherIdAndOkStatus(teacherId, status), size);
        return pageInfo;
    }

    /**
     * 秘书导出已审核的申请表到excel表
     *
     * @param status
     * @return
     */
    public List<TextBook> outExcelOfReview(Integer status) {
        return textBookDao.findByStatusReview(status);
    }


    /**
     * 获取未审核 和 已审核 的申请表
     *
     * @param status 得到的值应该为2
     * @return
     */
    public PageInfo<TextBook> findByStatusUnReview(int page, int size, Integer status) {
        PageHelper.startPage(page, size);
        PageInfo<TextBook> pageInfo;
        if (status == 2) {
            pageInfo = new PageInfo<>(textBookDao.findByStatusUnReview(status), size);
        } else if (status >= 3) {
            pageInfo = new PageInfo<>(textBookDao.findByStatusReview(status), size);
        } else {
            return null;
        }
        return pageInfo;
    }

    public Integer updateTextbookStatus(Integer id, Integer status, String reviewOpinion) {
        return textBookDao.updateTextbookStatus(id, status, reviewOpinion);
    }

    /**
     * 导出申请表，多张
     *
     * @param textbookList
     * @param uploadFolder
     * @return
     * @throws IOException
     */
    public String outExcel(List<TextBook> textbookList, String uploadFolder) throws IOException {
        String filename = UUID.randomUUID().toString() + ".xls";
        OutputStream outputStream = new FileOutputStream(uploadFolder + filename);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("课程名称");
        row.createCell(1).setCellValue("课程学时");
        row.createCell(2).setCellValue("教材名称");
        row.createCell(3).setCellValue("出版单位");
        row.createCell(4).setCellValue("编者");
        row.createCell(5).setCellValue("出版时间");
        row.createCell(6).setCellValue("版次");
        row.createCell(7).setCellValue("书号ISBN");
        row.createCell(8).setCellValue("教材类型");
        row.createCell(9).setCellValue("联系电话");
        row.setHeightInPoints(30);
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 15 * 256);
        sheet.setColumnWidth(4, 10 * 256);
        sheet.setColumnWidth(5, 15 * 256);
        sheet.setColumnWidth(7, 15 * 256);
        sheet.setColumnWidth(8, 20 * 256);
        sheet.setColumnWidth(9, 12 * 256);

        int j = 1;
        for (int i = 0; i < textbookList.size(); i++) {
            HSSFRow row1 = sheet.createRow(j);
            String courseName = textbookList.get(i).getCourseName();
            if (courseName != null) {
                row1.createCell(0).setCellValue(courseName);
            }
            Integer courseTime = textbookList.get(i).getCourseTime();
            if (courseTime != null) {
                row1.createCell(1).setCellValue(courseTime);
            }
            String titleName = textbookList.get(i).getTitleName();
            if (titleName != null) {
                row1.createCell(2).setCellValue(titleName);
            }
            String publisher = textbookList.get(i).getPublisher();
            if (publisher != null) {
                row1.createCell(3).setCellValue(publisher);
            }
            String author = textbookList.get(i).getAuthor();
            if (author != null) {
                row1.createCell(4).setCellValue(author);
            }
            String titleDate = textbookList.get(i).getTitleDate();
            if (titleDate != null) {
                row1.createCell(5).setCellValue(titleDate);
            }
            String version = textbookList.get(i).getVersion();
            if (version != null) {
                row1.createCell(6).setCellValue(version);
            }
            String isbn = textbookList.get(i).getIsbn();
            if (isbn != null) {
                row1.createCell(7).setCellValue(isbn);
            }
            String titleType = textbookList.get(i).getTitleType();
            if (titleType != null) {
                row1.createCell(8).setCellValue(titleType);
            }
            String phone = textbookList.get(i).getPhone();
            if (phone != null) {
                row1.createCell(9).setCellValue(phone);
            }
            j++;
        }
        workbook.setActiveSheet(0);
        workbook.write(outputStream);
        outputStream.close();

        return filename;
    }

    /**
     * 教师导出单张申请表
     *
     * @param textBook
     * @param classInformations
     * @param user
     * @return
     * @throws Exception
     */
    public String outSimpleExcel(TextBook textBook, List<ClassInformation> classInformations, User user) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(uploadFolder + "finallymodel.xls");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
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


        System.out.println(user.getRealName());
        row.getCell(2).setCellValue(user.getRealName());
        row.getCell(6).setCellValue(textBook.getPhone());

        row = sheet.getRow(16);
        row.getCell(0).setCellValue("审核意见： " + textBook.getReviewOpinion());

        row = sheet.getRow(17);
        row.getCell(5).setCellValue("审核时间：" + (textBook.getReviewDate() == null ? "" : textBook.getReviewDate()));

        row = sheet.getRow(18);
        row.getCell(5).setCellValue("系主任签名：kk");
        String filename = UUID.randomUUID().toString() + ".xls";
        OutputStream outputStream = new FileOutputStream(uploadFolder + filename);
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();

        return filename;
    }

    public PageInfo<TextBook> findByCollege(int page, int size, String college) {
        PageHelper.startPage(page, size);
        PageInfo<TextBook> pageInfo = new PageInfo<>(textBookDao.findByCollege(college), size);
        return pageInfo;
    }

    public PageInfo<TextBook> findAllTextBook(int page, int size) {
        PageHelper.startPage(page, size);
        PageInfo<TextBook> pageInfo = new PageInfo<>(textBookDao.findAll(), size);
        return pageInfo;
    }

    /**
     * 返回统计个数
     *
     * @param teacherId
     * @return
     */
    public StatisticsRep findStatisticsByTeacherId(Integer teacherId) {
        return textBookDao.findStatisticsByTeacherId(teacherId);
    }
}
