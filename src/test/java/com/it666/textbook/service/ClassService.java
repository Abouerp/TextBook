package com.it666.textbook.service;

import com.it666.textbook.dao.ClassDao;
import com.it666.textbook.entity.Class;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Abouerp
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClassService {

    @Autowired
    private ClassDao classDao;

    @Test
    public void save(){

    }

    @Test
    public void findById(){
        Class byId = classDao.findById(1);
        System.out.println(byId);
    }

    @Test
    public void updateTextbookId(){

    }

    @Test
    public void update(){

    }

    @Test
    public void findByTextBookId(){

    }

    @Test
    public void deleteByTextBookId(){

    }
}
