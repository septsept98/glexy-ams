package com.lawencon.glexy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	
	public static void main(String[] args) {
		System.out.println("Masukkan : ");
		System.out.println("Masukkan Huruf : ");
		System.out.println("Masukkan symbol : ");
		System.out.println("Masukkan Alamat : ");
		SpringApplication.run(App.class, args);
	}
}
