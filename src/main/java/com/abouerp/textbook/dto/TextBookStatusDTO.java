package com.abouerp.textbook.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
public class TextBookStatusDTO {

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
