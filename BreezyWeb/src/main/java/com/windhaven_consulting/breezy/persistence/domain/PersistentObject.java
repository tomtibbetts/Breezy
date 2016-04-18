package com.windhaven_consulting.breezy.persistence.domain;

import java.util.UUID;

public class PersistentObject implements Comparable<PersistentObject> {

	private String name;
	
	private UUID id;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int compareTo(PersistentObject o) {
		return this.name.compareToIgnoreCase(o.getName());
	}

}
