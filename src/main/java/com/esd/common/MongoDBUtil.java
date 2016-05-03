package com.esd.common;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.esd.controller.CollectionPage;
import com.esd.dao.MongoDBDao;
import com.esd.entity.Downloads;
import com.esd.entity.History;
import com.esd.entity.Urls;
import com.esd.util.Md5;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Repository
public class MongoDBUtil {
	
	@Resource
	private MongoDBDao mongoDBDao;

	private static Logger log = Logger.getLogger(CollectionPage.class);

	public Long getDownloadsCount() {
		return mongoDBDao.count(new BasicDBObject(), Downloads.class);
	}

	public Long getUrlsCount() {
		return mongoDBDao.count(new BasicDBObject(), Urls.class);
	}

	public void dropTable() {
		mongoDBDao.dropCollection(Downloads.class);
		mongoDBDao.dropCollection(Urls.class);
	}

	public void downloadsInsert(String url) {
		if (url == null) {
			return;
		}
		BasicDBObject obj = new BasicDBObject("url", url);
		if (mongoDBDao.findOne(obj, Downloads.class) == null) {
			if (mongoDBDao.findOne(obj, Urls.class) == null) {
				mongoDBDao.insert(new Downloads(url));
			}
		}
	}

	public synchronized Downloads downloadsFindAndDeleteOne() {
		DBObject arg0 = new BasicDBObject();
		Downloads obj = mongoDBDao.findAndRemove(arg0, Downloads.class);
		if (obj == null) {
			System.out.println("整站采集完成！");
			dropTable();// 采集完成 ，删除所有临时表
			return null;
		}
		while (mongoDBDao.findOne(new BasicDBObject("url", obj.getUrl()), Urls.class) != null) {
			obj = mongoDBDao.findAndRemove(arg0, Downloads.class);
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
		DBObject doc = new BasicDBObject();
		Md5 md5 = new Md5();
		String md = md5.getMd5(new StringBuffer(url));
		md = md + ".html";
		doc.put("url", url);
		urlsCollections.setTitle(title);
		urlsCollections.setMd5(md);
		Urls ufs = mongoDBDao.findOne(doc, Urls.class);
		if (ufs != null) {
			return;
		}
		// 插入处理记录
		mongoDBDao.insert(urlsCollections);
		// 插入历史记录
		History history = mongoDBDao.findOne(doc, History.class);
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

}
