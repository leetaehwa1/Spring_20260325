package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.Message;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;

//서비스에서 매퍼를 호할 때 select, 컨트롤러에서 서비스를 호출할 때 get

@Service
public class UserService {
	@Autowired
	UserMapper userMapper ; // 클래스 객체 형식.
	public HashMap<String,Object> login(HashMap<String, Object> map){
		HashMap<String,Object> resultMap = new HashMap<String,Object>(map);
		User user = userMapper.selectUser(map);
		
		if(user != null) {
			if(user.getPwd().equals(map.get("pwd"))) {
				resultMap.put("message", user.getUserName() + " 님 환영합니다.");	
			}else {
				resultMap.put("message", "비밀번호를 확인해주세요.");
			}
		} else {
			resultMap.put("message", "없는 아이디 입니다.");
		}
		resultMap.put("result", "success");
		
		return resultMap;
	}
	
	public HashMap<String,Object> addUser(HashMap<String, Object> map){
		HashMap<String,Object> resultMap = new HashMap<String,Object>(map);
	try {
		int cnt = userMapper.insertUser(map);
		if(cnt > 0) {
		resultMap.put("message", "회원가입 완료.");	
		}else {
			resultMap.put("message", "회원가입 실패. 다시 시도해주세요.");
		}
		
		resultMap.put("result", "success");
		
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e.getMessage());
		resultMap.put("message", "서버 에러 발생 ! \n잠시 후 다시 시도해주세요");
		resultMap.put("result", "fail");
	}
		
		
		return resultMap;
	}
	
	public HashMap<String,Object> checkUser(HashMap<String, Object> map){
		HashMap<String,Object> resultMap = new HashMap<String,Object>(map);
		User user = userMapper.selectUser(map);
		
		try {
			if(user != null) {
				resultMap.put("message", "이미 사용중인 아이디 입니다.");
				resultMap.put("result", false);
		}else {
				resultMap.put("message", "사용 가능한 아이디 입니다.");
				resultMap.put("result", true);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			resultMap.put("message", "서버 에러.");
		}
		
		
		return resultMap;
	}
	
	// == 복습 == 
	public HashMap<String,Object> getUserList(HashMap<String,Object> map){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
	

		try {
			List<User> list = userMapper.selectUserList(map);
			resultMap.put("list", list);
			resultMap.put("result", "success");
			resultMap.put("message", Message.MSG_SEARCH);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			resultMap.put("result", "fail");
			resultMap.put("message", Message.MSG_SERVER_ERR);
		}
		return resultMap;
	}
	
	public HashMap<String,Object> removeUser(HashMap<String,Object> map){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
	

		try {
			int result = userMapper.deleteUser(map);
			resultMap.put("result", "success");
			resultMap.put("message", Message.MSG_REMOVE);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			resultMap.put("result", "fail");
			resultMap.put("message", Message.MSG_SERVER_ERR);
		}
		return resultMap;
	
}
}
