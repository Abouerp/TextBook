package com.abouerp.textbook.dto;

import com.abouerp.textbook.domain.Role;
import lombok.Data;

import java.util.Set;

/**
 * @author Abouerp
 */
@Data
public class AdministratorDTO {

    private Integer id;
    private String username;
    private String mobile;
    private String email;
    private String sex;
    private String college;
    private String jobNumber;

    private Set<Role> roles;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}