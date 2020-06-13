package com.abouerp.textbook.dto;

import lombok.Data;

/**
 * @author Abouerp
 */
@Data
public class StatisticsPublisherDTO {
    /**
     * 出版社名字
     */
    private String publisher;
    /**
     * 对应的数量
     */
    private Integer number;
}
