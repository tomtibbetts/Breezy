package com.windhaven_consulting.breezy.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.SystemBoardFactory;
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
	
	private BreezyBoard systemBoard = SystemBoardFactory.getSystemBoard();
	
	@Override
	public List<BreezyBoard> getAllBreezyBoards() {
		List<BreezyBoard> breezyBoards = new ArrayList<BreezyBoard>();
		breezyBoards.add(systemBoard);
		breezyBoards.addAll(breezyBoardViewMapper.getAsViewObjects(breezyBoardDODataService.findAll()));
		
		return breezyBoards;
	}

	@Override
	public BreezyBoard getBreezyBoardById(String id) {
		BreezyBoard breezyBoard = null;
		
		if(StringUtils.isNotEmpty(id) && systemBoard.getId().equals(UUID.fromString(id))) {
			breezyBoard = systemBoard;
		}
		else {
			breezyBoard = breezyBoardViewMapper.getAsViewObject(breezyBoardDODataService.findById(id));
		}
		
		return breezyBoard;
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
		breezyBoard.setMounted(true);
		mountedBoardManager.provisionBoard(breezyBoard);
		saveBoard(breezyBoard);
	}

	@Override
	public void mountBoard(BreezyBoard breezyBoard) {
		mountedBoardManager.provisionBoard(breezyBoard);
	}

	@Override
	public void unmountBoardAndSave(BreezyBoard breezyBoard) {
		mountedBoardManager.unprovisionBoard(breezyBoard);
		breezyBoard.setMounted(false);
		saveBoard(breezyBoard);
	}

}
