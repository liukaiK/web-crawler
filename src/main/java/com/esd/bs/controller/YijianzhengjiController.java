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

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

/**
 * 意见征集
 * 
 * @author liukai
 * 
 */
@Controller
public class YijianzhengjiController {
	
	private static Logger logger = Logger.getLogger(YijianzhengjiController.class);

	private WebClient webClient = null;

	public YijianzhengjiController() {
		webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
	}
	
	/**
	 * 关于网站改版的意见征集
	 * @param title
	 * @param name
	 * @param phone
	 * @param conContent
	 * @param code
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/yijianzhengji7", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> yijianzhengji7(String title, String name, String phone, String conContent, String code, HttpServletRequest request) throws InterruptedException {
		Map<String, Object> map = new HashMap<String, Object>();
		String url = "http://www.jlsy.gov.cn/hd/yjzz/yjzjj/yjzja/";
		String randomCode = (String) request.getSession().getAttribute("randomCode");
		if (!randomCode.equals(code)) {
			map.put("notice", false);
			map.put("message", "验证码错误!");
			return map;
		}
		try {
			HtmlPage page = webClient.getPage(url);
			
			HtmlInput title_input = (HtmlInput)page.getElementById("my:group1/my:field1");
			title_input.setValueAttribute(title);
			
			HtmlInput name_input = (HtmlInput) page.getElementById("my:group1/my:姓名");
			name_input.setValueAttribute(name);
			
			HtmlInput phone_input = (HtmlInput)page.getElementById("my:group1/my:联系电话");
			phone_input.setValueAttribute(phone);
			
			HtmlSpan content_text = (HtmlSpan)page.getElementById("my:group1/my:建议内容");
			content_text.setTextContent(conContent);
			
			HtmlButtonInput submitBtn = (HtmlButtonInput) page.getElementById("SubmitButton");
			HtmlPage hp = submitBtn.click();
			if (hp != null) {
				String pageUrl = hp.getUrl().toString().trim();
				if (pageUrl.equals("http://218.27.19.186:8182/infogate//collect")) {
					map.put("notice", true);
					map.put("message", "提交成功!");
					return map;
				}
			} else {
				map.put("notice", false);
				map.put("message", "程序出现错误!请稍后重试");
				return map;
			}
		} catch (FailingHttpStatusCodeException e) {
			logger.error(e.getMessage());
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (webClient != null) {
				webClient.close();
			}
		}
		return map;
	}
}
