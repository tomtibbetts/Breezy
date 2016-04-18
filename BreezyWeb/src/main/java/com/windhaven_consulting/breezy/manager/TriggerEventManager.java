package com.windhaven_consulting.breezy.manager;

import java.util.List;

import com.windhaven_consulting.breezy.persistence.domain.TriggerEvent;

public interface TriggerEventManager {

	void saveAll(List<TriggerEvent> triggerEvents);

	List<TriggerEvent> getAll();

}
