package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.domain.Authority;
import com.abouerp.textbook.domain.Role;
import com.abouerp.textbook.exception.RoleNotFoundException;
import com.abouerp.textbook.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.EnumMap;
import java.util.Set;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/authority")
public class AuthorityController {

    private final RoleService roleService;

    public AuthorityController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('AUTHORITY_READ')")
    public ResultBean<EnumMap<Authority, String>> getAll() {
        EnumMap<Authority, String> map = Authority.mappings;
        return ResultBean.ok(map);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public ResultBean<Role> updateRoleAuthority(@PathVariable Integer id, @RequestBody Set<Authority> authorities) {
        Role role = roleService.findById(id).orElseThrow(RoleNotFoundException::new);
        role.setAuthorities(authorities);
        return ResultBean.ok(roleService.save(role));
    }
}
