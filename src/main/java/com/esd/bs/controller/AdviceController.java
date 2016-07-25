package com.esd.bs.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.common.AutoDiscern;
import com.esd.common.CatDao;
import com.esd.common.InputStreamUtils;
import com.esd.config.PageConfig;
import com.esd.util.Md5;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

@Controller
public class AdviceController {

	private static Logger log = Logger.getLogger(AdviceController.class);

	private WebClient webClient = null;

	public AdviceController() {
		// TODO Auto-generated constructor stub
		webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
	}

	/**
	 * 建议 htmlunit模拟提交,重点**验证码的获取
	 * 
	 * @param url
	 * @param textarea
	 * @param name
	 * @param phone
	 * @param IdCard
	 * @param request
	 * @return
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws InterruptedException
	 */

	@RequestMapping(value = "/submitPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitPagePOST(String url, String textarea, String name, String phone, String IdCard, String code, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		String randomCode = (String) session.getAttribute("randomCode");
		if (!randomCode.equals(code)) {
			map.put("message", false);
			map.put("check", false);
			return map;
		}
		if (textarea == null || textarea.equals("")) {
			map.put("message", false);
			map.put("content", false);
			return map;
		}

		try {
			HtmlPage page1 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
			page1 = webClient.getPage(url);
			// 加载js时间
			HtmlTextArea hta = (HtmlTextArea) page1.getElementById("nr");
			hta.setText(textarea);
			HtmlInput xm = (HtmlInput) page1.getElementById("xm");
			xm.setValueAttribute(name);
			HtmlTextInput dh = (HtmlTextInput) page1.getElementById("dh");
			dh.setValueAttribute(phone);
			HtmlTextInput sfz = (HtmlTextInput) page1.getElementById("sfz");
			sfz.setValueAttribute(IdCard);
			// 验证码
			HtmlTextInput yzm = (HtmlTextInput) page1.getElementById("yzm");
			// 验证码
			HtmlImage hi = (HtmlImage) page1.getElementById("rndImg");
			// 下载验证码图片
			String yzmNum = getCodeNum(hi);
			if (yzmNum == null) {
				map.put("message", false);
				return map;
			}
			// 填写验证码
			yzm.setAttribute("value", yzmNum);
			// 提交按钮
			DomNodeList<DomElement> table = page1.getElementsByTagName("table");
			HtmlTable ht = (HtmlTable) table.get(4);
			HtmlTableRow htr = (HtmlTableRow) ht.getElementsByTagName("tr").get(2);
			HtmlButtonInput input = (HtmlButtonInput) htr.getHtmlElementsByTagName("input").get(0);
			// // 提交
			HtmlPage hp = null;
			hp = input.click();
			log.debug(hp);
			// 转换
			if (hp != null) {
				String pageUrl = hp.getUrl().toString().trim();
				String m = pro(pageUrl, hp.asXml());
				map.put("message", true);
				map.put("m", m);
			} else {
				map.put("message", false);
			}
		} catch (IOException e) {
			log.error(e);
			map.put("message", false);
			return map;
		} finally {
			if (webClient != null) {
				webClient.close();
			}
		}
		return map;
	}

	private String getCodeNum(HtmlImage hi) {
		// Long l = System.currentTimeMillis();
		// 下载验证码图片
		InputStream in = null;
		byte[] bs = null;
		try {
			in = hi.getWebResponse(true).getContentAsStream();
			bs = InputStreamUtils.InputStreamTOByte(in);
			in.close();
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 获得验证码
		AutoDiscern autoDiscern = new AutoDiscern();
		String yzmNum = autoDiscern.discernPic(bs, 1);
		// System.out.println("getCode" + (System.currentTimeMillis() - l));
		return yzmNum;
	}

	/**
	 * 新生成页面
	 * 
	 * @param pageUrl
	 * @param doc
	 * @return
	 */
	private String pro(String pageUrl, String doc) {
		Long l = System.currentTimeMillis();
		CatDao dao = new CatDao();
		dao.collectPageConfig();
		PageConfig pageConfig = dao.findPageConfig(pageUrl);
		pageConfig.setUrl(pageUrl);
		Document document = Jsoup.parse(doc, pageUrl);
		dao.singlCat(pageConfig, document);
		Md5 md5 = new Md5();
		String m = md5.getMd5(pageUrl);
		System.out.println("pro" + (System.currentTimeMillis() - l));
		return m;
	}

	/**
	 * 查看
	 * 
	 * @param url
	 * @return
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	@RequestMapping(value = "/lookPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> lookPagePost(String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		HtmlPage page1 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		try {
			page1 = webClient.getPage(url);
			// 查看按钮
			DomNodeList<DomElement> table = page1.getElementsByTagName("table");
			HtmlTable ht = (HtmlTable) table.get(4);
			HtmlTableRow htr = (HtmlTableRow) ht.getElementsByTagName("tr").get(2);
			HtmlButtonInput input = (HtmlButtonInput) htr.getHtmlElementsByTagName("input").get(1);
			// 查看
			HtmlPage hp = input.click();
			String pageUrl = hp.getUrl().toString().trim();
			String m = pro(pageUrl, hp.asXml());
			map.put("message", true);
			map.put("m", m);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (webClient != null) {
				webClient.close();
			}
		}

		// System.out.println("提交:" + input.asXml());
		// System.out.println(hp.asXml());
		return map;
	}

}
