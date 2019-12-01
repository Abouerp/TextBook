package com.it666.textbook.controller;

import com.it666.textbook.entity.LoginUser;
import com.it666.textbook.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
    public Map<String, String> login(@RequestBody LoginUser user) {
        LoginUser users = userService.findByUserName(user.getUserName());


        Map<String, String> map = new HashMap<>();
        map.put("success", "创建成功");
        map.put("core", "200");
        map.put("Type", "teacher");
        return map;
    }
}
