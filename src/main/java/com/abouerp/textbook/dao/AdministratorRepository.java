package com.abouerp.textbook.dao;

import com.abouerp.textbook.domain.Administrator;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Abouerp
 */
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Integer> , QuerydslPredicateExecutor<Administrator> {
    @EntityGraph(attributePaths = "roles.authorities")
    Optional<Administrator> findFirstByUsername(String username);

    List<Administrator> findByIdIn(List<Integer> ids);

    @Transactional
    void deleteByCollegeId(Integer collegeId);

    List<Administrator> findByCollege_Id(Integer id);

    Integer countByCollege_Id(Integer collegeId);

}
