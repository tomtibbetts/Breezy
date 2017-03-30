package com.windhaven_consulting.breezy.manager.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.BreezyBoardManager;
import com.windhaven_consulting.breezy.manager.MountedBoardManager;
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
	
	@Inject
	private MountedBoardManager mountedBoardManager;
	
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
		mountedBoardManager.unprovisionBoard(breezyBoard);
		
		BreezyBoardDO breezyBoardDO = breezyBoardViewMapper.getAsDiskObject(breezyBoard);
		breezyBoardDODataService.delete(breezyBoardDO);
	}

	@Override
	public void mountBoardAndSave(BreezyBoard breezyBoard) {
		mountedBoardManager.provisionBoard(breezyBoard);
		saveBoard(breezyBoard);
	}

	@Override
	public void mountBoard(BreezyBoard breezyBoard) {
		mountedBoardManager.provisionBoard(breezyBoard);
	}

	@Override
	public void unmountBoard(BreezyBoard breezyBoard) {
		mountedBoardManager.unprovisionBoard(breezyBoard);
		breezyBoard.setMounted(false);
		
		// we re-provision the board in an unmounted state so that its inputs and components still appear
		// in various UI screens using mock values
		mountedBoardManager.provisionBoard(breezyBoard);
		
		saveBoard(breezyBoard);
	}

}
