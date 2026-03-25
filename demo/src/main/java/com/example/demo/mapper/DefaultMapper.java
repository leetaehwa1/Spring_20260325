package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.User;

@Mapper
public interface DefaultMapper {
//	여러 클래스를 대상으로 할 때 리스트의 객체로로 넣어야 함.
	public List<User> selectUserList();
		
	
}
