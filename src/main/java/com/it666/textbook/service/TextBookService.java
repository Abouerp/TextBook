package com.it666.textbook.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.dao.TextBookDao;
import com.it666.textbook.entity.TextBook;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Abouerp
 */
@Service
public class TextBookService {

    private final TextBookDao textBookDao;

    public TextBookService(TextBookDao textBookDao){
        this.textBookDao = textBookDao;
    }

    public TextBook sava(TextBook textBook){
        textBookDao.save(textBook);
        return textBook;
    }

    public PageInfo<TextBook> findByTeacherId(int page, int size, Integer id) {
        PageHelper.startPage(page,size);
        PageInfo<TextBook> pageInfo =new PageInfo<>(textBookDao.findByTeacherId(id),size);
        return pageInfo;
    }

    public TextBook findTextBookById(Integer id){
        return textBookDao.findByTextBookById(id);
    }

    public void deleteByTextBookId(Integer id){
         textBookDao.deleteById(id);
    }

    public TextBook updateTextBook(TextBook textBook){
        textBookDao.updateTextbook(textBook);
        return textBook;
    }
}
