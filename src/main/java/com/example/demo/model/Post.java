package com.example.demo.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "comments" })
public class Post {
	private Integer id;
	private String title;
	private String content;
	private Timestamp createdOn;
	private List<Comment> comments = new ArrayList<>();

	public Post(Integer id) {
		this.id = id;
	}

	public Post(Integer id, String title, String content, Timestamp createdOn) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createdOn = createdOn;
	}
	
	public void addComment(Comment comment) {
		if (comment.getPost() == null) {
			comment.setPost(this);
		}
		this.comments.add(comment);
	}

}
