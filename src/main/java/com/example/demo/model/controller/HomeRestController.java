package com.example.demo.model.controller;

import java.util.Arrays;
import java.util.List;

import com.example.demo.model.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeRestController {

	@GetMapping("/")
	public List<Post> home() {
		Post post = new Post();
		post.setId( 1 );
		post.setTitle( "title" );
		return Arrays.asList( post );
	}
}
