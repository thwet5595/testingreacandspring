package com.thwet.react.test.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.thwet.react.test.entity.Comment;
import com.thwet.react.test.entity.Post;
import com.thwet.react.test.repository.PostRepository;

@Repository
public class PostRepositoryImpl implements PostRepository {

	@PersistenceContext(unitName = "test")
	private EntityManager entityManager;

	@Override
	public List<Post> findAll() {
		Query query = entityManager.createQuery("select p from Post p", Post.class);
		return query.getResultList();
	}

	@Override
	public void save(Post post) {
		if (post.getId() == null) {
			this.entityManager.persist(post);
		} else {
			this.entityManager.merge(post);
		}
	}

	@Override
	public Post findById(int id) {
		Query query = this.entityManager
				.createQuery("SELECT p from Post p JOIN FETCH p.user where p.id=? and p.deleted=?", Post.class);
		query.setParameter(1, id);
		query.setParameter(2, "0");
		Post post = null;
		try {
			post = (Post) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
	}

	@Override
	public List<Post> findPostByUserId(int id) {
		Query query = this.entityManager
				.createQuery("SELECT p from Post p JOIN FETCH p.user u where u.id=? and p.deleted=?", Post.class);
		query.setParameter(1, id);
		query.setParameter(2, "0");
		List<Post> post = null;
		try {
			post = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return post;
	}
}