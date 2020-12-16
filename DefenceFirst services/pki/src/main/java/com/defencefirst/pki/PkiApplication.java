package com.defencefirst.pki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication() 
@EnableScheduling
public class PkiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PkiApplication.class, args); 
		System.out.println("\n JAVA HOME: " + System.getProperty("java.home"));
	} 

} 
