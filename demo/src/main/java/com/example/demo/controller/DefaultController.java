package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.DefaultService;
import com.google.gson.Gson;

@Controller
public class DefaultController {
	@Autowired
	DefaultService defaultService;
	
	// JSP 파일 리턴해주는 주소 (화면 뿌리기)
	//-> /default => jsp 파일.
	@RequestMapping("/default.do") 
	public String test(Model model) throws Exception{
		return "/default"; 
	}
	
	@RequestMapping("/test.do") 
	public String test2(Model model) throws Exception{
		return "/test"; 
	}
	
	
	
	// 데이터 리턴해주는 주소 ajax 사용할 때.
	@RequestMapping(value = "/default.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String login(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		return new Gson().toJson(resultMap); 
	}
	
	// 데이터 리턴해주는 주소
	@RequestMapping(value = "/test.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String test(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
//		DefaultService obj = new DefaultService();
		resultMap = defaultService.getUserList();
		
		return new Gson().toJson(resultMap); 
	}
	
	
	
}
