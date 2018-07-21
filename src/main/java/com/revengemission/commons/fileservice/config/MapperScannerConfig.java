package com.revengemission.commons.fileservice.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.revengemission.commons.fileservice.persistence.mapper")
public class MapperScannerConfig {

}

