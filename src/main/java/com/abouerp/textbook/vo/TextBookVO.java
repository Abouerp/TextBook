package com.abouerp.textbook.vo;


import lombok.Data;


import java.util.Date;
import java.util.List;

/**
 * @author Abouerp
 */
@Data
public class TextBookVO {

    private String courseName;
    private Integer courseTime;
    private String titleName;
    private String publisher;
    private String author;
    private String titleDate;
    private String version;
    private String isbn;
    private String titleType;
    private String flag;
    private String phone;
    private Date date;
    private Integer status;
    private List<Integer> classLists;
    private String reviewOpinion;
    private Date reviewDate;

}
