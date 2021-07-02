package com.korea.dbapp.domain.post;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.korea.dbapp.domain.user.User;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // 프라이머리 키(기본키)
	private String title; // varchar(255) -> 255는 글자 수이다. 
	@Lob
	private String content;
	
	
	// post가 many . user가 one
	// user 오브젝트의 primary key가 foreign key -> 모순 해결
	// @ManyToOne 연관 관계 설정
	@ManyToOne 
	@JoinColumn(name = "user_id")    // 이름 변경
	private User user; // ORM 사용  . 컬럼을 못 만듦 
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
}
