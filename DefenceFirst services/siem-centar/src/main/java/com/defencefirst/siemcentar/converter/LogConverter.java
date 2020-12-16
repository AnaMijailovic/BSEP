package com.defencefirst.siemcentar.converter;

import com.defencefirst.siemcentar.dto.LogDTO;
import com.defencefirst.siemcentar.model.siem.Log;

public class LogConverter {
	
	private LogConverter() {
		throw new IllegalStateException("Utility class");
	}

	// Converts LogDTO to Log
	public static Log convertFromDto(LogDTO dto) {
		return new Log(dto.getId(), dto.getDatetime(), dto.getMac(), dto.getSourceName(), dto.getSeverityLevel(),
				dto.getEventId(), dto.getMessage());

	}

	// Converts Log to LogDTO
	public static LogDTO convertToDto(Log log)
			 {

		return new LogDTO(log.getId(), log.getDatetime(), log.getMac(), log.getSourceName(), log.getSeverityLevel(),
				log.getEventId(), log.getMessage());
	}
}
