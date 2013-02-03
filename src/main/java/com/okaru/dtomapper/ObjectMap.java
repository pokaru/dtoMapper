package com.okaru.dtomapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper around your typical map.  This class will be used throughout the
 * mapping framwork for referencing objects which will be mapped to and from.
 * 
 * @author pokaru
 *
 */
public class ObjectMap {
	private Map<String, Object> objectMap = new HashMap<String, Object>();
	
	public void put(String key, Object object){
		objectMap.put(key, object);
	}
	
	public Object get(String key){
		return objectMap.get(key);
	}
	
	/**
	 * Returns a read-only version of the object map.
	 * 
	 * @return
	 */
	public Map<String, Object> getObjectMap(){
		return Collections.unmodifiableMap(objectMap);
	}
}
