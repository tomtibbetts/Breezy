package com.windhaven_consulting.breezy.macrocontroller;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMPinState;

public class PWMPinStateConverterImpl implements ValueConverter {

	@Override
	public Object convert(String value) {
		PWMPinState result = null;
		
		if(StringUtils.isNotEmpty(value)) {
			result = PWMPinState.valueOf(StringUtils.upperCase(value));
		}
		
		return result;
	}

}
