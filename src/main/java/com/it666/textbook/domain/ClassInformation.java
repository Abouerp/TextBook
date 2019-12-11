package com.it666.textbook.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 开课班级
 *
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClassInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 年级
     */
    private String grade;
    /**
     * 专业
     */
    private String subject;
    /**
     * 专业人数
     */
    private Integer number;
    /**
     * 开课时间
     */
    private String date;
    /**
     * 必修/选修
     */
    private String classType;
    /**
     * 开课学期
     */
    private String semester;
    /**
     * 外键：一对多 属于哪个申请表
     */
    private Integer textbookId;
}
