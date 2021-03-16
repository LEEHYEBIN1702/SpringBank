package com.company.board.service.impl;

import com.company.board.service.BoardVO;

public interface BoardMapper {
	public BoardVO getSearchBoard(BoardVO vo);
	public int insertBoard(BoardVO vo);	
}
