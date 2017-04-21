package com.windhaven_consulting.breezy.macrocontroller;

import java.util.UUID;

import com.windhaven_consulting.breezy.component.GenericComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;
import com.windhaven_consulting.breezy.exceptions.BreezyApplicationException;
import com.windhaven_consulting.breezy.manager.MountedBoardManager;
import com.windhaven_consulting.breezy.manager.impl.MountedBoard;

@ControlledComponent("Branch")
public class SystemBranchComponent extends GenericComponent<BreezyPin> implements SystemComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MountedBoardManager mountedBoardManager;

	@ControlledMethod("Jump")
	public void jumpTo(@ControlledParameter(name = "Tag", required = true) String tag,
			MacroExecutor macroExecutor) {
		
		macroExecutor.redirect(tag);
	}

	@ControlledMethod("Jump On Equal")
	public void jumpOn(@ControlledParameter(name = "Mounted Board", parameterFieldType = ParameterFieldType.MOUNTED_BOARD, required = true) String mountedBoardId,
			@ControlledParameter(name = "Input", parameterFieldType = ParameterFieldType.DIGITAL_INPUT_PIN, required = true) String inputPinId,
			@ControlledParameter(name = "State", parameterFieldType = ParameterFieldType.PIN_STATE, required = true) PinState inputState,
			@ControlledParameter(name = "Tag", required = true) String tag,
			MacroExecutor macroExecutor) {
		MountedBoard mountedBoard = mountedBoardManager.getById(mountedBoardId);
		
		if(mountedBoard == null) {
			throw new BreezyApplicationException("No mounted board was found for " + mountedBoardId);
		}
		
		DigitalInputPin digitalInputPin = mountedBoard.getInputPinById(UUID.fromString(inputPinId));
		
		if(digitalInputPin == null) {
			throw new BreezyApplicationException("No digital input pin, " + inputPinId + ", was found for " + mountedBoardId);
		}
		
		if(digitalInputPin.isState(inputState)) {
			macroExecutor.redirect(tag);
		}
	}
	
	@Override
	public void test() {
		// Do nothing
	}

	@Override
	public void setMountedBoardManager(MountedBoardManager mountedBoardManager) {
		this.mountedBoardManager = mountedBoardManager;
	}

}
