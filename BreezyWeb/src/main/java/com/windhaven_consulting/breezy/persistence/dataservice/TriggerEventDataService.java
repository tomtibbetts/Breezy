package com.windhaven_consulting.breezy.persistence.dataservice;

import java.util.List;

import com.windhaven_consulting.breezy.persistence.domain.TriggerEvent;

public interface TriggerEventDataService extends GenericDataService<TriggerEvent> {

	void saveAll(List<TriggerEvent> triggerEvents);

}
