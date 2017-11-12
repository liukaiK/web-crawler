package com.crawler.service.site;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.crawler.collection.Site;
import com.crawler.dao.MongoDBDao;
import com.mongodb.WriteResult;

@Service
public class SiteService {
	
	@Autowired
	private MongoDBDao mongoDBDao;
	
	private final String collectionName = "sites";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	/**
	 * liukai-2016.10.11
	 * @param entityClass
	 * @param collectionName
	 * @return
	 */
	public <T> List<Site> findAllSite(Class<T> entityClass) {
		Query query = new Query();
		query.with(new Sort(Direction.DESC, "updateDate"));
		return this.mongoDBDao.find(query, Site.class, "sites");
	}

	public void addSite(String siteName, String domainName, String port) {
		
		Date date = new Date();
		Site site = new Site();
		String str = "index";
		
		//site.setId("1");
		site.setSiteName(siteName);
		site.setDomainName(domainName);
		site.setPort(port);
		site.setIndex(str.getBytes());
		site.setCreateDate(sdf.format(date));
		site.setUpdateDate(sdf.format(date));
		
		mongoDBDao.insert(site, collectionName);
	}

	
	public WriteResult removeSite(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		// 删除站点的同时 删除相关的表
		mongoDBDao.dropCollection(id + "_css");
		mongoDBDao.dropCollection(id + "_js");
		mongoDBDao.dropCollection(id + "_template");
		return mongoDBDao.remove(query, collectionName);
	}

	public WriteResult update(String id, String siteName, String domainName, String port) {
		Date date = new Date();
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		Update update = new Update();
		update.set("siteName", siteName);
		update.set("domainName", domainName);
		update.set("port", port);
		update.set("updateDate", sdf.format(date));
		return mongoDBDao.upsert(query, update, collectionName);
	}
	
	public <T> Site findOneById(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
		return mongoDBDao.findOne(query, Site.class, collectionName);
	}
}
