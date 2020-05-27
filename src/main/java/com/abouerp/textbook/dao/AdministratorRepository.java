package com.abouerp.textbook.dao;

import com.abouerp.textbook.domain.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Abouerp
 */
@Repository
public interface AdministratorRepository extends JpaRepository<Administrator,Integer> {
}
