package com.thwet.react.test.repository;

import java.util.List;

import com.thwet.react.test.entity.Post;

public interface PostRepository {
 
	List<Post>findAll();
	
	void save(Post post);
	
	Post findById(int id);
	
	List<Post>findPostByUserId(int id);
}
