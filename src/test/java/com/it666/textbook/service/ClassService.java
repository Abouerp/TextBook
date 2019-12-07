package com.it666.textbook.service;

import com.it666.textbook.dao.ClassInformationDao;
import com.it666.textbook.entity.ClassInformation;
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
public class ClassService {

    @Autowired
    private ClassInformationDao classDao;

    @Test
    public void save(){
        ClassInformation test = new ClassInformation();
        test.setGrade("20级");
        test.setSubject("移动");
        test.setNumber(50);
        test.setDate("大一第二学期");
        test.setClassType("选修");
        test.setTextbookId(25);
        classDao.save(test);
    }

    @Test
    public void findById(){
        ClassInformation byId = classDao.findById(1);
        System.out.println(byId);
    }

    @Test
    public void updateTextbookId(){
         classDao.updateTextbookId(54, 17);
    }

    @Test
    public void update(){
        ClassInformation test = new ClassInformation();
        test.setId(54);
        test.setGrade("20级");
        test.setSubject("溜溜球");
        test.setNumber(50);
        test.setDate("大一第二学期");
        test.setClassType("选修");
        test.setTextbookId(25);
        classDao.update(test);
    }

    @Test
    public void findByTextBookId(){
        List<ClassInformation> bytextbookid =  classDao.findByTextBookId(12);
        for (ClassInformation user: bytextbookid) {
            System.out.println(user);
        }
    }

    @Test
    public void deleteByTextBookId(){
//        待添加外键
//        int a = classDao.deleteByTextBookId(25);
    }
}
