package com.it666.textbook.service;

import com.it666.textbook.dao.UserDao;
import com.it666.textbook.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    public User findByUserName(String username){
        User user = userDao.findUserByUsername(username);
        return user;
    }
}
