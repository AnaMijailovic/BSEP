package com.defencefirst.pki.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Certificate Already Exists")
public class CertificateAlreadyExistsException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6021500194222393713L;

	public CertificateAlreadyExistsException(String message) {
		super(message);
	}
}
