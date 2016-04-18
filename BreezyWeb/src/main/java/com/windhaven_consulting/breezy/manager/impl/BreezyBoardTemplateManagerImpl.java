package com.windhaven_consulting.breezy.manager.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.BreezyBoardTemplateManager;
import com.windhaven_consulting.breezy.persistence.dataservice.BreezyBoardTemplateDataService;
import com.windhaven_consulting.breezy.persistence.domain.BreezyBoardTemplate;

@ApplicationScoped
public class BreezyBoardTemplateManagerImpl implements BreezyBoardTemplateManager {

	@Inject
	private BreezyBoardTemplateDataService breezyBoardTemplateDataService;
	
	@Override
	public List<BreezyBoardTemplate> getAllBoardTemplates() {
		return breezyBoardTemplateDataService.findAll();
	}

	@Override
	public BreezyBoardTemplate getBoardTemplateById(String id) {
		return breezyBoardTemplateDataService.findById(id);
	}

	@Override
	public void saveBoardTemplate(BreezyBoardTemplate breezyBoardTemplate) {
		breezyBoardTemplateDataService.save(breezyBoardTemplate);
	}

	@Override
	public void deleteBoardTemplate(BreezyBoardTemplate breezyBoardTemplate) {
		breezyBoardTemplateDataService.delete(breezyBoardTemplate);
	}

}
