package com.defencefirst.pki.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Certificate Not Found")
public class CertificateNotFoundException extends Exception{

	private static final long serialVersionUID = 5624402071979977510L;
	
	public CertificateNotFoundException(String message) {
		super(message);
	}


}
