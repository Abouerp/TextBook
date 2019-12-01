package com.it666.textbook.controller;

import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.entity.LoginUser;
import com.it666.textbook.entity.User;
import com.it666.textbook.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Abouerp
 */
@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResultBean<Boolean> login(@RequestBody LoginUser user) {
        User users = userService.findByUserName(user.getUserName());
//        List<User> all = userService.findAll();
//        System.out.println(all);
        if (user.getUserPassword().equals(users.getUserPassword())){
            return new ResultBean<>(true);
//            return "success";
        }else {
            return new ResultBean<>(false);
//            return "false";
        }
    }
}
