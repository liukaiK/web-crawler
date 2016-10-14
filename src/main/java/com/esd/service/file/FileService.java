package com.esd.service.file;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.esd.collection.DbFile;
import com.esd.dao.MongoDBDao;
import com.esd.util.Md5;
import com.mongodb.WriteResult;

@Service
public class FileService {
	@Autowired
	private MongoDBDao mongoDBDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	/**
	 * liukai-2016.10.11
	 * @param fileName
	 * @param collectionName
	 * @return
	 */
	public <T> DbFile findFileByName(String fileName, String collectionName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("fileName").is(fileName));
		return mongoDBDao.findOne(query, DbFile.class, collectionName);
	}
	
	
	
	/**
	 * liukai-2016.10.11
	 * @param fileName
	 * @param content
	 * @param siteName
	 * @param collectionName
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public WriteResult upsertFile(String fileName, String content, String siteName, String collectionName) throws UnsupportedEncodingException {
		String date = sdf.format(new Date());
		String filedir = File.separator + fileName;
		byte[] fileByte = content.getBytes("UTF-8");
		String md5File = Md5.getMd5File(fileByte);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("fileName").is(fileName));
		query.addCriteria(Criteria.where("siteName").is(siteName));
		
		Update update = new Update();
//		update.set("createDate", date);
		update.set("fileByte", fileByte);
		update.set("filedir", filedir);
		update.set("fileName", fileName);
		update.set("md5File", md5File);
		update.set("siteName", siteName);
		update.set("updateDate", date);
		
		return mongoDBDao.upsert(query, update, collectionName);
	}
	
	
	/**
	 * liukai-2016.10.11
	 * @param fileName
	 * @param collectionName
	 * @return
	 */
	public WriteResult removeFileByName(String fileName, String collectionName) {
		Query query = new Query();
		query.addCriteria(Criteria.where("fileName").is(fileName));
		return mongoDBDao.remove(query, collectionName);
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
}
