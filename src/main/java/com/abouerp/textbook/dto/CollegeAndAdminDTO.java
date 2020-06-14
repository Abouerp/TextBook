package com.abouerp.textbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统计各个学院教师的人数
 *
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollegeAndAdminDTO {

    private Integer collegeId;
    /**
     * 学院名称
     */
    private String collegeName;
    /**
     * 学院教师人数
     */
    private Integer totalNumber;
}
