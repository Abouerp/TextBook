package com.it666.textbook.controller;


import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.entity.User;
import com.it666.textbook.service.SecretaryService;
import com.it666.textbook.service.UserService;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URI;
import java.util.UUID;


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
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;

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
    public ResultBean<String> importProcess(HttpServletRequest request, @RequestBody MultipartFile myfile) throws IOException {
        String path =this.getClass().getClassLoader().getResource("").toString();
                //request.getSession().getServletContext().getRealPath("resources/upload");

        System.out.println(path);
//        String path2 = URI.create("classpath:/upload2").toString();
        File file = new File(path);
        if (!file.exists() ){
            file.mkdir();
        }
        String uuid = UUID.randomUUID().toString().replace("-","");
        String filename = uuid+"_"+myfile.getOriginalFilename();
        myfile.transferTo(new File(path,filename));

        return new ResultBean<>("file upload success");
    }

}
