package com.it666.textbook.dao;

import com.it666.textbook.entity.TextBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Abouerp
 */
@Mapper
public interface TextBookDao {

    @Select("select * from textbook where teacher_id= #{id}")
    public List<TextBook> findAll(Integer id);




}
