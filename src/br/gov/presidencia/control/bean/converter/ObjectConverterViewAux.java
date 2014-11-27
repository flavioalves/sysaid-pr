package br.gov.presidencia.control.bean.converter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import org.omnifaces.cdi.ViewScoped;

@Named
@ViewScoped
public class ObjectConverterViewAux implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Map<String, Object>> convertersMap = new HashMap<String, Map<String,Object>>();
	private Map<String, Map<Object, String>> reverseConvertersMap = new HashMap<String, Map<Object,String>>();
	private Map<String, Integer> incrementors = new HashMap<String, Integer>();
	
	public Map<String, Object> getConverterMap(String key) {
		Map<String, Object> cMap = convertersMap.get(key);
		if(cMap == null) {
			cMap = new HashMap<String, Object>();
			convertersMap.put(key, cMap);
		}
		return cMap;
	}
	
	public Map<Object, String> getReverseConverterMap(String key) {
		Map<Object, String> rcMap = reverseConvertersMap.get(key);
		if(rcMap == null) {
			rcMap = new HashMap<Object, String>();
			reverseConvertersMap.put(key, rcMap);
		}
		return rcMap;
	}
	
	public Integer getIncrementor(String key) {
		Integer incrementor = incrementors.get(key);
		if(incrementor == null) {
			incrementor = 0;
			incrementors.put(key, incrementor);
		}
		return incrementor;
	}
	
	public Integer increment(String key) {
		Integer incrementor = getIncrementor(key)+1;
		incrementors.put(key, incrementor);
		return incrementor;
	}
	
}
