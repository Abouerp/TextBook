package com.abouerp.textbook.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Abouerp
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString
@Getter
@Setter
@Accessors(chain = true)
@Table
public class Administrator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private String email;
    private String mobile;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;
    private String sex;
    private String jobNumber;
    private Boolean startTask;
    private String realName;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
    @ManyToOne
    private College college;
    @CreatedBy
    private Integer createBy;
    @LastModifiedBy
    private Integer updateBy;
    @CreationTimestamp
    private Instant createTime;
    @UpdateTimestamp
    private Instant updateTime;
}
