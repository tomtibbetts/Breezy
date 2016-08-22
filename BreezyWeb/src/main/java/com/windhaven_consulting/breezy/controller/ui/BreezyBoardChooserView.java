package com.windhaven_consulting.breezy.controller.ui;


import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.BreezyBoardManager;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;

@ManagedBean
@SessionScoped
public class BreezyBoardChooserView {

	@Inject
	private BreezyBoardManager breezyBoardManager;
	
	public List<BreezyBoard> getBreezyBoards() {
		return breezyBoardManager.getAllBreezyBoards();
	}
	
}
