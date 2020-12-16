package com.defencefirst.siemcentar.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogReportRequestDTO {
	
	private Date fromDate;
	private Date toDate;
	private String mac;

}