package com.esd.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.DBObject;

@Repository
public class MongoDBDao {

	@Autowired
	@Qualifier("mongoTemplate")
	protected MongoTemplate mongoTemplate;

	
	public <T> List<T> findAll(Class<T> entityClass) {
		return this.mongoTemplate.findAll(entityClass);
	}
	
	public <T> T findById(Class<T> entityClass, String id) {
		return this.mongoTemplate.findById(id, entityClass);
	}
	
	public <T> T findOne(DBObject obj, Class<T> entityClass) {
		return this.mongoTemplate.findOne(new BasicQuery(obj), entityClass);
	}
	
	public <T> List<T> find(DBObject obj, Class<T> entityClass){
		return this.mongoTemplate.find(new BasicQuery(obj), entityClass);
	}
	
	public <T> Long count(DBObject obj, Class<T> entityClass) {
		return this.mongoTemplate.count(new BasicQuery(obj), entityClass);
	}
	
	public <T> T findAndRemove(DBObject obj, Class<T> entityClass) {
		return this.mongoTemplate.findAndRemove(new BasicQuery(obj), entityClass);
	}
	
	public void insert(Object obj) {
		this.mongoTemplate.insert(obj);
	}
	
	/**
	 * 修改数据
	 * @param query
	 * @param update
	 * @param entityClass
	 */
	public <T> void update(DBObject query, DBObject update, Class<T> entityClass) {
		this.mongoTemplate.updateFirst(new BasicQuery(query), new BasicUpdate(update), entityClass);
	}
	
	public void remove(Object obj) {
		this.mongoTemplate.remove(obj);
	}
	
	public <T> void dropCollection(Class<T> entityClass) {
		this.mongoTemplate.dropCollection(entityClass);
	}
	
	public <T> List<T> findPage(Class<T> entityClass, Query query, int currentPage) {
		query.skip((currentPage - 1) * 20).limit(20);
		return this.mongoTemplate.find(query, entityClass);
	}

}
