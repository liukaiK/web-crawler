package com.esd.common;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.esd.collection.Downloads;
import com.esd.collection.History;
import com.esd.collection.Urls;
import com.esd.util.Md5;

@Repository
public class MongoDBUtil {
	
//	private static Logger logger = Logger.getLogger(MongoDBUtil.class);
	
	@Resource
	private MongoTemplate mongoTemplate;

	public Long getDownloadsCount() {
		return mongoTemplate.count(new Query(), Downloads.class);
	}

	public Long getUrlsCount() {
		return mongoTemplate.count(new Query(), Urls.class);
	}

	public void dropTable() {
		mongoTemplate.dropCollection(Downloads.class);
		mongoTemplate.dropCollection(Urls.class);
		System.gc();
	}

	public void downloadsInsert(String url) {
		Query query = new Query(Criteria.where("url").is(url));
		if (mongoTemplate.findOne(query, Downloads.class) == null) {
			if (mongoTemplate.findOne(query, Urls.class) == null) {
				mongoTemplate.insert(new Downloads(url));
			}
		}
	}

	public synchronized Downloads downloadsFindAndDeleteOne() {
		Query query = new Query();
		Downloads obj = mongoTemplate.findAndRemove(query, Downloads.class);
		if (obj == null) {
			dropTable();// 采集完成 ，删除所有临时表
			return null;
		}
		String url = obj.getUrl();
		while (mongoTemplate.findOne(new Query(Criteria.where("url").is(url)), Urls.class) != null) {
			obj = mongoTemplate.findAndRemove(query, Downloads.class);
			if (obj == null) {
				return null;
			} else {
				url = obj.getUrl();
			}
		}
		return obj;
	}


	public void urlsInsert(Urls urlsCollections) {
		String url = urlsCollections.getUrl();
		Query query = new Query(Criteria.where("url").is(url));
		String md = Md5.getMd5(new StringBuffer(url));
		md = md + ".html";
		urlsCollections.setMd5(md);
		Urls ufs = mongoTemplate.findOne(query, Urls.class);
		if (ufs == null) {
			mongoTemplate.insert(urlsCollections);
		}
	}
	
	
	public void historyInsert(String url, String title, String md, int state){
		// 插入历史记录
		History history = mongoTemplate.findOne(new Query(Criteria.where("url").is(url)), History.class);
		if (history != null) {
			mongoTemplate.remove(history);
		}
		history = new History();
		history.setUrl(url);
		history.setTitle(title);
		history.setMd5(md);
		history.setState(state);
		mongoTemplate.insert(history);
	}

}
