package com.windhaven_consulting.breezy.component.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

public class ComponentTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String componentName;
	
	private String clazzName;

	private Map<String, MethodTemplate> methodMap = new TreeMap<String, MethodTemplate>();

	private int numberOfOutputs;
	
	private List<String> pinNames;

	public ComponentTemplate(String clazzName, String componentName, int numberOfOutputs, String[] pinNames) {
		this.clazzName = clazzName;
		this.componentName = componentName;
		this.numberOfOutputs = numberOfOutputs;
		this.pinNames = new ArrayList<String>(pinNames.length);
		Collections.addAll(this.pinNames, pinNames);
	}
	
	public void add(MethodTemplate methodTemplate) {
		if(!methodMap.containsKey(methodTemplate.getComponentMethodName())) {
			methodMap.put(methodTemplate.getComponentMethodName(), methodTemplate);
		}
	}
	
	public int getNumberOfOutputs() {
		return numberOfOutputs;
	}

	public String getComponentName() {
		return componentName;
	}

	public String getClazzName() {
		return clazzName;
	}

	public MethodTemplate getMethodTemplate(String key) {
		return methodMap.get(key);
	}
	
	public Collection<MethodTemplate> getMethods() {
		return methodMap.values();
	}

	public List<String> getPinNames() {
		return pinNames;
	}
	
	public String getPinNameAt(int index) {
		String result = StringUtils.EMPTY;
		
		if(index < pinNames.size()) {
			result = pinNames.get(index);
		}
		
		return result;
	}
}
