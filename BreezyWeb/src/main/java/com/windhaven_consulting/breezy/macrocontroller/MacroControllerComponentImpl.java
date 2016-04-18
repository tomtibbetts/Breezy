package com.windhaven_consulting.breezy.macrocontroller;

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

/**
 * This class essentially becomes a placeholder object.  It holds the information to describe the component
 * but the implementation will be done in the MacroExecutor
 * 
 * @author Tom
 *
 */
@ControlledComponent("System")
public class MacroControllerComponentImpl extends Component implements MacroControllerComponent {
	
	private static final long serialVersionUID = 1L;

	@ControlledMethod("Wait")
	public void delay(@ControlledParameter(name = "Wait Time (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long waitTime) {
		/**
		 * Do nothing.  This will never execute.  Throw exception here.
		 */
		throw new UnsupportedOperationException();
	}

	@ControlledMethod("Wait On")
	public void waitOn(@ControlledParameter(name = "Mounted Board", parameterFieldType = ParameterFieldType.MOUNTED_BOARD, required = true) String mountedBoardId,
			@ControlledParameter(name = "Input", parameterFieldType = ParameterFieldType.INPUT_PIN, required = true) String inputPinId,
			@ControlledParameter(name = "State", parameterFieldType = ParameterFieldType.PIN_STATE, required = true) PinState inputState) {
		/**
		 * Do nothing.  This will never execute.  Throw exception here.
		 */
		throw new UnsupportedOperationException();
	}
	
	@ControlledMethod("Jump")
	public void jumpTo(@ControlledParameter(name = "Tag", required = true) String tag) {
		/**
		 * Do nothing.  This will never execute.  Throw exception here.
		 */
		throw new UnsupportedOperationException();
	}

	@ControlledMethod("Jump On")
	public void jumpOn(@ControlledParameter(name = "Mounted Board", parameterFieldType = ParameterFieldType.MOUNTED_BOARD, required = true) String mountedBoardId,
			@ControlledParameter(name = "Input", parameterFieldType = ParameterFieldType.INPUT_PIN, required = true) String inputPinId,
			@ControlledParameter(name = "State", parameterFieldType = ParameterFieldType.PIN_STATE, required = true) PinState inputState,
			@ControlledParameter(name = "Tag", required = true) String tag) {
		/**
		 * Do nothing.  This will never execute.  Throw exception here.
		 */
		throw new UnsupportedOperationException();
	}

	@Override
	public void test() {
		/**
		 * Do nothing.  This will never execute.  Throw exception here.
		 */
		throw new UnsupportedOperationException();
	}

}
