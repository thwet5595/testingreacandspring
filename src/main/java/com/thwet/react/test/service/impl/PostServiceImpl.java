package com.thwet.react.test.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thwet.react.test.dto.PostDto;
import com.thwet.react.test.entity.Post;
import com.thwet.react.test.repository.PostRepository;
import com.thwet.react.test.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	@Transactional("transactionManager")
	public List<Post> findAll() {
		return this.postRepository.findAll();
	}

	@Override
	public PostDto convertDto(Post post) {
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	@Transactional("transactionManager")
	public void save(Post post) {
		this.postRepository.save(post);
	}

	@Override
	@Transactional("transactionManager")
	public Post findById(int id) {
		return this.postRepository.findById(id);
	}

	@Override
	@Transactional("transactionManager")
	public List<Post> findPostByUserId(int id) {
		return this.postRepository.findPostByUserId(id);
	}
}
