package com.thwet.react.test.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.thwet.react.test.entity.Comment;
import com.thwet.react.test.entity.Post;
import com.thwet.react.test.repository.CommentRepository;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

	@PersistenceContext(unitName = "test")
	private EntityManager entityManager;

	@Override
	public void save(Comment comment) {
      if(comment.getId()==null){
    	  this.entityManager.persist(comment);
      }else{
    	  this.entityManager.merge(comment);
      }
	}

	@Override
	public List<Comment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comment> findByPostId(int id) {
		Query query = this.entityManager
				.createQuery("SELECT c from Comment c JOIN FETCH c.post p where p.id=?", Comment.class);
		query.setParameter(1, id);
//		query.setParameter(2, "0");
		List<Comment> comment = null;
		try {
			comment = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comment;
	}
}
