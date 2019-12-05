package com.it666.textbook.dao;

import com.it666.textbook.entity.Class;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Abouerp
 */
@Repository
public interface ClassDao {

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into class(grade,subject,number,date,class_type,textbook_id)values(#{grade},#{subject},#{number},#{date},#{classType},#{textbookId})")
    public void save(Class classMessage);

    @Select("select * from class where id=#{id}")
    public Class findById(Integer id);

    @Update("update  class set textbook_id=#{textbookId} where id=#{id}")
    public void updateTextbookId(Integer id, Integer textbookId);

    @Update("update class set grade=#{grade}, subject=#{subject},number=#{number},date=#{date},class_type=#{classType},textbook_id=#{textbookId} where id=#{id}")
    public void update(Class classMessage);

    @Select("select * from class where textbook_id=#{id}")
    public List<Class> findByTextBookId(Integer id);

    @Delete("delete from class where textbook_id=#{id}")
    public Integer deleteByTextBookId(Integer id);
}
