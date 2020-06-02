package com.abouerp.textbook.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 教材表
 *
 * @author Abouerp
 */
@Entity
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextBook implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课程学时
     */
    private Integer courseTime;
    /**
     * 教材名称
     */
    private String titleName;
    /**
     * 出版单位
     */
    private String publisher;
    /**
     * 编者
     */
    private String author;
    /**
     * 出版时间
     */
    private String titleDate;
    /**
     * 版次
     */
    private String version;
    /**
     * 书号ISBN
     */
    private String isbn;
    /**
     * 教材类型(教育部国家级规划教材、省部级规划教材、教育部国家级精品教材、省部级精品教材、无）
     */
    private String titleType;
    /**
     * 是否为近三年优质教材
     */
    private String flag;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 提交/保存时间
     */
    private Date date;
    /**
     * 状态 1待提交 2待审核 3已审核 4审核不通过
     */
    private Integer status;
    /**
     * list[包含的班级]
     */
    @OneToMany
    private Set<ClassInformation> classList;
    /**
     * 审核意见
     */
    private String reviewOpinion;
    /**
     * 审核通过时间
     */
    private Date reviewDate;
}
