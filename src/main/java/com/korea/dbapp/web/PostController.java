package com.korea.dbapp.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.korea.dbapp.domain.post.Post;
import com.korea.dbapp.domain.post.PostRepository;
import com.korea.dbapp.domain.user.User;
import com.korea.dbapp.domain.user.UserRepository;
import com.korea.dbapp.util.Script;

@Controller
public class PostController {

	private final PostRepository postRepository;
	private final HttpSession session;

	// DI
	public PostController(PostRepository postRepository, HttpSession session) {
		this.postRepository = postRepository;
		this.session = session;
	}

	@GetMapping({ "/", "/post" }) // default = path
	public String list(Model model) { // model = request
		// request.setAttribute()
		model.addAttribute("postsEntity", postRepository.findAll()); // 핵심 로직
		return "post/list"; // ViewResolver 도움!! + RequestDispatcher (request 유지 기법)
	}

	@GetMapping("/post/{id}")
	public String detail(@PathVariable int id, Model model) {
		Post postEntity = postRepository.findById(id).get();

		model.addAttribute("postEntity", postEntity);
		return "post/detail";
	}

	// post delete 할 때 인증과 권한 체크해야 함
	// MessageConverter - @ResponseBody 이걸 선언해서 - RestController
	@DeleteMapping("/post/{id}")
	public @ResponseBody String deleteById(@PathVariable int id) {

		// post로 삭제하기 (세션이 존재하고 / 포스트 아이디만으로 유저 아이디를 못 찾는다. 셀렉트 해서 찾아야 함. 거기 유저 값이랑 세션
		// 값이랑 비교)
		// 1. 권한 체크(post id를 통해 user id를 찾아서 session의 user id를 비교)

		// 세션의 user id 찾기
		int userId = ((User) session.getAttribute("principal")).getId(); // 이 부분 공부
		// 왜 변수를 지역변수로 만들었을까?

		// post의 user id({id}) 찾기
		Post postEntity = postRepository.findById(id).get(); // 이런 건 어떻게 처리하지?
		int postUserId = postEntity.getUser().getId(); // 글 쓴 사람

		// 2. {id}값으로 삭제
		if (userId == postUserId) {
			postRepository.deleteById(id);
			return "ok";
		} else {
			return "fail";
		} // end if

		// return "post/list"; <- 이 경우는 데이터가 없는데 리스트로 가면 postsEntity 못 찾아서 안 된다
	}// end deleteById

	@GetMapping("/post/saveForm")
	public String saveForm() {
		// 1. 인증 체크 - 숙제
		// title, content(자동으로 증가는 값) / 세션에서 받는 principal 통째로 받아서 user 데이터 받음

		return "post/saveForm"; // 파일을 호출
	}

	@PostMapping("/post")
	public String save(Post post) {
		User principal = (User) session.getAttribute("principal"); // principal엔 password 빼고 다 있음
		if (principal == null) {
			return "redirect:/auth/loginForm"; // 주소를 호출
		}

		post.setUser(principal);
		postRepository.save(post);

		return "redirect:/";

	}

	// 수정하기 - WHERE절 필요 - @PathVariable - Restful 규칙?
	@GetMapping("/post/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		// 수정하는 것 -> 인증과 권한 필요! 프론트맨에서 했지만 공격 받을 수 있어서 ex)postman
		User principal = (User) session.getAttribute("principal");
		int userId = principal.getId();

		Post postEntity = postRepository.findById(id).get();
		int postOwnId = postEntity.getUser().getId();

		if (userId == postOwnId) {
			model.addAttribute("postEntity", postEntity); // post(ManyToOne) 기본 방식 EAGER
			// 셀렉 두 번 하는 것보다 조인 하는 게 낫다
			return "post/updateForm";
		} else {
			return "redirect:/auth/loginForm";
		}
	}

	@PutMapping("/post/{id}")
	public @ResponseBody String update(@PathVariable int id, @RequestBody Post post) {
		User principal = (User) session.getAttribute("principal");
		int userId = principal.getId();

		Post postEntity = postRepository.findById(id).get();

		int postOwnId = postEntity.getUser().getId();

		if (userId == postOwnId) {
			postEntity.setTitle(post.getTitle());
			postEntity.setContent(post.getContent());
			postRepository.save(postEntity);
			return "ok";
		} else {
			return "redirect:/auth/loginForm";
		}

	}
}
