package com.it666.textbook.controller;

import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.entity.User;
import com.it666.textbook.service.SecretaryService;
import com.it666.textbook.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URI;


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

    public SecretaryController(SecretaryService secretaryService, UserService userService) {
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

    @PostMapping("/file")
    public ResultBean<String> importProcess(HttpServletRequest request, @RequestBody MultipartFile myfile)  {
        String path = request.getSession().getServletContext().getRealPath("/upload/");
//        System.out.println(path2);
        String path2 = URI.create("classpath:/upload2").toString();
        File file = new File(path);
        File file12 = new File(path2);
        if (!file.exists() ){
            file.mkdir();

            file12.mkdir();
        }
        String filename = myfile.getOriginalFilename();
        try {
            File file1 = new File(path, filename);
            myfile.transferTo(file1);


//            FileUtils.deleteDirectory(file1);
        }catch (IOException e){
            return new ResultBean<>("file upload false");
        }
        return new ResultBean<>("file upload success");
    }

}
