package com.defencefirst.siemcentar.config;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {

	// source: https://docs.jboss.org/drools/release/6.3.0.Final/drools-docs/html/ch04.html
	// singleton default kie session 	
	@Bean
    public KieSession getKieSession() {
    	KieContainer kieContainer =  KieServices.Factory.get().getKieClasspathContainer();
    	KieServices kieServices = KieServices.Factory.get();
		KieBaseConfiguration kieBaseConfiguration = kieServices.newKieBaseConfiguration();
		kieBaseConfiguration.setOption(EventProcessingOption.STREAM);
		KieBase kieBase = kieContainer.newKieBase(kieBaseConfiguration);
		KieSession kieSession = kieBase.newKieSession();
        return kieSession;
    }
	
}


