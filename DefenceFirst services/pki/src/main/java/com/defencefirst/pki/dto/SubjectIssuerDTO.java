package com.defencefirst.pki.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectIssuerDTO {
	 @NotNull(message = "Common Name is required")
	 @Pattern(regexp = "[a-zA-Z0-9.,!? ]*", message = "Common Name is not valid")
	 private String commonName;
	 
	 @NotNull(message = "Surname is required")
	 @Pattern(regexp = "[a-zA-Z0-9.,!? ]*", message = "Surname is not valid")
	 private String surname;
	 
	 @NotNull(message = "Given Name is required")
	 @Pattern(regexp = "[a-zA-Z0-9.,!? ]*", message = "Given Name is not valid")
	 private String givenName;
	 
	 @NotNull(message = "Email is required")
	 @Email(regexp = "[a-zA-Z0-9._]+[@][a-zA-Z.]+", message = "Email is not valid")
	 private String email;
	 
	 @NotNull(message = "Country is required")
	 @Pattern(regexp = "[a-zA-Z0-9.,!? ]*", message = "Country is not valid")
	 private String country;
	 
	 @NotNull(message = "Organization is required")
	 @Pattern(regexp = "[a-zA-Z0-9.,!? ]*", message = "Organization is not valid")
	 private String organization;
	 
	 @NotNull(message = "Organization Unit is required")
	 @Pattern(regexp = "[a-zA-Z0-9.,!? ]*", message = "Organization unit is not valid")
	 private String organizationalUnit;
	 
	 @NotNull(message = "State or province name is required")
	 @Pattern(regexp = "[a-zA-Z0-9.,!? ]*", message = "State or province name is not valid")
	 private String stateOrProvinceName;
	 
	 @NotNull(message = "Loaclity is required")
	 @Pattern(regexp = "[a-zA-Z0-9.,!? ]*", message = "Locality is not valid")
	 private String locality;


}
