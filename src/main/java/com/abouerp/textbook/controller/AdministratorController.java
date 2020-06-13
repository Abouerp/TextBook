package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.dao.AdministratorRepository;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.College;
import com.abouerp.textbook.domain.Role;
import com.abouerp.textbook.dto.AdministratorDTO;
import com.abouerp.textbook.exception.*;
import com.abouerp.textbook.mapper.AdministratorMapper;
import com.abouerp.textbook.security.UserPrincipal;
import com.abouerp.textbook.service.AdministratorService;
import com.abouerp.textbook.service.CollegeService;
import com.abouerp.textbook.service.RoleService;
import com.abouerp.textbook.vo.AdministratorVO;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/user")
public class AdministratorController {

    private final AdministratorRepository administratorRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AdministratorService administratorService;
    private final CollegeService collegeService;

    public AdministratorController(AdministratorRepository administratorRepository,
                                   RoleService roleService,
                                   PasswordEncoder passwordEncoder,
                                   AdministratorService administratorService,
                                   CollegeService collegeService) {
        this.administratorRepository = administratorRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.administratorService = administratorService;
        this.collegeService = collegeService;
    }

    private static Administrator update(Administrator administrator, AdministratorVO adminVO) {
        if (adminVO != null && adminVO.getRealName() != null) {
            administrator.setRealName(adminVO.getRealName());
        }
        if (adminVO != null && adminVO.getEmail() != null) {
            administrator.setEmail(adminVO.getEmail());
        }
        if (adminVO != null && adminVO.getSex() != null) {
            administrator.setSex(adminVO.getSex());
        }
        if (adminVO != null && adminVO.getMobile() != null) {
            administrator.setMobile(adminVO.getMobile());
        }
        if (adminVO != null && adminVO.getJobNumber() != null) {
            administrator.setJobNumber(adminVO.getJobNumber());
        }
        if (adminVO != null && adminVO.getRealName() != null) {
            administrator.setRealName(adminVO.getRealName());
        }
        if (adminVO != null && adminVO.getEnabled() != null) {
            administrator.setEnabled(adminVO.getEnabled());
        }
        return administrator;
    }

    @GetMapping("/me")
    public ResultBean<Map<String, Object>> me(@AuthenticationPrincipal Object object, CsrfToken csrfToken) {
        Map<String, Object> map = new HashMap<>(2);
        if (object instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) object;
            Administrator administrator = administratorRepository.getOne(userPrincipal.getId());
            map.put("user", AdministratorMapper.INSTANCE.toDTO(administrator));
        } else {
            map.put("user", new AdministratorDTO());
        }
        map.put("_csrf", csrfToken);
        return ResultBean.ok(map);
    }

    @PatchMapping("/me/password")
    public ResultBean<AdministratorDTO> updatePassword(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            String srcPassword,
            String password
    ) {
        Administrator administrator = administratorService.findById(userPrincipal.getId())
                .orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(srcPassword, administrator.getPassword())) {
            throw new PasswordNotMatchException();
        }
        administrator.setPassword(passwordEncoder.encode(password));
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator)));
    }

    @PatchMapping("/{id}/password")
    public ResultBean<AdministratorDTO> updateOtherPassword(
            @PathVariable Integer id,
            String srcPassword,
            String password) {
        Administrator administrator = administratorService.findById(id)
                .orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(srcPassword, administrator.getPassword())) {
            throw new PasswordNotMatchException();
        }
        administrator.setPassword(passwordEncoder.encode(password));
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator)));
    }

    @PutMapping("/me")
    public ResultBean<AdministratorDTO> updateMyself(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody AdministratorVO adminVO
    ) {
        Administrator admin = administratorService.findById(userPrincipal.getId())
                .orElseThrow(UserNotFoundException::new);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(update(admin, adminVO))));
    }

    @PostMapping
    public ResultBean<AdministratorDTO> save(@RequestBody AdministratorVO administratorVO) {
        Administrator administrator = administratorService.findFirstByUsername(administratorVO.getUsername()).orElse(null);
        if (administrator != null) {
            throw new UserRepeatException();
        }
        Set<Role> roles = roleService.findByIdIn(administratorVO.getRole())
                .stream()
                .collect(Collectors.toSet());
        College college = collegeService.findById(administratorVO.getCollegeId()).orElseThrow(CollegeNotFoundException::new);
        administratorVO.setPassword(passwordEncoder.encode(administratorVO.getPassword()));
        administrator = AdministratorMapper.INSTANCE.toAdmin(administratorVO);
        administrator.setRoles(roles);
        administrator.setCollege(college);
        administrator.setStartTask(false);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator)));
    }

    @PutMapping("/{id}")
    public ResultBean<AdministratorDTO> update(
            @PathVariable Integer id,
            @RequestBody AdministratorVO administratorVO) {
        Administrator administrator = administratorService.findById(id).orElseThrow(UserNotFoundException::new);
        if (administratorVO != null && administratorVO.getCollegeId() != null) {
            College college = collegeService.findById(administratorVO.getCollegeId()).orElseThrow(CollegeNotFoundException::new);
            administrator.setCollege(college);
        }
        if (administratorVO != null && administratorVO.getRole() != null && !administratorVO.getRole().isEmpty()) {
            administrator.setRoles(roleService.findByIdIn(administratorVO.getRole()).stream().collect(Collectors.toSet()));
        }
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(update(administrator, administratorVO))));
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Integer id) {
        administratorService.delete(id);
        return ResultBean.ok();
    }

    @GetMapping
    public ResultBean<Page<AdministratorDTO>> findAll(
            @PageableDefault Pageable pageable,
            AdministratorVO administrator) {
        return ResultBean.ok(administratorService.findAll(administrator, pageable).map(AdministratorMapper.INSTANCE::toDTO));
    }

    @GetMapping("/{id}")
    public ResultBean<AdministratorDTO> findById(@PathVariable Integer id) {
        Administrator administrator = administratorService.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administrator));
    }

    @PutMapping("/start-task")
    public ResultBean startOrDown(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody List<Integer> teacherIds,
            @RequestParam Boolean startTask) {
        Administrator administrator = administratorService.findById(userPrincipal.getId())
                .orElseThrow(UserNotFoundException::new);
        administratorService.findByIdIn(teacherIds)
                .stream()
                .filter(it -> administrator.getCollege().equals(it.getCollege()))
                .forEach(it -> {
                    it.setStartTask(startTask);
                    administratorService.save(it);
                });
        return ResultBean.ok();
    }

    @PutMapping("/start-task/{id}")
    public ResultBean startOrDownByCollege(
            @PathVariable Integer id,
            @RequestParam Boolean startTask){
        College college = collegeService.findById(id).orElseThrow(CollegeNotFoundException::new);
        administratorService.findByCollegeId(college.getId())
                .stream()
                .forEach(it -> {
                    it.setStartTask(startTask);
                        administratorService.save(it);
                });
        return ResultBean.ok();
    }

    // todo - fix role
//    @PostMapping("/excel")
//    public ResultBean<String> importProcess(@RequestBody MultipartFile file) {
//        if (file == null) {
//            return ResultBean.ok("file is null");
//        }
//        try {
//            InputStream inputStream = file.getInputStream();
//            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
//            HSSFSheet sheet = workbook.getSheetAt(0);
//            int lastRowNum = sheet.getLastRowNum();
//            List<Administrator> administratorList = new ArrayList<>();
//            for (int i = 1; i <= lastRowNum; i++) {
//                HSSFRow row = sheet.getRow(i);
//                String username = row.getCell(0).getStringCellValue();
//                String password = row.getCell(1).getStringCellValue();
//                String jobNumber = row.getCell(2).getStringCellValue();
//
//                Administrator administrator = new Administrator().setUsername(username)
//                        .setPassword("{noop}" + password)
//                        .setJobNumber(jobNumber)
//                        .setAccountNonExpired(true)
//                        .setAccountNonLocked(true)
//                        .setCredentialsNonExpired(true)
//                        .setEnabled(true);
//                administratorList.add(administrator);
//            }
//            administratorService.saveAll(administratorList);
//            return new ResultBean<>("insert teacher success");
//        } catch (IOException e) {
//            throw new ExcelErrorException();
//        }
//    }
}
