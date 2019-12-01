package com.it666.textbook.service;

import com.it666.textbook.bean.ResultBean;
import com.it666.textbook.dao.TextBookDao;
import com.it666.textbook.entity.TextBook;
import org.springframework.stereotype.Service;

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
}
