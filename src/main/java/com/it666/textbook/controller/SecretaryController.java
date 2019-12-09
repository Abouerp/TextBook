package com.it666.textbook.controller;


import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.entity.TextBook;
import com.it666.textbook.entity.User;
import com.it666.textbook.service.SecretaryService;
import com.it666.textbook.service.TextBookService;
import com.it666.textbook.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;


/**
 * 秘书
 *
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/secretary")
@Log4j2
public class SecretaryController {

    private final SecretaryService secretaryService;
    private final UserService userService;
    private final TextBookService textBookService;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${remote.port}")
    private String port;
    @Value("${remote.address}")
    private String address;

    public SecretaryController(SecretaryService secretaryService, UserService userService, TextBookService textBookService) {
        this.secretaryService = secretaryService;
        this.userService = userService;
        this.textBookService = textBookService;
    }

    /**
     * 添加教师
     *
     * @param user
     * @return
     */
    @PostMapping("/save")
    public ResultBean<Object> savaTeacher(@RequestBody User user) {
        User newuser = secretaryService.savaTeacher(user);
        return new ResultBean<>(newuser);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @PutMapping
    public ResultBean<Object> edit(@RequestBody User user) {
        User edit = userService.edit(user);
        return new ResultBean(edit);
    }

    /**
     * excel文件导入教师账户
     *
     * @param file
     * @return
     */
    @PostMapping("/excel")
    public ResultBean<String> importProcess(@RequestBody MultipartFile file) {
        if (file == null) {
            return new ResultBean<>("file is null");
        }
        String path = uploadFolder;
        System.out.println(path);
        File newfile = new File(path);
        if (!newfile.exists()) {
            newfile.mkdir();
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String filename = uuid + "_" + file.getOriginalFilename();
        try {
            /*file.transferTo(new File(path,filename));*/

            InputStream inputStream = file.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                HSSFRow row = sheet.getRow(i);
                User user = new User();
                String username = row.getCell(0).getStringCellValue();
                log.info("row.getcell(0) = {} "+row.getCell(0).getStringCellValue());
                user.setUserName(username);
                String password = row.getCell(1).getStringCellValue();
                user.setUserPassword(password);
                String jobnumber = row.getCell(2).getStringCellValue();
                user.setJobNumber(jobnumber);
                String usertype = row.getCell(3).getStringCellValue();
                if ("教师".equals(usertype)) {
                    user.setUserType(1);
                } else {
                    user.setUserType(2);
                }
                secretaryService.savaTeacher(user);
            }
            return new ResultBean<>("insert teacher success");
        } catch (IOException e) {
            return new ResultBean<>("file upload fail");
        }

    }

    /**
     * 根据申请表的状态导出excel表格
     * @param status
     * @return
     * @throws IOException
     */
    @GetMapping("/excel/{status}")
    public ResultBean<String> outExcel(@PathVariable Integer status) throws IOException{
        List<TextBook> textbookList =  textBookService.findByStatus(status);
        String filename = UUID.randomUUID().toString()+".xls";
        OutputStream outputStream = new FileOutputStream(uploadFolder+filename);

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
        sheet.setColumnWidth(0,20*256);
        sheet.setColumnWidth(2,20*256);
        sheet.setColumnWidth(3,15*256);
        sheet.setColumnWidth(4,10*256);
        sheet.setColumnWidth(5,15*256);
        sheet.setColumnWidth(7,15*256);
        sheet.setColumnWidth(8,20*256);
        sheet.setColumnWidth(9,12*256);

        int j = 1;
        for (int i=0; i<textbookList.size(); i++){
            HSSFRow row1 = sheet.createRow(j);
            String courseName = textbookList.get(i).getCourseName();
            if (courseName!=null){
                row1.createCell(0).setCellValue(courseName);
            }
            Integer courseTime = textbookList.get(i).getCourseTime();
            if (courseTime!=null) {
                row1.createCell(1).setCellValue(courseTime);
            }
            String titleName = textbookList.get(i).getTitleName();
            if (titleName!=null){
                row1.createCell(2).setCellValue(titleName);
            }
            String publisher = textbookList.get(i).getPublisher();
            if (publisher!=null){
                row1.createCell(3).setCellValue(publisher);
            }
            String author = textbookList.get(i).getAuthor();
            if (author!=null){
                row1.createCell(4).setCellValue(author);
            }
            String titleDate = textbookList.get(i).getTitleDate();
            if (titleDate!=null){
                row1.createCell(5).setCellValue(titleDate);
            }
            String version = textbookList.get(i).getVersion();
            if (version!=null){
                row1.createCell(6).setCellValue(version);
            }
            String isbn = textbookList.get(i).getIsbn();
            if (isbn!=null){
                row1.createCell(7).setCellValue(isbn);
            }
            String titleType = textbookList.get(i).getTitleType();
            if (titleType!=null){
                row1.createCell(8).setCellValue(titleType);
            }
            String phone = textbookList.get(i).getPhone();
            if (phone!=null){
                row1.createCell(9).setCellValue(phone);
            }
            j++;
        }
        workbook.setActiveSheet(0);
        workbook.write(outputStream);
        outputStream.close();

        String path = "/api/file/"+filename;
        return new ResultBean<>(path);
    }
}
