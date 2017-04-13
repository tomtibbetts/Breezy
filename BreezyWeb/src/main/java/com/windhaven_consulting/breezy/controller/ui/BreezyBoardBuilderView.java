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
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.component.GenericComponent;
import com.windhaven_consulting.breezy.component.library.ComponentTemplate;
import com.windhaven_consulting.breezy.component.library.ComponentTemplateLibraryManager;
import com.windhaven_consulting.breezy.controller.ui.converter.ExtensionConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.ExtensionPropertyKeyConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.ExtensionPropertyValueConverter;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
import com.windhaven_consulting.breezy.embeddedcontroller.InputType;
import com.windhaven_consulting.breezy.embeddedcontroller.OutputType;
import com.windhaven_consulting.breezy.embeddedcontroller.PinPullResistance;
import com.windhaven_consulting.breezy.embeddedcontroller.PropertyValueEnum;
import com.windhaven_consulting.breezy.embeddedcontroller.extensions.ExtensionType;
import com.windhaven_consulting.breezy.manager.BreezyBoardManager;
import com.windhaven_consulting.breezy.manager.BreezyBoardTemplateManager;
import com.windhaven_consulting.breezy.manager.ExtensionProviderManager;
import com.windhaven_consulting.breezy.manager.MountedBoardManager;
import com.windhaven_consulting.breezy.manager.impl.MountedBoard;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoardTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.ComponentConfiguration;
import com.windhaven_consulting.breezy.manager.viewobject.ComponentConfigurationTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.Extension;
import com.windhaven_consulting.breezy.manager.viewobject.ExtensionTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.InputConfigurationTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.InputPinConfiguration;
import com.windhaven_consulting.breezy.manager.viewobject.OutputConfigurationTemplate;
import com.windhaven_consulting.breezy.manager.viewobject.OutputPinConfiguration;

@ManagedBean
@ViewScoped
public class BreezyBoardBuilderView implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BreezyBoardTemplateManager breezyBoardTemplateManager;
	
	@Inject
	private BreezyBoardManager breezyBoardManager;
	
	@Inject
	private ExtensionProviderManager extensionProviderManager;
	
	@Inject
	private MountedBoardManager mountedBoardManager;
	
	@Inject
	private ComponentTemplateLibraryManager componentTemplateLibraryManager;
	
	private BreezyBoard breezyBoard;
	
	private String breezyBoardId;

	private String action;
	
	private String name;
	
	private String description;
	
	private boolean inactive;

	private boolean newBoard;
	
	private String selectedBoardTemplateId;

	private int selectedExtensionIndex;

	private BreezyBoardBuilderExtensionView workingExtension = new BreezyBoardBuilderExtensionView();

	private int selectedInputPinIndex;

	private InputPinConfiguration workingInputPinConfiguration = new InputPinConfiguration();

	private int selectedComponentIndex;

	private ComponentConfiguration workingComponentConfiguration = new ComponentConfiguration();

	private Map<ExtensionType, ExtensionPropertyKeyConverter> extensionPropertyKeyConverterByExtensionTypeMap = new HashMap<ExtensionType, ExtensionPropertyKeyConverter>();

	private Map<String, ExtensionPropertyValueConverter> extensionPropertyValueConverterByExtensionPropertyMap = new HashMap<String, ExtensionPropertyValueConverter>();

	private boolean isNewLineMode;

	private ExtensionConverter extensionConverter;

	private Map<String, Extension> nameToExtensionMap = new HashMap<String, Extension>();

	@PostConstruct
	public void postConstruct() {
		extensionConverter = new ExtensionConverter(nameToExtensionMap);
	}

	public void preRender() {
		if("newBoard".equals(action)) {
			newBoard = true;
			nameToExtensionMap.clear();
			
			selectedBoardTemplateId = StringUtils.EMPTY;
			
			breezyBoard = new BreezyBoard();
		}
		else if("findBoard".equals(action)) {
			newBoard = false;

			if(StringUtils.isNotEmpty(breezyBoardId)) {
				breezyBoard = breezyBoardManager.getBreezyBoardById(breezyBoardId);
				
				if(breezyBoard != null) {
					initializeConverters(breezyBoard);
				}
			}
		}
		
		action = StringUtils.EMPTY;
	}

	/**
	 * Common Actions
	 */
	public void saveBoard() {
		breezyBoardManager.saveBoard(breezyBoard);
	}
	
	public void deleteBoard() throws IOException {
		breezyBoardManager.deleteBoard(breezyBoard);
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().redirect("breezyBoardChooser.jsf");
	}
	
	public void mountBoard() {
		if(!breezyBoard.isMounted()) {
			breezyBoardManager.mountBoardAndSave(breezyBoard);
		}
	}
	
	public void unmountBoard() {
		if(breezyBoard.isMounted()) {
			breezyBoardManager.unmountBoardAndSave(breezyBoard);
		}
	}
	
	public ExtensionConverter getExtensionConverter() {
		return extensionConverter;
	}
	
	public List<Extension> getExtensionsByComponentType(String componentType) {
		List<Extension> extensions = new ArrayList<Extension>();
		
		ComponentTemplate componentTemplate = componentTemplateLibraryManager.getComponentTemplateFor(componentType);
		
		if(componentTemplate != null) {
			for(Extension extension : breezyBoard.getExtensions()) {
				if(componentTemplate.getOutputType() == extension.getExtensionType().getOutputType()) {
					extensions.add(extension);
				}
			}
		}
		
		return extensions;
	}
	
	public List<Extension> getExtensionsByInputType(String inputTypeString) {
		List<Extension> extensions = new ArrayList<Extension>();
		InputType inputType = InputType.valueOf(inputTypeString);
		
		if(inputType != null) {
			for(Extension extension : breezyBoard.getExtensions()) {
				if(inputType == extension.getExtensionType().getInputType()) {
					extensions.add(extension);
				}
			}
		}
		
		return extensions;
	}
	
	public Collection<ComponentTemplate> getComponentTypes() {
		Collection<ComponentTemplate> componentTemplates;
		List<OutputType> outputTypes = new ArrayList<OutputType>();
		
		for(Extension extension : breezyBoard.getExtensions()) {
			outputTypes.add(extension.getExtensionType().getOutputType());
		}
		
		componentTemplates = componentTemplateLibraryManager.getComponentTemplates(outputTypes);
		
		return componentTemplates;
	}

	/**
	 * First page
	 */
	public void onBreezyBoardTemplateChange(final AjaxBehaviorEvent event) {
		String id = (String) ((UIOutput) event.getSource()).getValue();
		
		BreezyBoardTemplate breezyBoardTemplate = breezyBoardTemplateManager.getBoardTemplateById(id);
		breezyBoard = buildBreezyBoard(breezyBoardTemplate);
	}

	/**
	 * Extension Section
	 * 
	 */
	public void saveExtension() {
		Extension extension = getExtension(workingExtension);
		breezyBoard.getExtensions().set(selectedExtensionIndex, extension);
		
		if(!nameToExtensionMap.containsKey(extension.getName())) {
			nameToExtensionMap.put(extension.getName(), extension);
		}
	}
	
	public void editExtension(int index) {
		selectedExtensionIndex = index;
		workingExtension = getExtensionDecorator(breezyBoard.getExtensions().get(index));
		isNewLineMode = false;
	}

	public void insertNewExtensionRowBefore(int index) throws IOException {
		selectedExtensionIndex = index;
		breezyBoard.getExtensions().add(index, new Extension());
		workingExtension = new BreezyBoardBuilderExtensionView();
		isNewLineMode = true;
	}
	
	public void insertNewExtensionRowAfter(int index) throws IOException {
		Extension extension = new Extension();
		
		int nextIndex = index + 1;
		selectedExtensionIndex = nextIndex;
		
		if(nextIndex == breezyBoard.getExtensions().size()) {
			breezyBoard.getExtensions().add(extension);
		}
		else {
			breezyBoard.getExtensions().add(nextIndex, extension);
		}

		workingExtension = new BreezyBoardBuilderExtensionView();
		isNewLineMode = true;
	}

	public void deleteExtensionRow(int index) {
		Extension extension = breezyBoard.getExtensions().get(index);
		
		/**
		 * iterate through all dependant inputs and components and delete those too.
		 */
		
//		if(extension != null) {
//			nameToExtensionTemplateMap.remove(extension);
//			
//			// TODO: do the same for components as for inputs?
//			for(InputConfigurationTemplate inputConfigurationTemplate : breezyBoardTemplate.getInputConfigurationTemplates()) {
//				if(inputConfigurationTemplate.getExtensionTemplate() != null && inputConfigurationTemplate.getExtensionTemplate().equals(extension)) {
//					inputConfigurationTemplate.setExtensionTemplate(null);
//					inputConfigurationTemplate.setMappedPin(StringUtils.EMPTY);
//					inputConfigurationTemplate.setPinPullResistance(null);
//				}
//			}
//		}
		
		breezyBoard.getExtensions().remove(index);
	}

	public List<ExtensionType> getExtensionTypes() {
		List<ExtensionType> extensionTypes = Arrays.asList(ExtensionType.values());
		
		return extensionTypes;
	}
	
	public void onExtensionTypeChange(final AjaxBehaviorEvent event) {
		ExtensionType extensionType = (ExtensionType) ((UIOutput) event.getSource()).getValue();
		buildExtensionPropertyKeyConverter(extensionType);
		
		workingExtension.getPropertyEntries().clear();
		
		List<PropertyValueEnum> propertyValueEnums = extensionProviderManager.getProperties(extensionType);
		
		for(PropertyValueEnum propertyValueEnum : propertyValueEnums) {
			MapEntryView<String, String> mapEntryView = new MapEntryView<String, String>(propertyValueEnum.getName(), "");
			
			workingExtension.getPropertyEntries().add(mapEntryView);
		}
	}
	
	public void cancelExtensionEdit() {
		if(isNewLineMode) {
			deleteExtensionRow(selectedExtensionIndex);
			isNewLineMode = false;
		}
	}
	
	/**
	 * Input Section
	 * 
	 */
	public void saveInput() {
		breezyBoard.getInputPinConfigurations().set(selectedInputPinIndex, workingInputPinConfiguration);
	}
	
	public void editInput(int index) {
		selectedInputPinIndex = index;
		workingInputPinConfiguration = getInputPinConfiguration(breezyBoard.getInputPinConfigurations().get(index));
		isNewLineMode = false;
	}
	
	public void cancelInputPin() {
		if(isNewLineMode) {
			deleteDigitalInputPinConfigurationRow(selectedInputPinIndex);
			isNewLineMode = false;
		}
	}

	public void insertNewDigitalInputPinRowBefore(int index) throws IOException {
		selectedInputPinIndex = index;

		// create a scratch configuration
		workingInputPinConfiguration = new InputPinConfiguration();
		
		// and add a placeholder in the breezy board
		breezyBoard.getInputPinConfigurations().add(index, new InputPinConfiguration());
		isNewLineMode = true;
	}
	
	public void insertNewDigitalInputPinRowAfter(int index) throws IOException {
		InputPinConfiguration inputConfigurationTemplate = new InputPinConfiguration();
		int nextIndex = index + 1;
		selectedInputPinIndex = nextIndex;
		
		if(nextIndex == breezyBoard.getExtensions().size()) {
			breezyBoard.getInputPinConfigurations().add(inputConfigurationTemplate);
		}
		else {
			breezyBoard.getInputPinConfigurations().add(nextIndex, inputConfigurationTemplate);
		}
		
		workingInputPinConfiguration = new InputPinConfiguration();
		isNewLineMode = true;
	}
	
	public void deleteDigitalInputPinConfigurationRow(int index) { 
		breezyBoard.getInputPinConfigurations().remove(index);
	}
	
	public List<BreezyPin> getPins(Extension extension) {
		List<BreezyPin> results = Collections.emptyList();
		
		if(extension != null && extension.getExtensionType() != null) {
			results = extensionProviderManager.getAvailablePins(extension.getExtensionType());
		}
		
		return results;
	}

	/**
	 * Component section
	 * 
	 */
	public void saveComponent() {
		copyComponentConfiguration(breezyBoard.getComponentConfigurations().get(selectedComponentIndex), workingComponentConfiguration);
	}
	
	public void editComponent(int index) {
		selectedComponentIndex = index;
		workingComponentConfiguration = copyComponentConfiguration(breezyBoard.getComponentConfigurations().get(index));
		isNewLineMode = false;
	}

	public void cancelComponent() {
		if(isNewLineMode) {
			deleteComponentConfigurationRow(selectedComponentIndex);
			isNewLineMode = false;
		}
	}
	
	public void testComponent(int index) {
		ComponentConfiguration componentConfiguration = breezyBoard.getComponentConfigurations().get(index);
		
		MountedBoard mountedBoard = mountedBoardManager.getById(breezyBoardId);
		GenericComponent<BreezyPin> component = mountedBoard.getComponent(componentConfiguration.getId().toString());
		
		component.test();
	}

	public void onComponentConfigurationComponentTypeChange(final AjaxBehaviorEvent event) {
		String currentMappedPin = StringUtils.EMPTY;
		Extension currentExtension = null;
		
		if(!workingComponentConfiguration.getOutputPinConfigurations().isEmpty()) {
			OutputPinConfiguration currentOutputConfiguration = workingComponentConfiguration.getOutputPinConfigurations().get(0);
			currentMappedPin = currentOutputConfiguration.getExtensionMappedPin();
			currentExtension = currentOutputConfiguration.getExtension();
		}
		
		workingComponentConfiguration.getOutputPinConfigurations().clear();
		
		String componentType = (String) ((UIOutput) event.getSource()).getValue();
		ComponentTemplate componentTemplate = componentTemplateLibraryManager.getComponentTemplateFor(componentType);
		
		if(componentTemplate != null) {
			for(int i = 0; i < componentTemplate.getNumberOfOutputs(); i++) {
				OutputPinConfiguration outputPinConfiguration = new OutputPinConfiguration();
				outputPinConfiguration.setName(componentTemplate.getPinNameAt(i));
				outputPinConfiguration.setExtensionMappedPin(currentMappedPin);
				outputPinConfiguration.setExtension(currentExtension);
				
				workingComponentConfiguration.getOutputPinConfigurations().add(outputPinConfiguration);
			}
		}
	}

	public void insertNewComponentConfigurationRowBefore(int index) throws IOException {
		selectedComponentIndex = index;
		workingComponentConfiguration = new ComponentConfiguration();
		breezyBoard.getComponentConfigurations().add(index, new ComponentConfiguration());

		isNewLineMode = true;
	}
	
	public void insertNewComponentConfigurationRowAfter(int index) throws IOException {
		ComponentConfiguration componentConfiguration = new ComponentConfiguration();
		
		int nextIndex = index + 1;
		selectedComponentIndex = nextIndex;
		
		if(nextIndex == breezyBoard.getComponentConfigurations().size()) {
			breezyBoard.getComponentConfigurations().add(componentConfiguration);
		}
		else {
			breezyBoard.getComponentConfigurations().add(nextIndex, componentConfiguration);
		}
		
		workingComponentConfiguration = new ComponentConfiguration();

		isNewLineMode = true;
	}
	
	public void deleteComponentConfigurationRow(int index) { 
		breezyBoard.getComponentConfigurations().remove(index);
	}
	
	/**
	 * Common getters/setters
	 * @return
	 */
	public BreezyBoard getBreezyBoard() {
		return breezyBoard;
	}

	public void setBreezyBoard(BreezyBoard breezyBoardTemplate) {
		this.breezyBoard = breezyBoardTemplate;
	}

	public String getBreezyBoardId() {
		return breezyBoardId;
	}

	public void setBreezyBoardId(String breezyBoardId) {
		this.breezyBoardId = breezyBoardId;
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
	
	public List<PinPullResistance> getPinPullResistances() {
		List<PinPullResistance> pinPullResistances = Arrays.asList(PinPullResistance.values());
		return pinPullResistances;
	}
	
	public boolean isNewBoard() {
		return newBoard;
	}
	
	public String getSelectedBoardTemplateId() {
		return selectedBoardTemplateId;
	}

	public void setSelectedBoardTemplateId(String selectedBoardTemplateId) {
		this.selectedBoardTemplateId = selectedBoardTemplateId;
	}

	public List<BreezyBoardTemplate> getBreezyBoardTemplates() {
		return breezyBoardTemplateManager.getAllBoardTemplates();
	}
	
	public List<String> getExtensionProperties(Extension extension) {
		return extensionProviderManager.getPropertyFieldNames(extension.getExtensionType());
	}
	
	public List<PropertyValueEnum> getPropertyValues(ExtensionType extensionType, String property) {
		return extensionProviderManager.getPropertyValues(extensionType, property);
	}
	
	public BreezyBoardBuilderExtensionView getWorkingExtension() {
		return workingExtension;
	}

	public void setWorkingExtension(BreezyBoardBuilderExtensionView workingExtension) {
		this.workingExtension = workingExtension;
	}
	
	public InputPinConfiguration getWorkingInputPinConfiguration() {
		return workingInputPinConfiguration;
	}

	public void setWorkingInputPinConfiguration(InputPinConfiguration workingInputPinConfiguration) {
		this.workingInputPinConfiguration = workingInputPinConfiguration;
	}

	public ComponentConfiguration getWorkingComponentConfiguration() {
		return workingComponentConfiguration;
	}

	public void setWorkingComponentConfiguration(ComponentConfiguration workingComponentConfiguration) {
		this.workingComponentConfiguration = workingComponentConfiguration;
	}

	public ExtensionPropertyKeyConverter getExtensionPropertyKeyConverter(ExtensionType extensionType) {
		return extensionPropertyKeyConverterByExtensionTypeMap.get(extensionType);
	}
	
	public ExtensionPropertyValueConverter getExtensionPropertyValueConverter(ExtensionType extensionType, String property) {
		String extensionPropertyValueMapKey = makeExtensionPropertyValueMapKey(extensionType, property);
		return extensionPropertyValueConverterByExtensionPropertyMap.get(extensionPropertyValueMapKey);
	}
	
	/**
	 * private helper methods
	 */

	private BreezyBoard buildBreezyBoard(BreezyBoardTemplate breezyBoardTemplate) {
		extensionPropertyKeyConverterByExtensionTypeMap.clear();
		extensionPropertyValueConverterByExtensionPropertyMap.clear();
		
		BreezyBoard breezyBoard = new BreezyBoard();
		breezyBoard.setName(breezyBoardTemplate.getName());
		breezyBoard.setDescription(breezyBoardTemplate.getDescription());

		Map<UUID, Extension> extensionIdToExtensionMap = new HashMap<UUID, Extension>();
		
		for(ExtensionTemplate extensionTemplate : breezyBoardTemplate.getExtensionTemplates()) {
			Extension extension = new Extension();
			extension.setName(extensionTemplate.getName());
			extension.setExtensionType(extensionTemplate.getExtensionType());
			extension.setId(extensionTemplate.getId());
			
			buildExtensionPropertyKeyConverter(extensionTemplate.getExtensionType());
			
			for(String propertyName : extensionProviderManager.getPropertyFieldNames(extensionTemplate.getExtensionType())) {
				extension.getProperties().put(propertyName, StringUtils.EMPTY);
			}
			
			breezyBoard.getExtensions().add(extension);
			extensionIdToExtensionMap.put(extension.getId(), extension);
		}
		
		for(InputConfigurationTemplate inputConfigurationTemplate : breezyBoardTemplate.getInputConfigurationTemplates()) {
			InputPinConfiguration inputPinConfiguration = new InputPinConfiguration();
			inputPinConfiguration.setName(inputConfigurationTemplate.getName());
			inputPinConfiguration.setExtension(extensionIdToExtensionMap.get(inputConfigurationTemplate.getExtensionTemplate().getId()));
			inputPinConfiguration.setExtensionMappedPin(inputConfigurationTemplate.getMappedPin());
			inputPinConfiguration.setPinPullResistance(inputConfigurationTemplate.getPinPullResistance());
			
			breezyBoard.getInputPinConfigurations().add(inputPinConfiguration);
		}
		
		for(ComponentConfigurationTemplate componentConfigurationTemplate : breezyBoardTemplate.getComponentConfigurationTemplates()) {
			ComponentConfiguration componentConfiguration = new ComponentConfiguration();
			componentConfiguration.setComponentType(componentConfigurationTemplate.getComponentType());
			componentConfiguration.setName(componentConfigurationTemplate.getName());
			
			for(OutputConfigurationTemplate outputConfigurationTemplate : componentConfigurationTemplate.getOutputConfigurationTemplates()) {
				OutputPinConfiguration outputPinConfiguration = new OutputPinConfiguration();
				outputPinConfiguration.setName(outputConfigurationTemplate.getName());
				outputPinConfiguration.setExtension(extensionIdToExtensionMap.get(outputConfigurationTemplate.getExtensionTemplate().getId()));
				outputPinConfiguration.setExtensionMappedPin(outputConfigurationTemplate.getMappedPin());
				
				componentConfiguration.getOutputPinConfigurations().add(outputPinConfiguration);
			}
			
			breezyBoard.getComponentConfigurations().add(componentConfiguration);
		}
		
		return breezyBoard;
	}
	
	/**
	 * Extension helper methods
	 */

	/**
	 * Factory method to create a BreezyBoardBuilderExtensionView from an Extension
	 * @param extension
	 * @return
	 */
	private BreezyBoardBuilderExtensionView getExtensionDecorator(Extension extension) {
		BreezyBoardBuilderExtensionView boardBuilderExtensionDecorator = new BreezyBoardBuilderExtensionView();
		boardBuilderExtensionDecorator.setDescription(extension.getDescription());
		boardBuilderExtensionDecorator.setId(extension.getId());
		boardBuilderExtensionDecorator.setName(extension.getName());
		boardBuilderExtensionDecorator.setType(extension.getExtensionType());
		
		for(Entry<String, String> entry : extension.getProperties().entrySet()) {
			MapEntryView<String, String> mapEntryView = new MapEntryView<String, String>(entry);
			boardBuilderExtensionDecorator.getPropertyEntries().add(mapEntryView);
		}
		
		return boardBuilderExtensionDecorator;
	}

	/**
	 * Factory method to create an Extension from a BreezyBoardBuilderExtensionView
	 * 
	 * @param boardBuilderExtensionView
	 * @return
	 */
	private Extension getExtension(BreezyBoardBuilderExtensionView boardBuilderExtensionView) {
		Extension extension = new Extension();
		
		extension.setDescription(boardBuilderExtensionView.getDescription());
		extension.setId(boardBuilderExtensionView.getId());
		extension.setName(boardBuilderExtensionView.getName());
		extension.setExtensionType(boardBuilderExtensionView.getType());
		
		for(MapEntryView<String, String> mapEntryView : boardBuilderExtensionView.getPropertyEntries()) {
			extension.getProperties().put(mapEntryView.getKey(), mapEntryView.getValue());
		}

		return extension;
	}
	
	/**
	 * Input pin configuration helper methods
	 */
	
	/**
	 * 
	 * @param inputPinConfiguration
	 * @return
	 */
	private InputPinConfiguration getInputPinConfiguration(InputPinConfiguration inputPinConfiguration) {
		InputPinConfiguration result = new InputPinConfiguration();
		result.setDebounce(inputPinConfiguration.getDebounce());
		result.setDescription(inputPinConfiguration.getDescription());
		result.setEventTrigger(inputPinConfiguration.isEventTrigger());
		result.setExtension(inputPinConfiguration.getExtension());
		result.setExtensionMappedPin(inputPinConfiguration.getExtensionMappedPin());
		result.setId(inputPinConfiguration.getId());
		result.setName(inputPinConfiguration.getName());
		result.setPinPullResistance(inputPinConfiguration.getPinPullResistance());
		
		return result;
	}
	
	/**
	 * Component Helper Methods
	 */
	private ComponentConfiguration copyComponentConfiguration(ComponentConfiguration componentConfiguration) {
		return copyComponentConfiguration(new ComponentConfiguration(), componentConfiguration);
	}

	private ComponentConfiguration copyComponentConfiguration(ComponentConfiguration destination, ComponentConfiguration source) {
		destination.setComponentType(source.getComponentType());
		destination.setId(source.getId());
		destination.setName(source.getName());
		destination.getOutputPinConfigurations().clear();
		destination.setInverted(source.isInverted());
		destination.setDescription(source.getDescription());
		
		for(OutputPinConfiguration sourceOutputPinConfiguration : source.getOutputPinConfigurations()) {
			OutputPinConfiguration outputConfiguration = new OutputPinConfiguration();
			outputConfiguration.setExtension(sourceOutputPinConfiguration.getExtension());
			outputConfiguration.setId(sourceOutputPinConfiguration.getId());
			outputConfiguration.setExtensionMappedPin(sourceOutputPinConfiguration.getExtensionMappedPin());
			outputConfiguration.setName(sourceOutputPinConfiguration.getName());
			outputConfiguration.setDescription(sourceOutputPinConfiguration.getDescription());
			
			destination.getOutputPinConfigurations().add(outputConfiguration);
		}
		
		return destination;
	}


	private void initializeConverters(BreezyBoard breezyBoard) {
		extensionPropertyKeyConverterByExtensionTypeMap.clear();
		extensionPropertyValueConverterByExtensionPropertyMap.clear();

		for(Extension extension : breezyBoard.getExtensions()) {
			buildExtensionPropertyKeyConverter(extension.getExtensionType());
			
			nameToExtensionMap.put(extension.getName(), extension);
		}

	}

	private void buildExtensionPropertyKeyConverter(ExtensionType extensionType) {
		if(extensionType != null && !extensionPropertyKeyConverterByExtensionTypeMap.containsKey(extensionType)) {
			List<PropertyValueEnum> properties = extensionProviderManager.getProperties(extensionType);
			ExtensionPropertyKeyConverter extensionPropertyKeyConverter = new ExtensionPropertyKeyConverter(properties);
			extensionPropertyKeyConverterByExtensionTypeMap.put(extensionType, extensionPropertyKeyConverter);
			
			for(PropertyValueEnum property : properties) {
				String extensionPropertyValueMapKey = makeExtensionPropertyValueMapKey(extensionType, property);
				
				if(!extensionPropertyValueConverterByExtensionPropertyMap.containsKey(extensionPropertyValueMapKey)) {
					List<PropertyValueEnum> propertyValues = extensionProviderManager.getPropertyValues(extensionType, property);
					ExtensionPropertyValueConverter extensionPropertyValueConverter = new ExtensionPropertyValueConverter(propertyValues);
					extensionPropertyValueConverterByExtensionPropertyMap.put(extensionPropertyValueMapKey, extensionPropertyValueConverter);
				}
			}
		}
	}
	
	private String makeExtensionPropertyValueMapKey(ExtensionType extensionType, PropertyValueEnum property) {
		return makeExtensionPropertyValueMapKey(extensionType, property.getName());
	}
	
	private String makeExtensionPropertyValueMapKey(ExtensionType extensionType, String property) {
		return extensionType.name() + "_" + property;
	}

}
