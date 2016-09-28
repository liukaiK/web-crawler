package com.esd.common;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.esd.collection.Downloads;
import com.esd.collection.History;
import com.esd.collection.Urls;
import com.esd.core.CollectionPage;
import com.esd.dao.MongoDBDao;
import com.esd.util.Md5;

@Repository
public class MongoDBUtil {
	
	@Resource
	private MongoDBDao mongoDBDao;

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
		Md5 md5 = new Md5();
		String md = md5.getMd5(new StringBuffer(url));
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

}
