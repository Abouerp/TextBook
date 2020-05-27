//package com.abouerp.textbook.dao;
//
//import com.abouerp.textbook.domain.User;
//import com.abouerp.textbook.entity.StatisticsCollegeRsp;
//import org.apache.ibatis.annotations.*;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * @author Abouerp
// */
//
//@Repository
//public interface UserDao {
//
//    @Select("select * from user")
//    public List<User> findAll();
//
//    @Select("select * from user where user_name = #{userName}")
//    public User findUserByUsername(String userName);
//
//    @Select("select * from user where id = #{id}")
//    public User findUserById(Integer id);
//
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    @Insert("insert into user(user_name,user_password,job_number,sex,college,email,phone,user_type,real_name, start_task)values(#{userName},#{userPassword},#{jobNumber},#{sex},#{college},#{email},#{phone},#{userType},#{realName},#{startTask})")
//    public int save(User user);
//
//    @Update("update user set user_name=#{userName}, user_password=#{userPassword}, job_number=#{jobNumber}, sex=#{sex}, college=#{college}, email=#{email}, phone=#{phone}, user_type=#{userType}, real_name=#{realName}, start_task=#{startTask} where id=#{id}")
//    public void edit(User user);
//
//    @Delete("delete from user where id=#{id}")
//    public Integer delete(Integer id);
//
//    /**
//     * 根据 学院 或者 是否启动任务 可以为空 来获取教师列表   动态sql
//     *
//     * @param startTask 任务状态
//     * @param userType  用户类型
//     * @param college   学院名称
//     * @return
//     */
//    @Select("<script>                                                           "
//            + " select * from user                                                "
//            + " <where>                                                           "
//            + "     <if test='startTask != null '>                                "
//            + "         and start_task = #{startTask}                             "
//            + "     </if>                                                         "
//            + "     <if test='userType != null '>                                 "
//            + "         and user_type = #{userType}                               "
//            + "     </if>                                                         "
//            + "     <if test='college != null and college != &quot;&quot;'>       "
//            + "         and  college=#{college}                                   "
//            + "     </if>                                                         "
//            + " </where>                                                          "
//            + "</script>")
//    public List<User> findUserByStartTaskAndCollege(Integer startTask, Integer userType, String college);
//
//    @Select("select * from user where user_type=#{userType}")
//    public List<User> findUserByType(Integer userType);
//
//    @Select("select college as collegeName,count(college) as totalNumber from user where user_type=1 group by college")
//    public List<StatisticsCollegeRsp> findStatisticsCollege();
//
//    /**
//     * 指定教师启动填写申请表任务
//     * @param teacherId
//     * @param startTask
//     * @return
//     */
//    @Update("update user set start_task=#{startTask} where id = #{teacherId}")
//    public Integer updateUserStartTask(Integer teacherId, Integer startTask);
//
//    /**
//     * 指定某个学院内所有教师启动填写申请表任务
//     * @param college
//     * @param startTask
//     * @return
//     */
//    @Update("update user set start_task=#{startTask} where college=#{college}")
//    public Integer updateUserStartTaskByCollege(String college, Integer startTask);
//}
