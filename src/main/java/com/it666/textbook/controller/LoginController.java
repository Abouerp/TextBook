package com.it666.textbook.controller;

import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.bean.ResultCode;
import com.it666.textbook.domain.LoginUser;
import com.it666.textbook.domain.User;
import com.it666.textbook.service.UserService;
import com.it666.textbook.utils.JwtUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Abouerp
 */
@Controller
@RequestMapping("/api")
public class LoginController {

    private final UserService userService;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping("/login")
    public ResultBean<Object> login(@RequestBody LoginUser user) {
        User users = userService.findByUserName(user.getUserName());
        if (users != null) {
            if (user.getUserPassword().equals(users.getUserPassword())) {
                String jwt = JwtUtils.createJWT(users.getId()+"", users.getUserName(), users.getUserType()+"");
                Map<String,Object> map = new HashMap<>();
                map.put("token", jwt);
                map.put("userType", users.getUserType());
                map.put("id", users.getId());
                map.put("realName",users.getRealName());
                return new ResultBean<>(ResultCode.SUCCESS,map);
            } else {
                return new ResultBean<>("密码错误！");
            }
        } else {
            return new ResultBean<>("该用户不存在");
        }
    }


    @GetMapping("/file/{hashName}")
    public ResultBean<Boolean> getFile(HttpServletResponse response,@PathVariable("hashName") String hashName)  {

        File file = new File(uploadFolder+"/111.mp4");
        FileInputStream ips = null;
        OutputStream out = null;
        try {
            response.setCharacterEncoding("UTF-8");
//            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;fileName=111.mp4" );
            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");

            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = ips.read(buffer)) > 0){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e) {
            System.out.println(21.07);
        }finally {
            try {
                out.close();
                ips.close();
            }catch (IOException  e){

            }
        }
        return new ResultBean(ResultCode.SUCCESS,true);
    }
}
