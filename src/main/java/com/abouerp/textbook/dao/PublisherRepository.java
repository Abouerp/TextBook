package com.abouerp.textbook.dao;

import com.abouerp.textbook.domain.TextBook;
import com.abouerp.textbook.entity.StatisticsPublisherRsp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Abouerp
 */
public interface PublisherRepository extends JpaRepository<TextBook,Integer> {
    @Query(value = "select publisher ,count(publisher) as number from textbook  group by publisher order by number"
            ,nativeQuery = true)
    List<Object[]> counts();
}
