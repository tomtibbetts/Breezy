package com.windhaven_consulting.breezy.persistence.dataservice.impl;

import javax.annotation.Resource;

import com.windhaven_consulting.breezy.persistence.dataservice.BreezyBoardTemplateDODataService;
import com.windhaven_consulting.breezy.persistence.domain.BreezyBoardTemplateDO;

public class BreezyBoardTemplateDODataServiceImpl extends BaseDODataServiceImpl<BreezyBoardTemplateDO> implements BreezyBoardTemplateDODataService {

	private static final String BOARD_FILE_EXTENSION = "boardtemplate";
	
	@Resource(name="breezy.boardsResourcePath")
	private String boardsResourcePath;


	@Override
	protected String getResoucePath() {
		return boardsResourcePath;
	}

	@Override
	protected String getFileExtension() {
		return BOARD_FILE_EXTENSION;
	}
}
