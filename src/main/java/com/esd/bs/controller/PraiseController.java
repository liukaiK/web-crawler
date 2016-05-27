package com.esd.bs.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.entity.other.Praise;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

/**
 * 我要表扬
 * 
 * @author liukai
 * 
 */
@Controller
public class PraiseController {

	private static Logger logger = Logger.getLogger(AdviceController.class);

	private WebClient webClient = null;

	private final String URL = "http://www.caacts.org.cn:8080/struts2_spring3_hibernate3_1.0/byTianjia.action";

	public PraiseController() {
		webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
	}

	@RequestMapping(value = "/addPraise", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPraise(Praise praise, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();

		HtmlPage page1 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		try {
			page1 = webClient.getPage(URL);

			HtmlSelect provinceSelect = (HtmlSelect) page1.getElementById("province");
			
			HtmlOption num = provinceSelect.getOptionByValue(praise.getConTarget());
			
			
			
			
			
			
			
			
			
			

		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(praise);

		return map;
	}

}
