package com.abouerp.textbook.service;

import com.abouerp.textbook.dao.CollegeRepository;
import com.abouerp.textbook.domain.College;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Abouerp
 */
@Service
public class CollegeService {

    private final CollegeRepository collegeRepository;

    public CollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    public Optional<College> findById(Integer id){
        return collegeRepository.findById(id);
    }

    public College save(College college) {
        return collegeRepository.save(college);
    }

    public void delete(Integer id){
        collegeRepository.deleteById(id);
    }

    public List<College> findAll(){
        return collegeRepository.findAll();
    }
}
