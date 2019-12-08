package com.it666.textbook.controller;


import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.entity.User;
import com.it666.textbook.service.SecretaryService;
import com.it666.textbook.service.UserService;


import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URI;
import java.util.UUID;


/**
 * 秘书
 *
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/secretary")
public class SecretaryController {

    private final SecretaryService secretaryService;
    private final UserService userService;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    public SecretaryController(SecretaryService secretaryService, UserService userService) {
        this.secretaryService = secretaryService;
        this.userService = userService;
    }

    /**
     * 添加教师
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
     * @param user
     * @return
     */
    @PutMapping
    public ResultBean<Object> edit(@RequestBody User user) {
        User edit = userService.edit(user);
        return new ResultBean(edit);
    }

    /**
     * 提交excel文件
     * @param file
     * @return
     */
    @PostMapping("/file")
    public ResultBean<String> importProcess(@RequestBody MultipartFile file) {
        if (file == null){
            return new ResultBean<>("file is null");
        }
        String path =uploadFolder;
        System.out.println(path);
        File newfile = new File(path);
        if (!newfile.exists() ){
            newfile.mkdir();
        }
        String uuid = UUID.randomUUID().toString().replace("-","");
        String filename = uuid+"_"+file.getOriginalFilename();
        try {
            //file.transferTo(new File(path,filename));

            InputStream inputStream = file.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i=1; i<=lastRowNum; i++){
                HSSFRow row = sheet.getRow(i);
                if (row==null){
                    break;
                }
                User user = new User();
                user.setUserName(row.getCell(0).getStringCellValue());
                user.setUserPassword(row.getCell(1).getStringCellValue());
                user.setJobNumber(row.getCell(2).getStringCellValue());
                String usertype = row.getCell(3).getStringCellValue();
                if ("教师".equals(usertype)){
                    user.setUserType(1);
                }else {
                    user.setUserType(2);
                }
                secretaryService.savaTeacher(user);
            }
            return new ResultBean<>("insert teacher success");
        } catch (IOException e) {
            return new ResultBean<>("file upload fail");
        }

    }

    public ResultBean<Boolean> processExcel(@PathVariable String fileName){
        String path = uploadFolder+"/"+fileName;
        File file = new File(path);



        return new ResultBean<>(true);
    }
}
