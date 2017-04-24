package com.windhaven_consulting.breezy.controller.ui;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.embeddedcontroller.EmbeddedControllerAdapter;
import com.windhaven_consulting.breezy.embeddedcontroller.SystemStatus;
import com.windhaven_consulting.breezy.manager.RevisionManager;
import com.windhaven_consulting.breezy.persistence.domain.Revision;

@ManagedBean
public class HomeManagedView {

	@Inject
	private EmbeddedControllerAdapter embeddedControllerAdapter;
	
	@Inject
	private RevisionManager revisionManager;
	
	public SystemStatus getSystemStatus() {
		return embeddedControllerAdapter.getSystemInfo();
	}
	
	public String getCurrentFormattedRevision() {
		Revision revision = revisionManager.getCurrentRevision();
		
		String result = String.format("%d.%d.%d", revision.getMajor(), revision.getMinor(), revision.getFix());
		
		return result;
	}
}
