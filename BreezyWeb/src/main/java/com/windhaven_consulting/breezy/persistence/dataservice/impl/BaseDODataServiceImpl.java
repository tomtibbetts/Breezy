package com.windhaven_consulting.breezy.persistence.dataservice.impl;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.windhaven_consulting.breezy.persistence.dataservice.GenericDataService;
import com.windhaven_consulting.breezy.persistence.domain.PersistentObject;
import com.windhaven_consulting.breezy.persistence.exceptions.BreezyPersistenceException;

public abstract class BaseDODataServiceImpl <T extends PersistentObject> implements GenericDataService<T> {

	private Class<T> persistentClass;
	
	private ObjectMapper objectMapper;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void postConstruct() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		
		// set up the necessary directory structures
		try {
			Path resourcePath = Paths.get(getResoucePath());
			Files.createDirectories(resourcePath);
		} catch (IOException e) {
			throw new PersistenceException("Unable to create directory structure for " + persistentClass.getName(), e);
		}
		
        // configure Object mapper for pretty print
		objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}

	@Override
	public void save(T persistentObject) {
        try {
        	if(persistentObject.getId() == null) {
        		persistentObject.setId(UUID.randomUUID());
        	}
        	
            Path path = Paths.get(getResoucePath(), persistentObject.getId() + "." + getFileExtension());
           
    		if(!Files.exists(path)) {
    			path = Files.createFile(path);
    		}

    		objectMapper.writeValue(path.toFile(), persistentObject);
		} catch (IOException e) {
			throw new BreezyPersistenceException("Cannot save: " + getResoucePath() + persistentObject.getId() + "*." + getFileExtension(), e);
		}
	}

	@Override
	public T findById(String id) {
        T persistentObject = null;

        if(StringUtils.isNotEmpty(id)) {
            Path path = Paths.get(getResoucePath(), id + "." + getFileExtension());

            try {
    			persistentObject = objectMapper.readValue(path.toFile(), persistentClass);
    		} catch (IOException e) {
    			throw new BreezyPersistenceException("IO Exception encountered finding file: " + getResoucePath() + id + "*." + getFileExtension(), e);
    		}
        }
		
		return persistentObject;
	}

	@Override
	public void delete(T persistentObject) {
        Path path = Paths.get(getResoucePath(), persistentObject.getId() + "." + getFileExtension());

        try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			throw new BreezyPersistenceException("Cannot delete: " + getResoucePath() + persistentObject.getId() + "*." + getFileExtension(), e);
		}
	}

	@Override
	public List<T> findAll() {
		List<T> persistentObjects = new ArrayList<T>();
		Path resourcePath = Paths.get(getResoucePath());
		
		if(Files.isDirectory(resourcePath)) {
	        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(resourcePath, "*." + getFileExtension())) {
	            for (Path path : directoryStream) {
	                T persistentObject = objectMapper.readValue(path.toFile(), persistentClass);
	                persistentObjects.add(persistentObject);
	            }
			} catch (IOException e) {
				throw new BreezyPersistenceException("IOException thrown for " + resourcePath + "*." + getFileExtension(), e);
			}
		}
		else {
			throw new BreezyPersistenceException("Defined resource: " + resourcePath + " is not a directory.");
		}
		
		// sorts by name
		Collections.sort(persistentObjects);
		
		return persistentObjects;
	}

	protected abstract String getResoucePath();
	
	protected abstract String getFileExtension();
	
	protected ObjectMapper getObjectMapper() {
		return objectMapper;
	}
}
