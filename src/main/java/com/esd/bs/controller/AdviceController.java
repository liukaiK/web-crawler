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

/**
 * 公众来信
 * @author liukai
 *
 */
@Controller
public class AdviceController {

	private static Logger log = Logger.getLogger(AdviceController.class);

	private WebClient webClient = null;
	
	private Md5 md5 = new Md5();

	public AdviceController() {
		webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
	}

	
	/**
	 * 建议 htmlunit模拟提交,重点**验证码的获取
	 * @param url
	 * @param author
	 * @param email
	 * @param mobile
	 * @param address
	 * @param type
	 * @param show
	 * @param title
	 * @param content
	 * @param certicode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submitPage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitPagePOST(String url, String author, String email, String mobile, String address, String type, String show, String title, String content, String certicode, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		String randomCode = (String) session.getAttribute("randomCode");
		if (!randomCode.equals(certicode)) {
			map.put("message", false);
			map.put("check", false);
			return map;
		}
		if (content == null || content.equals("")) {
			map.put("message", false);
			map.put("content", false);
			return map;
		}

		try {
			HtmlPage page1 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
			page1 = webClient.getPage(url);
			// 加载js时间
			HtmlInput author_input = (HtmlInput) page1.getElementById("author");
			author_input.setValueAttribute(author);
			
			HtmlInput email_input = (HtmlInput) page1.getElementById("email");
			email_input.setValueAttribute(email);
			
			HtmlInput mobile_input = (HtmlInput) page1.getElementById("mobile");
			mobile_input.setValueAttribute(mobile);
			
			HtmlInput address_input = (HtmlInput) page1.getElementById("address");
			address_input.setValueAttribute(address);
			
			HtmlInput type_input = (HtmlInput) page1.getElementByName("type");
			type_input.setValueAttribute(type);
			
			HtmlInput show_input = (HtmlInput) page1.getElementByName("show");
			show_input.setValueAttribute(show);
			
			HtmlInput title_input = (HtmlInput) page1.getElementByName("title");
			title_input.setValueAttribute(title);
			
			
			HtmlTextArea content_textare = (HtmlTextArea)page1.getElementByName("content");
			content_textare.setText(content);
			
			// 验证码
			HtmlTextInput yzm = (HtmlTextInput) page1.getElementById("certicode");
			// 验证码
			HtmlImage hi = (HtmlImage) page1.getElementById("verifyImage");
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
			HtmlTable ht = (HtmlTable) table.get(3);
			HtmlTableRow htr = (HtmlTableRow) ht.getElementsByTagName("tr").get(9);
			HtmlInput input = (HtmlInput) htr.getHtmlElementsByTagName("input").get(0);
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

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String url = "http://www.baic.gov.cn/mess/message/leavemess_new.jsp";
		WebClient webClient = new WebClient();
		HtmlPage page1 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		page1 = webClient.getPage(url);
		HtmlTextArea content_textare = (HtmlTextArea)page1.getElementByName("content");
		System.out.println(content_textare);
		
		
		DomNodeList<DomElement> table = page1.getElementsByTagName("table");
		HtmlTable ht = (HtmlTable) table.get(3);
		HtmlTableRow htr = (HtmlTableRow) ht.getElementsByTagName("tr").get(9);
		HtmlInput input = (HtmlInput) htr.getHtmlElementsByTagName("input").get(0);
		
		
		
		
		
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
