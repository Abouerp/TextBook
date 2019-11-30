package com.it666.textbook.dao;

import com.it666.textbook.entity.User;

/**
 * @author Abouerp
 */

public interface UserDao {

    @Select("select * from user where username = #{userName}")
    public User findUserByUsername(String userName);

}
