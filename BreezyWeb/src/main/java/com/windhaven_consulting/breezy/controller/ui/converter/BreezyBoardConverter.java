package com.windhaven_consulting.breezy.controller.ui.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;

public class BreezyBoardConverter implements Converter {

	private Map<UUID, BreezyBoard> breezyBoardIdToBreezyBoardMap = new HashMap<UUID, BreezyBoard>();

	public BreezyBoardConverter(List<BreezyBoard> breezyBoards) {
		for(BreezyBoard breezyBoard : breezyBoards) {
			breezyBoardIdToBreezyBoardMap .put(breezyBoard.getId(), breezyBoard);
		}
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		BreezyBoard result = null;
		
		if(StringUtils.isNotEmpty(id)) {
			result = breezyBoardIdToBreezyBoardMap.get(id);
		}
		
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String breezyBoardName = StringUtils.EMPTY;
		
		if(value != null && !value.equals("")) {
			BreezyBoard breezyBoard = breezyBoardIdToBreezyBoardMap.get(UUID.fromString((String) value));
			
			if(breezyBoard != null) {
				breezyBoardName = breezyBoard.getName();
			}
		}
		
		return breezyBoardName;
	}

}
