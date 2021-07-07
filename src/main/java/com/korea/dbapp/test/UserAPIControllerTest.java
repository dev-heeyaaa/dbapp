package com.korea.dbapp.test;

import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.korea.dbapp.domain.user.User;
import com.korea.dbapp.domain.user.UserRepository;

@RestController
public class UserAPIControllerTest {
	
	private final UserRepository userRepository; // final을 붙이는 이유는 실수를 안 하기 위해

	// 의존성 주입(DI)
	public UserAPIControllerTest(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	// 프로토콜은 지켜도 되고 안 지켜도 되는 것. cf) 인터페이스는 안 지키면 안 됨
	@PostMapping("/test/user") // "/user"는 명사 Post는 동사 (insert)
	public String save(User user) { // x-www-form-urlencoded...
		userRepository.save(user); // insert
		return "save ok";
	}
	
	@GetMapping("/test/user")
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	// http://localhost:8084/user/2
	@GetMapping("/test/user/{id}") // id는 디폴트라서
	public String findById(@PathVariable int id) {
		User userEntity = userRepository.findById(id).get();
		userEntity.getPosts().get(0).getTitle();
		//System.out.println(userEntity);
		return "ok";
		
		// 실시간에 터지는 null 오류
		// box로 wrapping 되어있는
	}
	
	// 없는 'username 찾기' 함수 만들기
	@GetMapping("/test/user/username/{username}")
	public User findByUsername(@PathVariable String username) {
		return userRepository.mFindByUsername(username);
	}
	
	// insert
	@PostMapping("/test/login")
	public String login(@RequestBody User user) {
		
		// Entity DB로 부터 리턴 받은 값
		User userEntity = userRepository.mLogin(user.getUsername(), user.getPassword());

		if(userEntity!= null) {
			return "login success";
		} else {
			return "login fail...!";
		}
		
	}
	
	@DeleteMapping("/test/user/{id}")
	public String deleteById(@PathVariable int id) {
		userRepository.deleteById(id);
		
		return "delete ok";
	}
	
	// update
	@PutMapping("/test/user/{id}")
	public String updateOne(@PathVariable int id, User user) { // 이렇게 적으면 form으로 전송 // user에 password, email
													// (... @RequestBody User user) 이렇게 적으면 json으로 전송
		//user.setId(id); // db에 id, username, email, password 가 있는데 수정할 게 한 개만 받아야 할 때 id만 save하면 username 날라감
		// 그렇다면 어떤 방식으로?
		
		// 1.id로 select 한다. 원데이터 들고오기.
		User userEntity = userRepository.findById(id).get(); // Entity : DB에서 들고온 데이터라고 표식
		// userEntity를 수정한다.
		// 2. 받은 body 데이터로 수정한기
		userEntity.setPassword(user.getPassword());
		userEntity.setEmail(user.getEmail());
		// 이렇게 안 하면 기존에 들고있던 데이터가 날라갈 수 있다.
		
		// 3. save 하면 된다. (이때 꼭 id 값이 db에 존재해야 update가 된다)
		userRepository.save(userEntity); // save - id값이 들어오면 update / id 값이 안 들어오면 insert
		
		return "update ok";
		// 한 번에 여러 데이터를 수정하는 것은 다 함수가 있다.
	}
}
