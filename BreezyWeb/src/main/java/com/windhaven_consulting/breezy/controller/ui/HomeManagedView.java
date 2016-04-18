package com.windhaven_consulting.breezy.controller.ui;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.embeddedcontroller.EmbeddedControllerAdapter;
import com.windhaven_consulting.breezy.embeddedcontroller.SystemStatus;

@ManagedBean
public class HomeManagedView {

	@Inject
	private EmbeddedControllerAdapter embeddedControllerAdapter;
	
	public SystemStatus getSystemStatus() {
		return embeddedControllerAdapter.getSystemInfo();
	}
}
