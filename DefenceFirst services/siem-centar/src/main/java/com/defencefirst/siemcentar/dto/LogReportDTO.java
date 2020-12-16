package com.defencefirst.siemcentar.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogReportDTO {
	
	private Date fromDate;
	private Date toDate;
	private String mac;
	private List<LogReportDataDTO> reportSeverityLevel;
	private List<LogReportDataDTO> reportEventId;

}
