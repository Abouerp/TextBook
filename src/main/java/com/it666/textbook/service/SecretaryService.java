package com.it666.textbook.service;

import com.it666.textbook.dao.UserDao;
import com.it666.textbook.domain.User;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class SecretaryService {

    private final UserDao userDao;

    public SecretaryService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User saveTeacher(User user){
        userDao.save(user);
        return user;
    }

    public User edit(User user){
        userDao.edit(user);
        return user;
    }
}
