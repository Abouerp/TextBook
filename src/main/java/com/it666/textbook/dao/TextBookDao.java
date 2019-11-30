package com.it666.textbook.dao;

import com.it666.textbook.entity.TextBook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

    @Insert("insert into textbook(class_name,class_time,text_bookName,publishing_firm,author,data,ISBN,class_message,teacher_name,status)values(#{},#{},#{},#{},#{},#{},#{})")
    public void save(TextBook textBook);




}
