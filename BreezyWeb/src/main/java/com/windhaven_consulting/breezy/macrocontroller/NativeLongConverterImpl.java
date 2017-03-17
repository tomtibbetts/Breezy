package com.windhaven_consulting.breezy.macrocontroller;

import org.apache.commons.lang3.StringUtils;

public class NativeLongConverterImpl implements ValueConverter {

	@Override
	public Object convert(String value) {
		Long result = null;
		
		if(StringUtils.isNotEmpty(value)) {
			result = Long.parseLong(value);
		}
		
		return result;
	}

}
