package com.korea.dbapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // View(jsp 파일)을 리턴
public class HomeController {
	
	@GetMapping("/home")
	public String home() {
		return "home"; // View 경로
	}

}
