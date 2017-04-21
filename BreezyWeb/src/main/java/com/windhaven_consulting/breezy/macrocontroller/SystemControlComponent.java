package com.windhaven_consulting.breezy.macrocontroller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

@ControlledComponent("Control")
public class SystemControlComponent extends GenericComponent<BreezyPin> implements SystemComponent {
	static final Logger LOG = LoggerFactory.getLogger(SystemControlComponent.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MountedBoardManager mountedBoardManager;

	@ControlledMethod("Wait")
	public void delay(@ControlledParameter(name = "Wait Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long waitTime,
			MacroExecutor macroExecutor) {
		
		try {
			long delay = waitTime;
			
			while(delay > 0 && macroExecutor.isRunning()) {
				if(delay > 100) {
					Thread.sleep(100);
					delay = delay - 100;
				}
				else {
					Thread.sleep(delay);
					delay = 0;
				}
			}
		} catch (InterruptedException e) {
			LOG.debug(e.getLocalizedMessage());
			throw new BreezyApplicationException("Wait command was unexpectedly interrupted.", e);
		}
	}

	@ControlledMethod("Wait On Equal")
	public void waitOn(@ControlledParameter(name = "Mounted Board", parameterFieldType = ParameterFieldType.MOUNTED_BOARD, required = true) String mountedBoardId,
			@ControlledParameter(name = "Input", parameterFieldType = ParameterFieldType.DIGITAL_INPUT_PIN, required = true) String inputPinId,
			@ControlledParameter(name = "State", parameterFieldType = ParameterFieldType.PIN_STATE, required = true) PinState inputState,
			MacroExecutor macroExecutor) {

		MountedBoard mountedBoard = mountedBoardManager.getById(mountedBoardId);
		
		if(mountedBoard == null) {
			throw new BreezyApplicationException("No mounted board was found for " + mountedBoardId);
		}
		
		DigitalInputPin digitalInputPin = mountedBoard.getInputPinById(UUID.fromString(inputPinId));
		
		if(digitalInputPin == null) {
			throw new BreezyApplicationException("No digital input pin, " + inputPinId + ", was found for " + mountedBoardId);
		}
		
		while(!digitalInputPin.isState(inputState) && macroExecutor.isRunning()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				throw new BreezyApplicationException("Wait On Equal command was unexpectedly interrupted.", e);
			}
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
