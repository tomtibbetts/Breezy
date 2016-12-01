package com.windhaven_consulting.breezy.component;

import com.windhaven_consulting.breezy.component.annotation.ControlledParameter;
import com.windhaven_consulting.breezy.embeddedcontroller.PWMOutputPin;

//ControlledComponent("DCC Encoder")
public class DccEncoder extends  GenericComponent<PWMOutputPin> {

	private static final long serialVersionUID = 1L;

//	@ControlledMethod("Select Loco")
	public void selectLoco(@ControlledParameter(name = "Address", required = true) String address) {
		
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
		
	}
}
