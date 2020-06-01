package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.dao.AdministratorRepository;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.Role;
import com.abouerp.textbook.dto.AdministratorDTO;
import com.abouerp.textbook.exception.PasswordNotMatchException;
import com.abouerp.textbook.exception.UserNotFoundException;
import com.abouerp.textbook.mapper.AdministratorMapper;
import com.abouerp.textbook.security.UserPrincipal;
import com.abouerp.textbook.service.AdministratorService;
import com.abouerp.textbook.service.RoleService;
import com.abouerp.textbook.vo.AdministratorVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

    public AdministratorController(AdministratorRepository administratorRepository,
                                   RoleService roleService,
                                   PasswordEncoder passwordEncoder,
                                   AdministratorService administratorService) {
        this.administratorRepository = administratorRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.administratorService = administratorService;
    }

    private static Administrator update(Administrator administrator, AdministratorVO adminVO) {
        if (adminVO != null && adminVO.getUsername() != null) {
            administrator.setUsername(adminVO.getUsername());
        }
        if (adminVO != null && adminVO.getEmail() != null) {
            administrator.setEmail(adminVO.getEmail());
        }
        if (adminVO != null && adminVO.getEnabled() != null) {
            administrator.setEnabled(adminVO.getEnabled());
        }
        if (adminVO != null && adminVO.getCollege() != null) {
            administrator.setCollege(adminVO.getCollege());
        }
        if (adminVO != null && adminVO.getSex() != null) {
            administrator.setSex(adminVO.getSex());
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
        Set<Role> roles = roleService.findByIdIn(administratorVO.getRole()).stream().collect(Collectors.toSet());
        administratorVO.setPassword(passwordEncoder.encode(administratorVO.getPassword()));
        Administrator administrator = AdministratorMapper.INSTANCE.toAdmin(administratorVO);
        administrator.setRoles(roles);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator)));
    }

    @PutMapping("/{id}")
    public ResultBean<Administrator> update(
            @PathVariable Integer id,
            @RequestBody AdministratorVO administratorVO) {
        Administrator administrator = administratorService.findById(id).orElseThrow(UserNotFoundException::new);
        return ResultBean.ok(administratorService.save(update(administrator, administratorVO)));
    }

    @DeleteMapping("/{id}")
    public ResultBean delete(@PathVariable Integer id) {
        administratorService.delete(id);
        return ResultBean.ok();
    }

    @GetMapping
    public ResultBean<Page<AdministratorDTO>> findAll(
            @PageableDefault Pageable pageable,
            Administrator administrator) {
        return ResultBean.ok(administratorService.findAll(administrator, pageable).map(AdministratorMapper.INSTANCE::toDTO));
    }

    @GetMapping("/{id}")
    public ResultBean<AdministratorDTO> findById(@PathVariable Integer id) {
        Administrator administrator = administratorService.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administrator));
    }

    @GetMapping("/test")
    public ResultBean<Object> test(){
        return ResultBean.ok("我可以了！！！！");
    }
}
