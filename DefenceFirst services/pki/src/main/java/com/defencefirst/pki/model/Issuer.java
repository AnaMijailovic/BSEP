package com.defencefirst.pki.model;

import java.security.PrivateKey;

import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issuer {
	
	private X500Name name;
	private PrivateKey privateKey;
	
}
