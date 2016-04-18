package com.windhaven_consulting.breezy.embeddedcontroller.impl;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.system.NetworkInfo;
import com.pi4j.system.SystemInfo;
import com.windhaven_consulting.breezy.embeddedcontroller.EmbeddedControllerAdapter;
import com.windhaven_consulting.breezy.embeddedcontroller.SystemStatus;
import com.windhaven_consulting.breezy.embeddedcontroller.exceptions.EmbeddedControllerException;

@ApplicationScoped
public class Pi4JControllerProxyImpl implements EmbeddedControllerAdapter {
	static final Logger LOG = LoggerFactory.getLogger(Pi4JControllerProxyImpl.class);

	@Resource
	private Boolean windowsEnvironment;
	
	private static GpioController gpioController;
	
	@PostConstruct
	public void postConstruct() {
		LOG.debug("Starting GpioFactory");
		
		if(!isWindowsEnvironment()) {
			gpioController = GpioFactory.getInstance();
		}
	}

	@PreDestroy
	public void preDestroy() {
		LOG.debug("Shutting down GpioFactory");
		
		if(!isWindowsEnvironment()) {
			gpioController.shutdown();
			gpioController = null;
		}
	}
	
	@Override
	public SystemStatus getSystemInfo() {
		SystemStatus systemStatus = new SystemStatus();

		if(isWindowsEnvironment()) {
			systemStatus.getIpAddresses().add("a Windows machine.");
		}
		else {
			try {
			systemStatus.setId(UUID.randomUUID().toString());
			systemStatus.setCpuArchitecture(SystemInfo.getCpuArchitecture());
			systemStatus.setCpuPart(SystemInfo.getCpuPart());
			systemStatus.setCpuRevision(SystemInfo.getCpuRevision());
			systemStatus.setCpuTemperature(SystemInfo.getCpuTemperature());
			systemStatus.setSerialNumber(SystemInfo.getSerial());
			
			systemStatus.setHostName(NetworkInfo.getHostname());
			
			for(String ipAddress : NetworkInfo.getIPAddresses()) {
				systemStatus.getIpAddresses().add(ipAddress);
			}
			
			} catch (IOException e) {
				throw new EmbeddedControllerException("IO Exception caught", e);
			} catch (InterruptedException e) {
				throw new EmbeddedControllerException("Interrupted Exception caught", e);
			} catch (Exception e) {
				throw new EmbeddedControllerException("Exception caught", e);
			}
		}
		
		return systemStatus;
	}
	
	public GpioController getGpioController() {
		return gpioController;
	}
	
	private boolean isWindowsEnvironment() {
		return windowsEnvironment != null && windowsEnvironment == true;
	}
}
