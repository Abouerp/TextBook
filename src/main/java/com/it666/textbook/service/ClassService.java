package com.it666.textbook.service;

import com.it666.textbook.dao.ClassDao;
import com.it666.textbook.entity.Class;
import org.springframework.stereotype.Service;

/**
 * @author Abouerp
 */
@Service
public class ClassService {

    private final ClassDao classDao;

    public ClassService(ClassDao classDao) {
        this.classDao = classDao;
    }

    public Class sava(Class classMessage) {
        classDao.save(classMessage);
        return classMessage;
    }

    public Class edit(Class classMessage){
        classDao.update(classMessage);
        return classMessage;
    }

    public Class findById(Integer id){
         return classDao.findById(id);
    }

    public void updateTeacherId(Integer id, Integer textbookId) {
        classDao.updateTextbookId(id,textbookId);
    }
}
