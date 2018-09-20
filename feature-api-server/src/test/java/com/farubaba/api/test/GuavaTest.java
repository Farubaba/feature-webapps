package com.farubaba.api.test;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.farubaba.api.demo.GuavaDemo;
import com.opensymphony.xwork2.interceptor.annotations.Before;

public class GuavaTest {
	private GuavaDemo guavaDemo;
	@Before
	public void setUp(){
		guavaDemo = new GuavaDemo();
	}
	
	@Test
	public void testListMultiMap(){
		List<String> list = guavaDemo.testListMultiMap();
		for(String s : list){
			System.out.println(s);
		}
	}
	
	@Test
	public void testSetMultiMap(){
		Set<String> set = guavaDemo.tesSetMultiMap();
		for(String s : set){
			System.out.println(s);
		}
	}
	
	@Test
	public void testDifficut(){
		String target = new String(new char[5]);
		System.out.println(target);
		System.out.println(target.replace('\0', '0'));
	}
}
