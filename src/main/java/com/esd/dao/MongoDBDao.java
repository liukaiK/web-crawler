package com.esd.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;

@Repository
public class  MongoDBDao {

	@Autowired
	@Qualifier("mongoTemplate")
	protected MongoTemplate mongoTemplate;
	

	/**
	 * cx-20160919
	 * @param objectToSave
	 * @param collectionName
	 */
	public void insert(Object objectToSave, String collectionName) {
		this.mongoTemplate.insert(objectToSave, collectionName);
	}
	
	
	/**
	 * liukai-2016.10.11
	 * @param query
	 * @param entityClass
	 * @param collectionName
	 * @return
	 */
	public <T> T findOne(Query query, Class<T> entityClass, String collectionName) {
		return this.mongoTemplate.findOne(query, entityClass, collectionName);
	}
	
	/**
	 * liukai-2016.10.11
	 * 符合条件则添加,不符合条件则修改
	 * @param query
	 * @param update
	 * @param collectionName
	 * @return
	 */
	public WriteResult upsert(Query query, Update update, String collectionName) {
		return mongoTemplate.upsert(query, update, collectionName);
		
	}
	
	/**
	 * liukai-2016.10.11
	 * @param query
	 * @param collectionName
	 * @return
	 */
	public WriteResult remove(Query query, String collectionName) {
		return mongoTemplate.remove(query, collectionName);
	}

	
	public <T> List<T> findAll(Class<T> entityClass, String collectionName) {
		return this.mongoTemplate.findAll(entityClass, collectionName);
	}
	
	
	
	
	
	
	
	
	
	
	
	public <T> List<T> findAll(Class<T> entityClass) {
		return this.mongoTemplate.findAll(entityClass);
	}
	
	public <T> T findById(String id, Class<T> entityClass) {
		return this.mongoTemplate.findById(id, entityClass);
	}
	
	public <T> T findOne(Query query, Class<T> entityClass) {
		return this.mongoTemplate.findOne(query, entityClass);
	}
	
	/**
	 * cx-20160909
	 * @param obj
	 * @param entityClass
	 * @return
	 */
	public <T> T findOneByCollectionName(Query query , Class<T> entityClass,String collectionName) {
		return this.mongoTemplate.findOne(query, entityClass,collectionName);
	}
	public <T> T findOneByCollectionName(Query query,DBObject obj, Class<T> entityClass,String collectionName) {
		return this.mongoTemplate.findOne(query, entityClass, collectionName);
		
	}
	/****************************************************************************************/
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

	public void insert(Collection<Object> batchToSave,String collectionName) {
		this.mongoTemplate.insert(batchToSave, collectionName);
	}
	/**
	 * 批量插入要输入域名（表名）
	 * 	cx-20160914
	 * @param obj
	 */
	public void inserts(Collection<Object> batchToSave,String collectionName) {
		this.mongoTemplate.insert(batchToSave, collectionName);
	}
	/**
	 * 删除数据
	 */
	public <T> void delete(Query q,String fileName,String collectionName,Class<T> c){
		this.mongoTemplate.findAndRemove(q, c, collectionName);
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
