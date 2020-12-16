package com.defencefirst.siemcentar.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.defencefirst.siemcentar.model.siem.Alarm;

@Repository
public interface AlarmRepository  extends MongoRepository<Alarm, String>{

}
