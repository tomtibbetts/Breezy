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

import com.windhaven_consulting.breezy.component.Component;
import com.windhaven_consulting.breezy.component.annotation.ParameterFieldType;
import com.windhaven_consulting.breezy.component.library.ComponentTemplate;
import com.windhaven_consulting.breezy.component.library.ComponentTemplateLibraryManager;
import com.windhaven_consulting.breezy.component.library.MethodTemplate;
import com.windhaven_consulting.breezy.component.library.ParameterTemplate;
import com.windhaven_consulting.breezy.controller.ui.converter.DigitalInputPinConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.DigitalOutputPinConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.MountedBoardConverter;
import com.windhaven_consulting.breezy.controller.ui.converter.PinStateConverter;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalInputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.DigitalOutputPin;
import com.windhaven_consulting.breezy.embeddedcontroller.PinState;
import com.windhaven_consulting.breezy.manager.MacroManager;
import com.windhaven_consulting.breezy.manager.MountedBoardManager;
import com.windhaven_consulting.breezy.manager.impl.MountedBoard;
import com.windhaven_consulting.breezy.persistence.domain.Macro;
import com.windhaven_consulting.breezy.persistence.domain.MacroStep;
import com.windhaven_consulting.breezy.persistence.domain.MethodParameter;

@ManagedBean
@ViewScoped
public class MacroBuilderView implements Serializable {
	static final Logger LOG = LoggerFactory.getLogger(MacroBuilderView.class);

	private static final long serialVersionUID = 1L;

	@Inject
	private MountedBoardManager mountedBoardManager;
	
	@Inject
	private MacroManager macroManager;
	
	@Inject
	private ComponentTemplateLibraryManager componentLibraryManager;
	
	private List<MountedBoard> mountedBoards = null;
	
	private Map<String, String> mountedBoardIdToNameMap = new HashMap<String, String>();
	
//	private Map<String, String> componentIdToNameMap = new HashMap<String, String>();
	
	private Map<String, Component> componentIdToComponentMap = new HashMap<String, Component>();

	private List<Component> components = new ArrayList<Component>();
	
	private List<String> functions = new ArrayList<String>();
	
	private List<DigitalInputPin> inputPins = new ArrayList<DigitalInputPin>();

	private List<DigitalOutputPin> digitalOutputPins = new ArrayList<DigitalOutputPin>();

	private Map<String, Map<String, List<ParameterTemplate>>> componentToMethods = new HashMap<String, Map<String, List<ParameterTemplate>>>();
	
	private MacroStep workingMacroStep = new MacroStep();

	private Macro macro;
	
	private String macroId;

	private String action;

	private int selectedMacroStepIndex;

	private boolean isNewLineMode;
	
	private MountedBoardConverter mountedBoardConverter;

	private PinStateConverter pinStateConverter;
	
	private DigitalInputPinConverter digitalInputPinConverter;

	private DigitalOutputPinConverter digitalOutputPinConverter;

	private List<String> logicStates = new ArrayList<String>();

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
	
	public void deleteMacro() throws IOException {
		macroManager.delete(macro);

		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().redirect("macroChooser.jsf");
	}

	public void onMountedBoardChange(final AjaxBehaviorEvent event) {
		String mountedBoardId = (String) ((UIOutput) event.getSource()).getValue();
		MountedBoard mountedBoard = mountedBoardManager.getById(mountedBoardId);
		
		if(mountedBoard != null) {
        	components = mountedBoard.getComponents();
		}
		else {
			components.clear();
		}
    	
    	functions.clear();
	}
	
	public void onComponentChange(final AjaxBehaviorEvent event) {
		String key = (String) ((UIOutput) event.getSource()).getValue();
		
		if(StringUtils.isNotEmpty(key)) {
			functions = new ArrayList<String>(componentToMethods.get(key).keySet());
			Collections.sort(functions);
        	digitalOutputPins = componentIdToComponentMap.get(key).getOutputPins();
		}
		else {
			functions.clear();
			digitalOutputPins.clear();
		}
	}
	
	public void onFunctionChange(final AjaxBehaviorEvent event) {
		String function = (String) ((UIOutput) event.getSource()).getValue();
		List<MethodParameter> methodParameters = new ArrayList<MethodParameter>();
		List<ParameterTemplate> parameterTemplates = componentToMethods.get(workingMacroStep.getComponentId()).get(function);

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
		MountedBoard mountedBoard = mountedBoardManager.getById(mountedBoardId);
		
		if(mountedBoard != null) {
        	inputPins = mountedBoard.getInputPins();
		}
		else {
			inputPins.clear();
		}
	}
	
	public List<MountedBoard> getMountedBoards() {
		return mountedBoards;
	}

	public String getMountedBoardName(String id) {
		return mountedBoardIdToNameMap.get(id);
	}
	
	public String getComponentName(String id) {
		String name = StringUtils.EMPTY;
		
		if(StringUtils.isNotEmpty(id)) {
			Component component = componentIdToComponentMap.get(id);
			
			if(component != null) {
				name = componentIdToComponentMap.get(id).getName();
			}
		}

		return name;
	}
	
    public Macro getMacro() {
    	return macro;
    }
	
	public List<Component> getComponents() {
		return components;
	}
	
	public Collection<String> getFunctions() {
		return functions;
	}
	
	public List<DigitalInputPin> getInputPins() {
		return inputPins;
	}

	public List<DigitalOutputPin> getDigitalOutputPins() {
		return digitalOutputPins;
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
			MountedBoard mountedBoard = mountedBoardManager.getById(workingMacroStep.getMountedBoardId());
			
			if(mountedBoard != null) {
	        	components = mountedBoard.getComponents();
			}
			
			if(StringUtils.isNotEmpty(workingMacroStep.getComponentId())) {
				Map<String, List<ParameterTemplate>> componentMethodsMap = componentToMethods.get(workingMacroStep.getComponentId());

				if(componentMethodsMap != null) {
					functions = new ArrayList<String>(componentToMethods.get(workingMacroStep.getComponentId()).keySet());
					Collections.sort(functions);
				}
				else {
					functions = Collections.emptyList();
					workingMacroStep.getMethodParameters().clear();
				}
			}
			
			for(MethodParameter methodParameter : workingMacroStep.getMethodParameters()) {
				if(ParameterFieldType.MOUNTED_BOARD == methodParameter.getParameterFieldType()) {
					MountedBoard parameterMountedbBoard = mountedBoardManager.getById(methodParameter.getFieldValue());
					
					if(parameterMountedbBoard != null) {
						inputPins = parameterMountedbBoard.getInputPins();
					}
					else {
						inputPins.clear();;
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
		System.out.println("insert before macro step");
		MacroStep newLine = new MacroStep();

		selectedMacroStepIndex = index;
		macro.getSteps().add(index, newLine);
		workingMacroStep = copyMacroStep(newLine);
		
		isNewLineMode = true;
	}
	
	public void insertEditRowAfter(int index) {
		System.out.println("insert after macro step");
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
		System.out.println("delete macro step");
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
	
	public MountedBoardConverter getMountedBoardConverter() {
		return mountedBoardConverter;
	}
	
	public DigitalInputPinConverter getDigitalInputPinConverter() {
		return digitalInputPinConverter;
	}
	
	public PinStateConverter getPinStateConverter() {
		return pinStateConverter;
	}
	
	public DigitalOutputPinConverter getDigitalOutputPinConverter() {
		return digitalOutputPinConverter;
	}
	
	private void initialize() {
		mountedBoardIdToNameMap.clear();
//		componentIdToNameMap.clear();
		componentIdToComponentMap.clear();
		componentToMethods.clear();
		logicStates.clear();
		
		logicStates.add(Boolean.FALSE.toString().toUpperCase());
		logicStates.add(Boolean.TRUE.toString().toUpperCase());
		
		List<DigitalInputPin> digitalInputPins = new ArrayList<DigitalInputPin>();
		List<DigitalOutputPin> digitalOutputPins = new ArrayList<DigitalOutputPin>();
		mountedBoards = mountedBoardManager.getAllMountedBoards();
		
		for(MountedBoard mountedBoard : mountedBoards) {
			digitalInputPins.addAll(mountedBoard.getInputPins());
			mountedBoardIdToNameMap.put(mountedBoard.getId(), mountedBoard.getName());
			
			for(Component component : mountedBoard.getComponents()) {
//				componentIdToNameMap.put(component.getId(), component.getName());
				componentIdToComponentMap.put(component.getId(), component);
				
				if(!componentToMethods.containsKey(component.getId())) {
					Map<String, List<ParameterTemplate>> methods = new TreeMap<String, List<ParameterTemplate>>();
					ComponentTemplate componentTemplate = componentLibraryManager.getComponentTemplateFor(component);
					
					for(MethodTemplate methodTemplate : componentTemplate.getMethods()) {
						List<ParameterTemplate> parameterTemplates = new ArrayList<ParameterTemplate>();
						
						for(ParameterTemplate parameterTemplate : methodTemplate.getParameters()) {
							parameterTemplates.add(parameterTemplate);
						}
						
						methods.put(methodTemplate.getComponentMethodName(), parameterTemplates);
					}
					
					componentToMethods.put(component.getId(), methods);
					digitalOutputPins.addAll(component.getOutputPins());
				}
			}
		}

		mountedBoardConverter = new MountedBoardConverter(mountedBoards);
		digitalInputPinConverter = new DigitalInputPinConverter(digitalInputPins);
		digitalOutputPinConverter = new DigitalOutputPinConverter(digitalOutputPins);
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
    	
    	Map<String, List<ParameterTemplate>> parameterTemplateByFunction = componentToMethods.get(destinationMacroStep.getComponentId());
    	
    	if(parameterTemplateByFunction != null) {
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
		}
    	
		return destinationMacroStep;
	}

    private void decorateMacro(Macro macro) {
    	for(MacroStep macroStep : macro.getSteps()) {
    		
    		Map<String, List<ParameterTemplate>> parameterTemplatesByComponentIdMap = componentToMethods.get(macroStep.getComponentId());
    		
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
