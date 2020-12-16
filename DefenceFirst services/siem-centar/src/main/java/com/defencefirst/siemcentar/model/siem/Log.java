package com.defencefirst.siemcentar.model.siem;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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
public class Log {

	@Id
	private String id;
	@Indexed(direction = IndexDirection.ASCENDING)
	private Date datetime;
	@Indexed(direction = IndexDirection.ASCENDING)
	private String mac;
	private String sourceName;
	private String severityLevel;
	private String eventId;
	private String message;

	
	public Log(String logString) {
		String[] tokens = logString.split("---");
		String firstPart = tokens[0];
		String secondPart = tokens[1];

		String[] firstTokens = firstPart.trim().split(" +");
		String[] secondTokens = secondPart.trim().split(" +");

		String datetime = firstTokens[0].trim() + " " + firstTokens[1].trim();
		SimpleDateFormat formatter = new  SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		try {
			this.datetime =  formatter.parse(datetime);
		}catch(Exception e) {
			this.datetime = new Date();
		}
		
		
		this.mac = firstTokens[2].trim();
		
		String[] sourceNameArray = Arrays.copyOfRange(firstTokens, 3, firstTokens.length);
		Arrays.stream(sourceNameArray).map(String::trim).toArray(unused -> sourceNameArray);
		this.sourceName = String.join(" ", sourceNameArray);
		
		this.severityLevel = secondTokens[0].trim();
		this.eventId = secondTokens[1].trim().substring(1, (secondTokens[1].length())-1);
		
		
		String[] messageArray = Arrays.copyOfRange(secondTokens, 2, secondTokens.length);
		Arrays.stream(messageArray).map(String::trim).toArray(unused -> messageArray);
		this.message = String.join(" ", messageArray);
				
		System.out.println(this);
	}

}
