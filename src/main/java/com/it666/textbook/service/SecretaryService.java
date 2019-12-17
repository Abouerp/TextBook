package com.it666.textbook.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it666.textbook.dao.TextBookDao;
import com.it666.textbook.dao.UserDao;
import com.it666.textbook.domain.User;
import com.it666.textbook.entity.StatisticsCollegeRsp;
import com.it666.textbook.entity.TextBookHistoryRsp;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Abouerp
 */
@Service
public class SecretaryService {

    private final UserDao userDao;
    private final TextBookDao textBookDao;

    public SecretaryService(UserDao userDao, TextBookDao textBookDao) {
        this.userDao = userDao;
        this.textBookDao = textBookDao;
    }

    public User saveTeacher(User user) {
        userDao.save(user);
        return user;
    }

    public User edit(User user) {
        userDao.edit(user);
        return user;
    }

    /**
     * 根据学院和启动任务状态获取所有
     * userType = 1  只获取所有教师
     *
     * @param page
     * @param size
     * @param startTask
     * @param college   学院名称
     * @return
     */
    public PageInfo<User> findUserByStartTaskAndCollege(int page, int size, Integer startTask, String college) {
        PageHelper.startPage(page, size);
        PageInfo<User> pageInfo = new PageInfo<>(userDao.findUserByStartTaskAndCollege(startTask, 1, college), size);
        return pageInfo;
    }

    /**
     * 获取统计信息， xxx学院， 多少教师
     * @return
     */
    public List<StatisticsCollegeRsp> findStatisticsCollege() {
        return userDao.findStatisticsCollege();
    }

    public PageInfo<TextBookHistoryRsp> findTextBookHistory(int page, int size, Integer status,String college) {
        PageHelper.startPage(page,size);
        PageInfo<TextBookHistoryRsp> pageInfo = new PageInfo<>(textBookDao.findTextBookHistory(status,college), size);
        return pageInfo;
    }

    /**
     * 更新教师启动任务状态
     * @param teacherListId
     * @param startTask
     */
    public void updateUserStartTask(List<Integer> teacherListId, Integer startTask) {
        for (Integer id:teacherListId) {
            userDao.updateUserStartTask(id, startTask);
        }
    }

    public void updateUserStartTaskByCollege(String college, Integer startTask){

    }
}
