package com.windhaven_consulting.breezy.persistence.dataservice.impl;

import java.util.List;

import com.windhaven_consulting.breezy.persistence.dataservice.GenericDataService;
import com.windhaven_consulting.breezy.persistence.domain.PersistentObject;

public abstract class BaseDataServiceImpl<T extends PersistentObject> implements GenericDataService<T> {

	@Override
	public abstract void save(T bean);

	@Override
	public abstract void delete(T bean);

	@Override
	public abstract List<T> findAll();

	@Override
	public abstract T findById(String id);

}
