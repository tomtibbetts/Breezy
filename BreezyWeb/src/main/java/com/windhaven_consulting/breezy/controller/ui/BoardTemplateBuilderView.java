package com.windhaven_consulting.breezy.controller.ui;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.RowEditEvent;

import com.windhaven_consulting.breezy.component.library.ComponentTemplate;
import com.windhaven_consulting.breezy.component.library.ComponentTemplateLibraryManager;
import com.windhaven_consulting.breezy.controller.ui.converter.ExtensionTemplateConverter;
import com.windhaven_consulting.breezy.controller.ui.utils.BoardTemplateUtility;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.InputType;
import com.windhaven_consulting.breezy.embeddedcontroller.OutputType;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;
import com.windhaven_consulting.breezy.manager.BreezyBoardTemplateManager;
import com.windhaven_consulting.breezy.manager.ExtensionProviderManager;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoardTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.ComponentConfigurationTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.ExtensionTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.InputConfigurationTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.OutputConfigurationTemplate;

@ManagedBean
@ViewScoped
public class BoardTemplateBuilderView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BreezyBoardTemplateManager breezyBoardTemplateManager;
	
	@Inject
	private ExtensionProviderManager extensionProviderManager;
	
	@Inject
	private ComponentTemplateLibraryManager componentTemplateLibraryManager;
	
	private ExtensionTemplateConverter extensionTemplateConverter;
	
	private Map<String, ExtensionTemplate> nameToExtensionTemplateMap = new HashMap<String, ExtensionTemplate>();
	
	private BreezyBoardTemplate breezyBoardTemplate;
	
	private String boardTemplateId;

	private String action;
	
	private String name;
	
	private String description;
	
	private boolean inactive;

	private List<BreezyPin> availableBreezyPins;

	private ComponentConfigurationTemplate workingComponentConfigurationTemplate = new ComponentConfigurationTemplate();

	private int selectedComponentIndex;

	private boolean isNewLineMode;

	private int selectedInputPinIndex;

	private InputConfigurationTemplate workingInputConfigurationTemplate = new InputConfigurationTemplate();
	
	private String saveBoardTemplateAsName;

	@PostConstruct
	public void postConstruct() {
		extensionTemplateConverter = new ExtensionTemplateConverter(nameToExtensionTemplateMap);
	}
	
	public void preRender() {
		if("newBoardTemplate".equals(action)) {
			nameToExtensionTemplateMap.clear();
			
			breezyBoardTemplate = new BreezyBoardTemplate();
		}
		else if("findBoardTemplate".equals(action)) {
			loadBoardTemplate(boardTemplateId);
		}
		
		action = StringUtils.EMPTY;
	}

	/**
	 * Common Actions
	 */
	public void saveBoardTemplate() {
		breezyBoardTemplateManager.saveBoardTemplate(breezyBoardTemplate);
	}
	
	public void saveBoardTemplateAs() {
		BreezyBoardTemplate newBreezyBoardTemplate = BoardTemplateUtility.cloneNew(breezyBoardTemplate);
		newBreezyBoardTemplate.setName(saveBoardTemplateAsName);
		breezyBoardTemplateManager.saveBoardTemplate(newBreezyBoardTemplate);
		
		loadBoardTemplate(newBreezyBoardTemplate.getId().toString());
	}

	public void cancelSaveBoardTemplateAs() {
		// Do nothing
	}
	
	public void deleteBoardTemplate() throws IOException {
		breezyBoardTemplateManager.deleteBoardTemplate(breezyBoardTemplate);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().redirect("boardTemplateChooser.jsf");
	}
	
	/**
	 * Extension Section
	 * 
	 */
	public void onExtensionTemplateRowEdit(RowEditEvent event) {
		ExtensionTemplate extensionTemplate = (ExtensionTemplate) event.getObject();
		
		if(!nameToExtensionTemplateMap.containsKey(extensionTemplate.getName())) {
			nameToExtensionTemplateMap.put(extensionTemplate.getName(), extensionTemplate);
		}
	}
	
	public void onExtensionTypeChange(final AjaxBehaviorEvent event) {
		ExtensionTemplate extensionTemplate = (ExtensionTemplate) ((UIOutput) event.getSource()).getValue();
		availableBreezyPins = extensionProviderManager.getAvailablePins(extensionTemplate.getExtensionType());
	}
	
	public void insertNewExtensionTemplateRowBefore(int index) throws IOException {
		breezyBoardTemplate.getExtensionTemplates().add(index, new ExtensionTemplate());
	}
	
	public void insertNewExtensionTemplateRowAfter(int index) throws IOException {
		ExtensionTemplate extensionTemplate = new ExtensionTemplate();
		
		int nextIndex = index + 1;
		
		if(nextIndex == breezyBoardTemplate.getExtensionTemplates().size()) {
			breezyBoardTemplate.getExtensionTemplates().add(extensionTemplate);
		}
		else {
			breezyBoardTemplate.getExtensionTemplates().add(nextIndex, new ExtensionTemplate());
		}
	}
	
	public void deleteExtensionTemplateRow(int index) throws IOException {
		ExtensionTemplate extensionTemplate = breezyBoardTemplate.getExtensionTemplates().get(index);
		
		if(extensionTemplate != null) {
			nameToExtensionTemplateMap.remove(extensionTemplate);
			
			// TODO: do the same for components as for inputs?
			for(InputConfigurationTemplate inputConfigurationTemplate : breezyBoardTemplate.getInputConfigurationTemplates()) {
				if(inputConfigurationTemplate.getExtensionTemplate() != null && inputConfigurationTemplate.getExtensionTemplate().equals(extensionTemplate)) {
					inputConfigurationTemplate.setExtensionTemplate(null);
					inputConfigurationTemplate.setMappedPin(StringUtils.EMPTY);
					inputConfigurationTemplate.setPinPullResistance(null);
				}
			}
		}
		
		breezyBoardTemplate.getExtensionTemplates().remove(index);
	}
	
	/**
	 * Input Configuration Template Section
	 * 
	 */
	public void saveInputPin() {
		copyInputConfigurationTemplate(breezyBoardTemplate.getInputConfigurationTemplates().get(selectedInputPinIndex), workingInputConfigurationTemplate);
		isNewLineMode = false;
	}
	
	public void cancelInputPin() {
		if(isNewLineMode) {
			deleteInputConfigurationTemplateRow(selectedInputPinIndex);
			isNewLineMode = false;
		}
	}

	public void editInputPin(int index) {
		selectedInputPinIndex = index;
		workingInputConfigurationTemplate = copyInputConfigurationTemplate(breezyBoardTemplate.getInputConfigurationTemplates().get(index));
	}

	public void insertNewInputConfigurationTemplateRowBefore(int index) throws IOException {
		selectedInputPinIndex = index;

		workingInputConfigurationTemplate = new InputConfigurationTemplate();
		breezyBoardTemplate.getInputConfigurationTemplates().add(index, new InputConfigurationTemplate());
		isNewLineMode = true;
	}
	
	public void insertNewInputConfigurationTemplateRowAfter(int index) throws IOException {
		InputConfigurationTemplate inputConfigurationTemplate = new InputConfigurationTemplate();
		int nextIndex = index + 1;
		selectedInputPinIndex = nextIndex;
		
		if(nextIndex == breezyBoardTemplate.getExtensionTemplates().size()) {
			breezyBoardTemplate.getInputConfigurationTemplates().add(inputConfigurationTemplate);
		}
		else {
			breezyBoardTemplate.getInputConfigurationTemplates().add(nextIndex, inputConfigurationTemplate);
		}
		
		workingInputConfigurationTemplate = new InputConfigurationTemplate();
		isNewLineMode = true;
	}
	
	public void deleteInputConfigurationTemplateRow(int index) { 
		breezyBoardTemplate.getInputConfigurationTemplates().remove(index);
	}
	
	/**
	 * Component section
	 * 
	 */
	public void saveComponent() {
		copyComponentConfigurationTemplate(breezyBoardTemplate.getComponentConfigurationTemplates().get(selectedComponentIndex), workingComponentConfigurationTemplate);
		isNewLineMode = false;
	}
	
	public void cancelComponent() {
		if(isNewLineMode) {
			deleteComponentConfigurationTemplateRow(selectedComponentIndex);
			isNewLineMode = false;
		}
	}
	
	public void editComponent(int index) {
		selectedComponentIndex = index;
		workingComponentConfigurationTemplate = copyComponentConfigurationTemplate(breezyBoardTemplate.getComponentConfigurationTemplates().get(index));
	}
	
	public void onComponentConfigurationComponentTypeChange(final AjaxBehaviorEvent event) {
		String currentMappedPin = StringUtils.EMPTY;
		ExtensionTemplate currentExtensionTemplate = null;
		
		if(!workingComponentConfigurationTemplate.getOutputConfigurationTemplates().isEmpty()) {
			OutputConfigurationTemplate currentOutputConfigurationTemplate = workingComponentConfigurationTemplate.getOutputConfigurationTemplates().get(0);
			currentMappedPin = currentOutputConfigurationTemplate.getMappedPin();
			currentExtensionTemplate = currentOutputConfigurationTemplate.getExtensionTemplate();
		}
		
		workingComponentConfigurationTemplate.getOutputConfigurationTemplates().clear();
		
		String componentType = (String) ((UIOutput) event.getSource()).getValue();
		ComponentTemplate componentTemplate = componentTemplateLibraryManager.getComponentTemplateFor(componentType);
		
		if(componentTemplate != null) {
			for(int i = 0; i < componentTemplate.getNumberOfOutputs(); i++) {
				OutputConfigurationTemplate outputConfigurationTemplate = new OutputConfigurationTemplate();
				outputConfigurationTemplate.setName(componentTemplate.getPinNameAt(i));
				outputConfigurationTemplate.setMappedPin(currentMappedPin);
				outputConfigurationTemplate.setExtensionTemplate(currentExtensionTemplate);
				
				workingComponentConfigurationTemplate.getOutputConfigurationTemplates().add(outputConfigurationTemplate);
			}
		}
	}

	public void insertNewComponentConfigurationTemplateRowBefore(int index) throws IOException {
		selectedComponentIndex = index;
		workingComponentConfigurationTemplate = new ComponentConfigurationTemplate();
		breezyBoardTemplate.getComponentConfigurationTemplates().add(index, new ComponentConfigurationTemplate());

		isNewLineMode = true;
	}
	
	public void insertNewComponentConfigurationTemplateRowAfter(int index) throws IOException {
		ComponentConfigurationTemplate componentConfigurationTemplate = new ComponentConfigurationTemplate();
		
		int nextIndex = index + 1;
		selectedComponentIndex = nextIndex;
		
		if(nextIndex == breezyBoardTemplate.getComponentConfigurationTemplates().size()) {
			breezyBoardTemplate.getComponentConfigurationTemplates().add(componentConfigurationTemplate);
		}
		else {
			breezyBoardTemplate.getComponentConfigurationTemplates().add(nextIndex, componentConfigurationTemplate);
		}
		
		workingComponentConfigurationTemplate = new ComponentConfigurationTemplate();

		isNewLineMode = true;
	}
	
	public void deleteComponentConfigurationTemplateRow(int index) { 
		breezyBoardTemplate.getComponentConfigurationTemplates().remove(index);
	}
	

	/**
	 * Common getters/setters
	 * @return
	 */
	public BreezyBoardTemplate getBreezyBoardTemplate() {
		return breezyBoardTemplate;
	}

	public void setBreezyBoardTemplate(BreezyBoardTemplate breezyBoardTemplate) {
		this.breezyBoardTemplate = breezyBoardTemplate;
	}

	public String getBoardTemplateId() {
		return boardTemplateId;
	}

	public void setBoardTemplateId(String boardTemplateId) {
		this.boardTemplateId = boardTemplateId;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isInactive() {
		return inactive;
	}

	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	
	public List<ExtensionType> getExtensionTypes() {
		List<ExtensionType> extensionTypes = Arrays.asList(ExtensionType.values());
		
		return extensionTypes;
	}
	
	public List<PinPullResistance> getPinPullResistances() {
		List<PinPullResistance> pinPullResistances = Arrays.asList(PinPullResistance.values());
		
		return pinPullResistances;
	}
	
	public ExtensionTemplateConverter getExtensionTemplateConverter() {
		return extensionTemplateConverter;
	}
	
	public List<ExtensionTemplate> getExtensionTemplatesByComponentType(String componentType) {
		List<ExtensionTemplate> extensionTemplates = new ArrayList<ExtensionTemplate>();
		
		ComponentTemplate componentTemplate = componentTemplateLibraryManager.getComponentTemplateFor(componentType);
		
		if(componentTemplate != null) {
			for(ExtensionTemplate extensionTemplate : breezyBoardTemplate.getExtensionTemplates()) {
				if(componentTemplate.getOutputType() == extensionTemplate.getExtensionType().getOutputType()) {
					extensionTemplates.add(extensionTemplate);
				}
			}
		}
		
		return extensionTemplates;
	}
	
	public List<ExtensionTemplate> getExtensionTemplatesByInputType(String inputTypeString) {
		List<ExtensionTemplate> extensionTemplates = new ArrayList<ExtensionTemplate>();
		InputType inputType = InputType.valueOf(inputTypeString);
		
		if(inputType != null) {
			for(ExtensionTemplate extensionTemplate : breezyBoardTemplate.getExtensionTemplates()) {
				if(inputType == extensionTemplate.getExtensionType().getInputType()) {
					extensionTemplates.add(extensionTemplate);
				}
			}
		}
		
		return extensionTemplates;
	}
	
	public List<? extends BreezyPin> getAvailablePins() {
		return availableBreezyPins;
	}
	
	public List<BreezyPin> getPins(ExtensionTemplate extensionTemplate) {
		List<BreezyPin> results = Collections.emptyList();
		
		if(extensionTemplate != null && extensionTemplate.getExtensionType() != null) {
			results = extensionProviderManager.getAvailablePins(extensionTemplate.getExtensionType());
		}
		
		return results;
	}
	
	public Collection<ComponentTemplate> getComponentTypes() {
		Collection<ComponentTemplate> componentTemplates;
		
		List<OutputType> outputTypes = new ArrayList<OutputType>();
		for(ExtensionTemplate extensionTemplate : breezyBoardTemplate.getExtensionTemplates()) {
			outputTypes.add(extensionTemplate.getExtensionType().getOutputType());
		}
		
		componentTemplates = componentTemplateLibraryManager.getComponentTemplates(outputTypes);
		
		return componentTemplates;
	}
	
	public ComponentConfigurationTemplate getWorkingComponentConfigurationTemplate() {
		return workingComponentConfigurationTemplate;
	}

	public InputConfigurationTemplate getWorkingInputConfigurationTemplate() {
		return workingInputConfigurationTemplate;
	}
	
	public void setSaveBoardTemplateAsName(String saveBoardTemplateAsName) {
		this.saveBoardTemplateAsName = saveBoardTemplateAsName;
	}
	
	public String getSaveBoardTemplateAsName() {
		return "Copy of " + breezyBoardTemplate.getName();
	}
	
	private void loadBoardTemplate(String id) {
		if(StringUtils.isNotEmpty(id)) {
			breezyBoardTemplate = breezyBoardTemplateManager.getBoardTemplateById(id);
			
			if(breezyBoardTemplate != null) {
				nameToExtensionTemplateMap.clear();

				for(ExtensionTemplate extensionTemplate : breezyBoardTemplate.getExtensionTemplates()) {
					nameToExtensionTemplateMap.put(extensionTemplate.getName(), extensionTemplate);
				}
			}
		}
	}
	
	private ComponentConfigurationTemplate copyComponentConfigurationTemplate(ComponentConfigurationTemplate componentConfigurationTemplate) {
		return copyComponentConfigurationTemplate(new ComponentConfigurationTemplate(), componentConfigurationTemplate);
	}

	private ComponentConfigurationTemplate copyComponentConfigurationTemplate(ComponentConfigurationTemplate destination, ComponentConfigurationTemplate source) {
		destination.setComponentType(source.getComponentType());
		destination.setId(source.getId());
		destination.setName(source.getName());
		destination.getOutputConfigurationTemplates().clear();
		
		for(OutputConfigurationTemplate sourceOutputConfigurationTemplate : source.getOutputConfigurationTemplates()) {
			OutputConfigurationTemplate outputConfigurationTemplate = new OutputConfigurationTemplate();
			outputConfigurationTemplate.setExtensionTemplate(sourceOutputConfigurationTemplate.getExtensionTemplate());
			outputConfigurationTemplate.setId(sourceOutputConfigurationTemplate.getId());
			outputConfigurationTemplate.setMappedPin(sourceOutputConfigurationTemplate.getMappedPin());
			outputConfigurationTemplate.setName(sourceOutputConfigurationTemplate.getName());
			
			destination.getOutputConfigurationTemplates().add(outputConfigurationTemplate);
		}
		
		return destination;
	}

	
	private InputConfigurationTemplate copyInputConfigurationTemplate(InputConfigurationTemplate inputConfigurationTemplate) {
		return copyInputConfigurationTemplate(new InputConfigurationTemplate(), inputConfigurationTemplate);
	}

	private InputConfigurationTemplate copyInputConfigurationTemplate(InputConfigurationTemplate destination, InputConfigurationTemplate source) {
		destination.setExtensionTemplate(source.getExtensionTemplate());
		destination.setId(source.getId());
		destination.setMappedPin(source.getMappedPin());
		destination.setName(source.getName());
		destination.setPinPullResistance(source.getPinPullResistance());
		
		return destination;
	}

}
