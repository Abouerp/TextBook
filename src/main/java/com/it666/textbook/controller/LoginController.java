package com.it666.textbook.controller;

import com.it666.textbook.entity.LoginUser;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Abouerp
 */
@RestController
public class LoginController {


    @PostMapping("/login")
    public Map<String,String> login(@RequestBody LoginUser loginUser){

        Map<String,String> map = new HashMap<>();
        map.put("success","创建成功");
        map.put("core","200");
        map.put("Type","teacher");
        return map;
    }
}
