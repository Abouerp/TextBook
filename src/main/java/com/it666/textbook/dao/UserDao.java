package com.it666.textbook.dao;

import com.it666.textbook.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Abouerp
 */

@Repository
public interface UserDao {

    @Select("select * from user")
    public List<User> findAll();

    @Select("select * from user where user_name = #{userName}")
    public User findUserByUsername(String userName);

    @Select("select * from user where id = #{id}")
    public User findUserById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user(user_name,user_password,job_number,sex,college,email,phone,user_type)values(#{userName},#{userPassword},#{jobNumber},#{sex},#{college},#{email},#{phone},#{userType})")
    public int save(User user);

    @Update("update user set user_name=#{userName}, user_password=#{userPassword}, job_number=#{jobNumber}, sex=#{sex}, college=#{college}, email=#{email}, phone=#{phone}, user_type=#{userType}, real_name=#{realName} where id=#{id}")
    public void edit(User user);

    @Delete("delete from user where id=#{id}")
    public void delete(Integer id);
}
