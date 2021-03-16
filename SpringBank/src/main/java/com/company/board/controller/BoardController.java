package com.company.board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.company.board.service.BoardVO;
import com.company.board.service.impl.BoardMapper;
import com.company.common.FileRenamePolicy;

@Controller
public class BoardController {
	@Autowired BoardMapper boardMapper;
	
	@GetMapping("/insertBoard")
	public String insertBoardForm() {
		return "board/insertBoard";
	}
	
	@PostMapping("/insertBoard")
	public String insertBoard(BoardVO vo) throws IllegalStateException, IOException {
		System.out.println(vo);
		//파일 첨부 처리
		MultipartFile[] files = vo.getUploadFile();
		boolean start = true;
		String filenames = "";
		for(MultipartFile file : files) {
		if(file != null && ! file.isEmpty() && file.getSize() > 0 ) {
			String filename = file.getOriginalFilename();
			//같은 파일명이 있는지 검사. 있으면 새로운 이름으로 저장 (파일명 중복체크 -> rename)
			File rename = FileRenamePolicy.rename(new File("c:/upload", filename));
			if( ! start ) {
				start = false;
				filenames += ",";
			}else {
				start = false;
			}
			filenames +=rename.getName();
			//임시폴더에서 업로드 폴더로 파일 이동
			file.transferTo(rename);
		    }
		}
		vo.setFileName(filenames); //vo 업로드된 파일명 담아서 디비 저장
		
	
	    //등록 서비스 호출
		boardMapper.insertBoard(vo);
		
		return "redirect:/getSearchBoard?seq="+vo.getSeq();
	}
	
	//단건조회
	@GetMapping("/getSearchBoard")
	public String getSearchBoard(BoardVO vo, Model model) {
		model.addAttribute("board", boardMapper.getSearchBoard(vo));
		return "board/getBoard";
	}
	
	//파일 다운
	@RequestMapping("/fileDown")
	public void fileDown(BoardVO vo, HttpServletResponse response) throws IOException {
		vo = boardMapper.getSearchBoard(vo);
		File file = new File("c:/upload", vo.getFileName());
		if (file.exists()) {
		 response.setContentType("application/octet-stream;charset=UTF-8");
		 response.setHeader("Content-Disposition", "attachment; filename=\"" 
		            + URLEncoder.encode(vo.getFileName(), "utf-8") + "\"");
		 
		 BufferedInputStream in = null;
		 BufferedOutputStream out = null;
		 try {
		 in = new BufferedInputStream(new FileInputStream(file));
		 out = new BufferedOutputStream(response.getOutputStream());
		 FileCopyUtils.copy(in, out);
		 out.flush();
		 } catch (IOException ex) {
		 } finally {
		 in.close(); 
		 response.getOutputStream().flush(); 
		 response.getOutputStream().close();
		 }
		}else {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter()
			        .append("<script>")
			        .append("alert('file not found (파일 없음)';")
			        .append("history.go(-1);") //전 페이지로 가는 기능
			        .append("</script>");
		     
		}
	}
	
	//멀티파일 다운
		@RequestMapping("/fileNameDown")
		public void fileNameDown(BoardVO vo, HttpServletResponse response) throws IOException {
			File file = new File("c:/upload", vo.getFileName());
			if (file.exists()) {
			 response.setContentType("application/octet-stream;charset=UTF-8");
			 response.setHeader("Content-Disposition", "attachment; filename=\"" 
			            + URLEncoder.encode(vo.getFileName(), "utf-8") + "\"");
			 
			 BufferedInputStream in = null;
			 BufferedOutputStream out = null;
			 try {
			 in = new BufferedInputStream(new FileInputStream(file));
			 out = new BufferedOutputStream(response.getOutputStream());
			 FileCopyUtils.copy(in, out);
			 out.flush();
			 } catch (IOException ex) {
			 } finally {
			 in.close(); 
			 response.getOutputStream().flush(); 
			 response.getOutputStream().close();
			 }
			}else {
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter()
				        .append("<script>")
				        .append("alert('file not found (파일 없음)';")
				        .append("history.go(-1);") //전 페이지로 가는 기능
				        .append("</script>");
			     
			}
		}
}
