package com.windhaven_consulting.breezy.macrocontroller;

public class LongConverterImpl implements ValueConverter {

	@Override
	public Object convert(String value) {
		return Long.parseLong(value);
	}

}
