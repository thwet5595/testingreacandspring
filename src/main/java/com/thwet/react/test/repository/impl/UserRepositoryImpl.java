package com.thwet.react.test.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.thwet.react.test.entity.User;
import com.thwet.react.test.repository.UserRepository;
@Repository
public class UserRepositoryImpl implements UserRepository {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext(unitName = "test")
	private EntityManager entityManager;

	@Override
	public void save(User user) {
		if (user.getId() == null) {
			this.entityManager.persist(user);
		} else {
			this.entityManager.merge(user);
		}
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		Query query = entityManager.createQuery("select u from User u where u.email = ? AND u.password = ?",
				User.class);
		query.setParameter(1, email);
		query.setParameter(2, password);
		return (User) query.getSingleResult();
	}

	@Override
	public User findByEmail(String email) {
		User user = null;
		logger.info("User email is::"+email);
		try{
			Query query = entityManager.createQuery("select u from User u where u.email = ?", User.class);
			query.setParameter(1, email);
			user = (User) query.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}
}
