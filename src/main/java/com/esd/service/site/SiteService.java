package com.esd.service.site;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.esd.collection.Site;
import com.esd.dao.MongoDBDao;
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
	public <T> List<T> findAllSite(Class<T> entityClass) {
		return this.mongoDBDao.findAll(entityClass, collectionName);
	}

	public void addSite(String siteName, String domainName, String port) {
		Date date = new Date();
		Site site = new Site();
		site.setSiteName(siteName);
		site.setDomainName(domainName);
		site.setPort(port);
		site.setCreateDate(sdf.format(date));
		site.setUpdateDate(sdf.format(date));
		mongoDBDao.insert(site, collectionName);
	}

	
	public WriteResult removeSite(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));
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
	
	
	
	
//	public <T> Site findOne(String id) {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("_id").is(id));
//		return mongoDBDao.findOne(query, Site.class, "sites");
//	}
}
