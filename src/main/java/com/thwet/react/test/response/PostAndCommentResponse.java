package com.thwet.react.test.response;

import java.util.List;

import com.thwet.react.test.dto.CommentDto;

public class PostAndCommentResponse {

	String id;
	
	String title;
	
	String questions;
	
	List<CommentDto> commentDto;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public List<CommentDto> getCommentDto() {
		return commentDto;
	}

	public void setCommentDto(List<CommentDto> commentDto) {
		this.commentDto = commentDto;
	}
}
