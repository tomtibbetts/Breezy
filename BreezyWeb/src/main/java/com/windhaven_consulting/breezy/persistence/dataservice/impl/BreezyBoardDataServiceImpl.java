package com.windhaven_consulting.breezy.persistence.dataservice.impl;

import java.util.UUID;

import javax.annotation.Resource;

import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;
import com.windhaven_consulting.breezy.manager.viewobject.ComponentConfiguration;
import com.windhaven_consulting.breezy.manager.viewobject.Extension;
import com.windhaven_consulting.breezy.manager.viewobject.InputPinConfiguration;
import com.windhaven_consulting.breezy.manager.viewobject.OutputPinConfiguration;
import com.windhaven_consulting.breezy.persistence.dataservice.BreezyBoardDataService;

public class BreezyBoardDataServiceImpl extends BaseDODataServiceImpl<BreezyBoard> implements BreezyBoardDataService {

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

	@Override
	public void save(BreezyBoard breezyBoard) {
		for(Extension extension : breezyBoard.getExtensions()) {
			if(extension.getId() == null) {
				extension.setId(UUID.randomUUID());
			}
		}
		
		for(InputPinConfiguration inputPinConfiguration : breezyBoard.getInputPinConfigurations()) {
			if(inputPinConfiguration.getId() == null) {
				inputPinConfiguration.setId(UUID.randomUUID());
			}
		}
		
		for(ComponentConfiguration componentConfiguration : breezyBoard.getComponentConfigurations()) {
			if(componentConfiguration.getId() == null) {
				componentConfiguration.setId(UUID.randomUUID());
			}
			
			for(OutputPinConfiguration outputPinConfiguration : componentConfiguration.getOutputPinConfigurations()) {
				if(outputPinConfiguration.getId() == null) {
					outputPinConfiguration.setId(UUID.randomUUID());
				}
			}
		}
		
		super.save(breezyBoard);
	}

}
