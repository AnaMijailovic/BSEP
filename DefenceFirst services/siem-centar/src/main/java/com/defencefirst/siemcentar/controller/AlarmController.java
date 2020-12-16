package com.defencefirst.siemcentar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.defencefirst.siemcentar.model.siem.Alarm;
import com.defencefirst.siemcentar.service.AlarmService;

@RestController
@CrossOrigin
@RequestMapping(value = "/siem/alarms", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlarmController {
	
	@Autowired
	private AlarmService alarmService;

	// Find all logs
	@GetMapping()
	public ResponseEntity<List<Alarm>> getAll() {

		return new ResponseEntity<>(alarmService.getAll(), HttpStatus.OK);
	}
}
