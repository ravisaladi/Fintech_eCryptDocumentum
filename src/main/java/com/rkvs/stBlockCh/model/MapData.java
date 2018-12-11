/**
 * 
 */
package com.rkvs.stBlockCh.model;

import java.util.HashMap;

/**
 * @author rsaladi
 *
 */
public class MapData {
	private HashMap<String, String> keyHashMap;
	
	public MapData(HashMap<String,String> map) {
		keyHashMap = map;
	}
	
	public String getSringPair(String key) {
		return keyHashMap.get(key);
	}
	
	public void addToMap(String key, String value) {
		keyHashMap.put(key, value);
	}
	
	public HashMap<String,String> getHashMap() {
		return keyHashMap;
	}

}
