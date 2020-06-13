package com.abouerp.textbook.dao;

import com.abouerp.textbook.domain.TextBook;
import com.abouerp.textbook.entity.StatisticsPublisherRsp;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

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


}
