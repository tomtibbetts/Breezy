package com.windhaven_consulting.breezy.manager;

import java.util.Collection;
import java.util.List;

import com.windhaven_consulting.breezy.manager.impl.MountedBoard;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;

public interface MountedBoardManager {
	
	List<MountedBoard> getAllMountedBoards();

	Collection<MountedComponent> getAllMountedComponents();
	
	MountedBoard getById(String id);

	@Deprecated
	MountedBoard getByName(String mountedBoardName);

	void provisionBoard(BreezyBoard breezyBoard);

	void unprovisionBoard(BreezyBoard breezyBoard);
}
