package com.it666.textbook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Abouerp
 */
@RestController
public class LoginController {


    @GetMapping("/login")
    public Map<String,String> login(){
        Map<String,String> map = new HashMap<>();
        map.put("success","创建成功");
        map.put("core","200");
        map.put("Type","teacher");
        return map;
    }
}
