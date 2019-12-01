package com.it666.textbook.dao;

import com.it666.textbook.entity.LoginUser;
import com.it666.textbook.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * @author Abouerp
 */
@Mapper
@Component
public interface UserDao {

    @Select("select * from user where user_name = #{userName}")
    public User findUserByUsername(String userName);

    @Insert("insert into user(user_name,user_password,job_number,sex,age,email,phone,user_type)values(#{userName},#{userPassword},#{jobNumber},#{sex},#{age},#{email},#{phone},#{userType})")
    @Options(keyColumn = "id",useGeneratedKeys = true)
    public void save(User user);

    @Update("update user set user_name=#{userName}, user_password=#{userPassword}, job_number=#{jobNumber}, sex=#{sex}, age=#{age}, email=#{email},phone=#{phone}, user_type=#{userType} where id=#{id}")
    public void edit(User user);

    @Delete("delete from user where id=#{id}")
    public void delete(Integer id);
}
