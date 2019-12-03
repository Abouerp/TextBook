package com.it666.textbook.controller;

import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.entity.TextBook;
import com.it666.textbook.entity.User;
import com.it666.textbook.service.TextBookService;
import com.it666.textbook.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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

    public TeacherController(TextBookService textBookService,UserService userService){
        this.textBookService = textBookService;
        this.userService = userService;
    }

    @PostMapping
    public TextBook save(@RequestBody TextBook textBook){
        return textBookService.sava(textBook);
    }

    @PutMapping
    public ResultBean<Object> edit(@RequestBody User user) {
        User edit = userService.edit(user);
        return new ResultBean(edit);
    }

}
