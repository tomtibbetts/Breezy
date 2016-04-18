package com.windhaven_consulting.breezy.controller.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.controller.ui.converter.DigitalInputPinConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.MountedBoardConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.PinStateConverter;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;
import com.windhaven_consulting.breezy.manager.MacroManager;
import com.windhaven_consulting.breezy.manager.MountedBoardManager;
import com.windhaven_consulting.breezy.manager.TriggerEventManager;
import com.windhaven_consulting.breezy.manager.impl.MountedBoard;
import com.windhaven_consulting.breezy.persistence.domain.Macro;
import com.windhaven_consulting.breezy.persistence.domain.TriggerEvent;

@ManagedBean
@ViewScoped
public class EventsBuilderView {
	static final Logger LOG = LoggerFactory.getLogger(EventsBuilderView.class);

	@Inject
	private MountedBoardManager mountedBoardManager;
	
	@Inject
	private MacroManager macroManager;
	
	@Inject
	private TriggerEventManager triggerEventManager;
	
	private List<TriggerEvent> triggerEvents = new ArrayList<TriggerEvent>();
	
	private List<MountedBoard> mountedBoards = new ArrayList<MountedBoard>();
	
	private TriggerEvent workingTriggerEvent = new TriggerEvent();
	
	private Map<String, Macro> macroIdToMacroMap = new HashMap<String, Macro>();
	
	private List<Macro> macros = new ArrayList<>();

	private Map<String, String> digitalInputPinMap = new TreeMap<String, String>();
	
	private List<DigitalInputPin> inputPins = new ArrayList<DigitalInputPin>();

	private boolean isNewLineMode;

	private int selectedTriggerEventIndex;
	
	private MountedBoardConverter mountedBoardConverter;

	private DigitalInputPinConverter digitalInputPinConverter;

	private PinStateConverter pinStateConverter;
	
	@PostConstruct
	public void postConstruct() {
		initialize();
	}

	public void preRenderView() {
	}
	
	public void newEvent() {
		workingTriggerEvent = new TriggerEvent();
		isNewLineMode = true;
	}
	
	public void deleteRow(int index) {
		triggerEvents.remove(index);
		triggerEventManager.saveAll(triggerEvents);
	}
	
	public void editTriggerEvent(int index) {
		selectedTriggerEventIndex = index;
		workingTriggerEvent = copyTriggerEvent(triggerEvents.get(selectedTriggerEventIndex));
		
		if(StringUtils.isNotEmpty(workingTriggerEvent.getMountedBoardId())) {
			MountedBoard mountedBoard = mountedBoardManager.getById(workingTriggerEvent.getMountedBoardId());
			inputPins = mountedBoard.getInputPins();
		}
	}

	public void saveTriggerEvent() {
		if(isNewLineMode) {
			triggerEvents.add(copyTriggerEvent(workingTriggerEvent));
			isNewLineMode = false;
		}
		else {
			triggerEvents.set(selectedTriggerEventIndex, copyTriggerEvent(workingTriggerEvent));
		}
		
		triggerEventManager.saveAll(getTriggerEvents());
	}
	
	public void cancelTriggerEventEdit() {
	}
	
	public void onMountedBoardChange(final AjaxBehaviorEvent event) {
		String mountedBoardId = (String) ((UIOutput) event.getSource()).getValue();
		MountedBoard mountedBoard = mountedBoardManager.getById(mountedBoardId);
		
		if(mountedBoard != null) {
        	inputPins = mountedBoard.getInputPins();
		}
		else {
			inputPins.clear();
		}
	}
	
	public TriggerEvent getWorkingTriggerEvent() {
		return workingTriggerEvent;
	}
	
	public List<TriggerEvent> getTriggerEvents() {
		return triggerEvents;
	}
	
	public Map<String, String> getDigitalInputPinMap() {
		return digitalInputPinMap;
	}
	
	public List<PinState> getPinStates() {
		return Arrays.asList(PinState.values());
	}
	
	public List<Macro> getMacros() {
		return macros;
	}
	
	public Macro getMacroById(String macroId) {
		return macroIdToMacroMap.get(macroId);
	}
	
	public List<MountedBoard> getMountedBoards() {
		return mountedBoards;
	}
	
	public MountedBoardConverter getMountedBoardConverter() {
		return mountedBoardConverter;
	}
	
	public List<DigitalInputPin> getInputPins() {
		return inputPins;
	}

	public DigitalInputPinConverter getDigitalInputPinConverter() {
		return digitalInputPinConverter;
	}
	
	public PinStateConverter getPinStateConverter() {
		return pinStateConverter;
	}
	
	private void initialize() {
		mountedBoards = mountedBoardManager.getAllMountedBoards();
		List<DigitalInputPin> inputPins = new ArrayList<DigitalInputPin>();
		
		for(MountedBoard mountedBoard : mountedBoards) {
			inputPins.addAll(mountedBoard.getInputPins());
			
			for(DigitalInputPin digitalInputPin : mountedBoard.getInputPins()) {
				digitalInputPinMap.put(digitalInputPin.getName(), digitalInputPin.getName());
			}
		}
		
		for(Macro macro : macroManager.getAllMacros()) {
			macros.add(macro);
			macroIdToMacroMap.put(macro.getId().toString(), macro);
		}
		
		triggerEvents = triggerEventManager.getAll();
		
		mountedBoardConverter = new MountedBoardConverter(mountedBoards);
		digitalInputPinConverter = new DigitalInputPinConverter(inputPins);
		pinStateConverter = new PinStateConverter();
	}
	
	private TriggerEvent copyTriggerEvent(TriggerEvent sourceTriggerEvent) {
		return copyTriggerEvent(new TriggerEvent(), sourceTriggerEvent);
	}

	private TriggerEvent copyTriggerEvent(TriggerEvent destinationTriggerEvent, TriggerEvent sourceTriggerEvent) {
		destinationTriggerEvent.setComment(sourceTriggerEvent.getComment());
		destinationTriggerEvent.setEnabled(sourceTriggerEvent.isEnabled());
		destinationTriggerEvent.setId(sourceTriggerEvent.getId());
		destinationTriggerEvent.setInputPinId(sourceTriggerEvent.getInputPinId());
		destinationTriggerEvent.setMacroId(sourceTriggerEvent.getMacroId());
		destinationTriggerEvent.setMountedBoardId(sourceTriggerEvent.getMountedBoardId());
		destinationTriggerEvent.setName(sourceTriggerEvent.getName());
		destinationTriggerEvent.setState(sourceTriggerEvent.getState());
		
		return destinationTriggerEvent;
	}
	
}
