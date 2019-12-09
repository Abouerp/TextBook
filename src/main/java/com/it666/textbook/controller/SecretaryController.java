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
                log.info("row.getcell(0) = {} " + row.getCell(0).getStringCellValue());
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
     *
     * @param status
     * @return
     * @throws IOException
     */
    @GetMapping("/excel/{status}")
    public ResultBean<String> outExcel(@PathVariable Integer status) throws IOException {
        List<TextBook> textbookList = textBookService.findByStatus(status);
        String path = textBookService.outExcel(textbookList, uploadFolder);
        return new ResultBean<>(path);
    }

    @PutMapping("/textbook/{id}/{status}")
    public ResultBean<Object> putTextBookStatus(@PathVariable Integer id, @PathVariable Integer status){
        textBookService.updateTextbookStatus(id,status);
        return new ResultBean<>("update success");
    }
}
