package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.domain.College;
import com.abouerp.textbook.dto.CollegeAndAdminDTO;
import com.abouerp.textbook.exception.CollegeNotFoundException;
import com.abouerp.textbook.mapper.CollegeMapper;
import com.abouerp.textbook.service.AdministratorService;
import com.abouerp.textbook.service.CollegeService;
import com.abouerp.textbook.vo.CollegeVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @PreAuthorize("hasAuthority('COLLEGE_CREATE')")
    public ResultBean<College> save(@RequestBody CollegeVO collegeVO) {
        return ResultBean.ok(collegeService.save(CollegeMapper.INSTANCE.toCollege(collegeVO)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('COLLEGE_UPDATE')")
    public ResultBean<College> update(
            @PathVariable Integer id,
            @RequestBody CollegeVO collegeVO) {
        College college = collegeService.findById(id).orElseThrow(CollegeNotFoundException::new);
        return ResultBean.ok(collegeService.save(update(college, collegeVO)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('COLLEGE_DELETE')")
    public ResultBean delete(@PathVariable Integer id) {
        administratorService.deleteByCollegeId(id);
        collegeService.delete(id);
        return ResultBean.ok();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('COLLEGE_READ')")
    public ResultBean<List<College>> findAll() {
        return ResultBean.ok(collegeService.findAll());
    }

    @GetMapping("/user-number")
    public ResultBean<List<CollegeAndAdminDTO>> countNumber(){
        List<College> colleges = collegeService.findAll();
        List<CollegeAndAdminDTO> collegeAndAdminDTOList = new ArrayList<>();
        for (College college:colleges){
            CollegeAndAdminDTO collegeAndAdminDTO = new CollegeAndAdminDTO()
                    .setCollegeId(college.getId())
                    .setCollegeName(college.getName())
                    .setTotalNumber(administratorService.countByCollege_Id(college.getId()));
            collegeAndAdminDTOList.add(collegeAndAdminDTO);
        }
        return ResultBean.ok(collegeAndAdminDTOList);
    }
}
