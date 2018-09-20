package com.farubaba.api.test;

import java.util.Properties;
import java.util.Set;

import org.junit.Test;

import com.farubaba.api.singleton.AppConfig;

public class PropertiesTest {
	
	@Test
	public void testProp() {
		Properties prop = AppConfig.getInstance().reloadDefaultProperties();
		Set<Object> keySet = prop.keySet();
		for(Object key : keySet) {
			System.out.println(prop.get(key));	
		}
		
	}
}
