package com.windhaven_consulting.breezy.manager.impl;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.embeddedcontroller.StateChange;
import com.windhaven_consulting.breezy.embeddedcontroller.StateChangeEvent;
import com.windhaven_consulting.breezy.manager.MacroManager;
import com.windhaven_consulting.breezy.manager.TriggerEventManager;
import com.windhaven_consulting.breezy.persistence.domain.Macro;
import com.windhaven_consulting.breezy.persistence.domain.TriggerEvent;

@ApplicationScoped
public class TriggerEventObserver {
	static final Logger LOG = LoggerFactory.getLogger(TriggerEventObserver.class);
	
	@Inject
	private TriggerEventManager triggerEventManager;
	
	@Inject
	private MacroManager macroManager;
	
	@PostConstruct
	public void postConstruct() {
//		LOG.debug("*************************** TriggerEventObserver::postConstruct *************************");
	}
	
	public void observeInputs(@Observes @StateChange StateChangeEvent stateChangeEvent) {
//		LOG.debug("**************************** TriggerEventObserver::observeInputs (start): id: " + stateChangeEvent.getId() + ", id = " + stateChangeEvent.getId() + ", pinState = " + stateChangeEvent.getPinState() + "**************************");

		Macro macro = null;
		
		for(TriggerEvent triggerEvent : triggerEventManager.getAll()) {
			if(triggerEvent.isEnabled() 
					&& StringUtils.isNotEmpty(triggerEvent.getInputPinId()) 
					&& triggerEvent.getInputPinId().equals(stateChangeEvent.getId()) 
					&& triggerEvent.getState() == stateChangeEvent.getPinState()) {
				macro = macroManager.getMacroById(triggerEvent.getMacroId());
				break;
			}
		}

		if(macro != null) {
//			LOG.debug(this.getClass().getName() + "::observed state change for macro, '" + macro.getName() + "'");
			
			macroManager.runMacro(macro);
		}
		
//		LOG.debug("**************************** TriggerEventObserver::observeInputs (end): id: " + stateChangeEvent.getId() + ", pinState = " + stateChangeEvent.getPinState() + "**************************");
	}

}
