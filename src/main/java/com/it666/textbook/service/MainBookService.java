package com.it666.textbook.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it666.textbook.dao.MainPageDao;
import com.it666.textbook.domain.MainBook;
import org.springframework.stereotype.Service;
import sun.applet.Main;

/**
 * @author Abouerp
 */
@Service
public class MainBookService {

    private final MainPageDao mainPageDao;

    public MainBookService(MainPageDao mainPageDao) {
        this.mainPageDao = mainPageDao;
    }

    public PageInfo<MainBook> findByCollege(int page, int size, String college) {
        PageHelper.startPage(page,size);
        PageInfo<MainBook> pageInfo = new PageInfo<>(mainPageDao.findByCollege(college),size);
        return pageInfo;
    }

    public MainBook findByBookId(Integer id){
        return mainPageDao.findByBookId(id);
    }
}
