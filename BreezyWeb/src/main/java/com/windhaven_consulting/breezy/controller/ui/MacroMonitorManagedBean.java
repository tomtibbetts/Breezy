package com.windhaven_consulting.breezy.controller.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.MacroManager;
import com.windhaven_consulting.breezy.persistence.domain.Macro;

@ManagedBean
@SessionScoped
public class MacroMonitorManagedBean {

	@Inject
	private MacroManager macroManager;

	private List<Macro> runningMacros;
	
	@PostConstruct
	public void postConstruct() {
		runningMacros = macroManager.getAllRunningMacros();
	}
	
	public List<Macro> getRunningMacros() {
		return runningMacros;
	}
	
	public void endMacro(int index) {
		macroManager.stopMacro(runningMacros.get(index));
	}
	
	public void stopAllMacros() {
		macroManager.stopAllMacros();
	}
	
	public void refreshPage() {
		runningMacros = macroManager.getAllRunningMacros();
	}
}
