package com.windhaven_consulting.breezy.macrocontroller;

import org.apache.commons.lang3.StringUtils;

public class BooleanConverterImpl implements ValueConverter {

	@Override
	public Object convert(String value) {
		Boolean result = null;
		
		if(StringUtils.isEmpty(value)) {
			result = Boolean.FALSE;
		}
		else {
			result = Boolean.parseBoolean(value);
		}
		
		return result;
	}

}
