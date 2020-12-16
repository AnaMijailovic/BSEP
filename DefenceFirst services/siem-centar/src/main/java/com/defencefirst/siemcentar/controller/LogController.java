package com.defencefirst.siemcentar.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.defencefirst.siemcentar.dto.LogReportDTO;
import com.defencefirst.siemcentar.model.siem.Log;
import com.defencefirst.siemcentar.service.LogService;

@RestController
@CrossOrigin
@RequestMapping(value = "/siem/logs", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogController {

	@Autowired
	private LogService logService;

	// Find all logs
	@GetMapping()
	@PreAuthorize("hasAuthority('PERMISSION_SEARCH_LOGS')")
	public ResponseEntity<List<Log>> getAll(@RequestParam(defaultValue = "") String id,
			@RequestParam(defaultValue = "") String eventId,
			@RequestParam(defaultValue = "") String mac, @RequestParam(defaultValue = "") String severityLevel,
			@RequestParam(defaultValue = "") String sourceName, @RequestParam(defaultValue = "") String message,
			@RequestParam(required = false) Long fromDate, @RequestParam(required = false) Long toDate) {
			
			List<Log> result;
			if (id.equals("") && eventId.equals("") && mac.equals("") && sourceName.equals("") && message.equals("")
					&& fromDate == null && toDate == null) {
				result = logService.getAll();
			}else {
				Date from = fromDate == null ? null : new Date(fromDate);
				Date to = toDate == null ? null : new Date(toDate);
				result = logService.searchLogs(id, eventId, mac, sourceName, severityLevel, message, from, to);
			}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value="/report")
	@PreAuthorize("hasAuthority('PERMISSION_REPORTS')")
	public ResponseEntity<LogReportDTO> getReport(@RequestParam(required=true) String mac, @RequestParam(required=true) Long fromDate,
			@RequestParam(required=true) Long toDate) {

		LogReportDTO report = logService.getReport(mac, new Date(fromDate), new Date(toDate));
		return new ResponseEntity<>(report, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<String> addLog(@RequestBody String logString) {

		logService.addLog(logString);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
