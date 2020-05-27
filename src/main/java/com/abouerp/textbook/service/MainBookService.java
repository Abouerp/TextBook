//package com.abouerp.textbook.service;
//
//import com.abouerp.textbook.dao.MainPageDao;
//import com.abouerp.textbook.dao.TextBookDao;
//import com.abouerp.textbook.entity.StatisticsPublisherRsp;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.abouerp.textbook.domain.MainBook;
//import org.springframework.stereotype.Service;
//
///**
// * @author Abouerp
// */
//@Service
//public class MainBookService {
//
//    private final MainPageDao mainPageDao;
//    private final TextBookDao textBookDao;
//
//    public MainBookService(MainPageDao mainPageDao, TextBookDao textBookDao) {
//        this.mainPageDao = mainPageDao;
//        this.textBookDao = textBookDao;
//    }
//
//    public PageInfo<MainBook> findByCollege(int page, int size, String college) {
//        PageHelper.startPage(page, size);
//        PageInfo<MainBook> pageInfo = new PageInfo<>(mainPageDao.findByCollege(college), size);
//        return pageInfo;
//    }
//
//    public MainBook findByBookId(Integer id) {
//        return mainPageDao.findByBookId(id);
//    }
//
//    public PageInfo<StatisticsPublisherRsp> findStatisticsPublisherRsp(int page, int size) {
//        PageHelper.startPage(page,size);
//        PageInfo<StatisticsPublisherRsp> pageInfo = new PageInfo<>(textBookDao.findStatisticsPublisherRsp(),size);
//        return pageInfo;
//    }
//}
