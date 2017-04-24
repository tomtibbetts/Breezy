package com.windhaven_consulting.breezy.controller.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.controller.ui.converter.BreezyBoardConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.InputPinConfigurationConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.PinStateConverter;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;
import com.windhaven_consulting.breezy.manager.BreezyBoardManager;
import com.windhaven_consulting.breezy.manager.MacroManager;
import com.windhaven_consulting.breezy.manager.TriggerEventManager;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;
import com.windhaven_consulting.breezy.manager.viewobject.InputPinConfiguration;
import com.windhaven_consulting.breezy.persistence.domain.Macro;
import com.windhaven_consulting.breezy.persistence.domain.TriggerEvent;

@ManagedBean
@ViewScoped
public class EventsBuilderView {
	static final Logger LOG = LoggerFactory.getLogger(EventsBuilderView.class);

	@Inject
	private BreezyBoardManager breezyBoardManager;
	
	@Inject
	private MacroManager macroManager;
	
	@Inject
	private TriggerEventManager triggerEventManager;
	
	private List<TriggerEvent> triggerEvents = new ArrayList<TriggerEvent>();
	
	private TriggerEvent workingTriggerEvent = new TriggerEvent();
	
	private Map<String, Macro> macroIdToMacroMap = new HashMap<String, Macro>();
	
	private List<Macro> macros = new ArrayList<>();

	private boolean isNewLineMode;

	private int selectedTriggerEventIndex;
	
	private PinStateConverter pinStateConverter;

	private List<InputPinConfiguration> inputPinConfigurations = new ArrayList<InputPinConfiguration>();

	private InputPinConfigurationConverter inputPinConfigurationConverter;

	private List<BreezyBoard> breezyBoards = new ArrayList<BreezyBoard>();

	private Map<UUID, BreezyBoard> breezyBoardIdToBreezyBoardMap = new HashMap<UUID, BreezyBoard>();

	private Map<String, String> inputPinConfigurationMap = new HashMap<String, String>();

	private BreezyBoardConverter breezyBoardConverter;
	
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
			BreezyBoard breezyBoard = breezyBoardManager.getBreezyBoardById(workingTriggerEvent.getMountedBoardId());
			inputPinConfigurations  = breezyBoard.getInputPinConfigurations();
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
		BreezyBoard breezyBoard = breezyBoardManager.getBreezyBoardById(mountedBoardId);
		
		if(breezyBoard != null) {
			inputPinConfigurations = breezyBoard.getInputPinConfigurations();
		}
		else {
			inputPinConfigurations.clear();
		}
	}
	
	public TriggerEvent getWorkingTriggerEvent() {
		return workingTriggerEvent;
	}
	
	public List<TriggerEvent> getTriggerEvents() {
		return triggerEvents;
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
	
	public List<BreezyBoard> getBreezyBoards() {
		return breezyBoards;
	}
	
	public BreezyBoardConverter getBreezyBoardConverter() {
		return breezyBoardConverter;
	}
	
	public BreezyBoard getBreezyBoard(String id) {
		BreezyBoard breezyBoard = null;
		
		if(StringUtils.isNotEmpty(id)) {
			breezyBoard = breezyBoardIdToBreezyBoardMap.get(UUID.fromString(id));
		}
		
		return breezyBoard;
	}

	public List<InputPinConfiguration> getInputPinConfigurations() {
		return inputPinConfigurations;
	}
	
	public InputPinConfigurationConverter getInputPinConfigurationConverter() {
		return inputPinConfigurationConverter;
	}
	
	public PinStateConverter getPinStateConverter() {
		return pinStateConverter;
	}
	
	public String getSelectedMacroId() {
		String result = StringUtils.EMPTY;
		
		if(!workingTriggerEvent.getMacroIds().isEmpty()) {
			result = workingTriggerEvent.getMacroIds().get(0);
		}
		
		return result;
	}
	
	public void setSelectedMacroId(String macroId) {
		if(workingTriggerEvent.getMacroIds().isEmpty()) {
			workingTriggerEvent.getMacroIds().add(macroId);
		}
		else {
			workingTriggerEvent.getMacroIds().set(0, macroId);
		}
	}
	
	private void initialize() {
		breezyBoardIdToBreezyBoardMap.clear();
		breezyBoards = breezyBoardManager.getAllBreezyBoards();
		inputPinConfigurations = new ArrayList<InputPinConfiguration>();
		for(BreezyBoard breezyBoard : breezyBoards ) {
			breezyBoardIdToBreezyBoardMap.put(breezyBoard.getId(), breezyBoard);
			
			inputPinConfigurations.addAll(breezyBoard.getInputPinConfigurations());
			
			for(InputPinConfiguration inputPinConfiguration : inputPinConfigurations) {
				inputPinConfigurationMap .put(inputPinConfiguration.getName(), inputPinConfiguration.getName());
			}
		}
		
		for(Macro macro : macroManager.getAllMacros()) {
			macros.add(macro);
			macroIdToMacroMap.put(macro.getId().toString(), macro);
		}
		
		triggerEvents = triggerEventManager.getAll();
		
		breezyBoardConverter = new BreezyBoardConverter(breezyBoards);
		inputPinConfigurationConverter = new InputPinConfigurationConverter(inputPinConfigurations);
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
		
		destinationTriggerEvent.getMacroIds().clear();
		destinationTriggerEvent.getMacroIds().addAll(sourceTriggerEvent.getMacroIds());
		
		destinationTriggerEvent.setMountedBoardId(sourceTriggerEvent.getMountedBoardId());
		destinationTriggerEvent.setName(sourceTriggerEvent.getName());
		destinationTriggerEvent.setState(sourceTriggerEvent.getState());
		
		return destinationTriggerEvent;
	}
	
}
