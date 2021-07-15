package com.korea.dbapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	
	@PostMapping("/auth/join") 
	public String join(User user) { 
		userRepository.save(user); 
		return "redirect:/auth/loginForm";
	}
	// redirect를 쓴다는 건 controller에 다시 때린다는 것  -> 데이터 때문에 
	
	@GetMapping("/auth/loginForm")
	public String loginForm(User user) {
		return "auth/loginForm";
	}
	
	// 얘만 RestController (data를 리턴)
	@PostMapping("/auth/login")
	public @ResponseBody String login(User user) {
		User userEntity =  userRepository.mLogin(user.getUsername(), user.getPassword());
		if(userEntity == null) {
			return Script.back("로그인 실패");

		}else {
			
			session.setAttribute("principal", userEntity); // principal - 인증 주최 session
			return Script.href("/");
		}
		
	}
	// f
	@GetMapping("/user/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/user/updateForm") // DB에서 select 한 게 아니라 session을 들고 온 거니 WHERE절이 안 걸린다.
	public String updateForm() {
		// 1. 인증과 권한을 검사해야 함
		// 2. 세션값을 사용하면 됨
		return "user/updateForm";
	}
	
	@PostMapping("/user/{id}") // 원래는 put으로 해야 한다. 나중에 자바스크립트 put으로 요청하기!!
	public String update(@PathVariable int id, String password, String address) { // updateForm에 name=username, name=email은 지우기!
			
		// 공통 관심사 (AOP(Aspect Oriented Programming, 관점지향프로그래밍) - 반복적인 공통관심사 코드를 분리시킨다)
		User principal = (User) session.getAttribute("principal"); // getAttribute는 리턴 타입이 오브젝트이다.
		
		if(principal != null && id == principal.getId()) {
			User userEntity = userRepository.findById(id).get(); // 핵심 로직
			userEntity.setPassword(password);
			userEntity.setAddress(address);
			userRepository.save(userEntity);
			session.setAttribute("principal", userEntity);
			return "redirect:/"; // 홈으로 가기	
		}
		return "redirect:/auth/loginForm";
	}	
	
	@GetMapping("/juso")
	public String jusoRequest() {
		return "/juso/jusoPopup";
	}
	
	// Model - Request.setAttribute
	// request.getAttribute 
	// jsp 에서는 url로 가능한데 스프링은 uri로 받아야 해서 -> Controller -> 컨트롤러의 roadFullAddr -> 주소popUp.jsp -> request.setAttribute (=) Model 
	// Body에서 찾는 게 아니라 톰캣이 들고있는 request 객체에서 찾아야 함 -> EL표현식
	@PostMapping("/juso")
	public String jusoResponse(String roadFullAddr, String inputYn, Model model) {
		System.out.println("주소: "+roadFullAddr);
		model.addAttribute("roadFullAddr", roadFullAddr);
		model.addAttribute("inputYn",inputYn);
		return "/juso/jusoPopup"; // Controller 타고 파일에 가서 응답 두 번 함
		
	}
}
