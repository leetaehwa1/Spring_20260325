package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.StudentService;
import com.google.gson.Gson;

@Controller
public class StudentController {
	@Autowired
	StudentService studentService;
	
	@RequestMapping("/stu-list.do") 
	public String stu(Model model) throws Exception{
		return "/student/stu-list"; 
	}
	
	@RequestMapping("/stu-add.do") 
	public String add(Model model) throws Exception{
		return "/student/stu-add"; 
	}
	
	@RequestMapping(value = "/stu-list.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String stu(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
//		DefaultService obj = new DefaultService();
		resultMap = studentService.getStuList(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	@RequestMapping(value = "/stu-remove.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String stuRemove(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println(map);
//		DefaultService obj = new DefaultService();
		resultMap = studentService.removeStudent(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	@RequestMapping(value = "/stu-check.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String stuCheck(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println(map);
//		DefaultService obj = new DefaultService();
		resultMap = studentService.checkStudent(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	@RequestMapping(value = "/stu-add.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String add(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println(map);
//		DefaultService obj = new DefaultService();
		resultMap = studentService.addStudent(map);
		
		return new Gson().toJson(resultMap); 
	}
	
}
