package com.defencefirst.siemcentar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defencefirst.siemcentar.model.siem.Alarm;
import com.defencefirst.siemcentar.repository.AlarmRepository;

@Service
public class AlarmService {
	
	@Autowired
	private AlarmRepository alarmRepository;
	
	public Alarm save(Alarm alarm) {
		return alarmRepository.save(alarm);
	}


	public List<Alarm> getAll() {
		return alarmRepository.findAll();
	}
}
