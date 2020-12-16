package com.defencefirst.pki.converters;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;

import com.defencefirst.pki.dto.SubjectIssuerDTO;

public class x500NameConverter {
	
	// Converts SubjectIssuerDTO to x500Name 
	public static X500Name generateX500Name(SubjectIssuerDTO dto) {

		// class X500NameBuilder creates X500Name object
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		if (dto.getCommonName() != null)
			builder.addRDN(BCStyle.CN, dto.getCommonName());
		if (dto.getSurname() != null)
			builder.addRDN(BCStyle.SURNAME, dto.getSurname());
		if (dto.getGivenName() != null)
			builder.addRDN(BCStyle.GIVENNAME, dto.getGivenName());
		if (dto.getOrganization() != null)
			builder.addRDN(BCStyle.O, dto.getOrganization());
		if (dto.getOrganizationalUnit() != null)
			builder.addRDN(BCStyle.OU, dto.getOrganizationalUnit());
		if (dto.getCountry() != null)
			builder.addRDN(BCStyle.C, dto.getCountry());
		if (dto.getEmail() != null)
			builder.addRDN(BCStyle.E, dto.getEmail());
		if (dto.getLocality() != null)
			builder.addRDN(BCStyle.L, dto.getLocality());
		if (dto.getStateOrProvinceName() != null)
			builder.addRDN(BCStyle.ST, dto.getStateOrProvinceName());

		return builder.build();

	}
	
	// Converts x500Name to SubjectIssuerDTO
	public static SubjectIssuerDTO x500NameToSubjectIssuerDTO(X500Name x500Name) {
		
		SubjectIssuerDTO dto = new SubjectIssuerDTO();
		
		RDN[] cn = x500Name.getRDNs(BCStyle.CN);
		if(cn.length >= 1) {
			dto.setCommonName(IETFUtils.valueToString(cn[0].getFirst().getValue()));
		}else { dto.setCommonName("");}
		
		RDN[] givenname = x500Name.getRDNs(BCStyle.GIVENNAME);
		if( givenname .length >= 1) {
			dto.setGivenName(IETFUtils.valueToString(givenname[0].getFirst().getValue()));
		}else {
			dto.setGivenName("");
		}
		
		RDN[] surname = x500Name.getRDNs(BCStyle.SURNAME);
		if(surname.length >= 1) {
			dto.setSurname(IETFUtils.valueToString(surname[0].getFirst().getValue()));
		}else {
			dto.setSurname("");
		}
		
		RDN[] email = x500Name.getRDNs(BCStyle.E);
		if(email.length >= 1) {
			dto.setEmail(IETFUtils.valueToString(email[0].getFirst().getValue()));
		}else {
			dto.setEmail("");
		}
		
		RDN[] organization = x500Name.getRDNs(BCStyle.O);
		if(organization.length >= 1) {
			dto.setOrganization(IETFUtils.valueToString(organization[0].getFirst().getValue()));
		}else {
			dto.setOrganization("");
		}
		
		RDN[] organizationalUnit = x500Name.getRDNs(BCStyle.OU);
		if(organizationalUnit.length >= 1) {
			dto.setOrganizationalUnit(IETFUtils.valueToString(organizationalUnit[0].getFirst().getValue()));
		}else {
			dto.setOrganizationalUnit("");
		}
		
		RDN[] country = x500Name.getRDNs(BCStyle.C);
		if(country.length >= 1) {
			dto.setCountry(IETFUtils.valueToString(country[0].getFirst().getValue()));
		}else {
			dto.setCountry("");
		}
		
		RDN[] state = x500Name.getRDNs(BCStyle.ST);
		if(state.length >= 1) {
			dto.setStateOrProvinceName(IETFUtils.valueToString(state[0].getFirst().getValue()));
		}else {
			dto.setStateOrProvinceName("");
		}
		
		RDN[] locality = x500Name.getRDNs(BCStyle.L);
		if(locality.length >= 1) {
			dto.setLocality(IETFUtils.valueToString(locality[0].getFirst().getValue()));
		}else {
			dto.setLocality("");
		}
		
		return dto;
	}

}
