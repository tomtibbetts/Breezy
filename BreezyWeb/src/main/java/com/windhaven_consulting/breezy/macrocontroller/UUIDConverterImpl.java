package com.windhaven_consulting.breezy.macrocontroller;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class UUIDConverterImpl implements ValueConverter {

	@Override
	public Object convert(String value) {
		UUID result = null;
		
		if(StringUtils.isNotEmpty(value)) {
			result = UUID.fromString(value);
		}
		
		return result;
	}

}
