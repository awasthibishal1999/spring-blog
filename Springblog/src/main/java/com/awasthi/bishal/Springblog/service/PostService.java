package com.awasthi.bishal.Springblog.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.transform.ToListResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.awasthi.bishal.Springblog.dto.PostDto;
import com.awasthi.bishal.Springblog.exception.PostNotFoundException;
import com.awasthi.bishal.Springblog.model.Post;
import com.awasthi.bishal.Springblog.repository.PostRepo;


@Service
public class PostService {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private PostRepo postRepo;
	
	@Transactional
	public void createPost(PostDto postDto) {
		
		Post post = mapFromDtoPost(postDto);
		postRepo.save(post);		
	}

	@Transactional
	public List<PostDto>showAllPosts() {
		
		List<Post> posts = postRepo.findAll(); 
		
		return posts.stream().map(this::mapFromPostToDto).collect(Collectors.toList());
	}

	

	@Transactional
	public PostDto readSinglePost(Long id) {
		
		Post post = postRepo.findById(id).orElseThrow(() -> new PostNotFoundException("For id "+id));
		
		return mapFromPostToDto(post);
	}
	
	private PostDto mapFromPostToDto(Post post) {
		PostDto postDto  = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setContent(post.getContent());
		postDto.setUsername(post.getUsername());
		return postDto;
	}

	private Post mapFromDtoPost(PostDto postDto) {
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		User loggedInUser = authService.getCurrentUser().orElseThrow(()->new IllegalArgumentException("User not found"));
		post.setCreatedOn(Instant.now());
		post.setUsername(loggedInUser.getUsername());
		post.setUpdatedOn(Instant.now());
		return post;
	}


}
