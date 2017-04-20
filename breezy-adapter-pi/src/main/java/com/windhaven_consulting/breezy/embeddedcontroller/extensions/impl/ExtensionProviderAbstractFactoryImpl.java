package com.windhaven_consulting.breezy.embeddedcontroller.extensions.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPinProperty;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.StateChange;
import com.windhaven_consulting.breezy.embeddedcontroller.StateChangeEvent;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerRuntimeException;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProvider;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderAbstractFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionProviderFactory;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mock.MockDigitalExtensionProviderFactoryImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.mock.MockPWMExtensionProviderFactoryImpl;
import com.windhaven_consulting.breezy.embeddedcontroller.impl.Pi4JControllerProxyImpl;

public class ExtensionProviderAbstractFactoryImpl implements ExtensionProviderAbstractFactory {
	static final Logger LOG = LoggerFactory.getLogger(ExtensionProviderAbstractFactoryImpl.class);

	@Resource
	private Boolean windowsEnvironment;

	@Inject
	@Named("systemExtensionProviderFactory")
	private ExtensionProviderFactory<BreezyPin> systemExtensionProviderFactory;
	
	@Inject
	@Named("mcp23S08ExtensionProviderFactory")
	private ExtensionProviderFactory<BreezyPin> mcp23S08ExtensionProviderFactory;
	
	@Inject
	@Named("mcp23017ExtensionProviderFactory")
	private ExtensionProviderFactory<BreezyPin> mcp23017ExtensionProviderFactory;
	
	@Inject
	@Named("mcp23S17ExtensionProviderFactory")
	private ExtensionProviderFactory<BreezyPin> mcp23S17ExtensionProviderFactory;
	
	@Inject
	@Named("pca9685ExtensionProviderFactory")
	private ExtensionProviderFactory<BreezyPin> pca9685ExtensionProviderFactory;

	@Inject
	@StateChange
	private Event<StateChangeEvent> events;
	
	@Inject
	private Pi4JControllerProxyImpl pi4JControllerProxy;

	private GpioPinListenerDigital gpioPinListenerDigital;

	
	private Map<ExtensionType, ExtensionProviderFactory<BreezyPin>> extensionTypeToProviderFactoryMap = new HashMap<ExtensionType, ExtensionProviderFactory<BreezyPin>>();
	
	private Map<ExtensionType, ExtensionProviderFactory<BreezyPin>> mockExtensionTypeToProviderFactoryMap = new HashMap<ExtensionType, ExtensionProviderFactory<BreezyPin>>();
	
	@PostConstruct
	public void postConstruct() {
		extensionTypeToProviderFactoryMap.put(ExtensionType.SYSTEM, systemExtensionProviderFactory);
		extensionTypeToProviderFactoryMap.put(ExtensionType.MCP23S08, mcp23S08ExtensionProviderFactory);
		extensionTypeToProviderFactoryMap.put(ExtensionType.MCP23017, mcp23017ExtensionProviderFactory);
		extensionTypeToProviderFactoryMap.put(ExtensionType.MCP23S17, mcp23S17ExtensionProviderFactory);
		extensionTypeToProviderFactoryMap.put(ExtensionType.PCA9685, pca9685ExtensionProviderFactory);
		
		mockExtensionTypeToProviderFactoryMap.put(ExtensionType.SYSTEM, new MockDigitalExtensionProviderFactoryImpl(systemExtensionProviderFactory));
		mockExtensionTypeToProviderFactoryMap.put(ExtensionType.MCP23S08, new MockDigitalExtensionProviderFactoryImpl(mcp23S08ExtensionProviderFactory));
		mockExtensionTypeToProviderFactoryMap.put(ExtensionType.MCP23017, new MockDigitalExtensionProviderFactoryImpl(mcp23017ExtensionProviderFactory));
		mockExtensionTypeToProviderFactoryMap.put(ExtensionType.MCP23S17, new MockDigitalExtensionProviderFactoryImpl(mcp23S17ExtensionProviderFactory));
		mockExtensionTypeToProviderFactoryMap.put(ExtensionType.PCA9685, new MockPWMExtensionProviderFactoryImpl(pca9685ExtensionProviderFactory));
		
		initializeInputListener();
	}
	
	@Override
	/**
	 * returns a new instance of an ExtensionProvider (prototype pattern)
	 */
	public ExtensionProvider<BreezyPin> getNewExtensionProvider(ExtensionType extensionType, Map<String, String> properties) throws EmbeddedControllerException {
		
		ExtensionProviderFactory<BreezyPin> extensionProviderFactory = null;
		ExtensionProvider<BreezyPin> extensionProvider = null;
		
		if(!isWindowsEnvironment()) {
			extensionProviderFactory = getExtensionProviderFactory(extensionType);
			
			extensionProviderFactory.validateProperties(properties);

			GpioController gpioController = pi4JControllerProxy.getGpioController();;
			GpioProvider gpioProvider;

			gpioProvider = extensionProviderFactory.getGpioProvider(properties);
		
			// get extension provider from typed factory
			extensionProvider = extensionProviderFactory.getExtensionProvider(gpioController, gpioProvider, gpioPinListenerDigital);
		}
		else {
			extensionProviderFactory = getMockExtensionProviderFactory(extensionType);
			extensionProviderFactory.validateProperties(properties);

			extensionProvider = extensionProviderFactory.getExtensionProvider(null, null, null);
		}
		
		return extensionProvider;
	}

	@Override
	public List<String> getPropertyFieldNames(ExtensionType extensionType) {
		ExtensionProviderFactory<BreezyPin> extensionProviderFactory = getExtensionProviderFactory(extensionType);
		return extensionProviderFactory.getPropertyFieldNames();
	}

	@Override
	public List<BreezyPin> getAvailablePins(ExtensionType extensionType) {
		ExtensionProviderFactory<BreezyPin> extensionProviderFactory = getExtensionProviderFactory(extensionType);
		return extensionProviderFactory.getAvailablePins();
	}

	@Override
	public List<PropertyValueEnum> getProperties(ExtensionType extensionType) {
		ExtensionProviderFactory<BreezyPin> extensionProviderFactory = getExtensionProviderFactory(extensionType);
		return extensionProviderFactory.getProperties();
	}

	@Override
	public List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, PropertyValueEnum property) {
		ExtensionProviderFactory<BreezyPin> extensionProviderFactory = getExtensionProviderFactory(extensionType);
		return extensionProviderFactory.getPropertyValues(property);
	}
	
	@Override
	public List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, String property) {
		ExtensionProviderFactory<BreezyPin> extensionProviderFactory = getExtensionProviderFactory(extensionType);
		return extensionProviderFactory.getPropertyValues(property);
	}
	
	private void initializeInputListener() {
//		LOG.debug("Creating a digital pin listener\n");
		gpioPinListenerDigital = new GpioPinListenerDigital() {
		
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
//				LOG.debug("Firing StateChangeEvent: " + event.getPin().getProperty(BreezyPinProperty.NAME.name()) + ", id: " + event.getPin().getProperty(BreezyPinProperty.ID.name()));
				
				GpioPin gpioPin = event.getPin();
				com.pi4j.io.gpio.PinState pinState = event.getState();
				
				StateChangeEvent stateChangeEvent = new StateChangeEvent();
				stateChangeEvent.setName(gpioPin.getProperty(BreezyPinProperty.NAME.name()));
				stateChangeEvent.setId(gpioPin.getProperty(BreezyPinProperty.ID.name()));
				stateChangeEvent.setPinState(PinState.find(pinState.getValue()));

				events.fire(stateChangeEvent);
			}
		}; 
	}

	private ExtensionProviderFactory<BreezyPin> getExtensionProviderFactory(ExtensionType extensionType) {
		ExtensionProviderFactory<BreezyPin> extensionProviderFactory = extensionTypeToProviderFactoryMap.get(extensionType);

		if(extensionProviderFactory == null) {
			throw new EmbeddedControllerRuntimeException("No Extension Provider Factory found for '" + extensionType.name() + "'.");
		}
		
		return extensionProviderFactory;
	}

	private ExtensionProviderFactory<BreezyPin> getMockExtensionProviderFactory(ExtensionType extensionType) {
		ExtensionProviderFactory<BreezyPin> mockExtensionProviderFactory = mockExtensionTypeToProviderFactoryMap.get(extensionType);

		if(mockExtensionProviderFactory == null) {
			throw new EmbeddedControllerRuntimeException("No Mock Extension Provider Factory found for '" + extensionType.name() + "'.");
		}
		
		return mockExtensionProviderFactory;
	}

	protected boolean isWindowsEnvironment() {
		return windowsEnvironment != null && windowsEnvironment == true;
	}
	
}
