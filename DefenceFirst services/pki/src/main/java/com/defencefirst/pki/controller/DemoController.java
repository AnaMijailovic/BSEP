package com.defencefirst.pki.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pki/demo", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemoController {
	
	@GetMapping()
	public ResponseEntity<String> getCategories(){	

		return new ResponseEntity<>("Hello from pki!", HttpStatus.OK);
	} 


}
