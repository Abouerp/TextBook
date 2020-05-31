package com.abouerp.textbook.vo;

import com.abouerp.textbook.domain.Authority;
import lombok.Data;


import java.util.HashSet;
import java.util.Set;

/**
 * @author Abouerp
 */
@Data
public class RoleVO {

    private String name;

    private String description;

    private Set<Authority> authorities = new HashSet<>();

}
