package com.it666.textbook.service;

import com.it666.textbook.dao.UserDao;
import com.it666.textbook.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Abouerp
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {

    @Autowired
    private UserDao userDao;

    @Test
    void findAll() {
        List<User> all = userDao.findAll();
        for (User a : all) {
            System.out.println(a);
        }
    }

    @Test
    void findByUsername() {
        User all = userDao.findUserByUsername("hahaha");
        System.out.println(all);
    }

    @Test
    void findUserById() {
        User u = userDao.findUserById(5);
        System.out.println(u);
    }

    @Test
    void save() {
        User u = new User();
        u.setUserName("fasdfsdfah");
        u.setUserPassword("adasfah");
        u.setUserType(2);
        int a = userDao.save(u);
    }

    @Test
    void edit() {
        User u = new User();
        u.setUserName("345g43ywe");
        u.setUserPassword("s345t");
        u.setId(5);
        u.setUserType(1);
        u.setJobNumber("12351");
        userDao.edit(u);
    }

    @Test
    void delete() {
        userDao.delete(6);
    }
}