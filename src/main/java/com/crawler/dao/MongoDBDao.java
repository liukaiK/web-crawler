package com.crawler.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;


@Repository
public class  MongoDBDao {

	//@Autowired
	@Qualifier("mongoTemplate")
	protected MongoTemplate mongoTemplate; 
	
//	if(mongoTemplate == null){
//		mongoTemplate = MongoDBLink.link();
//	}
	public MongoTemplate getM(){
		if(mongoTemplate == null){
			mongoTemplate = MongoDBLink.link();
		} 
		return mongoTemplate;
	}
//	public MongoTemplate getMongoTemplate() {
//		return mongoTemplate;
//	}
//	public void setMongoTemplate(MongoTemplate mongoTemplate) {
//		this.mongoTemplate = mongoTemplate;
//	}
	/**
	 * cx-20160919
	 * @param objectToSave
	 * @param collectionName
	 */
	public void insert(Object objectToSave, String collectionName) { 
		getM();
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
		getM();
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
		getM();
		return mongoTemplate.upsert(query, update, collectionName);
	}
	
	/**
	 * liukai-2016.10.11
	 * @param query
	 * @param collectionName
	 * @return
	 */
	public WriteResult remove(Query query, String collectionName) {
		getM();
		return mongoTemplate.remove(query, collectionName);
	}
	
	public <T> List<T> findAll(Class<T> entityClass, String collectionName) {
		getM();
		return this.mongoTemplate.findAll(entityClass, collectionName);
	}
	
	public <T> List<T> find(Query query, Class<T> entityClass, String collectionName) {
		getM();
		//System.out.println("q:"+query+"\nclass:"+entityClass+"\ncollectionName:"+collectionName);	
		return this.mongoTemplate.find(query, entityClass, collectionName);
		
	}
	
	public void dropCollection(String collectionName) {
		getM();
		this.mongoTemplate.dropCollection(collectionName);
	}
	
	public <T> List<T> findAll(Class<T> entityClass) {
		getM();
		return this.mongoTemplate.findAll(entityClass);
	}
	
	public <T> T findById(String id, Class<T> entityClass) {
		getM();
		return this.mongoTemplate.findById(id, entityClass);
	}
	
	public <T> T findOne(Query query, Class<T> entityClass) {
		getM();
		return this.mongoTemplate.findOne(query, entityClass);
	}
	
	/**
	 * cx-20160909
	 * @param obj
	 * @param entityClass
	 * @return
	 */
	public <T> T findOneByCollectionName(Query query , Class<T> entityClass,String collectionName) {
		getM();
		return this.mongoTemplate.findOne(query, entityClass,collectionName);
	}
	public <T> T findOneByCollectionName(Query query,DBObject obj, Class<T> entityClass,String collectionName) {
		getM();
		return this.mongoTemplate.findOne(query, entityClass, collectionName);	
	}
	public <T> void upsert(Query query,Update update,Class<T> entityClass,String  collectionName){
		getM();
		this.mongoTemplate.upsert(query, update, entityClass, collectionName);
	}
	/****************************************************************************************/
	public <T> List<T> find(Query query, Class<T> entityClass){
		getM();
		return this.mongoTemplate.find(query, entityClass);
	}
	
	public <T> Long count(Query query, Class<T> entityClass) {
		getM();
		return this.mongoTemplate.count(query, entityClass);
	}
	
	public <T> T findAndRemove(Query query, Class<T> entityClass) {
		getM();
		return this.mongoTemplate.findAndRemove(query, entityClass);
	}
	
	public void insert(Object obj) {
		getM();
		this.mongoTemplate.insert(obj);
	}

	public void insert(Collection<Object> batchToSave,String collectionName) {
		getM();
		this.mongoTemplate.insert(batchToSave, collectionName);
	}
	/**
	 * 批量插入要输入域名（表名）
	 * 	cx-20160914
	 * @param obj
	 */
//	public void inserts(Collection<Object> batchToSave,String collectionName) {
//		this.mongoTemplate.insert(batchToSave, collectionName);
//	}
	/**
	 * 删除数据
	 */
	public <T> void delete(Query q,String fileName,String collectionName,Class<T> c){
		getM();
		this.mongoTemplate.findAndRemove(q, c, collectionName);
	}
	
	public void remove(Object obj) {
		getM();
		this.mongoTemplate.remove(obj);
	}
	
	public <T> void dropCollection(Class<T> entityClass) {
		getM();
		this.mongoTemplate.dropCollection(entityClass);
	}
	
	public <T> List<T> findPage(Query query, Class<T> entityClass, int currentPage) {
		getM();
		query.skip((currentPage - 1) * 20).limit(20);
		return this.mongoTemplate.find(query, entityClass);
	}

}
