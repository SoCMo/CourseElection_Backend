package com.spring.CourseElection;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.spring.CourseElection.dao")
public class CourseelectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseelectionApplication.class, args);
    }

}
