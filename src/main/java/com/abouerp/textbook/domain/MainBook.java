package com.abouerp.textbook.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Abouerp
 */
@Data
@Accessors(chain = true)
@Entity
@Table
public class MainBook implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    /**
     * 书号ISBN
     */
    private String isbn;
}
