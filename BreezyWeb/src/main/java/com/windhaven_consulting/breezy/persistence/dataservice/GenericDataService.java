package com.windhaven_consulting.breezy.persistence.dataservice;

import java.util.List;

import com.windhaven_consulting.breezy.persistence.domain.PersistentObject;

public interface GenericDataService <T extends PersistentObject> {

	void save(T bean);
	
	void delete(T bean);
	
	List<T> findAll();

	T findById(String id);
}
