package com.windhaven_consulting.breezy.persistence.dataservice.impl;

import javax.annotation.Resource;

import com.windhaven_consulting.breezy.persistence.dataservice.BreezyBoardDODataService;
import com.windhaven_consulting.breezy.persistence.domain.BreezyBoardDO;

public class BreezyBoardDODataServiceImpl extends BaseDODataServiceImpl<BreezyBoardDO> implements BreezyBoardDODataService {
	private static final String BOARD_FILE_EXTENSION = "board";
	
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
