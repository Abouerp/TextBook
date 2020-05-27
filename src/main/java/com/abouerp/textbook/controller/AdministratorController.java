package com.abouerp.textbook.controller;

import com.abouerp.textbook.bean.ResultBean;
import com.abouerp.textbook.dao.AdministratorRepository;
import com.abouerp.textbook.domain.Administrator;
import com.abouerp.textbook.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/user")
public class AdministratorController {

    private final AdministratorRepository administratorRepository;

    public AdministratorController(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @GetMapping("/me")
    public ResultBean<Map<String, Object>> me(@AuthenticationPrincipal Object object, CsrfToken csrfToken) {
        Map<String, Object> map = new HashMap<>(2);
        if (object instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) object;
            Administrator administrator = administratorRepository.getOne(userPrincipal.getId());
//            map.put("user", AdministratorMapper.INSTANCE.toDTO(administrator));
        } else {
//            map.put("user", new AdministratorDTO());
        }
        map.put("_csrf", csrfToken);
        return ResultBean.ok(map);
    }
}
