package com.windhaven_consulting.breezy.manager;

import java.util.List;

import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;

public interface BreezyBoardManager {

	List<BreezyBoard> getAllBreezyBoards();

	BreezyBoard getBreezyBoardById(String id);

	void saveBoard(BreezyBoard breezyBoard);

	void deleteBoard(BreezyBoard breezyBoard);
	
	void mountBoard(BreezyBoard breezyBoard);
	
	void unmountBoardAndSave(BreezyBoard breezyBoard);

	void mountBoardAndSave(BreezyBoard breezyBoard);
}
