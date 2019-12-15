package com.it666.textbook.service;

import com.it666.textbook.dao.UserDao;
import com.it666.textbook.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public User findByUserId(Integer id){
        User user = userDao.findUserById(id);
        return user;
    }

    public List<User> findAll(){
         return userDao.findAll();
    }

    public User edit(User user){
        userDao.edit(user);
        return user;
    }


}
