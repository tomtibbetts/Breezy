package com.windhaven_consulting.breezy.component.library;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;

public class ParameterTemplate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String argumentName;
	
	private String argumentLabel;

	private Class<?> argumentType;

	private boolean required;

	private ParameterFieldType parameterFieldType;

	public ParameterTemplate(String argumentName, String argumentLabel, Class<?> argumentType, boolean required, ParameterFieldType parameterFieldType) {
		this.argumentName = argumentName;
		this.argumentLabel = argumentLabel;
		this.argumentType = argumentType;
		this.required = required;
		this.parameterFieldType = parameterFieldType;
	}
	
	public Class<?> getArgumentType() {
		return argumentType;
	}

	public String getArgumentLabel() {
		return argumentLabel;
	}

	@JsonIgnore
	public String getArgumentName() {
		return argumentName;
	}

	public boolean isRequired() {
		return required;
	}

	public ParameterFieldType getParameterFieldType() {
		return parameterFieldType;
	}
}

