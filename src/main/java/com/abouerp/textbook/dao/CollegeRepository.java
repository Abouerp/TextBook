package com.abouerp.textbook.dao;

import com.abouerp.textbook.domain.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Abouerp
 */
@Repository
public interface CollegeRepository extends JpaRepository<College, Integer> {
}
