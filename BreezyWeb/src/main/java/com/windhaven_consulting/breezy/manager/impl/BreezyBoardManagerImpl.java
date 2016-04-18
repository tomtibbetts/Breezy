package com.windhaven_consulting.breezy.manager.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.BreezyBoardManager;
import com.windhaven_consulting.breezy.persistence.dataservice.BreezyBoardDataService;
import com.windhaven_consulting.breezy.persistence.domain.BreezyBoard;

@ApplicationScoped
public class BreezyBoardManagerImpl implements BreezyBoardManager {

	@Inject
	private BreezyBoardDataService breezyBoardDataService;
	
	@Override
	public List<BreezyBoard> getAllBreezyBoards() {
		return breezyBoardDataService.findAll();
	}

	@Override
	public BreezyBoard getBreezyBoardById(String id) {
		return breezyBoardDataService.findById(id);
	}

	@Override
	public void saveBoard(BreezyBoard breezyBoard) {
		breezyBoardDataService.save(breezyBoard);
	}

	@Override
	public void deleteBoard(BreezyBoard breezyBoard) {
		breezyBoardDataService.delete(breezyBoard);
	}

}
