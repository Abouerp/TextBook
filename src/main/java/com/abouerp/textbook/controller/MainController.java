package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.dao.PublisherRepository;
import com.abouerp.textbook.dao.TextBookRepository;
import com.abouerp.textbook.domain.College;
import com.abouerp.textbook.domain.MainBook;
import com.abouerp.textbook.entity.StatisticsPublisherRsp;
import com.abouerp.textbook.exception.CollegeNotFoundException;
import com.abouerp.textbook.service.CollegeService;
import com.abouerp.textbook.service.MainBookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//import com.github.pagehelper.PageInfo;
//import com.abouerp.textbook.bean.ResultBean;
//import com.abouerp.textbook.bean.ResultCode;
//import com.abouerp.textbook.domain.MainBook;
//import com.abouerp.textbook.entity.StatisticsPublisherRsp;
//import com.abouerp.textbook.service.MainBookService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/main")
public class MainController {

    private final MainBookService mainBookService;
    private final CollegeService collegeService;
    private final PublisherRepository publisherRepository;

    public MainController(
            MainBookService mainBookService,
            CollegeService collegeService,
            PublisherRepository publisherRepository) {
        this.mainBookService = mainBookService;
        this.collegeService = collegeService;
        this.publisherRepository = publisherRepository;
    }

    @GetMapping("/{id}")
    public ResultBean<Page<MainBook>> findAll(
            @PathVariable Integer id,
            @PageableDefault Pageable pageable){
        College college = collegeService.findById(id).orElseThrow(CollegeNotFoundException::new);
        MainBook mainBook = new MainBook();
        mainBook.setCollege(college.getName());
        return ResultBean.ok(mainBookService.findAll(pageable,mainBook));
    }

    @GetMapping("/book/{id}")
    public ResultBean<MainBook> findByBookId(@PathVariable Integer id) {
        return  ResultBean.ok(mainBookService.findByBookId(id));
    }

    @GetMapping("/publisher")
    public ResultBean<Object> count(){
        return ResultBean.ok(publisherRepository.counts());
    }

}
