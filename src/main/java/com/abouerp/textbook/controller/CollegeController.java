package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.College;
import com.abouerp.textbook.exception.CollegeNotFoundException;
import com.abouerp.textbook.mapper.CollegeMapper;
import com.abouerp.textbook.service.AdministratorService;
import com.abouerp.textbook.service.CollegeService;
import com.abouerp.textbook.vo.CollegeVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/college")
public class CollegeController {

    private final CollegeService collegeService;
    private final AdministratorService administratorService;

    public CollegeController(
            CollegeService collegeService,
            AdministratorService administratorService) {
        this.collegeService = collegeService;
        this.administratorService = administratorService;
    }

    private static College update(College college, CollegeVO collegeVO) {
        if (collegeVO != null && collegeVO.getName() != null) {
            college.setName(collegeVO.getName());
        }
        if (collegeVO != null && collegeVO.getDescription() != null) {
            college.setDescription(collegeVO.getDescription());
        }
        return college;
    }

    @PostMapping
    public ResultBean<College> save(@RequestBody CollegeVO collegeVO) {
        return ResultBean.ok(collegeService.save(CollegeMapper.INSTANCE.toCollege(collegeVO)));
    }

    @PutMapping("/{id}")
    public ResultBean<College> update(
            @PathVariable Integer id,
            @RequestBody CollegeVO collegeVO) {
        College college = collegeService.findById(id).orElseThrow(CollegeNotFoundException::new);
        return ResultBean.ok(collegeService.save(update(college, collegeVO)));
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Integer id) {
        administratorService.deleteByCollegeId(id);
        collegeService.delete(id);
        return ResultBean.ok();
    }

    @GetMapping
    public ResultBean<List<College>> findAll() {
        return ResultBean.ok(collegeService.findAll());
    }
}
