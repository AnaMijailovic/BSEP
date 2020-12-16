package com.defencefirst.pki.dto;

import org.springframework.beans.factory.annotation.Value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedVadilityDTO {
	
	private int rootValidityYrs;	
	private int intermediateValidityYrs;
	private int endUserValidityYrs;

}
