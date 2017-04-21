package com.windhaven_consulting.breezy.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.library.ComponentTemplateLibraryManager;
import com.windhaven_consulting.breezy.macrocontroller.MacroExecutor;
import com.windhaven_consulting.breezy.manager.AlertManager;
import com.windhaven_consulting.breezy.manager.MacroExecutorManager;
import com.windhaven_consulting.breezy.manager.MountedBoardManager;
import com.windhaven_consulting.breezy.persistence.domain.Macro;
import com.windhaven_consulting.breezy.persistence.domain.MacroStep;

@Named("copyOfMacroExecutorManager")
@ApplicationScoped
public class MacroExecutorManagerImpl implements MacroExecutorManager {
	static final Logger LOG = LoggerFactory.getLogger(MacroExecutorManagerImpl.class);

	@Inject
	private ComponentTemplateLibraryManager componentTemplateLibraryManager;
	
	@Inject
	private MountedBoardManager mountedBoardManager;
	
	@Inject
	private AlertManager alertManager;

	private Map<Macro, MacroExecutor> macroMap = new ConcurrentHashMap<Macro, MacroExecutor>();

	private ExecutorService cachedServicePool;
	
	@PostConstruct
	public void postConstruct() {
		cachedServicePool = Executors.newCachedThreadPool();
	}
	
	@Override
	public void runMacro(Macro macro) {
		if(isValidMacro(macro)) {
			if(!macroMap.containsKey(macro)) {
				MacroExecutor macroExecutor = getMacroExecutor(macro);
				macroMap.putIfAbsent(macro, macroExecutor);
				
				cachedServicePool.execute(macroExecutor);
			}
		}
	}

	@Override
	public List<Macro> getRunningMacros() {
		return new ArrayList<Macro>(macroMap.keySet());
	}

	@Override
	public void terminate(Macro macro) {
		MacroExecutor macroExecutor = macroMap.get(macro);
		
		if(macroExecutor != null) {
			macroExecutor.stop();
			
			macroMap.remove(macro);
		}
	}

	@Override
	public void terminateAllMacros() {
		for(Macro macro : macroMap.keySet()) {
			MacroExecutor macroExecutor = macroMap.get(macro);
			macroExecutor.stop();
		}
		
		macroMap.clear();
	}

	@Override
	public void notifyStopped(Macro macro) {
		macroMap.remove(macro);
	}

	@Override
	public boolean isMacroRunning(Macro macro) {
		MacroExecutor macroExecutor = macroMap.get(macro);
		return macroExecutor != null;
	}

	private MacroExecutor getMacroExecutor(Macro macro) {
		MacroExecutor macroExecutor = new MacroExecutor(macro, this, mountedBoardManager, componentTemplateLibraryManager);
		
		return macroExecutor;
	}

	private boolean isValidMacro(Macro macro) {
		boolean result = true;
		
		if(!macro.isEnabled()) {
			alertManager.addAlert("Cannot execute macro, '" + macro.getName() + "',. Macro is not enabled.");
			result = false;
		}
		
		else if(macroMap.containsKey(macro)) {
			alertManager.addAlert("Cannot execute macro, '" + macro.getName() + "',. Macro is already running.");
		}
		
		else {
			for(MacroStep macroStep : macro.getSteps()) {
				MountedBoard mountedBoard = mountedBoardManager.getById(macroStep.getMountedBoardId());
				
				if(mountedBoard == null || !mountedBoard.isMounted()) {
					alertManager.addAlert("Cannot execute macro, '" + macro.getName() + "',. Macro references a board that either does not exist or is not mounted.");
					result = false;
					break;
				}
			}
		}
		
		return result;
	}

}
