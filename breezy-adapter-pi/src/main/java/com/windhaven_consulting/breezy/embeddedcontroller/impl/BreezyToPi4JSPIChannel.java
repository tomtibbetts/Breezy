package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.util.HashMap;
import java.util.Map;

import com.pi4j.io.spi.SpiChannel;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezySPIChannel;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;

public class BreezyToPi4JSPIChannel {
	private static Map<BreezySPIChannel, SpiChannel> breezyToPi4JSPIChannelMap = new HashMap<BreezySPIChannel, SpiChannel>();

	static {
		breezyToPi4JSPIChannelMap.put(BreezySPIChannel.CHANNEL_0, SpiChannel.CS0);
		breezyToPi4JSPIChannelMap.put(BreezySPIChannel.CHANNEL_1, SpiChannel.CS1);
	}
	
	public static SpiChannel getChannel(BreezySPIChannel breezySPIChannel) {
		SpiChannel result = breezyToPi4JSPIChannelMap.get(breezySPIChannel);
		
		if(result == null) {
			throw new EmbeddedControllerException("Breezy SPI Channel, '" + breezySPIChannel.toString() + "' has no Pi4J (Raspberry Pi) equivalent.");
		}
		
		return result;
	}

}
