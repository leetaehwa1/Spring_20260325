package com.example.demo.controller;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@Value("${client_id}")
	private String client_id;

    @Value("${redirect_uri}")
    private String redirect_uri;
	
	// JSP 파일 리턴해주는 주소 (화면 뿌리기)
	//-> /default => jsp 파일.
	@RequestMapping("/login.do") 
	public String login(Model model) throws Exception{
		String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
        model.addAttribute("location", location);
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
	
	
	@RequestMapping("/main.do") 
	public String main(Model model) throws Exception{
		return "/main"; 
	}
	
	@RequestMapping(value = "/kakao.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String kakao(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		
			HashMap<String, Object> resultMap = new HashMap<String, Object>(map);
	        String tokenUrl = "https://kauth.kakao.com/oauth/token";

	        RestTemplate restTemplate = new RestTemplate();
	        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	        params.add("grant_type", "authorization_code");
	        params.add("client_id", client_id);
	        params.add("redirect_uri", redirect_uri);
	        params.add("code",(String)map.get("code"));

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
	        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

	        Map<String, Object> responseBody = response.getBody();
	        System.out.println(responseBody);
	        resultMap = (HashMap<String, Object>) getUserInfo((String) responseBody.get("access_token"));
	        
		return new Gson().toJson(resultMap); 
	}
	
	private Map<String, Object> getUserInfo(String accessToken) {
	    String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setBearerAuth(accessToken);
	    HttpEntity<String> entity = new HttpEntity<>(headers);

	    ResponseEntity<String> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, String.class);

	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.readValue(response.getBody(), Map.class);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null; // 예외 발생 시 null 반환
	    }
	}
	
	@RequestMapping(value = "/user/remove.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody 
	public String remove(Model model, @RequestParam HashMap<String, Object> map) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>(map);
		resultMap = userService.removeUser(map);
		
		return new Gson().toJson(resultMap); 
	}
	
	@RequestMapping(value = "/user/fileUpload.dox", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public String result(@RequestParam(value="file1", required=false) MultipartFile multi, @RequestParam("userId") String userId, HttpServletRequest request,HttpServletResponse response, Model model)
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
				map.put("userId", userId);
				
				
				// insert 쿼리 실행
			    userService.addUser(map);
				
				model.addAttribute("filename", multi.getOriginalFilename());
				model.addAttribute("uploadPath", file.getAbsolutePath());
				
				return "redirect:/user/list.do";
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		return "redirect:/user/list.do";
	}
	    
//	 현재 시간을 기준으로 파일 이름 생성
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

