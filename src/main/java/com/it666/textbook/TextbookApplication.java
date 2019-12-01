package com.it666.textbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(value = "com.it666.textbook.dao")
@SpringBootApplication
public class TextbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(TextbookApplication.class, args);
    }

}
