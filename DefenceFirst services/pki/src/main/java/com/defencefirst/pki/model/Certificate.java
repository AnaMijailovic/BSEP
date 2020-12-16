package com.defencefirst.pki.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data  
@NoArgsConstructor
@AllArgsConstructor
public class Certificate { 
	
	@Id 
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, unique=true) 
	private String serialNumber;
	
	@Column(nullable=false) 
	private String parentCertSerialNumber;
	
	@Column(nullable=false)
	private CertificateType type;
	
	@Column(nullable=false) 
	private String issuerCommonName;
	
	@Column(nullable=false)
	private Date notBefore;
	
	@Column(nullable=false)
	private Date notAfter;
	
	@Column(nullable=true)
	private boolean revoked;
	
	@Column(nullable=true)
	private Date revocationDate;
	
	@Column(nullable=true)
	private RevokeReason revokeReason;
}
