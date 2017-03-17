package com.windhaven_consulting.breezy.embeddedcontroller.extensions.pca.PCA9685;

import java.util.Arrays;
import java.util.List;

import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;


public enum PCA9685Address implements PropertyValueEnum {
	ADDRESS_0("Address 0", "0x40"),
	ADDRESS_1("Address 1", "0x41"),
	ADDRESS_2("Address 2", "0x42"),
	ADDRESS_3("Address 3", "0x43"),
	ADDRESS_4("Address 4", "0x44"),
	ADDRESS_5("Address 5", "0x45"),
	ADDRESS_6("Address 6", "0x46"),
	ADDRESS_7("Address 7", "0x47"),
	ADDRESS_8("Address 8", "0x48"),
	ADDRESS_9("Address 9", "0x49"),
	ADDRESS_10("Address 10 (0X0A)", "0x4a"),
	ADDRESS_11("Address 11 (0X0B)", "0x4b"),
	ADDRESS_12("Address 12 (0X0C)", "0x4c"),
	ADDRESS_13("Address 13 (0X0D)", "0x4d"),
	ADDRESS_14("Address 14 (0X0E)", "0x4e"),
	ADDRESS_15("Address 15 (0X0F)", "0x4f"),
	ADDRESS_16("Address 16 (0X10)", "0x50"),
	ADDRESS_17("Address 17 (0X11)", "0x51"),
	ADDRESS_18("Address 18 (0X12)", "0x52"),
	ADDRESS_19("Address 19 (0X13)", "0x53"),
	ADDRESS_20("Address 20 (0X14)", "0x54"),
	ADDRESS_21("Address 21 (0X15)", "0x55"),
	ADDRESS_22("Address 22 (0X16)", "0x56"),
	ADDRESS_23("Address 23 (0X17)", "0x57"),
	ADDRESS_24("Address 24 (0X18)", "0x58"),
	ADDRESS_25("Address 25 (0X19)", "0x59"),
	ADDRESS_26("Address 26 (0X1A)", "0x5a"),
	ADDRESS_27("Address 27 (0X1B)", "0x5b"),
	ADDRESS_28("Address 28 (0X1C)", "0x5c"),
	ADDRESS_29("Address 29 (0X1D)", "0x5d"),
	ADDRESS_30("Address 30 (0X1E)", "0x5e"),
	ADDRESS_31("Address 31 (0X1F)", "0x5f"),
	ADDRESS_32("Address 32 (0X20)", "0x60"),
	ADDRESS_33("Address 33 (0X21)", "0x61"),
	ADDRESS_34("Address 34 (0X22)", "0x62"),
	ADDRESS_35("Address 35 (0X23)", "0x63"),
	ADDRESS_36("Address 36 (0X24)", "0x64"),
	ADDRESS_37("Address 37 (0X25)", "0x65"),
	ADDRESS_38("Address 38 (0X26)", "0x66"),
	ADDRESS_39("Address 39 (0X27)", "0x67"),
	ADDRESS_40("Address 40 (0X28)", "0x68"),
	ADDRESS_41("Address 41 (0X29)", "0x69"),
	ADDRESS_42("Address 42 (0X2A)", "0x6a"),
	ADDRESS_43("Address 43 (0X2B)", "0x6b"),
	ADDRESS_44("Address 44 (0X2C)", "0x6c"),
	ADDRESS_45("Address 45 (0X2D)", "0x6d"),
	ADDRESS_46("Address 46 (0X2E)", "0x6e"),
	ADDRESS_47("Address 47 (0X2F)", "0x6f"),
	ADDRESS_48("All Call (0X30)", "0x70"),
	ADDRESS_49("Address 49 (0X31)", "0x71"),
	ADDRESS_50("Address 50 (0X32)", "0x72"),
	ADDRESS_51("Address 51 (0X33)", "0x73"),
	ADDRESS_52("Address 52 (0X34)", "0x74"),
	ADDRESS_53("Address 53 (0X35)", "0x75"),
	ADDRESS_54("Address 54 (0X36)", "0x76"),
	ADDRESS_55("Address 55 (0X37)", "0x77"),
	ADDRESS_56("Address 56 (0X38)", "0x78"),
	ADDRESS_57("Address 57 (0X39)", "0x79"),
	ADDRESS_58("Address 58 (0X3A)", "0x7a"),
	ADDRESS_59("Address 59 (0X3B)", "0x7b"),
	ADDRESS_60("Address 60 (0X3C)", "0x7c"),
	ADDRESS_61("Address 61 (0X3D)", "0x7d"),
	ADDRESS_62("Address 62 (0X3E)", "0x7e"),
	ADDRESS_63("Address 63 (0X3F)", "0x7f");
	
	private String label;
	private String value;
	
	private PCA9685Address(String label, String value) {
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String getName() {
		return this.name();
	}

	@Override
	public List<PropertyValueEnum> getProperties() {
		return Arrays.asList(PCA9685Address.values());
	}

}
