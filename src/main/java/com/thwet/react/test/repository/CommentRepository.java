package com.thwet.react.test.repository;

import java.util.List;

import com.thwet.react.test.entity.Comment;

public interface CommentRepository {

	void save(Comment comment);

	List<Comment> findAll();
	
	List<Comment> findByPostId(int id);
}
