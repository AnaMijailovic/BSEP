package com.defencefirst.pki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data 
@Configuration
public class RecommendedValidityConfig {
	
	@Value("${validity.root}")
	private int rootValidityYrs;
	
	@Value("${validity.intermediate}")
	private int intermediateValidityYrs;
	
	@Value("${validity.endUser}")
	private int endUserValidityYrs;

}
