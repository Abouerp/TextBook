package com.abouerp.textbook.service;

import com.abouerp.textbook.dao.AdministratorRepository;
import com.abouerp.textbook.domain.Administrator;
import org.springframework.stereotype.Service;

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

    public Administrator save(Administrator administrator){
        return administratorRepository.save(administrator);
    }

    public void delete(Integer id){
        administratorRepository.deleteById(id);
    }

    public Optional<Administrator> findById(Integer id){
        return administratorRepository.findById(id);
    }

    
}
