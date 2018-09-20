package com.farubaba.api.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.farubaba.data.dao.DataCenter;
import com.farubaba.data.dao.DataCenterImpl;
import com.farubaba.data.model.Book;
import com.farubaba.json.JsonFactory;
import com.farubaba.json.JsonService;
import com.farubaba.json.JsonType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonServiceTest {

	private Gson gson = new GsonBuilder().create();
	@Before
	public void setUp(){
		
	}
	
    private static final Class<?>[] PRIMITIVE_TYPES = { int.class, long.class, short.class,
	      float.class, double.class, byte.class, boolean.class, char.class, Integer.class, Long.class,
	      Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class };
	@Test
	public void testJsonService(){
		DataCenter dataCenter = new DataCenterImpl();
		JsonService jsonService = JsonFactory.getInstance().getJsonService();
		
		//合法的JSON
		List<Book> bookList = dataCenter.getBookList("v587");
		Map<String,Book> bookMap = dataCenter.getBookMap("v123");
		Book book = dataCenter.getBook("123", "book name", "book type", "v404");
		String[] strs = new String[]{"{}","[]","{  }","[  ]",""," ","null","NULL","Null","NuLl"};
		int a = 20;
		boolean b = true;
		char c = 10;
		char cc = 'a';
		Double d = Double.valueOf(20.90);
		
		//不合法的JSON
		String jsonNull = null;
		String plainString = "this is primitive json";
		
		JsonType jsonType = jsonService.getJsonType(jsonNull);
		assertFalse(jsonService.isValidJson(jsonType));
		assertFalse(jsonService.isValidJsonObject(jsonType));
		assertFalse(jsonService.isValidJsonArray(jsonType));
		assertFalse(jsonService.isValidJsonNull(jsonType));
		assertFalse(jsonService.isValidJsonPrimitive(jsonType));
	
		jsonType = jsonService.getJsonType(plainString);
		assertFalse(jsonService.isValidJson(jsonType));
		assertFalse(jsonService.isValidJsonObject(jsonType));
		assertFalse(jsonService.isValidJsonArray(jsonType));
		assertFalse(jsonService.isValidJsonNull(jsonType));
		assertFalse(jsonService.isValidJsonPrimitive(jsonType));
		
		
		jsonType = jsonService.getJsonType(gson.toJson(bookList));
		assertTrue(jsonService.isValidJson(jsonType));
		assertFalse(jsonService.isValidJsonObject(jsonType));
		assertTrue(jsonService.isValidJsonArray(jsonType));
		assertFalse(jsonService.isValidJsonNull(jsonType));
		assertFalse(jsonService.isValidJsonPrimitive(jsonType));
		
		
		jsonType = jsonService.getJsonType(gson.toJson(bookMap));
		assertTrue(jsonService.isValidJson(jsonType));
		assertTrue(jsonService.isValidJsonObject(jsonType));
		assertFalse(jsonService.isValidJsonArray(jsonType));
		assertFalse(jsonService.isValidJsonNull(jsonType));
		assertFalse(jsonService.isValidJsonPrimitive(jsonType));
		
		
		jsonType = jsonService.getJsonType(gson.toJson(book));
		assertTrue(jsonService.isValidJson(jsonType));
		assertTrue(jsonService.isValidJsonObject(jsonType));
		assertFalse(jsonService.isValidJsonArray(jsonType));
		assertFalse(jsonService.isValidJsonNull(jsonType));
		assertFalse(jsonService.isValidJsonPrimitive(jsonType));
		
		for(String s : strs){
			jsonType = jsonService.getJsonType(s);
			if(s.equals(strs[0])){//"{}"
				assertTrue(jsonService.isValidJson(jsonType));
				assertTrue(jsonService.isValidJsonObject(jsonType));
				assertFalse(jsonService.isValidJsonArray(jsonType));
				assertFalse(jsonService.isValidJsonNull(jsonType));
				assertFalse(jsonService.isValidJsonPrimitive(jsonType));
			}else if(s.equals(strs[1])){//"[]"
				assertTrue(jsonService.isValidJson(jsonType));
				assertFalse(jsonService.isValidJsonObject(jsonType));
				assertTrue(jsonService.isValidJsonArray(jsonType));
				assertFalse(jsonService.isValidJsonNull(jsonType));
				assertFalse(jsonService.isValidJsonPrimitive(jsonType));
			}else if(s.equals(strs[2])){//"{ }"
				assertTrue(jsonService.isValidJson(jsonType));
				assertTrue(jsonService.isValidJsonObject(jsonType));
				assertFalse(jsonService.isValidJsonArray(jsonType));
				assertFalse(jsonService.isValidJsonNull(jsonType));
				assertFalse(jsonService.isValidJsonPrimitive(jsonType));
			}else if(s.equals(strs[3])){//"[ ]"
				assertTrue(jsonService.isValidJson(jsonType));
				assertFalse(jsonService.isValidJsonObject(jsonType));
				assertTrue(jsonService.isValidJsonArray(jsonType));
				assertFalse(jsonService.isValidJsonNull(jsonType));
				assertFalse(jsonService.isValidJsonPrimitive(jsonType));
			}else if(s.equals(strs[4])){//""
				assertTrue(jsonService.isValidJson(jsonType));
				assertFalse(jsonService.isValidJsonObject(jsonType));
				assertFalse(jsonService.isValidJsonArray(jsonType));
				//""-->JsonNull
				assertTrue(jsonService.isValidJsonNull(jsonType));
				assertFalse(jsonService.isValidJsonPrimitive(jsonType));
			}else if(s.equals(strs[5])){//" "
				assertTrue(jsonService.isValidJson(jsonType));
				assertFalse(jsonService.isValidJsonObject(jsonType));
				assertFalse(jsonService.isValidJsonArray(jsonType));
				//" "-->JsonNull
				assertTrue(jsonService.isValidJsonNull(jsonType));
				assertFalse(jsonService.isValidJsonPrimitive(jsonType));
			}else if(s.equals(strs[6])){//"null"
				assertTrue(jsonService.isValidJson(jsonType));
				assertFalse(jsonService.isValidJsonObject(jsonType));
				assertFalse(jsonService.isValidJsonArray(jsonType));
				//null-->JsonNull
				assertTrue(jsonService.isValidJsonNull(jsonType));
				assertFalse(jsonService.isValidJsonPrimitive(jsonType));
			}else if(s.equals(strs[7])){//"NULL"
				assertTrue(jsonService.isValidJson(jsonType));
				assertFalse(jsonService.isValidJsonObject(jsonType));
				assertFalse(jsonService.isValidJsonArray(jsonType));
				//NULL-->JsonNull
				assertTrue(jsonService.isValidJsonNull(jsonType));
				assertFalse(jsonService.isValidJsonPrimitive(jsonType));
			}else if(s.equals(strs[8])){//"Null"
				assertTrue(jsonService.isValidJson(jsonType));
				assertFalse(jsonService.isValidJsonObject(jsonType));
				assertFalse(jsonService.isValidJsonArray(jsonType));
				//Null-->JsonNull
				assertTrue(jsonService.isValidJsonNull(jsonType));
				assertFalse(jsonService.isValidJsonPrimitive(jsonType));
			}else if(s.equals(strs[9])){//"NuLl"
				assertTrue(jsonService.isValidJson(jsonType));
				assertFalse(jsonService.isValidJsonObject(jsonType));
				assertFalse(jsonService.isValidJsonArray(jsonType));
				//NuLl-->JsonNull
				assertTrue(jsonService.isValidJsonNull(jsonType));
				assertFalse(jsonService.isValidJsonPrimitive(jsonType));
			}
		}
		
		jsonType = jsonService.getJsonType(String.valueOf(a));
		assertTrue(jsonService.isValidJson(jsonType));
		assertFalse(jsonService.isValidJsonObject(jsonType));
		assertFalse(jsonService.isValidJsonArray(jsonType));
		assertFalse(jsonService.isValidJsonNull(jsonType));
		assertTrue(jsonService.isValidJsonPrimitive(jsonType));
		
		jsonType = jsonService.getJsonType(String.valueOf(b));
		assertTrue(jsonService.isValidJson(jsonType));
		assertFalse(jsonService.isValidJsonObject(jsonType));
		assertFalse(jsonService.isValidJsonArray(jsonType));
		assertFalse(jsonService.isValidJsonNull(jsonType));
		assertTrue(jsonService.isValidJsonPrimitive(jsonType));
		
		jsonType = jsonService.getJsonType(String.valueOf(c));
		assertTrue(jsonService.isValidJson(jsonType));
		assertFalse(jsonService.isValidJsonObject(jsonType));
		assertFalse(jsonService.isValidJsonArray(jsonType));
		System.out.println("空白 c = " + String.valueOf(c));
		//assertFalse(jsonService.isValidJsonNull(String.valueOf(c)));
		assertTrue(jsonService.isValidJsonNull(jsonType));
		assertFalse(jsonService.isValidJsonPrimitive(jsonType));
		
		jsonType = jsonService.getJsonType(String.valueOf(cc));
		assertTrue(jsonService.isValidJson(jsonType));
		assertFalse(jsonService.isValidJsonObject(jsonType));
		assertFalse(jsonService.isValidJsonArray(jsonType));
		System.out.println("空白 cc = " + String.valueOf(cc));
		//assertFalse(jsonService.isValidJsonNull(String.valueOf(cc)));
		assertFalse(jsonService.isValidJsonNull(jsonType));
		assertTrue(jsonService.isValidJsonPrimitive(jsonType));
		
		jsonType = jsonService.getJsonType(String.valueOf(d));
		assertTrue(jsonService.isValidJson(jsonType));
		assertFalse(jsonService.isValidJsonObject(jsonType));
		assertFalse(jsonService.isValidJsonArray(jsonType));
		assertFalse(jsonService.isValidJsonNull(jsonType));
		assertTrue(jsonService.isValidJsonPrimitive(jsonType));
	}
}
