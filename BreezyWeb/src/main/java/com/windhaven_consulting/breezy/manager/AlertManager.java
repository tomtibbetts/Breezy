package com.windhaven_consulting.breezy.manager;

import java.util.List;

import com.windhaven_consulting.breezy.manager.viewobject.Alert;

public interface AlertManager {

	List<Alert> getAlerts();
	
	void clearAlerts(List<Alert> alerts);
	
	void addAlert(String message);
}
