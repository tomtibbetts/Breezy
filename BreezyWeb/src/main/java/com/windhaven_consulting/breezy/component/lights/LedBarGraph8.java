package com.windhaven_consulting.breezy.component.lights;

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.component.annotation.ControlledComponent;
import com.windhaven_consulting.breezy.component.annotation.ControlledMethod;
import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;

@ControlledComponent(value="Led Eight Segment Bar Graph", numberOfOutputs=8)
public class LedBarGraph8 extends Component {

	private static final long serialVersionUID = 1L;

	@ControlledMethod("Cylon")
	public void cylon(@ControlledParameter(name = "Duration (milleseconds)", parameterFieldType = ParameterFieldType.NUMBER, required = true) long duration) {
		for(DigitalOutputPin digitalOutputPin : getOutputPins()) {
			digitalOutputPin.pulse(duration, true);
		}
		
		for(int i = getOutputPins().size() - 1; i >= 0; i--) {
			getOutputPin(i).pulse(duration, true);
		}
	}

	@ControlledMethod("Enterprise")
	public void enterprise(@ControlledParameter(name = "Duration (milleseconds)", required = true, parameterFieldType = ParameterFieldType.NUMBER) long duration) {
		int rightIndex = getOutputPins().size() / 2;
		int leftIndex = rightIndex - 1;
		
		for(int i = rightIndex, j = leftIndex; i < getOutputPins().size(); i++, j--) {
			getOutputPin(i).pulse(duration);
			getOutputPin(j).pulse(duration, true);
		}
	}

	@Override
	public void test() {
		cylon(50);
		enterprise(200);
	}
}
