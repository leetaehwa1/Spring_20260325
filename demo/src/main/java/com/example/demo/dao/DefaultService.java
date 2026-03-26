package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.DefaultMapper;
import com.example.demo.model.User;

//서비스에서 매퍼를 호할 때 select, 컨트롤러에서 서비스를 호출할 때 get

@Service
public class DefaultService {
	@Autowired
	DefaultMapper defaultMapper ; // 클래스 객체 형식.
	public HashMap<String,Object> getUserList(){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		List<User> list = defaultMapper.selectUserList();
		
		resultMap.put("list", list);
		resultMap.put("message", "데이터 조회 성공");
		resultMap.put("result", "success");
		
		return resultMap;
	}
	
	
}
