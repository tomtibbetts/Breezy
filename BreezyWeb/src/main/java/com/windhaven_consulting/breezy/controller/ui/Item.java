package com.windhaven_consulting.breezy.controller.ui;

import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String columnOneValue;
	private String columnTwoValue;
	
	public Item(String name, String columnOneValue, String columnTwoValue) {
		this.name = name;
		this.columnOneValue = columnOneValue;
		this.columnTwoValue = columnTwoValue;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColumnOneValue() {
		return columnOneValue;
	}
	public void setColumnOneValue(String columnOneValue) {
		this.columnOneValue = columnOneValue;
	}
	public String getColumnTwoValue() {
		return columnTwoValue;
	}
	public void setColumnTwoValue(String columnTwoValue) {
		this.columnTwoValue = columnTwoValue;
	}
	
}
