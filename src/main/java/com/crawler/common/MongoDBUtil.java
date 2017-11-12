package com.crawler.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.crawler.collection.DbFile;
import com.crawler.collection.DbPgFile;
import com.crawler.collection.Downloads;
import com.crawler.collection.History;
import com.crawler.collection.Urls;
import com.crawler.controller.site.SiteController;
import com.crawler.core.CollectionPage;
import com.crawler.dao.MongoDBDao;
import com.crawler.util.Md5;
import com.crawler.util.UtilFile;

@Repository
public class MongoDBUtil {

	@Resource
	private MongoDBDao mongoDBDao;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private Collection<Object> db = new ArrayList<Object>();

	private static Logger logger = Logger.getLogger(CollectionPage.class);
	
	public Long getDownloadsCount() {
		return mongoDBDao.count(new Query(), Downloads.class);
	}

	public Long getUrlsCount() {
		return mongoDBDao.count(new Query(), Urls.class);
	}
	
	public void dropTable() {
		mongoDBDao.dropCollection(Downloads.class);
		mongoDBDao.dropCollection(Urls.class);
	}

	public void downloadsInsert(String url) {
		if (url == null) {
			return;
		}
		Query query = new Query(Criteria.where("url").is(url));
		if (mongoDBDao.findOne(query, Downloads.class) == null) {
			if (mongoDBDao.findOne(query, Urls.class) == null) {
				mongoDBDao.insert(new Downloads(url));
			}
		}
	}

	public synchronized Downloads downloadsFindAndDeleteOne() {
		Query query = new Query();
		Downloads obj = mongoDBDao.findAndRemove(query, Downloads.class);
		if (obj == null) {
			logger.debug("-----------整站采集完成！----------------");
			dropTable();// 采集完成 ，删除所有临时表
			return null;
		}
		while (mongoDBDao.findOne(new Query(Criteria.where("url").is(obj.getUrl())), Urls.class) != null) {
			obj = mongoDBDao.findAndRemove(query, Downloads.class);
			if (obj == null) {
				return null;
			}
		}
		return obj;
	}

	public void urlsInsert(Urls urlsCollections, String title) {
		
		if (urlsCollections == null) {
			return;
		}
		String url = urlsCollections.getUrl();
		Query query = new Query(Criteria.where("url").is(url));
		String md = Md5.getMd5(url);
		md = md + ".html";
		urlsCollections.setTitle(title);
		urlsCollections.setMd5(md);
		
		Urls ufs = mongoDBDao.findOne(query, Urls.class);
		
		if (ufs != null) {
			return;
		}
		// 插入处理记录
		mongoDBDao.insert(urlsCollections);
		
		// 插入历史记录
		History history = mongoDBDao.findOne(query, History.class);
		
		if (history != null) {
			mongoDBDao.remove(history);
		}
		history = new History();
		history.setUrl(url);
		history.setTitle(title);
		history.setMd5(md);
		history.setState(urlsCollections.getState());
		mongoDBDao.insert(history);
		
	}
	/**
	 * cx-20160926
	 */
	public void insertFile(String fileName, byte[] fileByte, String filedir, String type){
		DbFile df = new DbFile();
		String md5File = Md5.getMd5File(fileByte);
//		df.setCreateDate(new Date());
		df.setFileByte(fileByte);
		df.setFiledir(filedir);
		df.setFileName(fileName);
		df.setMd5File(md5File);
		df.setSiteName(SiteController.siteId);
//		df.setUpdateDate(new Date());
		mongoDBDao.insert(df, SiteController.siteId + "_" + type);
	}
	public <T> void insertFile(DbFile dbFile,String collectionName){
		mongoDBDao.insert(dbFile, collectionName);
	}
	/**
	 * 新增pg插入数据库
	 * @param dbPgFile
	 * @param siteName
	 */
	public void insertPg(DbPgFile dbPgFile){
		mongoDBDao.insert(dbPgFile, SiteController.siteId + "_pg");
	}
	/**
	 * cx-20160927
	 * 删除file
	 */
	public <T> void delete(String fileName,String type,Class<T> c){
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("fileName").is(fileName));
		
		mongoDBDao.delete(new Query(criatira), fileName, SiteController.siteId+"_"+type,c);
	}
	/**
	 * cx-20160928
	 * 按条件查询一条数据
	 * 返回DbFile类型数据
	 */
	public <T> T findOneByCollectionName(String collectionName,Criteria criatira,Class<T> entityClass){
//		Criteria criatira = new Criteria();
//		criatira.andOperator(Criteria.where("fileName").is(fileName)); 
		return mongoDBDao.findOneByCollectionName(new Query(criatira),entityClass ,collectionName);
	}
	/**
	 * 
	 */
	public void insertFile(String fileName,String content,String filedir,String fileType,boolean ctrl){
		
		if(!ctrl){
			//MongoDBDao mongoDBDao = (MongoDBDao)SpringContextUtil.getBeanDao("mongoDBDao");
			mongoDBDao.insert(db,SiteController.siteId + "_" + fileType);
		}else{
			String md5File =  Md5.getMd5(content);
			//存入mongodb
			byte[] fileByte = new byte[content.length()]; 
			fileByte = content.getBytes();
			DbFile df = new DbFile();
//			df.setId(1);
//			df.setUserId("123456");
			df.setFileName(fileName);
			df.setFileByte(fileByte);
			df.setMd5File(md5File);
			df.setFiledir(filedir);
//			df.setCreateDate(new Date());
//			df.setUpdateDate(new Date());
			df.setSiteName(SiteController.siteId);
			
			db.add(df);
			
			//mongoDBDao.insert(db,SiteController.siteId + "_" + fileType);
		}
		//新线程中重新获取bean
		//mongoDBDao.insert1(df,"_html");
		//
	}
	/**
	 * 批量插入数据，输入域名（表名）
	 * cx-20160909
	 * @param obj
	 * @param collectionName
	 */
	public void insertFiles(String collectionName,String url){
		
		File fold = new File(url);
		if (fold.exists()) {

			//文件组
			File[] file = fold.listFiles();
			byte[] fileByte = null; 
			String md5File = null;
			String fileName = null;
			
			Collection<Object> db = new ArrayList<Object>();
			
			for (int i = 0; i < file.length; i++) {
				
				if(!file[i].isDirectory()){
		
					fileName = file[i].getName();
					//System.out.println(fileName);
					fileByte = UtilFile.FiletoBytes(file[i]);
					md5File = Md5.getMd5File(fileByte);
					//fileName = md5.getMd5(fileName);
					
					DbFile df = new DbFile();
//					df.setId(m);
//					df.setUserId("00000");
					df.setFileName(fileName);
					df.setFileByte(fileByte);
					df.setMd5File(md5File);
//					df.setCreateDate(new Date());
//					df.setUpdateDate(new Date());
					df.setSiteName(SiteController.siteId);
					db.add(df);
				}
			}
			
			mongoDBDao.insert(db, collectionName);
		}
	}
	/**
	 * 取得项目根目录
	 * cx-20160910
	 * @param request
	 * @return
	 */
	public String url(HttpServletRequest request) {
		String url = request.getSession().getServletContext().getRealPath("/");
		return url;
	}
	/**
	 * cx-201609110
	 * 查询file
	 */
	public DbFile findFile(Query query){
		return mongoDBDao.findOne(query, DbFile.class);
	}
	public <T> T findById(String id,Class<T> entityClass){
		return mongoDBDao.findById(id, entityClass);
	}
	public <T> void upsert(String siteId,byte[] b,Class<T> entityClass,String collectionName){
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("_id").is(siteId));
		Update update = new Update();
		update.set("index", b);
		
		mongoDBDao.upsert(new Query(criatira), update, entityClass, collectionName);
	}
	/**
	 * cx-201609110
	 * 查询files
	 */
	public List<DbFile> findFiles(Query query){		
		return mongoDBDao.find(query, DbFile.class);
	}
	/**
	 * cx-20160920
	 * 查询files by collectionName
	 * @param entityClass
	 * @param collectionName
	 * @return
	 */
	public <T> List<T> findAll(Class<T> entityClass,String collectionName) {
		//MongoDBDao mongoDBDao = (MongoDBDao)SpringContextUtil.getBean("mongoDBDao");
		return mongoDBDao.findAll(entityClass, collectionName);
	}

}
