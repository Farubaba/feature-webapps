package com.farubaba.api.demo;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SortedSetMultimap;

public class GuavaDemo {
	
	public List<String> testListMultiMap(){
		ListMultimap<String, String> listMultiMap = MultimapBuilder.hashKeys().arrayListValues().build();
		listMultiMap.put("a", "1");
		listMultiMap.put("a", "2");
		listMultiMap.put("a", "2");
		listMultiMap.put("b", "3");
		listMultiMap.put("c", "4");
		
		List<String> list= listMultiMap.get("a");
		
		return list;
		
	}
	
	public Set<String> tesSetMultiMap(){
		SortedSetMultimap<String, String> setMultiMap = MultimapBuilder.treeKeys().treeSetValues().build();
		setMultiMap.put("a", "1");
		setMultiMap.put("a", "2");
		setMultiMap.put("a", "2");
		setMultiMap.put("b", "3");
		setMultiMap.put("c", "4");
		
		Set<String> set= setMultiMap.get("a");	
		return set;
	}
}
