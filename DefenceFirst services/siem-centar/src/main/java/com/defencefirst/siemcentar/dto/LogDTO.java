package com.defencefirst.siemcentar.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {

	private String id;
	private Date datetime;
	private String mac;
	private String sourceName;
	private String severityLevel;
	private String eventId;
	private String message;

}
