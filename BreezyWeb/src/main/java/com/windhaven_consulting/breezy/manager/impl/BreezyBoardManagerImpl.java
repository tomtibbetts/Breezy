package com.windhaven_consulting.breezy.manager.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.BreezyBoardManager;
import com.windhaven_consulting.breezy.manager.ViewDiskObjectMapper;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;
import com.windhaven_consulting.breezy.persistence.dataservice.BreezyBoardDODataService;
import com.windhaven_consulting.breezy.persistence.domain.BreezyBoardDO;

@ApplicationScoped
public class BreezyBoardManagerImpl implements BreezyBoardManager {

	@Inject
	private ViewDiskObjectMapper<BreezyBoard, BreezyBoardDO> breezyBoardViewMapper;
	
	@Inject
	private BreezyBoardDODataService breezyBoardDODataService;
	
	@Override
	public List<BreezyBoard> getAllBreezyBoards() {
		return breezyBoardViewMapper.getAsViewObjects(breezyBoardDODataService.findAll());
	}

	@Override
	public BreezyBoard getBreezyBoardById(String id) {
		return breezyBoardViewMapper.getAsViewObject(breezyBoardDODataService.findById(id));
	}

	@Override
	public void saveBoard(BreezyBoard breezyBoard) {
		BreezyBoardDO breezyBoardDO = breezyBoardViewMapper.getAsDiskObject(breezyBoard);
		breezyBoardDODataService.save(breezyBoardDO);
		breezyBoard.setId(breezyBoardDO.getId());
	}

	@Override
	public void deleteBoard(BreezyBoard breezyBoard) {
		BreezyBoardDO breezyBoardDO = breezyBoardViewMapper.getAsDiskObject(breezyBoard);
		breezyBoardDODataService.delete(breezyBoardDO);
	}

}
