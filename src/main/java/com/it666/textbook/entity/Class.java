package com.it666.textbook.entity;

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
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private Integer peopleNumber;
}
