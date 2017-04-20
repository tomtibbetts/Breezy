package com.windhaven_consulting.breezy.controller.ui;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.AlertManager;
import com.windhaven_consulting.breezy.manager.viewobject.Alert;

@ManagedBean
@RequestScoped
public class AlertsView {

	@Inject
	private AlertManager alertManager;
	
	private List<Alert> selectedAlerts;
	
	private Alert selectedAlert;
	
	public List<Alert> getAlerts() {
		return alertManager.getAlerts();
	}
	
	public List<Alert> getSelectedAlerts() {
		return selectedAlerts;
	}
	
	public Alert getSelectedAlert() {
		return selectedAlert;
	}

	public void setSelectedAlert(Alert selectedAlert) {
		this.selectedAlert = selectedAlert;
	}

	public void setSelectedAlerts(List<Alert> selectedAlerts) {
		this.selectedAlerts = selectedAlerts;
	}
	
	public void clearAll() {
		if(selectedAlerts != null) {
			alertManager.clearAlerts(selectedAlerts);
		}
	}
}
