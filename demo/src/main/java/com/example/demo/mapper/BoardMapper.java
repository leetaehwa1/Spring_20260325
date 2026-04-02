package com.example.demo.mapper;


import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Board;
import com.example.demo.model.User;


@Mapper
public interface BoardMapper {
//	여러 클래스를 대상으로 할 때 리스트의 객체로로 넣어야 함.
	public List<Board> selectBoardList(HashMap<String, Object> map);
	
	// 첨부파일 목록
	public List<Board> selectBoardFile(HashMap<String, Object> map);
	
	public int insertBoard(HashMap<String, Object> map);
	
	public Board selectBoard(HashMap<String,Object> map);
	
	public int updateCnt(HashMap<String,Object> map);
	 
	public int updateBoard(HashMap<String, Object> map);
	
	//파일 삽입
	public int insertBoardFile(HashMap<String, Object> map);
	
	
}
