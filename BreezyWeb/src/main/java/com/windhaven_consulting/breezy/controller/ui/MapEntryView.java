package com.windhaven_consulting.breezy.controller.ui;

import java.io.Serializable;
import java.util.Map.Entry;

public class MapEntryView<K, V> implements Serializable {

	private static final long serialVersionUID = 1L;

	private K key;
	
	private V value;

	public MapEntryView(Entry<K, V> entry) {
		this.key = entry.getKey();
		this.value = entry.getValue();
	}

	public MapEntryView(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

}
