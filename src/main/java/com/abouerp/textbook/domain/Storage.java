package com.abouerp.textbook.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * 文件上传
 *
 * @author Abouerp
 */
@Entity
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Storage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 唯一标识
     */
    private String sha1;
    /**
     * 页面上传时的名称
     */
    private String originalFilename;
    /**
     * 文件类型
     */
    private String contentType;

}
