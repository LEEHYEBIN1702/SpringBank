package com.company.temp;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.bank.service.BankVO;
import com.company.common.BankAPI;

@Controller
public class BankController {
	
    @Autowired BankAPI bankAPI; 
	
	@RequestMapping("/auth")
	public String auth() throws Exception {
		String reqURL ="https://testapi.openbanking.or.kr/oauth/2.0/authorize";
		//String reqURL ="https://testapi.openbanking.or.kr/oauth/2.0/authorize_account";
		String response_type = "code";
		String client_id ="bdfbcaa0-d62f-4718-9427-7329bc3c11bb";
		String redirect_uri = "http://localhost:88/temp/callback";
		String scope = "login inquiry transfer";
		String state = "01234567890123456789012345678912";
		String auth_type = "0";
		
		StringBuilder qstr = new StringBuilder();
		qstr.append("response_type="+response_type)
		    .append("&client_id=" + client_id)
		    .append("&redirect_uri=" + redirect_uri)
		    .append("&scope=" + scope)
		    .append("&state=" + state)
		    .append("&auth_type=" + auth_type);
		return "redirect:" + reqURL + "?" + qstr.toString();
	                  
	}
	
	@RequestMapping("/callback")
	public String callback(@RequestParam Map<String, Object> map, HttpSession session) {
		System.out.println(map.get("code"));
		String code = (String)map.get("code");
		//access_token 받기
		
		  String access_token = bankAPI.getAccessToken(code);
		  System.out.println("access_token" + access_token);
		
		//session
		  session.setAttribute("access_token", access_token);
		 
		return "home";
	}
	
	@RequestMapping("/userinfo")
	public String userinfo(HttpSession session, 
			               HttpServletRequest request) {
		//String access_token = (String)session.getAttribute("access_token");
		//String access_token = request.getParameter("access_token");
		String access_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzcwNTI3Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2MjMxMzk5OTYsImp0aSI6ImQ1OWNhY2E0LTI3OWEtNGM3Ny1hMDQyLTRhM2Q3M2JlZjFjYiJ9.yNXtaky_Fm7vfDn7UBVAhfDpantQk7VtD-5jiru6iSo";
		String use_num = "1100770527"; 
		Map<String, Object> userinfo = bankAPI.getUserInfo(access_token, use_num);
		System.out.println("userinfo" + userinfo);
		return "home";
		
	}
	
	String access_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzcwNTI3Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2MjMxMzk5OTYsImp0aSI6ImQ1OWNhY2E0LTI3OWEtNGM3Ny1hMDQyLTRhM2Q3M2JlZjFjYiJ9.yNXtaky_Fm7vfDn7UBVAhfDpantQk7VtD-5jiru6iSo";
	@RequestMapping("/getAccountList")
	public String getAccountList(HttpServletRequest request, Model model) {
		String use_num = "1100770527"; 
		Map<String, Object> map = bankAPI.getAccountList(access_token, use_num);
		System.out.println("userinfo" + map);
		model.addAttribute("list", map);
		return "bank/getAccountList";
		
	}
	
	@RequestMapping("/getBalance")
	public String getBalance(BankVO vo, Model model) {
		vo.setAccess_token(access_token);
		Map<String, Object> map = bankAPI.getBalance(vo);
		System.out.println("잔액=" + map);
		model.addAttribute("balance", map);
		return "bank/getBalance";	
	}
	
	@ResponseBody
	@RequestMapping("/ajaxGetBalance")
	public Map ajaxGetBalance(BankVO vo) {
		vo.setAccess_token(access_token);
		Map<String, Object> map = bankAPI.getBalance(vo);
		System.out.println("잔액=" + map);
		return map;	
	}
	
	@RequestMapping("/getOrgAuthorize")
	public String getOrgAuthorize() {
		Map<String, Object> map = bankAPI.getOrgAccessToken();
		System.out.println("access_token :" + map.get("access_token"));
		return "home";
	}
}
