package com.defencefirst.pki.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDTO { 
	
	 @NotNull(message = "Certificate type is required")
	 @Pattern(regexp = "root|intermediate|end user|ROOT|INTERMEDIATE|END USER", message = "Certificate type is not valid")
	 private String type;
	 @NotNull(message = "Certificate serial number is required")
	 @Pattern(regexp = "[0-9]+", message = "Serial number is not valid")
	 private String issuerSerialNumber;
	 @Valid
	 private SubjectIssuerDTO issuer;
	 @Valid
	 private SubjectIssuerDTO subject;
	 @Valid
	 private ValidityDTO validity; 
	 private KeyUsageExtensionDTO keyUsageExtention;
	 
}
