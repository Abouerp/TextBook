package com.it666.textbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 申请表历史api 回复
 *
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextBookHistoryRsp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 申请表的id
     */
    private Integer id;
    /**
     * 申请人名字
     */
    private String teacherName;
    /**
     * 教材名称
     */
    private String titleName;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 申请日期
     */
    private Date date;
    /**
     * 审核日期
     */
    private Date reviewDate;
    /**
     * 状态 1待提交 2待审核 3已审核 4审核不通过
     */
    private Integer status;
}
