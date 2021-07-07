package com.korea.dbapp.test;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korea.dbapp.domain.post.Post;
import com.korea.dbapp.domain.post.PostRepository;

@RestController
public class PostAPIControllerTest {

	private final PostRepository postRepository;

	public PostAPIControllerTest(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}
	
	@GetMapping("/test/post")
	public List<Post> findAll(){
		// select * from post
		return postRepository.findAll();
	}
	
	@GetMapping("/test/post/{id}")
	public String findById(@PathVariable int id) {
		Post postEntity = postRepository.findById(id).get();
		postEntity.getId();
		return "ok";
	}

}
