package com.windhaven_consulting.breezy.controller.ui;


import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.MacroManager;
import com.windhaven_consulting.breezy.persistence.domain.Macro;

@ManagedBean
@ViewScoped
public class MacroBuilderChooserView {

	@Inject
	private MacroManager macroManager;
	
	public List<Macro> getMacros() {
		return macroManager.getAllMacros();
	}
	
	public void throwException() {
		throw new NullPointerException("message");
	}
}
