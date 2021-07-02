package com.korea.dbapp.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.korea.dbapp.domain.post.Post;

import java.util.List;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // primary key (기본키) 번호 사용
	
	@Column(unique = true, length = 20)  // 테이블을 유니크하게 만들어줌 / username 20자로 제한
	private String username; 
	private String password;
	private String email;
	private String address;
	
	// user가 1 post가 many
	// user에서 FK 안 만들어 줌
	// 양방향 매핑
	@JsonIgnoreProperties({"user"}) // json으로 계속 파싱하면서 때린다. user만 json으로 파싱하지 마라
	@OneToMany(mappedBy = "user") // FK 만들지 마라고 하는 것.
	private List<Post> posts; // 이 안에 post를 넣을 때 user를 건드리지 말라. 
	
	public List<Post> getPosts() {
		return posts;
	}
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	public int getId() {
		return id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
