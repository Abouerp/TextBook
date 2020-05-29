package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.dao.AdministratorRepository;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.domain.Role;
import com.abouerp.textbook.dto.AdministratorDTO;
import com.abouerp.textbook.mapper.AdministratorMapper;
import com.abouerp.textbook.security.UserPrincipal;
import com.abouerp.textbook.service.AdministratorService;
import com.abouerp.textbook.service.RoleService;
import com.abouerp.textbook.vo.AdministratorVO;
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

    @PostMapping
    public ResultBean<AdministratorDTO> save(@RequestBody AdministratorVO administratorVO){
        Set<Role> roles =  roleService.findByIdIn(administratorVO.getRole()).stream().collect(Collectors.toSet());
        administratorVO.setPassword(passwordEncoder.encode(administratorVO.getPassword()));
        Administrator administrator = AdministratorMapper.INSTANCE.toAdmin(administratorVO);
        administrator.setRoles(roles);
        return ResultBean.ok(AdministratorMapper.INSTANCE.toDTO(administratorService.save(administrator)));
    }
}
