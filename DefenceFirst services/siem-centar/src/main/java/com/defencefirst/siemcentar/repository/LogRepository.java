package com.defencefirst.siemcentar.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.defencefirst.siemcentar.model.siem.Log;

@Repository
public interface LogRepository extends MongoRepository<Log, String> {
	
		@Query("{'sourceName': {$regex: ?3 }, 'message': {$regex: ?5}, 'mac': {$regex: ?2},"
				+ " 'eventId': {$regex: ?1}, 'severityLevel': {$regex: ?4}})")
		List<Log> searchLogs(String id, String eventId, String mac,
				String sourceName, String severityLevel, String message);
		
		
		List<Log> findByMac(String mac);
}
