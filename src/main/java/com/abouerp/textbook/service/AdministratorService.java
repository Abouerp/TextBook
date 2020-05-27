package com.abouerp.textbook.service;

import com.abouerp.textbook.dao.AdministratorRepository;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    
}
