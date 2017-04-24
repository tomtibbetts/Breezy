package com.windhaven_consulting.breezy.manager;

import com.windhaven_consulting.breezy.persistence.domain.Revision;

public interface RevisionManager {

	void save(Revision revision);
	
	Revision getCurrentRevision();

	Revision createIfNotExist();
}
