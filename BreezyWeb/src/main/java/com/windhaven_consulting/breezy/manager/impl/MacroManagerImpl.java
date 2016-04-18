package com.windhaven_consulting.breezy.manager.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.MacroExecutorManager;
import com.windhaven_consulting.breezy.manager.MacroManager;
import com.windhaven_consulting.breezy.persistence.dataservice.MacroDataService;
import com.windhaven_consulting.breezy.persistence.domain.Macro;

@ApplicationScoped
public class MacroManagerImpl implements MacroManager {

	@Inject
	private MacroDataService macroDataService;
	
	@Inject
	private MacroExecutorManager macroExecutorManager;
	
	@Override
	public void runMacro(Macro macro) {
		macroExecutorManager.runMacro(macro);
	}

	@Override
	public void stopMacro(Macro macro) {
		macroExecutorManager.terminate(macro);
	}

	@Override
	public List<Macro> getAllMacros() {
		return macroDataService.findAll();
	}

	@Override
	public void saveMacro(Macro macro) {
		macroDataService.save(macro);
	}

	@Override
	public Macro getMacroById(String macroId) {
		return macroDataService.findById(macroId);
	}

	@Override
	public void stopAllMacros() {
		macroExecutorManager.terminateAllMacros();
	}

	@Override
	public List<Macro> getAllRunningMacros() {
		return macroExecutorManager.getRunningMacros();
	}

	@Override
	public void delete(Macro macro) {
		macroDataService.delete(macro);
	}

	@Override
	public boolean isRunning(Macro macro) {
		return macroExecutorManager.isMacroRunning(macro);
	}
}
