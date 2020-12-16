package com.defencefirst.siemcentar.service;

import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import com.defencefirst.siemcentar.model.Item;




@Service
public class SampleAppService {


	private DroolsService droolsService;

	public SampleAppService(DroolsService droolsService) {
		this.droolsService = droolsService;
	}

   

    
    public Item getClassifiedItem(Item i) {

		KieSession kieSession = droolsService.getKieSession();
        kieSession.insert(i);
        kieSession.fireAllRules();
        kieSession.dispose();
        return i;
    }
}
