package com.lawencon.glexy.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.lawencon.util.ExcelUtil;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@ComponentScan(basePackages = "com.lawencon")
public class ObjectConfig {
	
	
	@Bean("initTable")
	@Autowired
	public SpringLiquibase initTable(DataSource dataSource) {

		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("/db/init_table.sql");

		return liquibase;
	}

	@Bean
	@DependsOn("initTable")
	@Autowired
	public SpringLiquibase initData(DataSource dataSource) {

		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("/db/init_data.sql");

		return liquibase;
	}
	
	@Bean
	public ExcelUtil excelUtil() {
		return new ExcelUtil();
	}

}
