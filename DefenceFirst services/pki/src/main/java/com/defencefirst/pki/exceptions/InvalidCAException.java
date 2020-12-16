package com.defencefirst.pki.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid CA")
public class InvalidCAException extends Exception{
	
	private static final long serialVersionUID = 6198391385171931665L;

	public InvalidCAException(String message) {
		super(message);
	}

}
