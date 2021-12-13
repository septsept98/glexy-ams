package com.lawencon.glexy;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.lawencon.glexy.dao.StatusTransactionDao;
import com.lawencon.glexy.dao.StatusTransactionDaoImpl;
import com.lawencon.glexy.model.StatusTransaction;

@SpringBootApplication
@ComponentScan(basePackages = "com.lawencon")
public class App {
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
