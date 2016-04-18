package com.windhaven_consulting.breezy.component.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MethodTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String method;
	
	private String componentMethodName;
	
	private List<ParameterTemplate> parameters = new ArrayList<ParameterTemplate>();

	public MethodTemplate(String method, String componentMethodName) {
		this.method = method;
		this.componentMethodName = componentMethodName;
	}
	
	public String getMethod() {
		return method;
	}
	
	public String getComponentMethodName() {
		return componentMethodName;
	}

	public void add(ParameterTemplate parameterDescriptor) {
		parameters.add(parameterDescriptor);
	}
	
	public List<ParameterTemplate> getParameters() {
		return parameters;
	}

}
