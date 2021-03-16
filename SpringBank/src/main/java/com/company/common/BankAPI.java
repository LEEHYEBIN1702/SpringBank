package com.company.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.company.bank.service.BankVO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Component
public class BankAPI {


	public String getAccessToken (String authorize_code) {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://testapi.openbanking.or.kr/oauth/2.0/token";
        
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            
            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=bdfbcaa0-d62f-4718-9427-7329bc3c11bb");
            sb.append("&client_secret=d94fcd37-18ee-4574-8194-f777b216b0cd");
            sb.append("&redirect_uri=http://localhost:88/temp/callback");
            sb.append("&code=xYI6HjKgHdm6WR1yAiHyGkafU15jNP");
            bw.write(sb.toString());
            bw.flush();
            
            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
 
            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            
            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
            
            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);
            
            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return access_Token;
    }
	
	public HashMap<String, Object> getAccountList(String access_token, String use_num) {
    // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
    HashMap<String, Object> map = new HashMap<>();
    String reqURL = "https://testapi.openbanking.or.kr/v2.0/account/list";
    StringBuilder qstr = new StringBuilder();
	qstr.append("user_seq_no="+ "1100770527")
	    .append("&include_cancel_yn=" + "Y")
	    .append("&sort_order=" + "D");
    try {
        URL url = new URL(reqURL + "?" + qstr );
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        // 요청에 필요한 Header에 포함될 내용
        conn.setRequestProperty("Authorization", "Bearer " + access_token);
        // 출력되는 값이 200이면 정상작동
        int responseCode = conn.getResponseCode();
        System.out.println("responseCode : " + responseCode);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        String line = "";
        String result = "";
        
        while ((line = br.readLine()) != null) {
            result += line;
        }
        System.out.println("response body : " + result);
        
        //string -> 맵에 담기
        Gson gson = new Gson();
        map = gson.fromJson(result, HashMap.class);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return map;
} 
	public String getDate() { //데이트 포맷
		String str = "";
		Date date= new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
		str = format.format(date);
		return str;
	}
	public String getRand() { //난수값 만들기
		long time = System.currentTimeMillis();
		String str = Long.toString(time);
		return str.substring(str.length()-9);
		
	}
	public String getRand32() { //32자리 난수값 만들기
		long time = System.currentTimeMillis();
		String str = Long.toString(time);
		return str.substring(str.length()-32);
		
	}
	
	public HashMap<String,Object> getBalance(BankVO vo) {
		 HashMap<String, Object> map = new HashMap<>();
		    String reqURL = "https://testapi.openbanking.or.kr/v2.0/account/balance/fin_num";
		    StringBuilder qstr = new StringBuilder();
			qstr.append("bank_tran_id="+"M202111682"+"U"+getRand())
			    .append("&fintech_use_num="+vo.getFintech_use_num())
			    .append("&tran_dtime="+getDate());
		    try {
		        URL url = new URL(reqURL + "?" + qstr );
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		        conn.setRequestMethod("GET");
		        
		        // 요청에 필요한 Header에 포함될 내용
		        conn.setRequestProperty("Authorization", "Bearer " + vo.getAccess_token());
		        // 출력되는 값이 200이면 정상작동
		        int responseCode = conn.getResponseCode();
		        System.out.println("responseCode : " + responseCode);
		        
		        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        
		        String line = "";
		        String result = "";
		        
		        while ((line = br.readLine()) != null) {
		            result += line;
		        }
		        System.out.println("response body : " + result);
		        
		        //string -> 맵에 담기
		        Gson gson = new Gson();
		        map = gson.fromJson(result, HashMap.class);
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return map;
	}
	
	public HashMap<String, Object> getUserInfo(String access_token, String use_num) {
	    // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
	    HashMap<String, Object> userInfo = new HashMap<>();
	    String reqURL = "https://testapi.openbanking.or.kr/v2.0/account/list";
	    StringBuilder qstr = new StringBuilder();
		qstr.append("user_seq_no="+ "1100770527")
		    .append("&include_cancel_yn=" + "Y")
		    .append("&sort_order=" + "D");
	    try {
	        URL url = new URL(reqURL + "?" + qstr );
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        
	        // 요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Authorization", "Bearer " + access_token);
	        // 출력되는 값이 200이면 정상작동
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        
	        String line = "";
	        String result = "";
	        
	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        System.out.println("response body : " + result);
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return userInfo;
	}

	public Map<String, Object> getOrgAccessToken() {
		   Map<String, Object> map = new HashMap<String, Object>();
		    String access_Token = "";
	        String refresh_Token = "";
	        String reqURL = "https://testapi.openbanking.or.kr/oauth/2.0/token";
	        
	        try {
	            URL url = new URL(reqURL);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            
	            //    POST 요청을 위해 기본값이 false인 setDoOutput을 true로
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);
	            
	            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	            
	            //    POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	            StringBuilder sb = new StringBuilder();
	            sb.append("client_id=bdfbcaa0-d62f-4718-9427-7329bc3c11bb");
	            sb.append("&client_secret=d94fcd37-18ee-4574-8194-f777b216b0cd");
	            sb.append("&scope=oob");
	            sb.append("&grant_type=client_credentials");
	            bw.write(sb.toString());
	            bw.flush();
	            
	            //    결과 코드가 200이라면 성공
	            int responseCode = conn.getResponseCode();
	            System.out.println("responseCode : " + responseCode);
	 
	            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = "";
	            String result = "";
	            
	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            System.out.println("response body : " + result);
	            
	            //    Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
	            Gson gson = new Gson();
	            map = gson.fromJson(result, HashMap.class);
	            
	            br.close();
	            bw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } 
	        
		return map;
	}
	
	public Map<String, Object> getOrgAccessTokenRestTemplate() {
		String reqURL = "https://testapi.openbanking.or.kr/oauth/2.0/token";   
		
		String access_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJNMjAyMTExNjgyIiwic2NvcGUiOlsib29iIl0sImlzcyI6Imh0dHBzOi8vd3d3Lm9wZW5iYW5raW5nLm9yLmtyIiwiZXhwIjoxNjIzMzA2NTk4LCJqdGkiOiJkN2UwNzNkZS1mODM3LTQ1OTAtODgxMy01MGVkOTcyZWU2YjYifQ.bBUCZTgc9xoMoUTqoPy7qEW-Em6VIBtvkVwgfV_yg7s";
		String client_id = "bdfbcaa0-d62f-4718-9427-7329bc3c11bb";
		String client_secret = "d94fcd37-18ee-4574-8194-f777b216b0cd";
		
        MultiValueMap<String, String> param = new LinkedMultiValueMap<String, String>();
        param.add("client_id", client_id);
        param.add("client_secret", client_secret);
        param.add("scope", "oob");
        param.add("grant_type", "client_credentials");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        HttpEntity<MultiValueMap<String, String>> request = 
        		new HttpEntity<MultiValueMap<String, String>>( param, headers);
        
        RestTemplate restTemplate = new RestTemplate();
        Map map = restTemplate.postForObject(reqURL, request, Map.class);
        
        return map;
	}

}
