package com.thwet.react.test.service;

import java.util.List;

import com.thwet.react.test.dto.CommentDto;
import com.thwet.react.test.entity.Comment;

public interface CommentService {

	void save(Comment comment);

	List<Comment> findByPostId(int id);
	
	CommentDto convertDto(Comment comment);
	
}
