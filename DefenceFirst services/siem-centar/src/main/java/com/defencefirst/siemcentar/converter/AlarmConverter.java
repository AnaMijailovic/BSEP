package com.defencefirst.siemcentar.converter;

import java.util.Set;
import java.util.stream.Collectors;

import com.defencefirst.siemcentar.dto.AlarmDTO;
import com.defencefirst.siemcentar.dto.LogDTO;
import com.defencefirst.siemcentar.model.siem.Alarm;
import com.defencefirst.siemcentar.model.siem.Log;

public class AlarmConverter {

	private AlarmConverter() {
		throw new IllegalStateException("Utility class");
	}

	// Converts AlarmDTO to Alarm
	public static Alarm convertFromDTO(AlarmDTO dto) {

		Set<Log> logSet = dto.getLogs().stream().map(log -> {
			return LogConverter.convertFromDto(log);
		}).collect(Collectors.toSet());

		return new Alarm(dto.getId(), dto.getDatetime(), dto.getMessage(), logSet);

	}

	// Converts Alarm to AlarmDTO
	public static AlarmDTO convertToDto(Alarm alarm) {

		Set<LogDTO> logSet = alarm.getLogs().stream().map(log -> {
			return LogConverter.convertToDto(log);
		}).collect(Collectors.toSet());

		return new AlarmDTO(alarm.getId(), alarm.getDatetime(), alarm.getMessage(), logSet);
	}
}
