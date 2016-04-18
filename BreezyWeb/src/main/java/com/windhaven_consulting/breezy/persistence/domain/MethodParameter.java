package com.windhaven_consulting.breezy.persistence.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;

public class MethodParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fieldName;
	
	private String fieldValue;

	private ParameterFieldType parameterFieldType;

	private boolean required;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@JsonIgnore
	public ParameterFieldType getParameterFieldType() {
		return parameterFieldType;
	}

	public void setParameterFieldType(ParameterFieldType parameterFieldType) {
		this.parameterFieldType = parameterFieldType;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	@JsonIgnore
	public boolean isRequired() {
		return required;
	}
	
	
}
