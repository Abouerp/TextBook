package com.it666.textbook.domain;

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
public class MainBook implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 学院
     */
    private String college;
    /**
     * 当当地址
     */
    private String bookUrl;
    /**
     * 图片地址
     */
    private String imageUrl;
    /**
     * 图书名字
     */
    private String titleName;
    /**
     * 作者
     */
    private String author;
    /**
     * 出版社
     */
    private String publisher;
    /**
     * 出版时间
     */
    private String titleDate;
}
