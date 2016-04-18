package com.windhaven_consulting.breezy.macrocontroller;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.embeddedcontroller.PinState;

public class PinStateConverterImpl implements ValueConverter {

	@Override
	public Object convert(String value) {
		PinState result = null;
		
		if(StringUtils.isNotEmpty(value)) {
			result = PinState.valueOf(StringUtils.upperCase(value));
		}
		
		return result;
	}

}
