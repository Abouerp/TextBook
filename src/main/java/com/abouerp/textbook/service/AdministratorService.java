package com.abouerp.textbook.service;

import com.abouerp.textbook.dao.AdministratorRepository;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.QAdministrator;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public Administrator save(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public void delete(Integer id) {
        administratorRepository.deleteById(id);
    }

    public Optional<Administrator> findById(Integer id) {
        return administratorRepository.findById(id);
    }

    public Page<Administrator> findAll(Administrator administrator, Pageable pageable) {
        QAdministrator qAdministrator = QAdministrator.administrator;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (administrator != null && administrator.getUsername() != null && !administrator.getUsername().isEmpty()) {
            booleanBuilder.and(qAdministrator.username.containsIgnoreCase(administrator.getUsername()));
        }
        if (administrator != null && administrator.getEmail() != null && !administrator.getEmail().isEmpty()) {
            booleanBuilder.and(qAdministrator.email.containsIgnoreCase(administrator.getEmail()));
        }
        if (administrator != null && administrator.getMobile() != null && !administrator.getMobile().isEmpty()) {
            booleanBuilder.and(qAdministrator.mobile.containsIgnoreCase(administrator.getMobile()));
        }
        if (administrator != null && administrator.getCollege() != null && !administrator.getCollege().isEmpty()) {
            booleanBuilder.and(qAdministrator.college.containsIgnoreCase(administrator.getCollege()));
        }
        if (administrator != null && administrator.getSex() != null && !administrator.getSex().isEmpty()) {
            booleanBuilder.and(qAdministrator.sex.containsIgnoreCase(administrator.getSex()));
        }
        if (administrator != null && administrator.getEnabled() != null) {
            booleanBuilder.and(qAdministrator.enabled.eq(administrator.getEnabled()));
        }
        return administratorRepository.findAll(booleanBuilder, pageable);
    }

    public List<Administrator> saveAll(List<Administrator> administratorList) {
        return administratorRepository.saveAll(administratorList);
    }

    public List<Administrator> findByIdIn(List<Integer> ids){
        return administratorRepository.findByIdIn(ids);
    }
}
