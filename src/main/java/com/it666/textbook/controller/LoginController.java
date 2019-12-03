package com.it666.textbook.controller;

import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.entity.LoginUser;
import com.it666.textbook.entity.User;
import com.it666.textbook.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Abouerp
 */
@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/login")
    public ResultBean<Object> login(@RequestBody LoginUser user) {
        User users = userService.findByUserName(user.getUserName());
        if (users != null) {
            if (user.getUserPassword().equals(users.getUserPassword())) {
                return new ResultBean<>(users);
            } else {
                return new ResultBean<>("密码错误！");
            }
        } else {
            return new ResultBean<>("该用户不存在");
        }
    }
}
