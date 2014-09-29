package com.justanymsg.mel.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Mappers implements InitializingBean {

	private static Map<String, Set<String>> REVERSE_CACHE = new HashMap<String, Set<String>>();

	@Autowired
	@Qualifier("applicationDomain")
	private Properties properties;

	public void afterPropertiesSet() {
		// Populating reverse cache
		for (Object key : properties.keySet()) {
			final String k = String.valueOf(key);
			final String v = String.valueOf(properties.get(key));
			if (!REVERSE_CACHE.containsKey(v)) {
				REVERSE_CACHE.put(v, new HashSet<String>());
			}
			REVERSE_CACHE.get(v).add(k);
		}
	}

	public boolean containsKey(String type, String value) {
		return properties.containsKey(type + '.' + value);
	}

	public String getKey(String type, Object value) {
		final String strValue = String.valueOf(value).trim();
		if (REVERSE_CACHE.containsKey(strValue)) {
			for (String key : REVERSE_CACHE.get(strValue)) {
				if (key.startsWith(type)) {
					return key.replaceAll(".*\\.", "");
				}
			}
		}
		return null;
	}

	public Object getValue(String type, String value) {
		return properties.getProperty(trim(type) + '.' + trim(value));
	}

	private String trim(String value) {
		return value == null ? "" : value.trim();
	}

	public String getStringValue(String type, String value) {
		try {
			return String.valueOf(getValue(type, value));
		} catch (Exception e) {
			return null;
		}
	}

	public Integer getIntegerValue(String type, String value) {
		return Integer.valueOf(getStringValue(type, value));
	}
	
	public Boolean getBooleanValue(String type, String value)
	{
		return Boolean.valueOf(getStringValue(type, value));
	}

	public Set<String> getSetValues(String type, String value) {
		try {
			return new HashSet<String>(Arrays.asList(getStringValue(type, value).split(",")));
		} catch (Exception e) {
			return null;
		}
	}

}
