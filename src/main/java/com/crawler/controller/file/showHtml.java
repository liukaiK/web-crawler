package com.crawler.controller.file;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crawler.collection.DbFile;
import com.crawler.common.MongoDBUtil;
import com.crawler.controller.site.SiteController;

@Controller
@RequestMapping("/admin")
public class showHtml {
	
	@Autowired
	private MongoDBUtil mdu;
	/**
	 * cx-20161014 mongodb取html流
	 * @param fileName
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/showPage")
	public void showPage(String fileName,HttpServletResponse response) throws IOException {
		
		String collectionName = SiteController.siteId + "_html";
		Criteria criatira = new Criteria();
		criatira.andOperator(Criteria.where("fileName").is(fileName));
		DbFile  df = mdu.findOneByCollectionName(collectionName, criatira, DbFile.class);
		String htmlC = new String(df.getFileByte(),"utf-8");
		System.out.println(htmlC);	
		//StringBuffer sbHtml = new StringBuffer();
        //sbHtml.append(htmlC);
		response.setCharacterEncoding("utf-8");           
	    response.setContentType("text/html; charset=utf-8");
		
	    PrintWriter writer = response.getWriter();           
	    writer.write(htmlC);
	}
}
