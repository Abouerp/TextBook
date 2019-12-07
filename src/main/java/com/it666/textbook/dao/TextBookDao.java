package com.it666.textbook.dao;

import com.it666.textbook.entity.TextBook;
import org.apache.ibatis.annotations.*;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Abouerp
 */

@Repository
public interface TextBookDao {

    @Select("select * from textbook where teacher_id= #{id}")
    public List<TextBook> findAll(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into textbook(course_name,course_time,title_name,publisher,author,title_date,version,ISBN,title_type,flag,phone,date,status,teacher_id)" +
            "values(#{courseName},#{courseTime},#{titleName},#{publisher},#{author},#{titleDate},#{version},#{isbn},#{titleType},#{flag},#{phone},#{date},#{status},#{teacherId})")
    public void save(TextBook textBook);

    @Update("update textbook set course_name=#{courseName},course_time=#{courseTime},title_name=#{titleName},publisher=#{publisher},author=#{author},title_date=#{titleDate}," +
            "version=#{version},ISBN=#{isbn},title_type=#{titleType},flag=#{flag},phone=#{phone},date=#{date},status=#{status},teacher_id=#{teacherId} where id = #{id}")
    public void updateTextbook(TextBook textBook);

    @Delete("delete from textbook where id = #{id}")
    public void delete(Integer id);

    @Select("select * from textbook where teacher_id = #{id}")
    public List<TextBook> findByTeacherId(Integer id);

    @Select("select * from textbook where id = #{id}")
    public TextBook findByTextBookById(Integer id);

    @Delete("delete from textbook where id = #{id}")
    public void deleteById(Integer id);

    @Update("update textbook set review_date=#{date} where id=#{id}")
    public void updateReviewDate(Date date, Integer id);

    @Select("select * from textbook where teacher_id=#{teacherId} and status=#{status}")
    public List<TextBook> findByTeacherIdAndStatus(Integer teacherId, Integer status);
}
