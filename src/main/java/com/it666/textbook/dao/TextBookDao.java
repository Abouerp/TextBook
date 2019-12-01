package com.it666.textbook.dao;

import com.it666.textbook.entity.TextBook;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Abouerp
 */
@Mapper
@Component
public interface TextBookDao {

    @Select("select * from textbook where teacher_id= #{id}")
    public List<TextBook> findAll(Integer id);

    @Insert("insert into textbook(course_name,course_time,title_time,publisher,author,date,ISBN,status,class_id,teacher_id)values(#{courseName},#{courseTime},#{titleName},#{publisher},#{author},#{date},#{ISBN},#{status},#{classId},#{teacherId})")
    @Options(keyColumn = "id",useGeneratedKeys = true)
    public void save(TextBook textBook);

    @Update("update textbook set course_name=#{courseName},course_time=#{courseTime},title_time=#{titleName},publisher=#{publisher},author=#{author},date=#{date},ISBN=#{ISBN},status=#{status},class_id=#{classId},teacher_id=#{teacherId} where id = #{id}")
    public void edit(TextBook textBook);


}
