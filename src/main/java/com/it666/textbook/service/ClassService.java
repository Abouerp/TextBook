package com.it666.textbook.service;

import com.it666.textbook.dao.ClassInformationDao;
import com.it666.textbook.entity.ClassInformation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abouerp
 */
@Service
public class ClassService {

    private final ClassInformationDao classDao;

    public ClassService(ClassInformationDao classDao) {
        this.classDao = classDao;
    }

    public List<Integer> save(List<ClassInformation> classMessage) {
        List<Integer> list = new ArrayList<>();
        for (ClassInformation c : classMessage) {
            classDao.save(c);
            list.add(c.getId());
        }
//        classDao.save(classMessage);
        return list;
    }

    public ClassInformation edit(ClassInformation classMessage) {
        classDao.update(classMessage);
        return classMessage;
    }

    public ClassInformation findById(Integer id) {
        return classDao.findById(id);
    }

    public void updateTeacherId(Integer id, Integer textbookId) {
        classDao.updateTextbookId(id, textbookId);
    }

    public List<ClassInformation> findByTextBookId(Integer id) {
        return classDao.findByTextBookId(id);
    }

    public Integer deleteByTextBookId(Integer id) {
        return classDao.deleteByTextBookId(id);
    }
}
