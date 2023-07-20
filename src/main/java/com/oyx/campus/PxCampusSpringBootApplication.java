package com.oyx.campus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan(basePackages = "com.oyx.campus.mapper")
@SpringBootApplication
public class PxCampusSpringBootApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context =SpringApplication.run(PxCampusSpringBootApplication.class, args);
        System.out.println("test");
        String property = context.getEnvironment().getProperty("server.port");
        System.out.println(property);
    }

}
