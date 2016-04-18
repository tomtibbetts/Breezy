package com.windhaven_consulting.breezy.controller.ui.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.manager.impl.MountedBoard;

public class MountedBoardConverter implements Converter {
	
	private Map<String, MountedBoard> mountedBoardIdToMountedBoardMap = new HashMap<String, MountedBoard>();

	public MountedBoardConverter(List<MountedBoard> mountedBoards) {
		for(MountedBoard mountedBoard : mountedBoards) {
			mountedBoardIdToMountedBoardMap.put(mountedBoard.getId(), mountedBoard);
		}
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String id) {
		MountedBoard result = null;
		
		if(StringUtils.isNotEmpty(id)) {
			result = mountedBoardIdToMountedBoardMap.get(id);
		}
		
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String mountedBoardName = StringUtils.EMPTY;
		
		if(value != null && !value.equals("")) {
			MountedBoard mountedBoard = mountedBoardIdToMountedBoardMap.get((String) value);
			
			if(mountedBoard != null) {
				mountedBoardName = mountedBoard.getName();
			}
		}
		
		return mountedBoardName;
	}

}
