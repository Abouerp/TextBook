package com.abouerp.textbook.dao;

import com.abouerp.textbook.domain.ClassInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Abouerp
 */
@Repository
public interface ClassInformationRepository extends JpaRepository<ClassInformation,Integer> {

    List<ClassInformation> findByIdIn(List<Integer> ids);
}
