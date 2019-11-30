package com.it666.textbook.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 教材表
 *
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextBook implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 课程名称
     */
    private String className;
    /**
     * 课程学时
     */
    private Integer classTime;
    /**
     * 教材名称
     */
    private String textBookName;
    /**
     * 出版单位
     */
    private String publishingFirm;
    /**
     * 编者
     */
    private String author;
    /**
     * 出版时间
     */
    private Data data;
    /**
     * 书好ISBN
     */
    private String ISBN;
    /**
     * 开课班级 （年级，专业，人数）
     */
    private Class classMessage;
    /**
     * 选定教师名称
     */
    private String teacherName;
    /**
     * 状态 0保存 1提交
     */
    private Integer status;
    /**
     * 所属教师
     */
    private Integer teacherId;
}
