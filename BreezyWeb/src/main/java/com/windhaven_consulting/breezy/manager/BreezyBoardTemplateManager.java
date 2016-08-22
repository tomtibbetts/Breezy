package com.windhaven_consulting.breezy.manager;

import java.util.List;

import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoardTemplate;

public interface BreezyBoardTemplateManager {

	List<BreezyBoardTemplate> getAllBoardTemplates();

	BreezyBoardTemplate getBoardTemplateById(String boardTemplateId);

	void saveBoardTemplate(BreezyBoardTemplate breezyBoardTemplate);

	void deleteBoardTemplate(BreezyBoardTemplate breezyBoardTemplate);

}
