package com.company.temp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.bank.service.TestVO;

@Controller
public class TestController {

	// get 방식으로 보내서 커맨드 객체(VO)에 담아보겠다.

	@RequestMapping("/getTest1")
	public String getTest1(TestVO vo) {
		System.out.println(vo);
		return "test";
	}

	// 파라미터 request.getParameter()과 같은 방식.

	@RequestMapping("/getTest2")
	public String getTest2(@RequestParam String firstName,
			               @RequestParam String salary) {
		System.out.println(firstName + ":" + salary);
		return "test";

	}

	// 파라미터 배열 request.getParameterValues()

	@RequestMapping("/getTest3")
	public String getTest3(@RequestParam("hobby") String[] hobbies) {
		System.out.println(Arrays.asList(hobbies));
		return "test";

	}

	// Json 타입

	@RequestMapping("/getTest4")
	public String getTest4(@RequestBody List<Map> vo) {
		System.out.println(vo);
		return "test";

	}

	// @PathVariable

	@RequestMapping("/getTest5/{firstName}/{salary}")
	public String getTest5(@PathVariable String firstName,
			               @PathVariable String salary, 
			               @ModelAttribute("tvo") TestVO vo, 
			               Model model) {
		vo.setFirstName(firstName);
		vo.setSalary(salary);
		System.out.println(vo);
		model.addAttribute("firstName", firstName);
		model.addAttribute("salary", salary);
		return "test";

	}
	
	// @ModelAndView (잘 안 쓰긴 함 걍 참고용)

	@RequestMapping("/getTest6/{firstName}/{salary}")
	public ModelAndView getTest6(@PathVariable String firstName,
			               @PathVariable String salary, 
			               @ModelAttribute("tvo") TestVO vo ) {
		vo.setFirstName(firstName);
		vo.setSalary(salary);
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("firstName", firstName);
//		mv.setViewName("test");
//		return mv; 이 과정을 한 줄로도 가능.
		
		return new ModelAndView("test", "firstName", firstName);

	}
	
	   // 응답결과가 Json인 경우 (리턴값 vo)

		@RequestMapping("/getTest7/{firstName}/{salary}")
		@ResponseBody
		public TestVO getTest7(@PathVariable String firstName,
				               @PathVariable String salary, 
				                TestVO vo ) {
			vo.setFirstName(firstName);
			vo.setSalary(salary);
			return vo;

		}
		
		 // 응답결과가 Json인 경우 (리턴값 map)

		@RequestMapping("/getTest8")
		@ResponseBody
		public List<Map> getTest8() {
			List list = new ArrayList<>();
			Map<String, String> map = new HashMap<>();
			map.put("name", "park");
			map.put("sal", "1000");
			list.add(map);
			
			map = new HashMap<>();
			map.put("name", "kim");
			map.put("sal", "2000");
			list.add(map);
			
			return list;
			
		
		}
}
