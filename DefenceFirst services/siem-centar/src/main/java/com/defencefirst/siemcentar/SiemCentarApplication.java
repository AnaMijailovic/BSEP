package com.defencefirst.siemcentar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SiemCentarApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SiemCentarApplication.class, args);
		System.out.println("\n JAVA HOME: " + System.getProperty("java.home"));

	}

}
