package com.thwet.react.test.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thwet.react.test.dto.CommentDto;
import com.thwet.react.test.entity.Comment;
import com.thwet.react.test.repository.CommentRepository;
import com.thwet.react.test.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	@Transactional("transactionManager")
	public void save(Comment comment) {
		this.commentRepository.save(comment);
	}

	@Override
	@Transactional("transactionManager")
	public List<Comment> findByPostId(int id) {
		return this.commentRepository.findByPostId(id);
	}

	@Override
	@Transactional("transactionManager")
	public CommentDto convertDto(Comment comment) {
		return this.modelMapper.map(comment, CommentDto.class);
	}
}
