package com.thwet.react.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thwet.react.test.entity.User;
import com.thwet.react.test.repository.UserRepository;
import com.thwet.react.test.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional("transactionManager")
	public void save(User user) {
		this.userRepository.save(user);
	}

	@Override
	@Transactional("transactionManager")
	public User findByEmailAndPassword(String email, String password) {
		return this.userRepository.findByEmailAndPassword(email, password);
	}

	@Override
	@Transactional("transactionManager")
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
}
