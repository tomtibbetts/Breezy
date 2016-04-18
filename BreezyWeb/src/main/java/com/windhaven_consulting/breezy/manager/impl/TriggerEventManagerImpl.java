package com.windhaven_consulting.breezy.manager.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.TriggerEventManager;
import com.windhaven_consulting.breezy.persistence.dataservice.TriggerEventDataService;
import com.windhaven_consulting.breezy.persistence.domain.TriggerEvent;

@ApplicationScoped
public class TriggerEventManagerImpl implements TriggerEventManager {

	@Inject
	private TriggerEventDataService triggerEventDataService;
	
	@Override
	public void saveAll(List<TriggerEvent> triggerEvents) {
		triggerEventDataService.saveAll(triggerEvents);
	}

	@Override
	public List<TriggerEvent> getAll() {
		return triggerEventDataService.findAll();
	}

}
