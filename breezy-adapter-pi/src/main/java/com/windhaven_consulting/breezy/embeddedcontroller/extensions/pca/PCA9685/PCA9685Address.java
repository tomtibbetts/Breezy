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
	ADDRESS_10("Address 10", "0x4a"),
	ADDRESS_11("Address 11", "0x4b"),
	ADDRESS_12("Address 12", "0x4c"),
	ADDRESS_13("Address 13", "0x4d"),
	ADDRESS_14("Address 14", "0x4e"),
	ADDRESS_15("Address 15", "0x4f"),
	ADDRESS_16("Address 16", "0x50"),
	ADDRESS_17("Address 17", "0x51"),
	ADDRESS_18("Address 18", "0x52"),
	ADDRESS_19("Address 19", "0x53"),
	ADDRESS_20("Address 20", "0x54"),
	ADDRESS_21("Address 21", "0x55"),
	ADDRESS_22("Address 22", "0x56"),
	ADDRESS_23("Address 23", "0x57"),
	ADDRESS_24("Address 24", "0x58"),
	ADDRESS_25("Address 25", "0x59"),
	ADDRESS_26("Address 26", "0x5a"),
	ADDRESS_27("Address 27", "0x5b"),
	ADDRESS_28("Address 28", "0x5c"),
	ADDRESS_29("Address 29", "0x5d"),
	ADDRESS_30("Address 30", "0x5e"),
	ADDRESS_31("Address 31", "0x5f"),
	ADDRESS_32("Address 0", "0x60"),
	ADDRESS_33("Address 1", "0x61"),
	ADDRESS_34("Address 2", "0x62"),
	ADDRESS_35("Address 3", "0x63"),
	ADDRESS_36("Address 4", "0x64"),
	ADDRESS_37("Address 5", "0x65"),
	ADDRESS_38("Address 6", "0x66"),
	ADDRESS_39("Address 7", "0x67"),
	ADDRESS_40("Address 8", "0x68"),
	ADDRESS_41("Address 9", "0x69"),
	ADDRESS_42("Address 10", "0x6a"),
	ADDRESS_43("Address 11", "0x6b"),
	ADDRESS_44("Address 12", "0x6c"),
	ADDRESS_45("Address 13", "0x6d"),
	ADDRESS_46("Address 14", "0x6e"),
	ADDRESS_47("Address 15", "0x6f"),
	ADDRESS_48("All Call", "0x70"),
	ADDRESS_49("Address 17", "0x71"),
	ADDRESS_50("Address 18", "0x72"),
	ADDRESS_51("Address 19", "0x73"),
	ADDRESS_52("Address 20", "0x74"),
	ADDRESS_53("Address 21", "0x75"),
	ADDRESS_54("Address 22", "0x76"),
	ADDRESS_55("Address 23", "0x77"),
	ADDRESS_56("Address 24", "0x78"),
	ADDRESS_57("Address 25", "0x79"),
	ADDRESS_58("Address 26", "0x7a"),
	ADDRESS_59("Address 27", "0x7b"),
	ADDRESS_60("Address 28", "0x7c"),
	ADDRESS_61("Address 29", "0x7d"),
	ADDRESS_62("Address 30", "0x7e"),
	ADDRESS_63("Address 31", "0x7f");
	
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
