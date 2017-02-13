package com.esd.bs.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.collection.History;
import com.esd.entity.SearchResult;

@Controller
public class SearchController {
	
	@Resource
	private MongoTemplate mongoTemplate;
	
	/**
	 * 搜索页
	 * 
	 * @param serCon
	 * @return
	 * @throws UnknownHostException 
	 * @throws NumberFormatException 
	 * @throws IOException
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView searchPOST(String serCon, int currentPage, HttpSession session) throws NumberFormatException, UnknownHostException {
		Map<String, Object> map = new HashMap<String, Object>();
		Query dbo = new Query();
		Pattern pattern = Pattern.compile("^.*" + serCon + ".*$", Pattern.CASE_INSENSITIVE);
//		dbo.put("title", pattern);
//		dbo.put("state", "1");
		dbo.addCriteria(Criteria.where("title").is(pattern));
		dbo.addCriteria(Criteria.where("state").is(1));
		long total = mongoTemplate.count(dbo, History.class);
		List<History> list = findPage(dbo, History.class, currentPage);
		SearchResult searchResult = new SearchResult();
		searchResult.setHistory(list);
		searchResult.setCurrentPage(currentPage);
		searchResult.setSerCon(serCon);
		searchResult.setTotal(total);
		searchResult.setTotalPage(total % 20 == 0 ? (int) total / 20 : (int) (total / 20) + 1);
		map.put("result", searchResult);
		return new ModelAndView("result", map);
		
	}
	
	public <T> List<T> findPage(Query query, Class<T> entityClass, int currentPage) {
		query.skip((currentPage - 1) * 20).limit(20);
		return mongoTemplate.find(query, entityClass);
	}
}
