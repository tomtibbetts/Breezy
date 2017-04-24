package com.windhaven_consulting.breezy.manager.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.RevisionManager;
import com.windhaven_consulting.breezy.persistence.dataservice.RevisionDODataService;
import com.windhaven_consulting.breezy.persistence.domain.Revision;

@ApplicationScoped
public class RevisionManagerImpl implements RevisionManager {

	@Inject
	private RevisionDODataService revisionDODataService;
	
	@Override
	public void save(Revision revision) {
		revisionDODataService.save(revision);
	}

	@Override
	public Revision getCurrentRevision() {
		Revision result = null;
		
		List<Revision> results = revisionDODataService.findAll();
		
		if(!results.isEmpty()) {
			result = results.get(0);
		}
		
		return result;
	}

	@Override
	public Revision createIfNotExist() {
		return revisionDODataService.createIfNotExist();
	}

}
