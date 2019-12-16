package com.it666.textbook.entity;

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
public class StatisticsCollegeRsp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 学院名称
     */
    private String collegeName;
    /**
     * 学院教师人数
     */
    private Integer totalNumber;
}
