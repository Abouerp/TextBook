package com.it666.textbook.dao;

import com.it666.textbook.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author Abouerp
 */
@Mapper
@Component
public interface UserDao {

    @Select("select * from user where username = #{userName}")
    public User findUserByUsername(String userName);


}
