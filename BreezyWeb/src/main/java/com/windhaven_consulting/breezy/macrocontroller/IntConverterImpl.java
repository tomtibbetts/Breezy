package com.windhaven_consulting.breezy.macrocontroller;

public class IntConverterImpl implements ValueConverter {

	@Override
	public Object convert(String value) {
		return Integer.parseInt(value);
	}

}
