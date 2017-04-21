package com.windhaven_consulting.breezy.macrocontroller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.PWMPinState;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;
import com.windhaven_consulting.breezy.exceptions.BreezyApplicationException;

public class ValueConverterUtil {

	private static Map<Class<?>, ValueConverter> valueConverterMap = new HashMap<Class<?>, ValueConverter>();
	
	static {
		valueConverterMap.put(String.class, new StringConverterImpl());
		valueConverterMap.put(long.class, new NativeLongConverterImpl());
		valueConverterMap.put(Long.class, new NativeLongConverterImpl());
		valueConverterMap.put(int.class, new NativeIntConverterImpl());
		valueConverterMap.put(Integer.class, new NativeIntConverterImpl());
		valueConverterMap.put(PinState.class, new PinStateConverterImpl());
		valueConverterMap.put(PWMPinState.class, new PWMPinStateConverterImpl());
		valueConverterMap.put(Boolean.class, new BooleanConverterImpl());
		valueConverterMap.put(UUID.class, new UUIDConverterImpl());
	}

	public static Object getValueForType(String value, Class<?> type) {
		ValueConverter valueConverter = valueConverterMap.get(type);
		
		if(valueConverter == null) {
			throw new BreezyApplicationException("No converter found for type: " + type.getName());
		}
		
		return valueConverter.convert(value);
	}

}
