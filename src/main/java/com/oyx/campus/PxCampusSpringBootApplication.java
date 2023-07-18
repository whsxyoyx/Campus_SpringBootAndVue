package com.oyx.campus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.oyx.campus.mapper")
@SpringBootApplication
public class PxCampusSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(PxCampusSpringBootApplication.class, args);
    }

}
