package com.defencefirst.pki.converters;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import com.defencefirst.pki.dto.CertificateDTO;
import com.defencefirst.pki.dto.SubjectIssuerDTO;
import com.defencefirst.pki.dto.ValidityDTO;
import com.defencefirst.pki.model.Certificate;
import com.defencefirst.pki.model.CertificateType;

public class CertificateConverter {
	
	private CertificateConverter() {
		throw new IllegalStateException("Utility class");
	}
	
	// Converts CertificateDTO to Certificate
	public static Certificate convertFromDTO(CertificateDTO dto, boolean revoked, String serialNumber,
			String parentSerialNumber) {
		Certificate certificate = new Certificate();
		
		certificate.setNotBefore(dto.getValidity().getNotBefore());
		certificate.setNotAfter(dto.getValidity().getNotAfter());
		certificate.setSerialNumber(serialNumber);
		certificate.setParentCertSerialNumber(parentSerialNumber);
		certificate.setIssuerCommonName(dto.getSubject().getCommonName());
		certificate.setType(CertificateType.strToEnum(dto.getType()));	
		certificate.setRevoked(revoked);
 
		return certificate;

	} 
	
	// Converts java.security.cert.Certificate to CertificateDTO
	public static CertificateDTO javaCertificateToDto(java.security.cert.Certificate certificate) throws CertificateEncodingException{
		
		CertificateDTO dto = new CertificateDTO();
		
		JcaX509CertificateHolder certHolder = new JcaX509CertificateHolder((X509Certificate) certificate);
		X500Name issuerName = certHolder.getIssuer();
		X500Name subjectName = certHolder.getSubject();
		
		SubjectIssuerDTO issuerDto = x500NameConverter.x500NameToSubjectIssuerDTO(issuerName);
		SubjectIssuerDTO subjectDto = x500NameConverter.x500NameToSubjectIssuerDTO(subjectName);
		
		
		ValidityDTO validity = new ValidityDTO(certHolder.getNotBefore(), certHolder.getNotAfter());
		dto.setValidity(validity);
		dto.setIssuer(issuerDto);
		dto.setSubject(subjectDto);
		
		return dto;
	}
	
}
