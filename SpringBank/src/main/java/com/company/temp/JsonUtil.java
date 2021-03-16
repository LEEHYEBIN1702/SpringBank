package com.company.temp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



public class JsonUtil {
	public String toJson(Map<String, Object>map) {
		boolean start = true;
		StringBuilder result = new StringBuilder();
		//to do
		result.append("{ ");
		Iterator<String> keys = map.keySet().iterator();
		while(keys.hasNext()) {
			String key = keys.next();
			String value = (String) map.get(key);
			if ( !start ) {
				result.append(", ");
			}else {
				start= false;
			}
			result.append(key +":"+ value);
		}
		result.append(" }");
		return result.toString();
	}
	
	public String toJson(List<Map<String, Object>>map) {
		String result = "";
		//to do
		return result;
	}
	
	public String toObjectJson(Object vo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException { //리플렉션 기능 사용 (검색해보기)
		String result="";
		//to do
		Field[] fields = vo.getClass().getDeclaredFields();
		for (Field field : fields) {	
			String fieldName = field.getName();
			String method = "get" +field.getName()
			                .substring(0,1).toUpperCase()
					        +field.getName().substring(1);
			Method m = vo.getClass().getDeclaredMethod(method, null);
			String value = (String) m.invoke(vo,null); //invoke는 메소드 실행시켜주는 기능.
			
			System.out.println(fieldName +":"+value);
		}
		return result;
	}
	
	public String toObjectJson(List<Object> vo) {
		String result = "";
		//to do
		return result;
	}

}
