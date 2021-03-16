package com.company.common;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.company.board.service.BoardVO;
import com.company.temp.JsonUtil;

public class JsonUtilTest {
	public static void main(String[] args) throws SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		//1
		JsonUtil json = new JsonUtil();
		Map<String, Object>map = new HashMap<>();
		map.put("username", "홍길동");
		map.put("age", "30");
		map.put("dept", "개발");
		String result = json.toJson(map);
		System.out.println(result); // {"username" : "홍길동", "age" : 30, "dept" : "개발"}
		
		//3
		BoardVO vo = new BoardVO();
		vo.setContent("content");
		vo.setTitle("title");
		vo.setFileName("file");
		result = json.toObjectJson(vo);
	}

}
