package com.hxx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shlcm
 */
@MapperScan("com.hxx.dao.mapper")
@SpringBootApplication
public class MyBlogApp {
    public static void main(String[] args) {
        SpringApplication.run(MyBlogApp.class,args);
    }
}
