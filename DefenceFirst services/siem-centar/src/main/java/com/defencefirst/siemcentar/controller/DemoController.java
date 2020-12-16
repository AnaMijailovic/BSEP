package com.defencefirst.siemcentar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.defencefirst.siemcentar.model.Item;
import com.defencefirst.siemcentar.service.SampleAppService;

@RestController
@RequestMapping(value = "/siem/demo", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemoController {
	@Autowired 
	SampleAppService sampleService;



	@GetMapping(value = "/item")
	public ResponseEntity<String> getQuestions() {
		System.out.println("Uleteo u item");
		Item newItem = new Item(1L, "pera", 10D, 23D);
		System.out.println(newItem.getId());
		System.out.println(newItem.getName());
		System.out.println(newItem.getCost());
		System.out.println(newItem.getSalePrice());
		System.out.println(newItem.getCategory());
		System.out.println(newItem.toString());

		Item i2 = sampleService.getClassifiedItem(newItem);
		
		
		System.out.println("\nRecieved: " + i2.getCategory().name());
		return new ResponseEntity<>(i2.getCategory().name(), HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<String> getCategories(){	

		return new ResponseEntity<>("Hello from siem centar!", HttpStatus.OK);
	} 
	
	@PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> postSomething(@RequestBody() String text){
		
		System.out.println("\nRecieved: " + text);
		return new ResponseEntity<>("Post from siem centar!", HttpStatus.OK);
	}


}
