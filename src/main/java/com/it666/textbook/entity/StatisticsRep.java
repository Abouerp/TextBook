package com.it666.textbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统计代提交，代审核，已审核 全部记录 实体类
 *
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticsRep implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 待提交
     */
    private Integer unSubmit;
    /**
     * 未审核
     */
    private Integer unReview;
    /**
     * 已审核
     */
    private Integer review;
    /**
     * 全部记录
     */
    private Integer count;
}
