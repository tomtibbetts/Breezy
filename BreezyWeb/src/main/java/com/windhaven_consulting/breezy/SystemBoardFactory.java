package com.windhaven_consulting.breezy;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;
import com.windhaven_consulting.breezy.manager.viewobject.ComponentConfiguration;

@ApplicationScoped
public class SystemBoardFactory {
	private static final String SYSTEM = "System";
	
	public static BreezyBoard getSystemBoard() {
		BreezyBoard breezyBoard = new BreezyBoard();

		breezyBoard.setName(SYSTEM);
		breezyBoard.setDescription("Main System Access");
		breezyBoard.setMounted(true);
		breezyBoard.setId(UUID.fromString(SystemConstants.SYSTEM_BOARD_ID.getValue()));
		breezyBoard.setRestricted(true);
		
		// build 'Branch' Component
		ComponentConfiguration componentConfiguration = getComponentConfiguration("Branch", SystemConstants.SYSTEM_BRANCH_COMPONENT_ID);
		breezyBoard.getComponentConfigurations().add(componentConfiguration);
		
		// build 'Control' Component
		componentConfiguration = getComponentConfiguration("Control", SystemConstants.SYSTEM_CONTROL_COMPONENT_ID);
		breezyBoard.getComponentConfigurations().add(componentConfiguration);
		
		return breezyBoard;
	}

	private static ComponentConfiguration getComponentConfiguration(String nameType, SystemConstants systemConstant) {
		ComponentConfiguration componentConfiguration = new ComponentConfiguration();
		componentConfiguration.setComponentType(nameType);
		componentConfiguration.setId(UUID.fromString(systemConstant.getValue()));
		componentConfiguration.setName(nameType);
		
		return componentConfiguration;
	}
}
