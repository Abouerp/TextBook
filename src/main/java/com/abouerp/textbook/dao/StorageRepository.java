package com.abouerp.textbook.dao;

import com.abouerp.textbook.domain.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Abouerp
 */
@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {

    Optional<Storage> findBySha1(String sha1);
}
