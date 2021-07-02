package com.korea.dbapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.korea.dbapp.domain.user.User;
import com.korea.dbapp.domain.user.UserRepository;
import com.korea.dbapp.util.Script;

@Controller
public class UserController {
	private final UserRepository userRepository; // final을 붙이는 이유는 실수를 안 하기 위해
	private final HttpSession session;
	
	// 의존성 주입(DI)
	public UserController(UserRepository userRepository, HttpSession session) {
		super();
		this.userRepository = userRepository;
		this.session = session;
	}

	// 회원가입 페이지로 가는 것 / 회원가입을 수행하는 것 두 개가 필요	
	
	// 회원가입 페이지로 가는 것
	// 데이터 리턴이 아닌 파일을 응답하는 것 -> Restful API 규칙 안 지켜도 됨
	// 클라이언트는 버튼을 클릭해서 페이지 이동
	@GetMapping("/auth/joinForm")
	public String joinForm(User user) {
		return "auth/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm(User user) {
		return "auth/loginForm";
	}
	
	@PostMapping("/auth/join") 
	public String join(User user) { 
		userRepository.save(user); 
		return "redirect:/auth/loginForm";
	}
	// redirect를 쓴다는 건 controller에 다시 때린다는 것  -> 데이터 때문에 
	
	// 얘만 RestController (data를 리턴)
	@PostMapping("/auth/login")
	public @ResponseBody String login(User user) {
		User userEntity =  userRepository.mLogin(user.getUsername(), user.getPassword());
		if(userEntity == null) {
			return Script.back("로그인 실패");

		}else {
			session.setAttribute("principal", userEntity); // principal - 인증 주최
			return Script.href("/");
		}
		
	}
	// f
	@GetMapping("/user/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
}
