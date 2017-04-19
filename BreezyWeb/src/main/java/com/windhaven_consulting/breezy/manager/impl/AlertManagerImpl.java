package com.windhaven_consulting.breezy.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.windhaven_consulting.breezy.manager.AlertManager;
import com.windhaven_consulting.breezy.manager.viewobject.Alert;

@ApplicationScoped
public class AlertManagerImpl implements AlertManager {

	private List<Alert> alerts = new ArrayList<Alert>();

	@PostConstruct
	public void postConstruct() {
		addAlert("Macro 'Test PCA 9685' references unmounted boards and therefore cannot be run.");
		addAlert("Macro 'Test PCA 9685' references unmounted boards and therefore cannot be run.");
		addAlert("Macro 'Test PCA 9685' references unmounted boards and therefore cannot be run.");
		addAlert("Macro 'Test PCA 9685' references unmounted boards and therefore cannot be run.");
		addAlert("Macro 'Test PCA 9685' references unmounted boards and therefore cannot be run.");
	}
	
	@Override
	public List<Alert> getAlerts() {
		return alerts;
	}

	@Override
	public void clearAlerts(List<Alert> alertsToClear) {
		alerts.removeAll(alertsToClear);
	}

	@Override
	public void addAlert(String message) {
		UUID id = UUID.randomUUID();
		
		Alert alert = new Alert(id, message);
		alerts.add(alert);
	}

}
