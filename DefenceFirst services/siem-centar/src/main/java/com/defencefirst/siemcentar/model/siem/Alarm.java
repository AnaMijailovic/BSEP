package com.defencefirst.siemcentar.model.siem;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
	
	@Id
	private String id;
	@Indexed(direction = IndexDirection.ASCENDING)
	private Date datetime;
	private String message;
	private Set<Log> logs; // TODO Is this needed?
	
}
