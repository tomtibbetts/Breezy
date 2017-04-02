package com.windhaven_consulting.breezy.controller.ui;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.windhaven_consulting.breezy.component.GenericComponent;
import com.windhaven_consulting.breezy.controller.ui.converter.ExtensionPropertyKeyConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.ExtensionPropertyValueConverter;
import com.windhaven_consulting.breezy.embeddedcontroller.BreezyPin;
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
@SessionScoped
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
	
	private BreezyBoard breezyBoard;
	
	private String breezyBoardId;

	private String action;
	
	private String name;
	
	private String description;
	
	private boolean inactive;

	private boolean newBoard;
	
	private String selectedBoardTemplateId;

	private int selectedExtensionIndex;

	private BreezyBoardBuilderExtensionView workingExtension;

	private int selectedInputIndex;

	private InputPinConfiguration workingInputPinConfiguration = new InputPinConfiguration();

	private int selectedComponentIndex;

	private ComponentConfiguration workingComponentConfiguration = new ComponentConfiguration();

	private Map<ExtensionType, ExtensionPropertyKeyConverter> extensionPropertyKeyConverterByExtensionTypeMap = new HashMap<ExtensionType, ExtensionPropertyKeyConverter>();

	private Map<String, ExtensionPropertyValueConverter> extensionPropertyValueConverterByExtensionPropertyMap = new HashMap<String, ExtensionPropertyValueConverter>();

	public void preRender() {
		if("newBoard".equals(action)) {
			newBoard = true;
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
	}
	
	public void editExtension(int index) {
		selectedExtensionIndex = index;
		workingExtension = getExtensionDecorator(breezyBoard.getExtensions().get(index));
	}

	/**
	 * Input Section
	 * 
	 */
	public void saveInput() {
		breezyBoard.getInputPinConfigurations().set(selectedInputIndex, workingInputPinConfiguration);
	}
	
	public void editInput(int index) {
		selectedInputIndex = index;
		workingInputPinConfiguration = getInputPinConfiguration(breezyBoard.getInputPinConfigurations().get(index));
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
	}

	public void testComponent(int index) {
		ComponentConfiguration componentConfiguration = breezyBoard.getComponentConfigurations().get(index);
		
		MountedBoard mountedBoard = mountedBoardManager.getById(breezyBoardId);
		GenericComponent<BreezyPin> component = mountedBoard.getComponent(componentConfiguration.getId().toString());
		
		component.test();
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
		}
	}

	private void buildExtensionPropertyKeyConverter(ExtensionType extensionType) {
		if(!extensionPropertyKeyConverterByExtensionTypeMap.containsKey(extensionType)) {
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
