package com.example.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BlogServiceImplTest {

	@Autowired
	private BlogService blogService;
	
	@Test
	public void testCreatePost() {
		Post post = new Post(0, "My new Post", "This is my new test post", new Timestamp(System.currentTimeMillis()));
		Post savedPost = blogService.createPost(post);
		
		Post newPost = blogService.getPost(savedPost.getId());
		assertEquals("My new Post", newPost.getTitle());
		assertEquals("This is my new test post", newPost.getContent());
	}

	@Test
	public void testGetPosts() {
		List<Post> posts = blogService.getPosts();
		assertNotNull(posts);
		assertTrue(!posts.isEmpty());
		for (Post post : posts) {
			System.err.println(post);
		}
	}

	@Test
	public void testGetPost() {
		Integer postId = 1;
		Post post = blogService.getPost(postId);
		assertNotNull(post);
		System.err.println(post);
		List<Comment> comments = post.getComments();
		System.err.println(comments);
	}

	@Test
	public void testCreateComment() {
		Integer postId = 1;
		Post post = blogService.getPost(postId);
		assertNotNull(post);
		int oldSize = post.getComments().size();
		
		Comment comment = new Comment(0, post, "User4", "user4@gmail.com", "This is my new comment on post1",
				new Timestamp(System.currentTimeMillis()));
		Comment savedComment = blogService.createComment(comment);
		
		post = blogService.getPost(postId);
		List<Comment> comments = post.getComments();
		assertNotNull(comments);
		int newSize = post.getComments().size();
		
		assertEquals(oldSize+1, newSize);
		
		for (Comment comm : comments) {
			if (savedComment.getId() == comm.getId()) {
				assertEquals("User4", comm.getName());
				assertEquals("user4@gmail.com", comm.getEmail());
				assertEquals("This is my new comment on post1", comm.getContent());
			}
		}
	}

	@Test
	public void testDeleteComment() {
		Integer postId = 1;
		Post post = blogService.getPost(postId);
		assertNotNull(post);
		
		Comment comment = new Comment(0, post, "User4", "user4@gmail.com", "This is my new comment on post1",
				new Timestamp(System.currentTimeMillis()));
		Comment savedComment = blogService.createComment(comment);
		
		post = blogService.getPost(postId);
		List<Comment> comments = post.getComments();
		for (Comment comm : comments) {
			System.err.println(comm);
		}
		
		blogService.deleteComment(savedComment.getId());
		System.out.println("--------testDeleteComment--------");
		
		post = blogService.getPost(postId);
		comments = post.getComments();
		for (Comment comm : comments) {
			if (savedComment.getId() == comm.getId()) {
				fail("It has not deleted properly. Comment#id = "+savedComment.getId());
			} else {
				System.err.println(comm);
			}
		}
	}

}
