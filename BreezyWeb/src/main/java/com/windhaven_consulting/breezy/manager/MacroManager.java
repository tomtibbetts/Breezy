package com.windhaven_consulting.breezy.manager;

import java.util.List;

import com.windhaven_consulting.breezy.persistence.domain.Macro;

public interface MacroManager {

	void runMacro(Macro macro);

	void stopMacro(Macro macro);
	
	void stopAllMacros();
	
	List<Macro> getAllRunningMacros();
	
	List<Macro> getAllMacros();
	
	void saveMacro(Macro macro);
	
	Macro getMacroById(String macroId);

	void delete(Macro macro);
	
	boolean isRunning(Macro macro);
}
