package com.it666.textbook.controller;

import com.it666.textbook.entity.TextBook;
import com.it666.textbook.service.TextBookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public TeacherController(TextBookService textBookService){
        this.textBookService = textBookService;
    }

    @PostMapping
    public TextBook save(@RequestBody TextBook textBook){
        return textBookService.sava(textBook);
    }

}
