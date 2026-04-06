package com.example.demo.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.Message;
import com.example.demo.mapper.EmpMapper;
import com.example.demo.model.Emp;

//서비스에서 매퍼를 호할 때 select, 컨트롤러에서 서비스를 호출할 때 get

@Service
public class EmpService {
	@Autowired
	EmpMapper empMapper ; // 클래스 객체 형식.
	
	// 조회 -> get, 수정 -> edit, 삽입 -> add, 삭제 -> remove
	// ex) 학생목록 : getStudentList, 학생수정 -> editStudent
	
//	=== Mapper 호출 시 ===
	// 여러 개 리턴 -> selectXXXList
//	List<User> list = defaultMapper.selectUserList();
//		한 개 리턴 -> selectXXX
//	User info = defaultMapper.selectUser();
//	수정, 삭제, 삽입 -> updateXXX, deleteXXX, insertXXX
//	int result = defaultMapper.updateXXX();
	
	public HashMap<String,Object> getEmpList(HashMap<String,Object> map){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
	

		try {
			List<Emp> list = empMapper.selectEmpList(map);
			int totalCount = empMapper.selectEmpCount(map);
			
			resultMap.put("list", list);
			resultMap.put("totalCount", totalCount);
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
	
	public HashMap<String,Object> getEmp(HashMap<String,Object> map){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
	
		try {
			Emp info = empMapper.selectEmp(map);
			
			resultMap.put("info", info);
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
	
	public HashMap<String,Object> addEmp(HashMap<String,Object> map){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
	
		try {
			int result = empMapper.insertEmp(map);
			resultMap.put("result", "success");
			resultMap.put("message", Message.MSG_ADD);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			resultMap.put("result", "fail");
			resultMap.put("message", Message.MSG_SERVER_ERR);
		}
		return resultMap;
	}
	public HashMap<String,Object> removeEmp(HashMap<String,Object> map){
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
	
		try {
//
			int result = empMapper.deleteEmp(map);
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
