package com.defencefirst.siemcentar.dto;

import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDTO {

	private String id;
	private Date datetime;
	private String message;
	private Set<LogDTO> logs; // TODO Is this needed?

}
