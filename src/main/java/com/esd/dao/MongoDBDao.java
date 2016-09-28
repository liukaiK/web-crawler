package com.esd.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MongoDBDao {

	@Autowired
	@Qualifier("mongoTemplate")
	protected MongoTemplate mongoTemplate;

	
	public <T> List<T> findAll(Class<T> entityClass) {
		return this.mongoTemplate.findAll(entityClass);
	}
	
	public <T> T findById(String id, Class<T> entityClass) {
		return this.mongoTemplate.findById(id, entityClass);
	}
	
	public <T> T findOne(Query query, Class<T> entityClass) {
		return this.mongoTemplate.findOne(query, entityClass);
	}
	
	public <T> List<T> find(Query query, Class<T> entityClass){
		return this.mongoTemplate.find(query, entityClass);
	}
	
	public <T> Long count(Query query, Class<T> entityClass) {
		return this.mongoTemplate.count(query, entityClass);
	}
	
	public <T> T findAndRemove(Query query, Class<T> entityClass) {
		return this.mongoTemplate.findAndRemove(query, entityClass);
	}
	
	public void insert(Object obj) {
		this.mongoTemplate.insert(obj);
	}
	
	public void remove(Object obj) {
		this.mongoTemplate.remove(obj);
	}
	
	public <T> void dropCollection(Class<T> entityClass) {
		this.mongoTemplate.dropCollection(entityClass);
	}
	
	public <T> List<T> findPage(Query query, Class<T> entityClass, int currentPage) {
		query.skip((currentPage - 1) * 20).limit(20);
		return this.mongoTemplate.find(query, entityClass);
	}

}
