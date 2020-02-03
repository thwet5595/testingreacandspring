package com.thwet.react.test.service;

import com.thwet.react.test.entity.User;

public interface UserService {
	public void save(User user);

	public User findByEmailAndPassword(String email, String password);
	
	public User findByEmail(String email);
}
