package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.dao.ClassInformationRepository;
import com.abouerp.textbook.domain.ClassInformation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/class")
public class ClassInfoController {

    private final ClassInformationRepository classInformationRepository;

    public ClassInfoController(ClassInformationRepository classInformationRepository) {
        this.classInformationRepository = classInformationRepository;
    }

    @PostMapping
    public ResultBean<List<Integer>> save(
            @RequestBody List<ClassInformation> classInformationList) {
        return ResultBean.ok(classInformationRepository.saveAll(classInformationList)
                .stream()
                .map(ClassInformation::getId)
                .collect(Collectors.toList())
        );
    }
}
