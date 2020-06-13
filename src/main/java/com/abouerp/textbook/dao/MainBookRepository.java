package com.abouerp.textbook.dao;

import com.abouerp.textbook.domain.MainBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Abouerp
 */
@Repository
public interface MainBookRepository  extends JpaRepository<MainBook,Integer>, QuerydslPredicateExecutor<MainBook> {
}
