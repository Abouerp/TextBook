package com.it666.textbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

/**
 * 相当于springmvc.xml文件
 *
 * @author Abouerp
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("redirect:/login");
//    }

//    @Bean(name = "multipartResolver")
//    public MultipartResolver multipartResolver() throws IOException {
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("UTF-8");
//        resolver.setMaxInMemorySize(10240);     //最大内存大小 (10240)
//        resolver.setMaxUploadSize(50*1024*1024);
//        return resolver;
//    }
}
