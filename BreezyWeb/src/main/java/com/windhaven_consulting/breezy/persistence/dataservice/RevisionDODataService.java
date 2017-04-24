package com.windhaven_consulting.breezy.persistence.dataservice;

import com.windhaven_consulting.breezy.persistence.domain.Revision;

public interface RevisionDODataService extends GenericDataService<Revision> {

	Revision createIfNotExist();

}
