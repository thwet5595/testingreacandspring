package com.thwet.react.test.repository;

import com.thwet.react.test.entity.User;

public interface UserRepository {

	public void save(User user);

	public User findByEmailAndPassword(String email, String password);

	public User findByEmail(String email);
}
