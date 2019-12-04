package com.it666.textbook.controller;

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
import java.util.List;

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

    @PostMapping("/savetextbook")
    public ResultBean<TextBook> save(@RequestBody TextBook textBook) {
        textBook.setDate(new Date());
        TextBook sava = textBookService.sava(textBook);
        Integer textbook_id = sava.getId();
        List<Integer> classList = textBook.getClassList();
        for (Integer cl: classList){
            classService.updateTeacherId(cl,textbook_id);
        }
        return new ResultBean<>(sava);
    }

    @PostMapping("/saveclass")
    public Class classSave(@RequestBody Class classMessage) {
        return classService.sava(classMessage);
    }

    @PutMapping
    public ResultBean<Object> edit(@RequestBody User user) {
        User edit = userService.edit(user);
        return new ResultBean(edit);
    }

}
