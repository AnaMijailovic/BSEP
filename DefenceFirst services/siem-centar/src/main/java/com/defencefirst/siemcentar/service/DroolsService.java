package com.defencefirst.siemcentar.service;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

@Service
public class DroolsService {

	// source: https://docs.jboss.org/drools/release/6.3.0.Final/drools-docs/html/ch04.html
	// singleton default kie session 	
    private final KieSession kieSession;
    
 
    public DroolsService(KieSession kieSession) {
        this.kieSession = kieSession;
        
    }

    public KieSession getKieSession() {
        return kieSession;
    }
    
}
