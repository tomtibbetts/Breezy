package com.windhaven_consulting.breezy;

import java.util.UUID;

import com.windhaven_consulting.breezy.persistence.domain.BreezyBoard;
import com.windhaven_consulting.breezy.persistence.domain.ComponentConfiguration;

public class SystemBoardFactory {
	private static final String SYSTEM = "System";
	
	public static BreezyBoard getSystemBoard() {
		BreezyBoard breezyBoard = new BreezyBoard();

		breezyBoard.setName(SYSTEM);
		breezyBoard.setDescription("Main System Access");
		breezyBoard.setMounted(true);
		breezyBoard.setId(UUID.fromString(SystemConstants.SYSTEM_BOARD_ID.getValue()));
		breezyBoard.setRestricted(true);
		
		ComponentConfiguration componentConfiguration = new ComponentConfiguration();
		componentConfiguration.setComponentType(SYSTEM);
		componentConfiguration.setId(UUID.fromString(SystemConstants.SYSTEM_COMPONENT_ID.getValue()));
		componentConfiguration.setName(SYSTEM);
		
		breezyBoard.getComponentConfigurations().add(componentConfiguration);
		
		return breezyBoard;
	}
}
