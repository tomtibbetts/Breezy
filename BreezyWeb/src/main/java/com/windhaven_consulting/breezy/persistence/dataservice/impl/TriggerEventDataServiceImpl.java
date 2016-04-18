package com.windhaven_consulting.breezy.persistence.dataservice.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import com.windhaven_consulting.breezy.persistence.dataservice.TriggerEventDataService;
import com.windhaven_consulting.breezy.persistence.domain.TriggerEvent;
import com.windhaven_consulting.breezy.persistence.exceptions.BreezyPersistenceException;

public class TriggerEventDataServiceImpl extends BaseDODataServiceImpl<TriggerEvent> implements TriggerEventDataService {

	private static final String TRIGGER_EVENTS_FULL_FILE_NAME = "events.events";

	private static final String TRIGGER_EVENTS_FILE_EXTENSION = "events";
	
	@Resource(name="breezy.eventsResourcePath")
	private String eventsResourcePath;

	@Resource(name="breezy.releaseRevisionNumber")
	private String releaseRevisionNumber;
	
	@Override
	protected String getResoucePath() {
		return eventsResourcePath;
	}

	@Override
	protected String getFileExtension() {
		return TRIGGER_EVENTS_FILE_EXTENSION;
	}

	@Override
	public void saveAll(List<TriggerEvent> triggerEvents) {
		for(TriggerEvent triggerEvent : triggerEvents) {
			if(triggerEvent.getId() == null) {
				triggerEvent.setId(UUID.randomUUID());
				triggerEvent.setReleaseRevisionNumber(releaseRevisionNumber);
			}
		}
		
        try {
            Path path = Paths.get(eventsResourcePath, TRIGGER_EVENTS_FULL_FILE_NAME);
           
    		if(!Files.exists(path)) {
    			path = Files.createFile(path);
    		}

    		getObjectMapper().writeValue(path.toFile(), triggerEvents);
		} catch (IOException e) {
			throw new BreezyPersistenceException("IOException thrown", e);
		}
	}

	@Override
	public List<TriggerEvent> findAll() {
		List<TriggerEvent> triggerEvents = new ArrayList<>();
		Path path = Paths.get(eventsResourcePath, TRIGGER_EVENTS_FULL_FILE_NAME);

		try {
    		if(!Files.exists(path)) {
    			path = Files.createFile(path);
        		getObjectMapper().writeValue(path.toFile(), triggerEvents);
    		}

			triggerEvents = new ArrayList<>();
			triggerEvents.addAll(Arrays.asList(getObjectMapper().readValue(path.toFile(), TriggerEvent[].class)));
		} catch (IOException e) {
			throw new BreezyPersistenceException("JsonParseException thrown", e);
		}
		
		return triggerEvents;
	}

}
