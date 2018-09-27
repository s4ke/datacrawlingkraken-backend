package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;

public interface BlogService {
	public Post createPost(Post post);
	public List<Post> getPosts();
	public Post getPost(Integer postId);
	
	public Comment createComment(Comment comment);
	public int deleteComment(Integer commentId);
}
