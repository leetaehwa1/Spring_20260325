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
import com.example.demo.dao.UserService;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	// JSP 파일 리턴해주는 주소 (화면 뿌리기)
	//-> /default => jsp 파일.
	@RequestMapping("/login.do") 
	public String login(Model model) throws Exception{
		return "/user/login"; 
	}
	@RequestMapping("/join.do") 
	public String join(Model model) throws Exception{
		return "/user/sign-up"; 
	}
	
	@RequestMapping("/addr.do") 
	public String addr(Model model) throws Exception{
		return "/user/jusoPopup"; 
	}
	
	
	@RequestMapping(value = "/login.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String login(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(map);
		
//		DefaultService obj = new DefaultService();
		System.out.println(map);
		resultMap = userService.login(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	@RequestMapping(value = "/join.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String join(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(map);
		
//		DefaultService obj = new DefaultService();
		System.out.println(map);
		resultMap = userService.addUser(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	@RequestMapping(value = "/check.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String check(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(map);
		
//		DefaultService obj = new DefaultService();
		System.out.println(map);
		resultMap = userService.checkUser(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	
//	=== 복습 (User 테이블) ===
	@RequestMapping("/user/list.do") 
	public String user(HttpServletRequest request,Model model, @RequestParam HashMap<String, Object> map) throws Exception{
		return "/user/user-list"; 
	}
	
	@RequestMapping(value = "/user/list.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String list(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(map);
		resultMap = userService.getUserList(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	@RequestMapping(value = "/user/remove.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String remove(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(map);
		resultMap = userService.removeUser(map);
		
		return new Gson().toJson(resultMap); 
	}
}
