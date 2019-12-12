package com.it666.textbook.controller;


import com.github.pagehelper.PageInfo;
import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.bean.ResultCode;
import com.it666.textbook.domain.TextBook;
import com.it666.textbook.domain.User;
import com.it666.textbook.service.SecretaryService;
import com.it666.textbook.service.TextBookService;
import com.it666.textbook.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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
    @PostMapping("/teacher")
    public ResultBean<User> saveTeacher(@RequestBody User user) {
        User newUser = secretaryService.saveTeacher(user);
        return new ResultBean<>(newUser);
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
        File newFile = new File(path);
        if (!newFile.exists()) {
            newFile.mkdir();
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
                secretaryService.saveTeacher(user);
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
        return new ResultBean<>(ResultCode.SUCCESS,path);
    }

    /**
     * 审核申请表
     * @param id        申请表id
     * @param status    申请表状态
     * @param reviewOpinion     审核意见
     * @return
     */
    @PutMapping("/textbook/{id}/{status}")
    public ResultBean<Object> putTextBookStatus(@PathVariable Integer id, @PathVariable Integer status,@RequestBody String reviewOpinion) {
        TextBook textBook = textBookService.findTextBookById(id);
        textBook.setStatus(status);
        textBook.setReviewOpinion(reviewOpinion);
        textBook.setReviewDate(new Date());
        textBookService.updateTextBook(textBook);
        return new ResultBean<>(ResultCode.SUCCESS,textBook);
    }

    /**
     * 根据学院筛选申请表
     * @param page
     * @param size
     * @param collegeId
     * @return
     */
    @GetMapping("/college/{collegeId}")
    public ResultBean<PageInfo<TextBook>> findTextBookByCollege(@RequestParam(value = "page",defaultValue = "1")int page,
                                                                @RequestParam(value = "size",defaultValue = "10")int size,
                                                                @PathVariable Integer collegeId){
        String collegeName = null;
        switch (collegeId){
            case 1:
                collegeName = "电子信息学院";
                break;
            case 2:
                collegeName = "机电工程学院";
                break;
            case 3:
                collegeName = "计算机学院";
                break;
            case 4:
                collegeName = "材料与食品学院";
                break;
            case 5:
                collegeName = "人文社会科学学院";
                break;
            case 6:
                collegeName = "管理学院";
                break;
            case 7:
                collegeName = "经贸学院";
                break;
            case 8:
                collegeName = "外国语学院";
                break;
            case 9:
                collegeName = "艺术设计学院";
                break;
            case 10:
                collegeName = "马克思主义学院";
                break;
            default:
                collegeName = "体育部";
                break;
        }
        return new ResultBean<>(ResultCode.SUCCESS,textBookService.findByCollege(page,size,collegeName));
    }

    /**
     * 查看某个教师的所有申请表
     * @param page
     * @param size
     * @param id
     * @return
     */
    @GetMapping("/teacher/{id}")
    public ResultBean<PageInfo<TextBook>> findTextBookByTeacherId (@RequestParam(value = "page",defaultValue = "1")int page,
                                                        @RequestParam(value = "size",defaultValue = "10")int size,
                                                        @PathVariable Integer id){
        PageInfo<TextBook> pageInfo = textBookService.findByTeacherId(page, size, id);
        return new ResultBean<>(ResultCode.SUCCESS,pageInfo);
    }
}
