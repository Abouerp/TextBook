package com.it666.textbook.service;

import com.it666.textbook.dao.TextBookDao;
import com.it666.textbook.domain.TextBook;
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
        TextBook tb = new TextBook();
        tb.setTeacherId(4);
        tb.setCourseName("红红火火恍恍惚惚");
        tb.setCourseTime(45);
        textBookDao.save(tb);
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
        TextBook tb = textBookDao.findByTextBookById(13);
        System.out.println(tb);
    }

    @Test
    public void deleteByTextBookId(){
        textBookDao.deleteById(27);
    }

    @Test
    public void updateTextBook(){
        TextBook tb = new TextBook();
        tb.setTeacherId(5);
        tb.setCourseName("上海金");
        tb.setCourseTime(23);
        textBookDao.updateTextbook(tb);
    }

    @Test
    public void findByTeacherIdAndStatus() {
        List<TextBook> tbList = textBookDao.findByTeacherIdAndStatus(1, 1);
        for (TextBook tb: tbList) {
            System.out.println(tb);
        }
    }

    @Test
    public void findByCollege(){
        List<TextBook> college = textBookDao.findByCollege("计算机学院");
        for (TextBook c:college){
            System.out.println(c);
        }

    }

}
