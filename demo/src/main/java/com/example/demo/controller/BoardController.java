package com.example.demo.controller;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.BoardService;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BoardController {
	@Autowired
	BoardService boardService;
	
	// 리스트
	@RequestMapping("/board/board-list.do") 
	public String list(Model model) throws Exception{
		return "/board/board-list"; 
	}
	
	// 추가
	@RequestMapping("/board/board-add.do") 
	public String add(Model model) throws Exception{
		return "/board/board-add"; 
	}
	
	@RequestMapping("/board/board-view.do") 
	public String view(HttpServletRequest request, @RequestParam HashMap<String, Object> map) throws Exception{
		System.out.println(map);
		request.setAttribute("boardNo", map.get("boardNo"));
		return "/board/board-view"; 
	}
	
	@RequestMapping("/board/board-edit.do") 
	public String edit(HttpServletRequest request, @RequestParam HashMap<String, Object> map) throws Exception{
		System.out.println(map);
		request.setAttribute("boardNo", map.get("boardNo"));
		return "/board/board-edit"; 
	}
	
	// 데이터 리턴해주는 주소
	@RequestMapping(value = "/board/board-list.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String test(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(map);
		
//		DefaultService obj = new DefaultService();
		resultMap = boardService.getBoardList(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	@RequestMapping(value = "/board/board-add.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String add(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(map);
		
//		DefaultService obj = new DefaultService();
		resultMap = boardService.addBoard(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	@RequestMapping(value = "/board/board-info.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String get(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(map);
		
//		DefaultService obj = new DefaultService();
		resultMap = boardService.getBoard(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	@RequestMapping(value = "/board/board-edit.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String edit(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(map);
		
//		DefaultService obj = new DefaultService();
		resultMap = boardService.editBoard(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	
	@RequestMapping(value = "/fileUpload.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String result(@RequestParam(value="file1", required=false) MultipartFile multi, @RequestParam("idx") int idx, HttpServletRequest request,HttpServletResponse response, Model model)
	{
		String url = null;
		String path="c:\\img";
		try {

			//String uploadpath = request.getServletContext().getRealPath(path);
			String uploadpath = path;
			String originFilename = multi.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf("."),originFilename.length());
			long size = multi.getSize();
			String saveFileName = genSaveFileName(extName);
			
			System.out.println("uploadpath : " + uploadpath);
			System.out.println("originFilename : " + originFilename);
			System.out.println("extensionName : " + extName);
			System.out.println("size : " + size);
			System.out.println("saveFileName : " + saveFileName);
			String path2 = System.getProperty("user.dir");
			System.out.println("Working Directory = " + path2 + "\\src\\webapp\\img");
			if(!multi.isEmpty())
			{
				File file = new File(path2 + "\\src\\main\\webapp\\img", saveFileName);
				multi.transferTo(file);
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("fileName", saveFileName);
				map.put("path", "../../img/" + saveFileName);
				map.put("orgName", originFilename);
				map.put("size", size);
				map.put("ext", extName);
				map.put("idx", idx);
				
				
				// insert 쿼리 실행
			    boardService.addBoardFile(map);
				
				model.addAttribute("filename", multi.getOriginalFilename());
				model.addAttribute("uploadPath", file.getAbsolutePath());
				
				return "redirect:/board/board-list.do";
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return "redirect:/board/board-list.do";
	}
	    
	// 현재 시간을 기준으로 파일 이름 생성
	private String genSaveFileName(String extName) {
		String fileName = "";
		
		Calendar calendar = Calendar.getInstance();
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += extName;
		
		return fileName;
	}
}
