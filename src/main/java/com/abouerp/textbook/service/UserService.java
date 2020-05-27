//package com.abouerp.textbook.service;
//
//import com.abouerp.textbook.dao.UserDao;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import com.abouerp.textbook.domain.User;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * @author Abouerp
// */
//@Service
//public class UserService {
//
//    private final UserDao userDao;
//
//    public UserService(UserDao userDao) {
//        this.userDao = userDao;
//    }
//
//    public User findByUserName(String username) {
//        User user = userDao.findUserByUsername(username);
//        return user;
//    }
//
//    public User findByUserId(Integer id) {
//        User user = userDao.findUserById(id);
//        return user;
//    }
//
//    public List<User> findAll() {
//        return userDao.findAll();
//    }
//
//    public User edit(User user) {
//        userDao.edit(user);
//        return user;
//    }
//
//
//    /**
//     * 没用到
//     * 获取所有教师
//     *
//     * @param page
//     * @param size
//     * @param userType
//     * @return
//     */
//    public PageInfo<User> findUserByUserType(int page, int size, Integer userType) {
//        PageHelper.startPage(page, size);
//        PageInfo<User> pageInfo = new PageInfo<>(userDao.findUserByType(userType), size);
//        return pageInfo;
//    }
//
//    public Integer deleteUserById(Integer id){
//        return userDao.delete(id);
//    }
//}
