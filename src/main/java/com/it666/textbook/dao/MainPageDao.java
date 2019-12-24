package com.it666.textbook.dao;

import com.it666.textbook.domain.MainBook;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Abouerp
 */
@Repository
public interface MainPageDao {

    /**
     * 根据学院来查看
     *
     * @param college
     * @return
     */
    @Select("select * from book where college = #{college}")
    public List<MainBook> findByCollege(String college);

    @Select("select * from book where id = #{id}")
    public MainBook findByBookId(Integer id);
}
