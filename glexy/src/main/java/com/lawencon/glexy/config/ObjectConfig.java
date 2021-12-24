package com.lawencon.glexy.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.lawencon.util.ExcelUtil;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@ComponentScan(basePackages = "com.lawencon")
//@Profile("kkkk")
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
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {

		return new BCryptPasswordEncoder();

	}
	
	@Bean
	public Executor executor() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		DelegatingSecurityContextExecutor delegatingExecutorCustom = new DelegatingSecurityContextExecutor(
				executorService);
		return delegatingExecutorCustom;
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {

			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods(HttpMethod.POST.name(),
						HttpMethod.GET.name(), HttpMethod.PATCH.name(), HttpMethod.DELETE.name(),
						HttpMethod.PUT.name());
			}
		};
	}

}
