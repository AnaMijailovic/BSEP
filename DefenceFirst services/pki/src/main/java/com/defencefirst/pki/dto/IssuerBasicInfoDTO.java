package com.defencefirst.pki.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuerBasicInfoDTO {
	
	private String certSerialNumber;
	private String commonName;

}
