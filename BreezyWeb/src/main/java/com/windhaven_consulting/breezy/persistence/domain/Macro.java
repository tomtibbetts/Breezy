package com.windhaven_consulting.breezy.persistence.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Macro extends PersistentObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;
	
	private boolean autoStart;
	
	private boolean enabled;
	
	private List<MacroStep> steps = new ArrayList<MacroStep>();

	public List<MacroStep> getSteps() {
		return steps;
	}

	public void setSteps(List	<MacroStep> steps) {
		this.steps = steps;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAutoStart() {
		return autoStart;
	}

	public void setAutoStart(boolean autoStart) {
		this.autoStart = autoStart;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Macro other = (Macro) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
