package com.thwet.react.test.service;

import java.util.List;

import com.thwet.react.test.dto.PostDto;
import com.thwet.react.test.entity.Post;

public interface PostService {

	List<Post> findAll();

	PostDto convertDto(Post post);

	void save(Post post);

	Post findById(int id);
	
	List<Post>findPostByUserId(int id);
}
