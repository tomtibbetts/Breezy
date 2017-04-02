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
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.component.library.ComponentTemplate;
import com.windhaven_consulting.breezy.component.library.ComponentTemplateLibraryManager;
import com.windhaven_consulting.breezy.component.library.MethodTemplate;
import com.windhaven_consulting.breezy.component.library.ParameterTemplate;
import com.windhaven_consulting.breezy.controller.ui.converter.BreezyBoardConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.InputPinConfigurationConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.OutputPinConfigurationConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.PinStateConverter;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;
import com.windhaven_consulting.breezy.manager.BreezyBoardManager;
import com.windhaven_consulting.breezy.manager.MacroManager;
import com.windhaven_consulting.breezy.manager.viewobject.BreezyBoard;
import com.windhaven_consulting.breezy.manager.viewobject.ComponentConfiguration;
import com.windhaven_consulting.breezy.manager.viewobject.InputPinConfiguration;
import com.windhaven_consulting.breezy.manager.viewobject.OutputPinConfiguration;
import com.windhaven_consulting.breezy.persistence.domain.Macro;
import com.windhaven_consulting.breezy.persistence.domain.MacroStep;
import com.windhaven_consulting.breezy.persistence.domain.MethodParameter;

@ManagedBean
@ViewScoped
public class MacroBuilderView implements Serializable {
	static final Logger LOG = LoggerFactory.getLogger(MacroBuilderView.class);

	private static final long serialVersionUID = 1L;

	@Inject
	private BreezyBoardManager breezyBoardManager;
	
	@Inject
	private MacroManager macroManager;
	
	@Inject
	private ComponentTemplateLibraryManager componentLibraryManager;
	
	private Map<UUID, ComponentConfiguration> componentConfigurationIdToComponentConfigurationMap = new HashMap<UUID, ComponentConfiguration>();

	private List<ComponentConfiguration> componentConfigurations = new ArrayList<ComponentConfiguration>();
	
	private List<String> functions = new ArrayList<String>();
	
	private List<OutputPinConfiguration> outputPinConfigurations = new ArrayList<OutputPinConfiguration>();

	private Map<UUID, Map<String, List<ParameterTemplate>>> componentToMethods = new HashMap<UUID, Map<String, List<ParameterTemplate>>>();
	
	private MacroStep workingMacroStep = new MacroStep();

	private Macro macro;
	
	private String macroId;

	private String action;

	private int selectedMacroStepIndex;

	private boolean isNewLineMode;
	
	private PinStateConverter pinStateConverter;
	
	private List<String> logicStates = new ArrayList<String>();

	private List<InputPinConfiguration> inputPinConfigurations = new ArrayList<InputPinConfiguration>();

	private List<BreezyBoard> breezyBoards = null;

	private Map<UUID, BreezyBoard> breezyBoardIdToBreezyBoardMap = new HashMap<UUID, BreezyBoard>();

	private BreezyBoardConverter breezyBoardConverter;

	private InputPinConfigurationConverter inputPinConfigurationConverter;

	private OutputPinConfigurationConverter outputPinConfigurationConverter;

	@PostConstruct
	public void postConstruct() {
		initialize();
	}
	
	public void preRender() {
		if("newMacro".equals(action)) {
			macro = new Macro();
		}
		else if("findMacro".equals(action)) {
			macro = macroManager.getMacroById(macroId);
			decorateMacro(macro);
		}
		
		action = StringUtils.EMPTY;
	}
	
	public void testMacro() {
		macroManager.runMacro(macro);
	}
	
	public void stopMacro() {
		macroManager.stopMacro(macro);
	}
	
	public void saveMacro() {
		macroManager.saveMacro(macro);
	}
	
	public boolean isRunning() {
		return macroManager.isRunning(macro);
	}
	
	public boolean hasUnmountedBoards() {
		boolean result = false;
		
		for(MacroStep macroStep : macro.getSteps()) {
			BreezyBoard breezyBoard = breezyBoardIdToBreezyBoardMap.get(UUID.fromString(macroStep.getMountedBoardId()));
			
			if(!breezyBoard.isMounted()) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	public void deleteMacro() throws IOException {
		macroManager.delete(macro);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().redirect("macroChooser.jsf");
	}

	public void onMountedBoardChange(final AjaxBehaviorEvent event) {
		String mountedBoardId = (String) ((UIOutput) event.getSource()).getValue();
		BreezyBoard breezyBoard = breezyBoardManager.getBreezyBoardById(mountedBoardId);
				
		if(breezyBoard != null) {
        	componentConfigurations = breezyBoard.getComponentConfigurations();
		}
		else {
			componentConfigurations.clear();
		}
    	
    	functions.clear();
	}
	
	public void onComponentChange(final AjaxBehaviorEvent event) {
		String key = (String) ((UIOutput) event.getSource()).getValue();
		
		if(StringUtils.isNotEmpty(key)) {
			UUID id = UUID.fromString(key);
			functions = new ArrayList<String>(componentToMethods.get(id).keySet());
			Collections.sort(functions);
        	outputPinConfigurations = componentConfigurationIdToComponentConfigurationMap.get(id).getOutputPinConfigurations();
		}
		else {
			functions.clear();
			outputPinConfigurations.clear();
		}
	}
	
	public void onFunctionChange(final AjaxBehaviorEvent event) {
		String function = (String) ((UIOutput) event.getSource()).getValue();
		List<MethodParameter> methodParameters = new ArrayList<MethodParameter>();
		UUID componentKey = UUID.fromString(workingMacroStep.getComponentId());
		List<ParameterTemplate> parameterTemplates = componentToMethods.get(componentKey).get(function);

		if(parameterTemplates != null) {
			for(ParameterTemplate parameterTemplate : parameterTemplates) {
				MethodParameter methodParameter = new MethodParameter();
				methodParameter.setFieldName(parameterTemplate.getArgumentLabel());
				methodParameter.setParameterFieldType(parameterTemplate.getParameterFieldType());
				methodParameter.setRequired(parameterTemplate.isRequired());
				
				methodParameters.add(methodParameter);
			}
		}
		
		workingMacroStep.setMethodParameters(methodParameters);
	}

	public void onParameterMountedBoardChange(final AjaxBehaviorEvent event) {
		String mountedBoardId = (String) ((UIOutput) event.getSource()).getValue();
		BreezyBoard breezyBoard = breezyBoardManager.getBreezyBoardById(mountedBoardId);
		
		if(breezyBoard != null) {
        	inputPinConfigurations = breezyBoard.getInputPinConfigurations();
		}
		else {
			inputPinConfigurations.clear();
		}
	}
	
	public List<BreezyBoard> getBreezyBoards() {
		return breezyBoards;
	}

	public BreezyBoard getBreezyBoard(String id) {
		BreezyBoard result = null;
		
		if(StringUtils.isNotEmpty(id)) {
			result = breezyBoardIdToBreezyBoardMap.get(UUID.fromString(id));
		}
		return result;
	}
	
	public String getComponentName(String id) {
		String name = StringUtils.EMPTY;
		
		if(StringUtils.isNotEmpty(id)) {
			ComponentConfiguration componentConfiguration = componentConfigurationIdToComponentConfigurationMap.get(UUID.fromString(id));
			
			if(componentConfiguration != null) {
				name = componentConfiguration.getName();
			}
		}

		return name;
	}
	
    public Macro getMacro() {
    	return macro;
    }
	
	public List<ComponentConfiguration> getComponentConfigurations() {
		return componentConfigurations;
	}
	
	public Collection<String> getFunctions() {
		return functions;
	}
	
	public List<InputPinConfiguration> getInputPinConfigurations() {
		return inputPinConfigurations;
	}

	public List<OutputPinConfiguration> getOutputPinConfigurations() {
		return outputPinConfigurations;
	}
	
	public List<PinState> getPinStates() {
		return Arrays.asList(PinState.values());
	}
	
	public List<String> getLogicStates() {
		return logicStates;
	}
	
	public void editMacroStep(int index) {
		selectedMacroStepIndex = index;
		workingMacroStep = copyMacroStep(macro.getSteps().get(index));
		
		if(StringUtils.isNotEmpty(workingMacroStep.getMountedBoardId())) {
			BreezyBoard breezyBoard = breezyBoardManager.getBreezyBoardById(workingMacroStep.getMountedBoardId());
			
			if(breezyBoard != null) {
	        	componentConfigurations = breezyBoard.getComponentConfigurations();
			}
			
			if(StringUtils.isNotEmpty(workingMacroStep.getComponentId())) {
				UUID componentKey = UUID.fromString(workingMacroStep.getComponentId());
				Map<String, List<ParameterTemplate>> componentMethodsMap = componentToMethods.get(componentKey);

				if(componentMethodsMap != null) {
					functions = new ArrayList<String>(componentMethodsMap.keySet());
					Collections.sort(functions);
					outputPinConfigurations = componentConfigurationIdToComponentConfigurationMap.get(componentKey).getOutputPinConfigurations();
				}
				else {
					functions = Collections.emptyList();
					workingMacroStep.getMethodParameters().clear();
					outputPinConfigurations.clear();
				}
			}
			
			for(MethodParameter methodParameter : workingMacroStep.getMethodParameters()) {
				if(ParameterFieldType.MOUNTED_BOARD == methodParameter.getParameterFieldType()) {
					BreezyBoard parameterMountedbBoard = breezyBoardManager.getBreezyBoardById(methodParameter.getFieldValue());
					
					if(parameterMountedbBoard != null) {
						inputPinConfigurations = parameterMountedbBoard.getInputPinConfigurations();
					}
					else {
						inputPinConfigurations.clear();
					}
				}
			}
		}
	}
	
	public void saveMacroStep() {
//		System.out.println("Save macro step");
//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Loggin Error", "Invalid credentials");
//
//        check if field types are correct?  I.e. if text entered for non-number then error?
        
        copyMacroStep(macro.getSteps().get(selectedMacroStepIndex), workingMacroStep);
		isNewLineMode = false;
//		
//        FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void cancelEditMacroStep() {
		if(isNewLineMode) {
			deleteRow(selectedMacroStepIndex);
			isNewLineMode = false;
		}
	}
	
	public void insertEditRowBefore(int index) {
		MacroStep newLine = new MacroStep();

		selectedMacroStepIndex = index;
		macro.getSteps().add(index, newLine);
		workingMacroStep = copyMacroStep(newLine);
		
		isNewLineMode = true;
	}
	
	public void insertEditRowAfter(int index) {
		MacroStep newLine = new MacroStep();
		
		int nextIndex = index + 1;
		selectedMacroStepIndex = nextIndex;
		
		if(nextIndex == macro.getSteps().size()) {
			macro.getSteps().add(newLine);
		}
		else {
			macro.getSteps().add(nextIndex, newLine);
		}

		workingMacroStep = copyMacroStep(newLine);
		isNewLineMode = true;
	}
	
	public void deleteRow(int index) {
		macro.getSteps().remove(index);
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setMacroId(String macroId) {
		this.macroId = macroId;
	}
	
	public String getMacroId() {
		return macroId;
	}

	public MacroStep getWorkingMacroStep() {
		return workingMacroStep;
	}
	
	public BreezyBoardConverter getBreezyBoardConverter() {
		return breezyBoardConverter;
	}
	
	public InputPinConfigurationConverter getInputPinConfigurationConverter() {
		return inputPinConfigurationConverter;
	}
	
	public PinStateConverter getPinStateConverter() {
		return pinStateConverter;
	}
	
	public OutputPinConfigurationConverter getOutputPinConfigurationConverter() {
		return outputPinConfigurationConverter;
	}
	
	private void initialize() {
		breezyBoardIdToBreezyBoardMap.clear();
		componentConfigurationIdToComponentConfigurationMap.clear();
		componentToMethods.clear();
		logicStates.clear();
		
		logicStates.add(Boolean.FALSE.toString().toUpperCase());
		logicStates.add(Boolean.TRUE.toString().toUpperCase());
		
		List<InputPinConfiguration> inputPinConfigurations = new ArrayList<InputPinConfiguration>();
		List<OutputPinConfiguration> outputPinConfigurations = new ArrayList<OutputPinConfiguration>();
		breezyBoards = breezyBoardManager.getAllBreezyBoards();
		
		for(BreezyBoard breezyBoard : breezyBoards) {
			breezyBoardIdToBreezyBoardMap.put(breezyBoard.getId(), breezyBoard);
			inputPinConfigurations.addAll(breezyBoard.getInputPinConfigurations());
			
			for(ComponentConfiguration componentConfiguration : breezyBoard.getComponentConfigurations()) {
				componentConfigurationIdToComponentConfigurationMap.put(componentConfiguration.getId(), componentConfiguration);
				
				if(!componentToMethods.containsKey(componentConfiguration.getId())) {
					Map<String, List<ParameterTemplate>> methods = new TreeMap<String, List<ParameterTemplate>>();
//					ComponentTemplate componentTemplate = componentLibraryManager.getComponentTemplateFor(componentConfiguration);
					ComponentTemplate componentTemplate = componentLibraryManager.getComponentTemplateFor(componentConfiguration.getComponentType());  // is it getName()?
					
					for(MethodTemplate methodTemplate : componentTemplate.getMethods()) {
						List<ParameterTemplate> parameterTemplates = new ArrayList<ParameterTemplate>();
						
						for(ParameterTemplate parameterTemplate : methodTemplate.getParameters()) {
							parameterTemplates.add(parameterTemplate);
						}
						
						methods.put(methodTemplate.getComponentMethodName(), parameterTemplates);
					}
					
					componentToMethods.put(componentConfiguration.getId(), methods);
					outputPinConfigurations.addAll(componentConfiguration.getOutputPinConfigurations());
				}
			}
		}

		breezyBoardConverter = new BreezyBoardConverter(breezyBoards);
		inputPinConfigurationConverter = new InputPinConfigurationConverter(inputPinConfigurations);
		outputPinConfigurationConverter = new OutputPinConfigurationConverter(outputPinConfigurations);
		pinStateConverter = new PinStateConverter();
	}
	
	private MacroStep copyMacroStep(MacroStep sourceMacroStep) {
		return copyMacroStep(new MacroStep(), sourceMacroStep);
	}
	
    private MacroStep copyMacroStep(MacroStep destinationMacroStep, MacroStep sourceMacroStep) {
    	destinationMacroStep.setComment(sourceMacroStep.getComment());
    	destinationMacroStep.setComponent(sourceMacroStep.getComponent());
    	destinationMacroStep.setComponentId(sourceMacroStep.getComponentId());
    	destinationMacroStep.setComponentType(sourceMacroStep.getComponentType());
    	destinationMacroStep.setFunction(sourceMacroStep.getFunction());
    	destinationMacroStep.setId(sourceMacroStep.getId());
    	destinationMacroStep.setMountedBoardId(sourceMacroStep.getMountedBoardId());
    	destinationMacroStep.setMountedBoardName(sourceMacroStep.getMountedBoardName());
    	destinationMacroStep.setName(sourceMacroStep.getName());
    	destinationMacroStep.setStep(sourceMacroStep.getStep());
    	destinationMacroStep.setTag(sourceMacroStep.getTag());
    	destinationMacroStep.setValue(sourceMacroStep.getValue());
    	destinationMacroStep.getMethodParameters().clear();
    	
    	if(StringUtils.isNotEmpty(destinationMacroStep.getComponentId())) {
        	UUID componentId = UUID.fromString(destinationMacroStep.getComponentId());
        	Map<String, List<ParameterTemplate>> parameterTemplateByFunction = componentToMethods.get(componentId);
        	
//        	if(parameterTemplateByFunction != null) {
        		List<ParameterTemplate> parameterTemplates = parameterTemplateByFunction.get(destinationMacroStep.getFunction());
            	
        		for(int i = 0; i < sourceMacroStep.getMethodParameters().size(); i++) {
        			ParameterTemplate parameterTemplate = parameterTemplates.get(i);
        			MethodParameter sourceMethodParameter = sourceMacroStep.getMethodParameters().get(i);
        			
            		MethodParameter destinationMethodParameter = new MethodParameter();
            		destinationMethodParameter.setFieldName(sourceMethodParameter.getFieldName());
            		destinationMethodParameter.setFieldValue(sourceMethodParameter.getFieldValue());
            		
            		decorateMethodParameter(destinationMethodParameter, parameterTemplate);
            		
            		destinationMacroStep.getMethodParameters().add(destinationMethodParameter);
        		}
//    		}
    	}
    	
		return destinationMacroStep;
	}

    private void decorateMacro(Macro macro) {
    	for(MacroStep macroStep : macro.getSteps()) {
    		
    		Map<String, List<ParameterTemplate>> parameterTemplatesByComponentIdMap = componentToMethods.get(UUID.fromString(macroStep.getComponentId()));
    		
    		if(parameterTemplatesByComponentIdMap != null) {
        		List<ParameterTemplate> parameterTemplates = parameterTemplatesByComponentIdMap.get(macroStep.getFunction());

        		for(int i = 0; i < macroStep.getMethodParameters().size(); i++) {
        			ParameterTemplate parameterTemplate = parameterTemplates.get(i);
        			MethodParameter methodParameter = macroStep.getMethodParameters().get(i);
        			
        			decorateMethodParameter(methodParameter, parameterTemplate);
        		}
    		}
    	}
    }
    
	private void decorateMethodParameter(MethodParameter methodParameter, ParameterTemplate parameterTemplate) {
		methodParameter.setParameterFieldType(parameterTemplate.getParameterFieldType());
		methodParameter.setRequired(parameterTemplate.isRequired());
	}
	
}
