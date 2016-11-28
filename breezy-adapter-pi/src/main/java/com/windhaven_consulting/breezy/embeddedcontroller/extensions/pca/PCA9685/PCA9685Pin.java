package com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;

public class PCA9685Pin implements BreezyPin {

	private final static Map<String, BreezyPin> pinMap = new LinkedHashMap<String, BreezyPin>();

	private String name;

	private UUID id;
	
	public static final BreezyPin PWM_00 = createPwmPin("PWM_01");
    public static final BreezyPin PWM_01 = createPwmPin("PWM_01");
    public static final BreezyPin PWM_02 = createPwmPin("PWM_02");
    public static final BreezyPin PWM_03 = createPwmPin("PWM_03");
    public static final BreezyPin PWM_04 = createPwmPin("PWM_04");
    public static final BreezyPin PWM_05 = createPwmPin("PWM_05");
    public static final BreezyPin PWM_06 = createPwmPin("PWM_06");
    public static final BreezyPin PWM_07 = createPwmPin("PWM_07");
    public static final BreezyPin PWM_08 = createPwmPin("PWM_08");
    public static final BreezyPin PWM_09 = createPwmPin("PWM_09");
    public static final BreezyPin PWM_10 = createPwmPin("PWM_10");
    public static final BreezyPin PWM_11 = createPwmPin("PWM_11");
    public static final BreezyPin PWM_12 = createPwmPin("PWM_12");
    public static final BreezyPin PWM_13 = createPwmPin("PWM_13");
    public static final BreezyPin PWM_14 = createPwmPin("PWM_14");
    public static final BreezyPin PWM_15 = createPwmPin("PWM_15");

	
	public static List<BreezyPin> getPins() {
		return new ArrayList<BreezyPin>(pinMap.values());
	}

	public static BreezyPin getByName(String name) {
		return pinMap.get(name);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public UUID getId() {
		return id;
	}

	private PCA9685Pin(String pinName) {
		this.name = pinName;
	}

	private static BreezyPin createPwmPin(String pinName) {
		BreezyPin breezyPin = new PCA9685Pin(pinName);
		pinMap.put(pinName, breezyPin);
		
		return breezyPin;
	}

}
