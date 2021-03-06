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
import com.abouerp.textbook.service.TextBookService;
import com.abouerp.textbook.vo.AdministratorVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private final TextBookService textBookService;

    public AdministratorController(AdministratorRepository administratorRepository,
                                   RoleService roleService,
                                   PasswordEncoder passwordEncoder,
                                   AdministratorService administratorService,
                                   CollegeService collegeService,
                                   TextBookService textBookService) {
        this.administratorRepository = administratorRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.administratorService = administratorService;
        this.collegeService = collegeService;
        this.textBookService = textBookService;
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

    /**
     * 防止跨站请求伪造
     */
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
//            String srcPassword,
            String password
    ) {
        Administrator administrator = administratorService.findById(userPrincipal.getId())
                .orElseThrow(UserNotFoundException::new);
//        if (!passwordEncoder.matches(srcPassword, administrator.getPassword())) {
//            throw new PasswordNotMatchException();
//        }
        administrator.setPassword(passwordEncoder.encode(password));
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator)));
    }

    @PatchMapping("/{id}/password")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResultBean<AdministratorDTO> updateOtherPassword(
            @PathVariable Integer id,
//            String srcPassword,
            String password) {
        Administrator administrator = administratorService.findById(id)
                .orElseThrow(UserNotFoundException::new);
//        if (!passwordEncoder.matches(srcPassword, administrator.getPassword())) {
//            throw new PasswordNotMatchException();
//        }
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
    @PreAuthorize("hasAuthority('USER_CREATE')")
    public ResultBean<AdministratorDTO> save(@RequestBody @Valid AdministratorVO administratorVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ParamErrorException();
        }
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
    @PreAuthorize("hasAuthority('USER_UPDATE')")
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
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public ResultBean delete(@PathVariable Integer id) {
        textBookService.deleteByAdministrator_Id(id);
        administratorService.delete(id);
        return ResultBean.ok();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResultBean<Page<AdministratorDTO>> findAll(
            @PageableDefault Pageable pageable,
            AdministratorVO administrator) {
        return ResultBean.ok(administratorService.findAll(administrator, pageable).map(AdministratorMapper.INSTANCE::toDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResultBean<AdministratorDTO> findById(@PathVariable Integer id) {
        Administrator administrator = administratorService.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administrator));
    }

    @PutMapping("/start-task")
    @PreAuthorize("hasAuthority('TEXTBOOK_TASK_UPDATE')")
    public ResultBean startOrDown(
            @RequestBody List<Integer> teacherIds,
            @RequestParam Boolean startTask) {
        administratorService.findByIdIn(teacherIds)
                .stream()
                .forEach(it -> {
                    it.setStartTask(startTask);
                    administratorService.save(it);
                });
        return ResultBean.ok();
    }

    @PutMapping("/start-task/{id}")
    @PreAuthorize("hasAuthority('TEXTBOOK_TASK_UPDATE')")
    public ResultBean startOrDownByCollege(
            @PathVariable Integer id,
            @RequestParam Boolean startTask) {
        College college = collegeService.findById(id).orElseThrow(CollegeNotFoundException::new);
        administratorService.findByCollegeId(college.getId())
                .stream()
                .forEach(it -> {
                    it.setStartTask(startTask);
                    administratorService.save(it);
                });
        return ResultBean.ok();
    }

    @GetMapping("/judge")
    public ResultBean<Boolean> judgeUserName(String username){
        Administrator administrator = administratorService.findFirstByUsername(username).orElse(null);
        if (administrator != null) {
            return ResultBean.ok(true);
        }else {
            return ResultBean.ok(false);
        }
    }

    @GetMapping("/test")
    public ResultBean tst(){
        return ResultBean.ok();
    }
}