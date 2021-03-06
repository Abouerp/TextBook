package com.abouerp.textbook.service;

import com.abouerp.textbook.dao.AdministratorRepository;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.QAdministrator;
import com.abouerp.textbook.dto.CollegeAndAdminDTO;
import com.abouerp.textbook.vo.AdministratorVO;
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

    public Optional<Administrator> findFirstByUsername(String username) {
        return administratorRepository.findFirstByUsername(username);
    }

    public Page<Administrator> findAll(AdministratorVO administrator, Pageable pageable) {
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
        if (administrator != null && administrator.getSex() != null && !administrator.getSex().isEmpty()) {
            booleanBuilder.and(qAdministrator.sex.containsIgnoreCase(administrator.getSex()));
        }
        if (administrator != null && administrator.getCollegeId() != null) {
            booleanBuilder.and(qAdministrator.college.id.eq(administrator.getCollegeId()));
        }
        if (administrator != null && administrator.getStartTask() != null) {
            booleanBuilder.and(qAdministrator.startTask.eq(administrator.getStartTask()));
        }
        return administratorRepository.findAll(booleanBuilder, pageable);
    }

    public List<Administrator> saveAll(List<Administrator> administratorList) {
        return administratorRepository.saveAll(administratorList);
    }

    public List<Administrator> findByIdIn(List<Integer> ids) {
        return administratorRepository.findByIdIn(ids);
    }

    public void deleteByCollegeId(Integer collegeId) {
        administratorRepository.deleteByCollegeId(collegeId);
    }

    public List<Administrator> findByCollegeId(Integer id) {
        return administratorRepository.findByCollege_Id(id);
    }

    public Integer countByCollege_Id(Integer id){
        return administratorRepository.countByCollege_Id(id);
    }
}
