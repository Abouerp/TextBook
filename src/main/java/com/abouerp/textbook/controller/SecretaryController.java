//package com.abouerp.textbook.controller;
//
//
//import com.github.pagehelper.PageInfo;
//import com.abouerp.textbook.bean.ResultBean;
//import com.abouerp.textbook.bean.ResultCode;
//import com.abouerp.textbook.domain.TextBook;
//import com.abouerp.textbook.domain.User;
//import com.abouerp.textbook.entity.StatisticsCollegeRsp;
//import com.abouerp.textbook.entity.TextBookHistoryRsp;
//import com.abouerp.textbook.service.SecretaryService;
//import com.abouerp.textbook.service.TextBookService;
//import com.abouerp.textbook.service.UserService;
//import lombok.extern.log4j.Log4j2;
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.validation.Valid;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.*;
//
//
///**
// * 秘书
// *
// * @author Abouerp
// */
//@RestController
//@RequestMapping("/api/secretary")
//@Log4j2
//public class SecretaryController {
//
//    private final SecretaryService secretaryService;
//    private final UserService userService;
//    private final TextBookService textBookService;
//    private final RedisTemplate<Object, Object> redisTemplate;
//    @Value("${file.staticAccessPath}")
//    private String staticAccessPath;
//    @Value("${file.uploadFolder}")
//    private String uploadFolder;
//    @Value("${remote.port}")
//    private String port;
//    @Value("${remote.address}")
//    private String address;
//
//    public SecretaryController(SecretaryService secretaryService, UserService userService, TextBookService textBookService,RedisTemplate<Object, Object> redisTemplate) {
//        this.secretaryService = secretaryService;
//        this.userService = userService;
//        this.textBookService = textBookService;
//        this.redisTemplate =redisTemplate;
//    }
//
//    /**
//     * 添加教师
//     *
//     * @param user
//     * @return
//     */
//    @PostMapping("/teacher")
//    public ResultBean<Object> saveTeacher(@Valid @RequestBody User user, BindingResult bindingResult) {
//        Map<ResultCode, String> map = new HashMap<>();
//        if (bindingResult.hasErrors()) {
//            bindingResult.getAllErrors().forEach(e->{
//                map.put(ResultCode.CHECK_FAIL,e.getDefaultMessage() );
//            });
//            return new ResultBean<>(ResultCode.CHECK_FAIL,map);
//        }
//        User newUser = secretaryService.saveTeacher(user);
//        return new ResultBean<>(newUser);
//    }
//
//    /**
//     * 删除教师
//     * @param id
//     * @return
//     */
//    @DeleteMapping("/teacher/{id}")
//    public ResultBean<Integer> deleteTeacher(@PathVariable Integer id){
//        return new ResultBean<>(ResultCode.SUCCESS, userService.deleteUserById(id) );
//    }
//
//    /**
//     * 更新用户信息
//     *
//     * @param user
//     * @return
//     */
//    @PutMapping
//    public ResultBean<Object> edit(@RequestBody User user) {
//        User edit = userService.edit(user);
//        return new ResultBean(edit);
//    }
//
//    /**
//     * excel文件导入教师账户
//     *
//     * @param file
//     * @return
//     */
//    @PostMapping("/excel")
//    public ResultBean<String> importProcess(@RequestBody MultipartFile file) {
//        if (file == null) {
//            return new ResultBean<>("file is null");
//        }
//        String path = uploadFolder;
////        System.out.println(path);
//        File newFile = new File(path);
//        if (!newFile.exists()) {
//            newFile.mkdir();
//        }
//        String uuid = UUID.randomUUID().toString().replace("-", "");
////        String filename = uuid + "_" + file.getOriginalFilename();
//        try {
//            /*file.transferTo(new File(path,filename));*/
//
//            InputStream inputStream = file.getInputStream();
//            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
//            HSSFSheet sheet = workbook.getSheetAt(0);
//            int lastRowNum = sheet.getLastRowNum();
//            for (int i = 1; i <= lastRowNum; i++) {
//                HSSFRow row = sheet.getRow(i);
//                User user = new User();
//                String username = row.getCell(0).getStringCellValue();
//                log.info("row.getcell(0) = {} " + row.getCell(0).getStringCellValue());
//                user.setUserName(username);
//                String password = row.getCell(1).getStringCellValue();
//                user.setUserPassword(password);
//                String jobnumber = row.getCell(2).getStringCellValue();
//                user.setJobNumber(jobnumber);
//                String usertype = row.getCell(3).getStringCellValue();
//                if ("教师".equals(usertype)) {
//                    user.setUserType(1);
//                } else {
//                    user.setUserType(2);
//                }
//                secretaryService.saveTeacher(user);
//            }
//            return new ResultBean<>("insert teacher success");
//        } catch (IOException e) {
//            return new ResultBean<>("file upload fail");
//        }
//
//    }
//
//    /**
//     * 导出申请表到excel表格   根据传来的listId
//     * @param textBookListId
//     * @return
//     * @throws IOException
//     */
//    @PostMapping("/excel/out")
//    public ResultBean<String> outExcelOfReview(@RequestBody List<Integer> textBookListId) throws IOException {
//        List<TextBook> list = new ArrayList<>();
//        for (Integer id : textBookListId) {
//            list.add(textBookService.findTextBookById(id));
//        }
//        String path = textBookService.outExcel(list, uploadFolder);
//        return new ResultBean<>(ResultCode.SUCCESS, path);
//    }
//
//    /**
//     * 审核申请表
//     *
//     * @param id            申请表id
//     * @param status        申请表状态
//     * @param reviewOpinion 审核意见
//     * @return
//     */
//    @PutMapping("/textbook/{id}/{status}")
//    public ResultBean<Object> putTextBookStatus(@PathVariable Integer id, @PathVariable Integer status, @RequestBody String reviewOpinion) {
//        TextBook textBook = textBookService.findTextBookById(id);
//        textBook.setStatus(status);
//        textBook.setReviewOpinion(reviewOpinion);
//        textBook.setReviewDate(new Date());
//        textBookService.updateTextBook(textBook);
//
//        return new ResultBean<>(ResultCode.SUCCESS, textBook);
//    }
//
//    /**
//     * 根据学院筛选申请表
//     *
//     * @param page
//     * @param size
//     * @param collegeId
//     * @return
//     */
//    @GetMapping("/college/{collegeId}")
//    public ResultBean<PageInfo<TextBook>> findTextBookByCollege(@RequestParam(value = "page", defaultValue = "1") int page,
//                                                                @RequestParam(value = "size", defaultValue = "10") int size,
//                                                                @PathVariable Integer collegeId) {
//        String collegeName = common(collegeId);
//        return new ResultBean<>(ResultCode.SUCCESS, textBookService.findByCollege(page, size, collegeName));
//    }
//
//    /**
//     * 查看某个教师的所有申请表
//     *
//     * @param page
//     * @param size
//     * @param id   教师的id
//     * @return
//     */
//    @GetMapping("/teacher/{id}")
//    public ResultBean<PageInfo<TextBook>> findTextBookByTeacherId(@RequestParam(value = "page", defaultValue = "1") int page,
//                                                                  @RequestParam(value = "size", defaultValue = "10") int size,
//                                                                  @PathVariable Integer id) {
//        PageInfo<TextBook> pageInfo = textBookService.findByTeacherId(page, size, id);
//        return new ResultBean<>(ResultCode.SUCCESS, pageInfo);
//    }
//
//
//    /**
//     * 获取所有的申请表
//     *
//     * @param page
//     * @param size
//     * @return
//     */
//    @GetMapping("/textbook")
//    public ResultBean<PageInfo<TextBook>> findAllTextBook(@RequestParam(value = "page", defaultValue = "1") int page,
//                                                          @RequestParam(value = "size", defaultValue = "10") int size) {
//        PageInfo<TextBook> pageInfo = textBookService.findAllTextBook(page, size);
//        return new ResultBean<>(ResultCode.SUCCESS, pageInfo);
//    }
//
//    /**
//     * 获取所有未审核的申请表
//     *
//     * @param page
//     * @param size
//     * @param status 2：获得所有未审核的
//     * @return
//     */
//    @GetMapping("/textbook/{status}")
//    public ResultBean<PageInfo<TextBookHistoryRsp>> findByStatus(@RequestParam(value = "page", defaultValue = "1") int page,
//                                                       @RequestParam(value = "size", defaultValue = "10") int size,
//                                                       @PathVariable Integer status) {
//        return new ResultBean<>(ResultCode.SUCCESS, textBookService.findByStatusUnReview(page, size, status));
//    }
//
//    /**
//     * 动态根据 学院 和 启动任务 来获取教师
//     *
//     * @param page
//     * @param size
//     * @param startTask 0或者1    0不启动   1启动
//     * @param collegeId 学院id     1-11
//     * @return
//     */
//    @GetMapping("/teacher")
//    public ResultBean<PageInfo<User>> findUserByStartTaskAndCollege(@RequestParam(value = "page", defaultValue = "1") int page,
//                                                                    @RequestParam(value = "size", defaultValue = "10") int size,
//                                                                    @RequestParam(value = "startTask", defaultValue = "-1") Integer startTask,
//                                                                    @RequestParam(value = "collegeId", defaultValue = "0") Integer collegeId) {
//        String collegeName = null;
//        if (startTask == -1) {
//            startTask = null;
//        }
//        /**
//         * 0 表示没有选择学院
//         */
//        if (collegeId != 0) {
//            collegeName = common(collegeId);
//        }
//        return new ResultBean<>(ResultCode.SUCCESS, secretaryService.findUserByStartTaskAndCollege(page, size, startTask, collegeName));
//    }
//
//    /**
//     * 获取各个学院的教师人数
//     *
//     * @return
//     */
//    @GetMapping("/college")
//    public ResultBean<List<StatisticsCollegeRsp>> findStatisticsCollege() {
//        return new ResultBean<>(ResultCode.SUCCESS, secretaryService.findStatisticsCollege());
//    }
//
//    /**
//     * 获得历史申请表
//     *
//     * @param page      第几页
//     * @param size      一页的个数
//     * @param status    申请表状态
//     * @param collegeId 学院id
//     * @return
//     */
//    @GetMapping("/textbook/history")
//    public ResultBean<PageInfo<TextBookHistoryRsp>> findTextBookHistory(@RequestParam(value = "page", defaultValue = "1") int page,
//                                                                        @RequestParam(value = "size", defaultValue = "10") int size,
//                                                                        @RequestParam(value = "status", defaultValue = "-1") Integer status,
//                                                                        @RequestParam(value = "collegeId", defaultValue = "0") Integer collegeId) {
//        String collegeName = null;
//        /**
//         * 0 表示没有选择学院
//         */
//        if (collegeId != 0) {
//            collegeName = common(collegeId);
//        }
//        return new ResultBean<>(ResultCode.SUCCESS, secretaryService.findTextBookHistory(page, size, status, collegeName));
//    }
//
//    /**
//     * 指定教师启动填写申请表任务
//     * @param teacherListId
//     * @param startTask
//     * @return
//     */
//    @PutMapping("/teacher/{startTask}")
//    public ResultBean<String> updateUserStartTask(@RequestBody List<Integer> teacherListId,@PathVariable Integer startTask) {
//        secretaryService.updateUserStartTask(teacherListId,startTask);
//        return new ResultBean<>(ResultCode.SUCCESS, "open task success");
//    }
//
//    /**
//     * 指定某个学院所有老师都启动填写申请表任务
//     * @param collegeId
//     * @param startTask
//     * @return
//     */
//    @PutMapping("/teacher/{startTask}/{collegeId}")
//    public ResultBean<Integer> updateUserStartTaskByCollege(@PathVariable Integer collegeId,@PathVariable Integer startTask){
//        String collegeName = common(collegeId);
//        return new ResultBean<>(ResultCode.SUCCESS, secretaryService.updateUserStartTaskByCollege(collegeName,startTask) );
//    }
//
//    /**
//     * 获取学院名字公共抽离
//     *
//     * @param collegeId
//     * @return
//     */
//    public String common(Integer collegeId) {
//        String collegeName = null;
//        switch (collegeId) {
//            case 1:
//                collegeName = "电子信息学院";
//                break;
//            case 2:
//                collegeName = "机电工程学院";
//                break;
//            case 3:
//                collegeName = "计算机学院";
//                break;
//            case 4:
//                collegeName = "材料与食品学院";
//                break;
//            case 5:
//                collegeName = "人文社会科学学院";
//                break;
//            case 6:
//                collegeName = "管理学院";
//                break;
//            case 7:
//                collegeName = "经贸学院";
//                break;
//            case 8:
//                collegeName = "外国语学院";
//                break;
//            case 9:
//                collegeName = "艺术设计学院";
//                break;
//            case 10:
//                collegeName = "马克思主义学院";
//                break;
//            case 11:
//                collegeName = "体育部";
//                break;
//            default:
//                break;
//        }
//        return collegeName;
//    }
//}