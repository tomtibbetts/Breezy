package com.windhaven_consulting.breezy.manager;

import java.util.List;

import com.windhaven_consulting.breezy.persistence.domain.Macro;

public interface MacroExecutorManager {

	void runMacro(Macro macro);
	
	List<Macro> getRunningMacros();
	
	void terminate(Macro macro);
	
	void terminateAllMacros();

	void notifyStopped(Macro macro);
	
	boolean isMacroRunning(Macro macro);
}
