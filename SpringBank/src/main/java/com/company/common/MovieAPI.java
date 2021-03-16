package com.company.common;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieAPI {
	
	public Map getBoxOffice() {
		String url = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20210311";
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, Map.class); //restTemplate.getForObject 쓰면 값 알아서 다 들어오고 그 결과를 알아서 담아줌
	}
	
	public Map getMovieInfo() {
		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=f5eef3421c602c6cb7ea224104795888&movieCd=20205144";
		
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, Map.class); //restTemplate.getForObject 쓰면 값 알아서 다 들어오고 그 결과를 알아서 담아줌
	}
}
