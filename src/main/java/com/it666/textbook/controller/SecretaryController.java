package com.it666.textbook.controller;

import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.entity.User;
import com.it666.textbook.service.SecretaryService;
import com.it666.textbook.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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

    public SecretaryController(SecretaryService secretaryService,UserService userService) {
        this.secretaryService = secretaryService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResultBean<Object> savaTeacher(@RequestBody User user) {
        User newuser = secretaryService.savaTeacher(user);
        return new ResultBean<>(newuser);
    }

    @PutMapping
    public ResultBean<Object> edit(@RequestBody User user) {
        User edit = userService.edit(user);
        return new ResultBean(edit);
    }
}
