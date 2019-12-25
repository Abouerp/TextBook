package com.it666.textbook.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 教师 or 秘书
 *
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户名称
     */
    @NotNull
    private String userName;
    /**
     * 登陆密码
     */
    private String userPassword;
    /**
     * 工号
     */
    private String jobNumber;
    /**
     * 性别
     */
    private String sex;
    /**
     * 学院
     */
    private String college;
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式错误")
    private String email;
    /**
     * 电话
     */
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String phone;
    /**
     * 用户类型 1教师 2秘书
     */
    private Integer userType;
    /**
     * 教师名称
     */
    private String realName;
    /**
     * 是否启动任务  0不启动，1启动
     */
    private Integer startTask;
}
