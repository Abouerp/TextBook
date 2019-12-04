package com.it666.textbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.sql.In;

import java.io.Serializable;
import java.util.Date;

/**
 * 开课班级
 *
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Class implements Serializable {

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
     * 外键：一对多 属于哪个申请表
     */
    private Integer textbookId;
}
