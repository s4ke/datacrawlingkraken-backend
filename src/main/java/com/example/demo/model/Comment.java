package com.example.demo.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	private Integer id;
	private Post post;
	private String name;
	private String email;
	private String content;
	private Timestamp createdOn;

//	public Comment(Integer id, Integer postId, String name, String email, String content, Timestamp createdOn) {
//		this.id = id;
//		this.post = new Post(postId);
//		this.name = name;
//		this.email = email;
//		this.content = content;
//		this.createdOn = createdOn;
//	}

}
