package com.defencefirst.siemcentar.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defencefirst.siemcentar.dto.LogReportDTO;
import com.defencefirst.siemcentar.dto.LogReportDataDTO;
import com.defencefirst.siemcentar.model.siem.Log;
import com.defencefirst.siemcentar.repository.LogRepository;

@Service
public class LogService {
	@Autowired
	private DroolsService droolsService;
	@Autowired
	private AlarmService alarmService;
	
	
	@Autowired
	private LogRepository logRepository;

	public List<Log> getAll() {
		List<Log> logs =  logRepository.findAll();
		
		Collections.sort(logs, 
                (o1, o2) -> o2.getDatetime().compareTo(o1.getDatetime()));
		
		return logs;
	}
	
	public List<Log> searchLogs(String id, String eventId, String mac,
			String sourceName, String severityLevel, String message, Date fromDate, Date toDate) {
		List<Log> logs = logRepository.searchLogs(id, eventId, mac, sourceName, severityLevel, message);
		
		if(fromDate != null) {
			logs.removeIf(x -> x.getDatetime().before(fromDate));
		}
		
		if(toDate != null) {
			logs.removeIf(x -> x.getDatetime().after(toDate));
		}
		
		Collections.sort(logs, 
                (o1, o2) -> o2.getDatetime().compareTo(o1.getDatetime()));
		return logs;
		
	}
	
	public LogReportDTO getReport(String mac, Date fromDate, Date toDate) {
		LogReportDTO report = new LogReportDTO(fromDate, toDate, mac,
				new ArrayList<LogReportDataDTO>(), new ArrayList<LogReportDataDTO>());
		
		List<Log> logForMac = logRepository.findByMac(mac);
		
		if(fromDate != null) {
			logForMac.removeIf(x -> x.getDatetime().before(fromDate));
		}
		
		if(toDate != null) {
			logForMac.removeIf(x -> x.getDatetime().after(toDate));
		}
		
		for(Log log : logForMac) {
			boolean severityLevelFlag = false;
			boolean eventIdFlag = false;
			
			for(LogReportDataDTO logReport : report.getReportSeverityLevel()) {
				if(logReport.getLabel().equals(log.getSeverityLevel())) {
					logReport.setNumberOfLogs(logReport.getNumberOfLogs() + 1);
					severityLevelFlag = true;
				}
			}
			
			for(LogReportDataDTO logReport : report.getReportEventId()) {
				if(logReport.getLabel().equals(log.getEventId())) {
					logReport.setNumberOfLogs(logReport.getNumberOfLogs() + 1);
					eventIdFlag = true;
				}
			}
			
			if(!severityLevelFlag) {
				LogReportDataDTO newLogData = new LogReportDataDTO(log.getSeverityLevel(), 1);
				report.getReportSeverityLevel().add(newLogData);
			}
			
			if(!eventIdFlag) {
				LogReportDataDTO newLogData = new LogReportDataDTO(log.getEventId(), 1);
				report.getReportEventId().add(newLogData);
			}
		}
		
		return report;
	}

	public void addLog(String logString) {
		KieSession kieSession = droolsService.getKieSession();
		kieSession.setGlobal("alarmService", alarmService);
		Log log = new Log(logString);
		logRepository.save(log);
		kieSession.insert(log);
		kieSession.fireAllRules();
	}
}
