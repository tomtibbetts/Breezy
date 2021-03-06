package com.windhaven_consulting.breezy;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.manager.BreezyBoardManager;
import com.windhaven_consulting.breezy.manager.MacroManager;
import com.windhaven_consulting.breezy.manager.RevisionManager;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;
import com.windhaven_consulting.breezy.persistence.domain.Macro;

@WebListener
public class ApplicationStartup implements ServletContextListener {
	static final Logger LOG = LoggerFactory.getLogger(ApplicationStartup.class);
	
	@Inject
	private MacroManager macroManager;
	
	@Inject
	private BreezyBoardManager breezyBoardManager;
	
	@Inject
	private RevisionManager revisionManager;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		LOG.debug("DEBUG: ********************* Application Shutdown ***********************");
		
		killMacros();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		LOG.debug("DEBUG: ********************* Application Startup ***********************");

		checkRevision();
		mountBoards();
		autoStartMacros();
	}

	private void checkRevision() {
		revisionManager.createIfNotExist();
	}

	private void autoStartMacros() {
		LOG.debug("Starting all macros with autostart bit turned on.");
		
		for(Macro macro : macroManager.getAllMacros()) {
			if(macro.isAutoStart()) {
				macroManager.runMacro(macro);
			}
		}
	}

	private void mountBoards() {
		LOG.debug("Mounting all boards");
		
		for(BreezyBoard breezyBoard : breezyBoardManager.getAllBreezyBoards()) {
			if(breezyBoard.isMounted()) {
				breezyBoardManager.mountBoard(breezyBoard);
			}
		}
	}

	private void killMacros() {
		LOG.debug("Killing all macros");
		macroManager.stopAllMacros();
	}
}
