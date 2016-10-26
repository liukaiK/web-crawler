package com.esd.controller.file;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esd.collection.DbFile;
import com.esd.common.MongoDBUtil;
import com.esd.controller.site.SiteController;

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
		DbFile  df = mdu.findOneByCollectionName(collectionName, fileName+".html", DbFile.class);
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
