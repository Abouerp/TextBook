package com.it666.textbook.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    private String userName;
    /**
     * 登陆密码
     */
    private String userPassword;
}

