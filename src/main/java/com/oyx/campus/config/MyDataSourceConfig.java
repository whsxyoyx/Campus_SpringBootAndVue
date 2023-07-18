package com.oyx.campus.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author oyx
 * @create 2023-07-18 9:55
 */
@Configuration(proxyBeanMethods = true)
public class MyDataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource",ignoreUnknownFields = true)
    @ConditionalOnProperty(prefix = "spring.datasource" ,name="type", havingValue = "com.alibaba.druid.pool.DruidDataSource")
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }
}
