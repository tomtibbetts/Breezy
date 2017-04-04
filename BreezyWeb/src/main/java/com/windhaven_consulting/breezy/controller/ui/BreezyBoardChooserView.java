package com.windhaven_consulting.breezy.controller.ui;


import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.BreezyBoardManager;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;

@ManagedBean
@ViewScoped
public class BreezyBoardChooserView {

	@Inject
	private BreezyBoardManager breezyBoardManager;
	
	public List<BreezyBoard> getBreezyBoards() {
		List<BreezyBoard> breezyBoards = new ArrayList<BreezyBoard>();
		
		for(BreezyBoard breezyBoard : breezyBoardManager.getAllBreezyBoards()) {
			if(!breezyBoard.isRestricted()) {
				breezyBoards.add(breezyBoard);
			}
		}
		
		return breezyBoards;
	}
	
}
