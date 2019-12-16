package com.it666.textbook.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    /**
     * 根据学院和启动任务状态获取所有
     * userType = 1  只获取所有教师
     * @param page
     * @param size
     * @param startTask
     * @param college        学院名称
     * @return
     */
    public PageInfo<User> findUserByStartTaskAndCollege(int page, int size, Integer startTask, String college) {
        PageHelper.startPage(page,size);
        PageInfo<User> pageInfo = new PageInfo<>(userDao.findUserByStartTaskAndCollege(startTask,1,college),size);
        return pageInfo;
    }
}
