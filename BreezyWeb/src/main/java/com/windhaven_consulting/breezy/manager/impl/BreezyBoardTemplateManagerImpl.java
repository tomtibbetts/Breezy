package com.windhaven_consulting.breezy.manager.impl;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.windhaven_consulting.breezy.manager.BreezyBoardTemplateManager;
import com.windhaven_consulting.breezy.manager.ViewDiskObjectMapper;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoardTemplate;
import com.windhaven_consulting.breezy.persistence.dataservice.BreezyBoardTemplateDODataService;
import com.windhaven_consulting.breezy.persistence.domain.BreezyBoardTemplateDO;

@ApplicationScoped
public class BreezyBoardTemplateManagerImpl implements BreezyBoardTemplateManager {

	@Inject
	private ViewDiskObjectMapper<BreezyBoardTemplate, BreezyBoardTemplateDO> breezyBoardTemplateViewMapper;
	
	@Inject
	private BreezyBoardTemplateDODataService breezyBoardTemplateDODataService;
	
	@Override
	public List<BreezyBoardTemplate> getAllBoardTemplates() {
		return breezyBoardTemplateViewMapper.getAsViewObjects(breezyBoardTemplateDODataService.findAll());
	}

	@Override
	public BreezyBoardTemplate getBoardTemplateById(String id) {
		return breezyBoardTemplateViewMapper.getAsViewObject(breezyBoardTemplateDODataService.findById(id));
	}

	@Override
	public void saveBoardTemplate(BreezyBoardTemplate breezyBoardTemplate) {
		BreezyBoardTemplateDO breezyBoardTemplateDO = breezyBoardTemplateViewMapper.getAsDiskObject(breezyBoardTemplate);
		breezyBoardTemplateDODataService.save(breezyBoardTemplateDO);
		breezyBoardTemplate.setId(breezyBoardTemplateDO.getId());
	}

	@Override
	public void deleteBoardTemplate(BreezyBoardTemplate breezyBoardTemplate) {
		breezyBoardTemplateDODataService.delete(breezyBoardTemplateViewMapper.getAsDiskObject(breezyBoardTemplate));
	}

}
