package com.abouerp.textbook.dao;

import com.abouerp.textbook.domain.TextBook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Abouerp
 */
@Repository
public interface TextBookRepository extends JpaRepository<TextBook, Integer>, QuerydslPredicateExecutor<TextBook> {

    List<TextBook> findByAdministrator_Id(Integer id, Pageable pageable);

    List<TextBook> findByAdministratorIdIn(List<Integer> ids, Pageable pageable);

    List<TextBook> findByIdIn(List<Integer> ids);

    Integer countByStatusAndAdministrator_Id(Integer status, Integer id);

    Integer countByAdministrator_Id(Integer id);

    @Query(value = "select publisher ,count(publisher) as number from textbook  group by publisher order by number"
            ,nativeQuery = true)
    List<Object[]> counts();

    @Modifying
    @Transactional
    void deleteByAdministrator_Id(Integer id);
}
