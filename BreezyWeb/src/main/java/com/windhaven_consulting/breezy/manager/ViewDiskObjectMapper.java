package com.windhaven_consulting.breezy.manager;

import java.util.List;

public interface ViewDiskObjectMapper<V,D> {

	D getAsDiskObject(V source);
	
	List<D> getAsDiskObjects(List<V> sourceCollection);
	
	V getAsViewObject(D source);
	
	List<V> getAsViewObjects(List<D> sourceCollection);
}
