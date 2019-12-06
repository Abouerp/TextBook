package com.it666.textbook.service;

import com.it666.textbook.dao.TextBookDao;
import com.it666.textbook.entity.TextBook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Abouerp
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TextBookServiceTest {

    @Autowired
    private TextBookDao textBookDao;

    @Test
    public void save(){

    }

    @Test
    public void findByTeacherId(){
        List<TextBook> textbooks = textBookDao.findByTeacherId(1);
        for (TextBook textBook:textbooks){
            System.out.println(textBook);
        }
    }

    @Test
    public void findTextBookById(){

    }

    @Test
    public void deleteByTextBookId(){

    }

    @Test
    public void updateTextBook(){

    }

}
