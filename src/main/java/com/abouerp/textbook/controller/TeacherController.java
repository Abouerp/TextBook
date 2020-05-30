//package com.abouerp.textbook.controller;
//
//import com.github.pagehelper.PageInfo;
//import com.abouerp.textbook.bean.ResultBean;
//import com.abouerp.textbook.bean.ResultCode;
//import com.abouerp.textbook.domain.ClassInformation;
//import com.abouerp.textbook.domain.TextBook;
//import com.abouerp.textbook.domain.User;
//import com.abouerp.textbook.entity.StatisticsRep;
//import com.abouerp.textbook.service.ClassService;
//import com.abouerp.textbook.service.TextBookService;
//import com.abouerp.textbook.service.UserService;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 教师
// *
// * @author Abouerp
// */
//@RestController
//@RequestMapping("/api/teacher")
//@Log4j2
//public class TeacherController {
//
//    private final TextBookService textBookService;
//    private final UserService userService;
//    private final ClassService classService;
//    private final RedisTemplate<Object, Object> redisTemplate;
//
//    @Value("${remote.port}")
//    private String port;
//    @Value("${remote.address}")
//    private String address;
//
//    public TeacherController(TextBookService textBookService, UserService userService, ClassService classService, RedisTemplate<Object, Object> redisTemplate) {
//        this.textBookService = textBookService;
//        this.userService = userService;
//        this.classService = classService;
//        this.redisTemplate = redisTemplate;
//    }
//
//    /**
//     * 查看用户个人信息
//     *
//     * @param id
//     * @return
//     */
//    @GetMapping("/{id}")
//    public ResultBean<User> getMessage(@PathVariable Integer id) {
//        RedisSerializer redisSerializer = new StringRedisSerializer();
//        redisTemplate.setKeySerializer(redisSerializer);
//
//        /**
//         * 缓存穿透，双重检测锁
//         */
////        User user = (User) redisTemplate.opsForValue().get("user" + id);
////        if (null == user) {
////            synchronized (this) {
////                user = (User) redisTemplate.opsForValue().get("user" + id);
////                if (null == user) {
////                    User userId = userService.findByUserId(id);
////                    redisTemplate.opsForValue().set("user" + id, userId);
////                }
////            }
////        }
//        User user = userService.findByUserId(id);
//        return new ResultBean<>(user);
//    }
//
//    /**
//     * 更新用户信息
//     *
//     * @param user
//     * @return
//     */
//    @PutMapping
//    public ResultBean<User> edit(@RequestBody User user) {
//        User edit = userService.edit(user);
////        redisTemplate.opsForValue().set("user" + user.getId(), edit);
//        return new ResultBean(edit);
//    }
//
//    /**
//     * 提交教材所对应的班级
//     *
//     * @param classMessage
//     * @return @CrossOrigin
//     */
//    @PostMapping("/saveclass")
//    public ResultBean<Object> classSave(@Valid @RequestBody List<ClassInformation> classMessage, BindingResult bindingResult) {
//        Map<ResultCode, String> map = new HashMap<>();
//        if (bindingResult.hasErrors()) {
//            bindingResult.getAllErrors().forEach(e->{
//                map.put(ResultCode.CHECK_FAIL,e.getDefaultMessage() );
//            });
//            return new ResultBean<>(ResultCode.CHECK_FAIL,map);
//        }
//        return new ResultBean<>(ResultCode.SUCCESS, classService.save(classMessage) );
//    }
//
//    /**
//     * 提交教材申请表
//     *
//     * @param textBook
//     * @return
//     */
//    @PostMapping("/savetextbook")
//    public ResultBean<Object> save(@Valid @RequestBody TextBook textBook , BindingResult bindingResult) {
//        Map<ResultCode, String> map = new HashMap<>();
//        if (bindingResult.hasErrors()) {
//            bindingResult.getAllErrors().forEach(e->{
//                map.put(ResultCode.CHECK_FAIL,e.getDefaultMessage() );
//            });
//            return new ResultBean<>(ResultCode.CHECK_FAIL,map);
//        }
//
//        textBook.setDate(new Date());
//        TextBook save = textBookService.save(textBook);
//        Integer textbook_id = save.getId();
//        List<Integer> classList = textBook.getClassList();
//        if (classList != null) {
//            for (Integer cl : classList) {
//                classService.updateTeacherId(cl, textbook_id);
//            }
//        }
//        return new ResultBean<>(ResultCode.SUCCESS,save);
//    }
//
//    /**
//     * 获取该教师所提交的申请表，分页
//     *
//     * @param page
//     * @param size
//     * @param id   教师的id
//     * @return
//     */
//    @GetMapping("/getall/{id}")
//    public ResultBean<PageInfo<TextBook>> findAllTextBook(@RequestParam(value = "page", defaultValue = "1") int page,
//                                                          @RequestParam(value = "size", defaultValue = "10") int size,
//                                                          @PathVariable Integer id) {
//        return new ResultBean<>(textBookService.findByTeacherId(page, size, id));
//    }
//
//    /**
//     * 获取申请表详细信息
//     *
//     * @param id 申请表的id
//     * @return
//     */
//    @GetMapping("/findtextbook/{id}")
//    public ResultBean<Map<String, Object>> findTextBookById(@PathVariable Integer id) {
//        /**
//         * 查看缓存是否有要查询的申请表
//         */
////        RedisSerializer redisSerializer = new StringRedisSerializer();
////        redisTemplate.setKeySerializer(redisSerializer);
////        TextBook textBookredis = (TextBook) redisTemplate.opsForValue().get("textbook"+id);
////        if (textBookredis != null) {
////            List<ClassInformation> classList = classService.findByTextBookId(id);
////            Map<String, Object> map = new HashMap<>();
////            map.put("textbook", textBookredis);
////            map.put("class", classList);
////            return new ResultBean<>(ResultCode.SUCCESS,map);
////        }
//
//
//        TextBook textBook = textBookService.findTextBookById(id);
////        redisTemplate.opsForValue().set("textbook"+id, textBook);
//        Integer textBookId = textBook.getId();
//        List<ClassInformation> classList = classService.findByTextBookId(textBookId);
//        Map<String, Object> map = new HashMap<>();
//        map.put("textbook", textBook);
//        map.put("class", classList);
//        return new ResultBean<>(ResultCode.SUCCESS,map);
//    }
//
//    /**
//     * 更新教材申请表
//     *
//     * @param textBook
//     * @return
//     */
//    @PutMapping("/textbook")
//    public ResultBean<TextBook> updateTextBook(@RequestBody TextBook textBook) {
//        //把redis中的key序列化为一个字符串，可读性比较好
////        RedisSerializer redisSerializer = new StringRedisSerializer();
////        redisTemplate.setKeySerializer(redisSerializer);
////        redisTemplate.delete("textbook"+textBook.getId());
//
//        textBook.setDate(new Date());
//        Integer textBookId = textBook.getId();
//        classService.deleteByTextBookId(textBookId);
//        List<Integer> classList = textBook.getClassList();
//        for (Integer id : classList) {
//            classService.updateTeacherId(id, textBookId);
//        }
//        return new ResultBean<>(textBookService.updateTextBook(textBook));
//    }
//
//    /**
//     * 删除申请表
//     *
//     * @param id 申请表的
//     * @return
//     */
//    @DeleteMapping("/{id}")
//    public ResultBean<Boolean> deleteByTextBookId(@PathVariable Integer id) {
//
//        classService.deleteByTextBookId(id);
//        textBookService.deleteByTextBookId(id);
//        return new ResultBean<>(true);
//    }
//
//    /**
//     * 根据状态和教师id获取申请表list
//     *
//     * @param teacherId
//     * @param status
//     * @return
//     */
//    @GetMapping("/{teacherId}/{status}")
//    public ResultBean<PageInfo<TextBook>> findByTeacherIdAndStatus(@RequestParam(value = "page", defaultValue = "1") int page,
//                                                                   @RequestParam(value = "size", defaultValue = "10") int size,
//                                                                   @PathVariable Integer teacherId, @PathVariable Integer status) {
//        if (status >= 3) {
//            return new ResultBean<>(ResultCode.SUCCESS, textBookService.findByTeacherIdAndOkStatus(page, size, teacherId, status));
//        }
//        return new ResultBean<>(ResultCode.SUCCESS, textBookService.findByTeacherIdAndStatus(page, size, teacherId, status));
//    }
//
//    /**
//     * 导出单张申请表
//     *
//     * @param id 申请表的id
//     * @return string  文件名字
//     * @throws IOException
//     */
//    @GetMapping("/excel/{id}")
//    public ResultBean<String> simpleExcel(@PathVariable Integer id) throws Exception {
//        TextBook textBook = textBookService.findTextBookById(id);
//        List<ClassInformation> classInformations = classService.findByTextBookId(textBook.getId());
//        User user = userService.findByUserId(textBook.getTeacherId());
//        String path = textBookService.outSimpleExcel(textBook, classInformations, user);
//        return new ResultBean<>(ResultCode.SUCCESS, path);
//    }
//
//    /**
//     * 获取教师的申请表的统计信息
//     *
//     * @param teacherId
//     * @return
//     */
//    @GetMapping("/statistics/{teacherId}")
//    public ResultBean<StatisticsRep> totalNum(@PathVariable Integer teacherId) {
//        return new ResultBean<>(ResultCode.SUCCESS, textBookService.findStatisticsByTeacherId(teacherId));
//    }
//
//
//}