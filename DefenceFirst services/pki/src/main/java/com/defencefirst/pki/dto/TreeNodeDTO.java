package com.defencefirst.pki.dto;

import java.util.List;

import com.defencefirst.pki.model.CertificateType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreeNodeDTO {
	
	private String serialNumber;
	private CertificateType type;
	private String commonName;
	private boolean revoked;
	private List<TreeNodeDTO> children;
	
}
