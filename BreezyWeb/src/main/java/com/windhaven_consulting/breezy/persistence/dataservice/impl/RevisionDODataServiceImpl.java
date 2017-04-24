package com.windhaven_consulting.breezy.persistence.dataservice.impl;

import java.util.List;

import javax.annotation.Resource;

import com.windhaven_consulting.breezy.persistence.dataservice.RevisionDODataService;
import com.windhaven_consulting.breezy.persistence.domain.Revision;

public class RevisionDODataServiceImpl extends BaseDODataServiceImpl<Revision> implements RevisionDODataService {
	
	private static final String REVISION_FILE_EXTENSION = "revision";
	
	@Resource(name="breezy.revisionResourcePath")
	private String revisionResourcePath;

	@Resource(name="breezy.releaseRevisionMajor")
	private Integer revisionMajor;

	@Resource(name="breezy.releaseRevisionMinor")
	private Integer revisionMinor;

	@Resource(name="breezy.releaseRevisionFix")
	private Integer revisionFix;

	@Override
	protected String getResoucePath() {
		return revisionResourcePath;
	}

	@Override
	protected String getFileExtension() {
		return REVISION_FILE_EXTENSION;
	}

	@Override
	public Revision findById(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Revision createIfNotExist() {
		Revision result = null;
		
		List<Revision> revisions = findAll();
		
		if(revisions.isEmpty()) {
			Revision revision = new Revision();
			revision.setMajor(revisionMajor);
			revision.setMinor(revisionMinor);
			revision.setFix(revisionFix);
			
			save(revision);
			result = revision;
		}
		else {
			result = revisions.get(0);
		}
		
		return result;
	}

}
