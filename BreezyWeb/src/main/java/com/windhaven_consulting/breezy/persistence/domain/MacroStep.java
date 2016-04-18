package com.windhaven_consulting.breezy.persistence.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MacroStep extends PersistentObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private int step;
	
	private String tag;
	
	private String component;
	
	private String componentId;
	
	private String componentType;
	
	private String function;
	
	private String comment;
	
	private List<MethodParameter> methodParameters = new ArrayList<MethodParameter>();
	
	private String value;
	
	private String mountedBoardName;
	
	private String mountedBoardId;

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getComponentType() {
		return componentType;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<MethodParameter> getMethodParameters() {
		return methodParameters;
	}

	public void setMethodParameters(List<MethodParameter> methodParameters) {
		this.methodParameters = methodParameters;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMountedBoardName() {
		return mountedBoardName;
	}

	public void setMountedBoardName(String mountedBoardName) {
		this.mountedBoardName = mountedBoardName;
	}

	public String getMountedBoardId() {
		return mountedBoardId;
	}

	public void setMountedBoardId(String mountedBoardId) {
		this.mountedBoardId = mountedBoardId;
	}
	
}
