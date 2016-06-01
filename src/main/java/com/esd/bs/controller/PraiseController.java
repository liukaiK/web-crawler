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
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

/**
 * 我要表扬
 * 
 * @author liukai
 * 
 */
@Controller
public class PraiseController {

	private static Logger logger = Logger.getLogger(PraiseController.class);

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
	public Map<String, Object> addPraise(Praise praise, HttpServletRequest request) throws InterruptedException {
		Map<String, Object> map = new HashMap<String, Object>();
		HtmlPage page1 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		try {
			page1 = webClient.getPage(URL);
			HtmlSelect provinceSelect = (HtmlSelect) page1.getElementById("province");
			provinceSelect.setSelectedAttribute(praise.getConTarget(), true);
			Thread.sleep(500);
			HtmlSelect citySelect = (HtmlSelect) page1.getElementById("city");
			citySelect.setSelectedAttribute(praise.getConUnit(), true);
			Thread.sleep(500);
			HtmlSelect city1Select = (HtmlSelect) page1.getElementById("city1");
			city1Select.setSelectedAttribute(praise.getConType(), true);
			HtmlInput conName = (HtmlInput) page1.getElementsByTagName("input").get(0);
			conName.setAttribute("value", praise.getConName());
			HtmlInput email = (HtmlInput) page1.getElementsByTagName("input").get(2);
			email.setAttribute("value", praise.getEmail());
			HtmlTextArea conContentTextArea = (HtmlTextArea)page1.getElementsByTagName("textarea").get(0);
			conContentTextArea.setTextContent(praise.getConContent());
			HtmlAnchor submitBtn = (HtmlAnchor) page1.getElementById("submitBtn");
			HtmlPage hp = submitBtn.click();
			HtmlButton tijiaoButton = (HtmlButton) page1.getElementsByTagName("button").get(0);
			hp = tijiaoButton.click();
			if (hp != null) {
				String pageUrl = hp.getUrl().toString().trim();
				if (pageUrl.equals("http://www.caacts.org.cn:8080/struts2_spring3_hibernate3_1.0/addBiaoyang.action")) {
					map.put("notice", true);
					map.put("message", "提交成功!");
					return map;
				}
			}
		} catch (FailingHttpStatusCodeException e) {
			logger.error(e.getMessage());
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		map.put("notice", false);
		map.put("message", "提交失败,请稍后再试!");
		return map;
	}

}
