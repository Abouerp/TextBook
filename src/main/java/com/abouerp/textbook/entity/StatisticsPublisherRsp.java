package com.abouerp.textbook.entity;

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
public class StatisticsPublisherRsp implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 出版社名字
     */
    private String publisher;
    /**
     * 对应的数量
     */
    private Integer number;
}
