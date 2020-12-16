package com.defencefirst.pki.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Extension value is missing")
public class MissingExtensionValue extends Exception{

	private static final long serialVersionUID = 562440207179977510L;
	
	public MissingExtensionValue(String message) {
		super(message);
	}

}
