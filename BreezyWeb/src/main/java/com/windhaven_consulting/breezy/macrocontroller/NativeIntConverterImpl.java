package com.windhaven_consulting.breezy.macrocontroller;

import org.apache.commons.lang3.StringUtils;

public class NativeIntConverterImpl implements ValueConverter {

	@Override
	public Object convert(String value) {
		Integer result = null;
		
		if(StringUtils.isNotEmpty(value)) {
			result = Integer.parseInt(value);
		}
				
		return result;
	}

}
