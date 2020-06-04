package com.abouerp.textbook.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Abouerp
 */
@Data
public class AdministratorVO {
    private String username;
    private String password;
    private String mobile;
    private String email;
    private String sex;
    private String college;
    private String jobNumber;
    private List<Integer> role;
    private Boolean startTask = false;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;
}
