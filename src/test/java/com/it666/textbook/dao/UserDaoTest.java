package com.it666.textbook.dao;

import com.it666.textbook.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Abouerp
 */
class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    void findAll(){
        List<User> users = userDao.findAll();
        System.out.println(users.size());
    }

    @Test
    void findUserByUsername() {
        User user = userDao.findUserByUsername("admin");
        System.out.println(user);
    }

    @Test
    void save() {
    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
    }
}