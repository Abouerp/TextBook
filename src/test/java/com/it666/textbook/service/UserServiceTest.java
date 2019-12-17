package com.it666.textbook.service;

import com.it666.textbook.dao.UserDao;
import com.it666.textbook.domain.User;

import com.it666.textbook.entity.StatisticsCollegeRsp;
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
public class UserServiceTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void findAll() {
        List<User> all = userDao.findAll();
        for (User a : all) {
            System.out.println(a);
        }
    }

    @Test
    public void findByUsername() {
        User all = userDao.findUserByUsername("hahaha");
        System.out.println(all);
    }

    @Test
    public void findUserById() {
        User u = userDao.findUserById(5);
        System.out.println(u);
    }

    @Test
    public void save() {
        User u = new User();
        u.setUserName("fasdfsdfah");
        u.setUserPassword("adasfah");
        u.setUserType(2);
        int a = userDao.save(u);
    }

    @Test
    public void edit() {
        User u = new User();
        u.setUserName("345g43ywe");
        u.setUserPassword("s345t");
        u.setId(5);
        u.setUserType(1);
        u.setJobNumber("12351");
        userDao.edit(u);
    }

    @Test
    public void delete() {
        userDao.delete(6);
    }

    @Test
    public void findUserByStartTaskAndCollege(){
        List<User> list = userDao.findUserByStartTaskAndCollege(1, 1, "计算机学院");
        for (User user:list) {
            System.out.println(user);
        }
    }

    @Test
    public void findStatisticsCollege(){
        List<StatisticsCollegeRsp> list = userDao.findStatisticsCollege();
        for (StatisticsCollegeRsp statisticsCollegeRsp : list){
            System.out.println(statisticsCollegeRsp);
        }
    }


}