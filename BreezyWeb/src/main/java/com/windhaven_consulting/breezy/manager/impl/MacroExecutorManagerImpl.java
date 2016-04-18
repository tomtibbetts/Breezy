package com.windhaven_consulting.breezy.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.library.ComponentTemplateLibraryManager;
import com.windhaven_consulting.breezy.macrocontroller.MacroExecutor;
import com.windhaven_consulting.breezy.manager.MacroExecutorManager;
import com.windhaven_consulting.breezy.manager.MountedBoardManager;
import com.windhaven_consulting.breezy.persistence.domain.Macro;

@ApplicationScoped
public class MacroExecutorManagerImpl implements MacroExecutorManager {
	static final Logger LOG = LoggerFactory.getLogger(MacroExecutorManagerImpl.class);

	@Inject
	private ComponentTemplateLibraryManager componentTemplateLibraryManager;
	
	@Inject
	private MountedBoardManager mountedBoardManager;

	private Map<Macro, MacroExecutor> macroMap = Collections.synchronizedMap(new HashMap<Macro, MacroExecutor>());
	
	@Override
	public void runMacro(Macro macro) {
		if(macro.isEnabled() && !macroMap.containsKey(macro)) {
			MacroExecutor macroExecutor = getMacroExecutor(macro);
			macroMap.put(macro, macroExecutor);
			
			macroExecutor.start();
		}
		else {
			LOG.debug("A failed attempt to start the macro: " + macro.getName() + " was made.  Macro enable state is: " + macro.isEnabled());
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

}
