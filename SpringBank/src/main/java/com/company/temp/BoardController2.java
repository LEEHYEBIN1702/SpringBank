package com.company.temp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.bank.service.BoardVO2;

@Controller
public class BoardController2 {
	@RequestMapping("/insertBoard2")
	public String BoardTest(BoardVO2 vo, Model model) {
		System.out.println(vo);
		return "insertBoard";
	}

	
		@RequestMapping("/ajaxInsertBoard2")
		@ResponseBody
		public BoardVO2 BoardTest1(BoardVO2 vo) {
			System.out.println(vo);
			return vo;
		}

}
