package com.windhaven_consulting.breezy.persistence.dataservice.impl;

import javax.annotation.Resource;

import com.windhaven_consulting.breezy.persistence.dataservice.MacroDataService;
import com.windhaven_consulting.breezy.persistence.domain.Macro;

public class MacroDataServiceImpl extends BaseDODataServiceImpl<Macro> implements MacroDataService {

	private static final String MACRO_FILE_EXTENSION = "macro";
	
	@Resource(name="breezy.macrosResourcePath")
	private String macroResourcePath;

	@Resource(name="breezy.releaseRevisionNumber")
	private String releaseRevisionNumber;

	@Override
	protected String getResoucePath() {
		return macroResourcePath;
	}

	@Override
	protected String getFileExtension() {
		return MACRO_FILE_EXTENSION;
	}

}
