package com.abouerp.textbook.service;

import com.abouerp.textbook.dao.MainBookRepository;
import com.abouerp.textbook.domain.MainBook;
import com.abouerp.textbook.domain.QMainBook;
import com.abouerp.textbook.exception.MainBookNotFoundException;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class MainBookService {

    private final MainBookRepository mainBookRepository;

    public MainBookService(MainBookRepository mainBookRepository) {
        this.mainBookRepository = mainBookRepository;
    }

    public Page<MainBook> findAll(Pageable pageable, MainBook mainBook){
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QMainBook qMainBook = QMainBook.mainBook;
        if (mainBook == null) {
            return mainBookRepository.findAll(pageable);
        }
        if (mainBook.getCollege()!=null && !mainBook.getCollege().isEmpty()){
            booleanBuilder.and(qMainBook.college.containsIgnoreCase(mainBook.getCollege()));
        }
        return mainBookRepository.findAll(booleanBuilder,pageable);
    }

    public MainBook findByBookId(Integer id) {
        return mainBookRepository.findById(id).orElseThrow(MainBookNotFoundException::new);
    }

}
