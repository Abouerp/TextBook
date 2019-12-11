package com.it666.textbook.controller;

import com.github.pagehelper.PageInfo;
import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.entity.ClassInformation;
import com.it666.textbook.entity.TextBook;
import com.it666.textbook.entity.User;
import com.it666.textbook.service.ClassService;
import com.it666.textbook.service.TextBookService;
import com.it666.textbook.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

/**
 * 教师
 *
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/teacher")
@Log4j2
public class TeacherController {

    private final TextBookService textBookService;
    private final UserService userService;
    private final ClassService classService;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${remote.port}")
    private String port;
    @Value("${remote.address}")
    private String address;

    public TeacherController(TextBookService textBookService, UserService userService, ClassService classService) {
        this.textBookService = textBookService;
        this.userService = userService;
        this.classService = classService;
    }

    /**
     * 查看用户个人信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultBean<User> getMessage(@PathVariable Integer id) {
        return new ResultBean<>(userService.findByUserId(id));
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @PutMapping
    public ResultBean<User> edit(@RequestBody User user) {
        User edit = userService.edit(user);
        return new ResultBean(edit);
    }

    /**
     * 提交教材所对应的班级
     *
     * @param classMessage
     * @return @CrossOrigin
     */
    @PostMapping("/saveclass")
    public List<Integer> classSave(@RequestBody List<ClassInformation> classMessage) {
        return classService.save(classMessage);
    }

    /**
     * 提交教材申请表
     *
     * @param textBook
     * @return
     */
    @PostMapping("/savetextbook")
    public ResultBean<TextBook> save(@RequestBody TextBook textBook) {
        textBook.setDate(new Date());
        TextBook sava = textBookService.save(textBook);
        Integer textbook_id = sava.getId();
        List<Integer> classList = textBook.getClassList();
        if (classList != null) {
            for (Integer cl : classList) {
                classService.updateTeacherId(cl, textbook_id);
            }
        }
        return new ResultBean<>(sava);
    }

    /**
     * 获取该教师所提交的申请表，分页
     *
     * @param page
     * @param size
     * @param id   教师的id
     * @return
     */
    @GetMapping("/getall/{id}")
    public ResultBean<PageInfo<TextBook>> findAllTextBook(@RequestParam(value = "page", defaultValue = "1") int page,
                                                          @RequestParam(value = "size", defaultValue = "10") int size,
                                                          @PathVariable Integer id) {
        return new ResultBean<>(textBookService.findByTeacherId(page, size, id));
    }

    /**
     * 获取申请表详细信息
     *
     * @param id 申请表的id
     * @return
     */
    @GetMapping("/findtextbook/{id}")
    public ResultBean<Map<String, Object>> findTextBookById(@PathVariable Integer id) {
        TextBook textBook = textBookService.findTextBookById(id);
        Integer textBookId = textBook.getId();
        List<ClassInformation> classList = classService.findByTextBookId(textBookId);
        Map<String, Object> map = new HashMap<>();
        map.put("textbook", textBook);
        map.put("class", classList);
        return new ResultBean<>(map);
    }

    /**
     * 更新教材申请表
     *
     * @param textBook
     * @return
     */
    @PutMapping("/textbook")
    public ResultBean<TextBook> updateTextBook(@RequestBody TextBook textBook) {
        textBook.setDate(new Date());
        Integer textBookId = textBook.getId();
        classService.deleteByTextBookId(textBookId);
        List<Integer> classList = textBook.getClassList();
        for (Integer id : classList) {
            classService.updateTeacherId(id, textBookId);
        }
        return new ResultBean<>(textBookService.updateTextBook(textBook));
    }

    /**
     * 删除申请表
     *
     * @param id 申请表的
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultBean<Boolean> deleteByTextBookId(@PathVariable Integer id) {

        classService.deleteByTextBookId(id);
        textBookService.deleteByTextBookId(id);
        return new ResultBean<>(true);
    }

    /**
     * 根据状态和教师id获取申请表list
     *
     * @param teacherId
     * @param status
     * @return
     */
    @GetMapping("/{teacherId}/{status}")
    public ResultBean<List<TextBook>> findByTeacherIdAndStatus(@PathVariable Integer teacherId, @PathVariable Integer status) {
        return new ResultBean<>(textBookService.findByTeacherIdAndStatus(teacherId, status));
    }

    @GetMapping("/excel/{id}")
    public ResultBean<String> simpleExcel(@PathVariable Integer id) throws IOException {
        TextBook textBook = textBookService.findTextBookById(id);
        FileInputStream fileInputStream = new FileInputStream(uploadFolder+"finallymodel.xls");
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

        List<ClassInformation> classInformations = classService.findByTextBookId(textBook.getId());

        int i=9;
        for (ClassInformation classInformation :classInformations){
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
        User user = userService.findByUserId(textBook.getTeacherId());
        log.info("user = {}",user);
        System.out.println(user.getRealName());
        row.getCell(2).setCellValue(user.getRealName());
        row.getCell(6).setCellValue(textBook.getPhone());

        row = sheet.getRow(16);
        row.getCell(0).setCellValue("审核意见： "+textBook.getReviewOpinion());

        row = sheet.getRow(17);
        row.getCell(5).setCellValue("审核时间："+(textBook.getReviewDate()==null?"":textBook.getReviewDate()));

        row = sheet.getRow(18);
        row.getCell(5).setCellValue("系主任签名：kk");
        String filename = UUID.randomUUID().toString() + ".xls";
        OutputStream outputStream = new FileOutputStream(uploadFolder + filename);
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
        return new ResultBean<>(filename);
    }
}
