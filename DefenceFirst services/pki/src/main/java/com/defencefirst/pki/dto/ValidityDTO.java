package com.defencefirst.pki.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidityDTO {
	
	@NotNull
	private Date notBefore;
	@NotNull
	private Date notAfter;
}
