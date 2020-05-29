package com.abouerp.textbook.dao;

import com.abouerp.textbook.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Abouerp
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findFirstByName(String name);

    List<Role> findByIdIn(List<Integer> roles);
}
