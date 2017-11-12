package com.crawler.service.file;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.crawler.collection.DbPgFile;
import com.crawler.config.PageConfig;
import com.crawler.dao.MongoDBDao;
import com.mongodb.WriteResult;

@Service
public class PgService {
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	@Autowired
	private MongoDBDao mongoDBDao;
	
	/**
	 * liukai-2016.10.11
	 * @param fileName
	 * @param collectionName
	 * @return
	 */
	public <T> DbPgFile findFileByName(String fileName, String collectionName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("fileName").is(fileName));
		return mongoDBDao.findOne(query, DbPgFile.class, collectionName);
	}
	
	public WriteResult upsertFile(String fileName, String filedir, String siteName, PageConfig pageConfig, String collectionName) throws UnsupportedEncodingException {
		String date = sdf.format(new Date());
		Query query = new Query();
		query.addCriteria(Criteria.where("fileName").is(fileName));
		query.addCriteria(Criteria.where("siteName").is(siteName));
		
		Update update = new Update();
		update.set("fileName", fileName);
		update.set("filedir", filedir);
		update.set("pageConfig", pageConfig);
		update.set("md5File", pageConfig.toString());
		update.set("siteName", siteName);
		update.set("updateDate", date);
		
		return mongoDBDao.upsert(query, update, collectionName);
	}
	
	
	
	/**
	 * liukai-2016.10.11
	 * 从数据库查询所有文件
	 * @param entityClass
	 * @param collectionName
	 * @return
	 */
	public <T> List<T> findAll(Class<T> entityClass,String collectionName) {
		return this.mongoDBDao.findAll(entityClass, collectionName);
	}
	
	/**
	 * liukai-2016.10.12
	 * @param fileName
	 * @param collectionName
	 * @return
	 */
	public WriteResult removeFileByName(String fileName, String collectionName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("fileName").is(fileName));
		return mongoDBDao.remove(query, collectionName);
	}
	
}
