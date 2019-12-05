package com.it666.textbook.service;

import com.it666.textbook.dao.ClassDao;
import com.it666.textbook.entity.Class;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abouerp
 */
@Service
public class ClassService {

    private final ClassDao classDao;

    public ClassService(ClassDao classDao) {
        this.classDao = classDao;
    }

    public List<Integer> sava(List<Class> classMessage) {
        List<Integer> list = new ArrayList<>();
        for (Class c:classMessage){
            classDao.save(c);
            list.add(c.getId());
        }
//        classDao.save(classMessage);
        return list;
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

    public List<Class> findByTextBookId(Integer id){
        return classDao.findByTextBookId(id);
    }

    public Integer deleteByTextBookId(Integer id){
        return classDao.deleteByTextBookId(id);
    }
}
