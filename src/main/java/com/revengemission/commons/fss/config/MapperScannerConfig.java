package com.revengemission.commons.fss.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.revengemission.commons.fss.persistence.mapper")
public class MapperScannerConfig {

}

