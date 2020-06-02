package com.abouerp.textbook.vo;

import com.abouerp.textbook.domain.ClassInformation;
import lombok.Data;

import javax.persistence.OneToMany;
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
    private List<Integer> classList;
    private String reviewOpinion;
    private Date reviewDate;

}
