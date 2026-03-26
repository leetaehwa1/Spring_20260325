package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.StudentMapper;
import com.example.demo.model.Stu;

@Service
public class StudentService {
	@Autowired
	StudentMapper studentMapper ; // 클래스 객체 형식.
	public HashMap<String,Object> getStuList(){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		List<Stu> list = studentMapper.selectStuList();
		
		resultMap.put("list", list); 
		resultMap.put("message", "데이터 조회 성공");
		resultMap.put("result", "success");
		
		return resultMap;
	}
	
	public HashMap<String,Object> removeStudent(HashMap<String,Object> map){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		int cnt = studentMapper.deleteStudent(map);
		if(cnt > 0) {
			resultMap.put("message", "데이터 삭제 성공");
		}else {
			resultMap.put("message", "데이터 삭제 실패");
		}
		resultMap.put("result", "success"); // 이건 DB 와의 통신 성공
		return resultMap;
	}
	
	
}
