package com.windhaven_consulting.breezy.persistence.domain;

public class Revision extends PersistentObject {

	private Integer major;
	
	private Integer minor;
	
	private Integer fix;

	public Integer getMajor() {
		return major;
	}

	public void setMajor(Integer major) {
		this.major = major;
	}

	public Integer getMinor() {
		return minor;
	}

	public void setMinor(Integer minor) {
		this.minor = minor;
	}

	public Integer getFix() {
		return fix;
	}

	public void setFix(Integer fix) {
		this.fix = fix;
	}
	
}
