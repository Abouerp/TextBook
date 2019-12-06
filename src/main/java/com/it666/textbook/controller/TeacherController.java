package com.it666.textbook.controller;

import com.github.pagehelper.PageInfo;
import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.entity.Class;
import com.it666.textbook.entity.TextBook;
import com.it666.textbook.entity.User;
import com.it666.textbook.service.ClassService;
import com.it666.textbook.service.TextBookService;
import com.it666.textbook.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public TeacherController(TextBookService textBookService, UserService userService,ClassService classService) {
        this.textBookService = textBookService;
        this.userService = userService;
        this.classService = classService;
    }

    /**
     * 查看用户个人信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultBean<User> getMessage(@PathVariable Integer id){
        return new ResultBean<>(userService.findByUserId(id));
    }

    /**
     * 更新用户信息
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
     * @param classMessage
     * @return    @CrossOrigin
     */
    @PostMapping("/saveclass")
    public List<Integer> classSave(@RequestBody List<Class> classMessage) {
        return classService.save(classMessage);
    }

    /**
     * 提交教材申请表
     * @param textBook
     * @return
     */
    @PostMapping("/savetextbook")
    public ResultBean<TextBook> save(@RequestBody TextBook textBook) {
        textBook.setDate(new Date());
        TextBook sava = textBookService.save(textBook);
        Integer textbook_id = sava.getId();
        List<Integer> classList = textBook.getClassList();
        if (classList != null){
            for (Integer cl: classList){
                classService.updateTeacherId(cl,textbook_id);
            }
        }
        return new ResultBean<>(sava);
    }

    /**
     * 获取该教师所提交的所有申请表，分页
     * @param page
     * @param size
     * @param id       教师的id
     * @return
     */
    @GetMapping("/getall/{id}")
    public ResultBean<PageInfo<TextBook>> findAllTextBook(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                              @PathVariable Integer id) {
        return new ResultBean<>(textBookService.findByTeacherId(page,size,id));
    }

    /**
     * 获取申请表详细信息
     * @param id    申请表的id
     * @return
     */
    @GetMapping("/findtextbook/{id}")
    public ResultBean<Map<String,Object>> findTextBookById(@PathVariable Integer id){
        TextBook textBook = textBookService.findTextBookById(id);
        Integer textBookId = textBook.getId();
        List<Class> classList = classService.findByTextBookId(textBookId);
        Map<String,Object> map = new HashMap<>();
        map.put("textbook",textBook);
        map.put("class",classList);
        return new ResultBean<>(map);
    }

    /**
     * 更新教材申请表
     * @param textBook
     * @return
     */
    @PutMapping("/textbook")
    public ResultBean<TextBook> updateTextBook(@PathVariable TextBook textBook){
        return new ResultBean<>(textBookService.updateTextBook(textBook));
    }

    /**
     * 删除申请表
     * @param id    申请表的id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultBean<Boolean> deleteByTextBookId(@PathVariable Integer id){
        classService.deleteByTextBookId(id);
        textBookService.deleteByTextBookId(id);
        return new ResultBean<>(true);
    }
}
