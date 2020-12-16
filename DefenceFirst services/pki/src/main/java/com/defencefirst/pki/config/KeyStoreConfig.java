package com.defencefirst.pki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

  
@Data 
@Configuration
public class KeyStoreConfig {

	@Value("${keystore.type}")
	private  String type;
	
	@Value("${keystore.provider}")
	private  String provider; 

	@Value("${keystore.path}")
	private String path;
 
	@Value("${keystore.password}")
	private String password;

}
