package com.defencefirst.pki.model;

public enum CertificateType {
	ROOT, INTERMEDIATE, END_USER;
	
	public static CertificateType strToEnum(String type){
		switch(type.toLowerCase().trim()) {
		case "root":
			return ROOT;
		case "intermediate":
			return INTERMEDIATE;
		case "end_user": 
			return END_USER;
		case "end user":
			return END_USER;
		default:
			return null;
		}
			
	}
}
