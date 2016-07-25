package com.esd.bs.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.common.AutoDiscern;
import com.esd.common.InputStreamUtils;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

@Controller
public class ConsultationController {

	private static Logger log = Logger.getLogger(ConsultationController.class);

	/**
	 * 咨询 运算型的验证码
	 * 
	 * @param url
	 * @param textarea
	 * @param problem
	 * @param phone
	 * @param code
	 * @param request
	 * @return
	 * @throws FailingHttpStatusCodeException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "/submitPage2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitPage2POST(String url, String textarea, String problem, String phone, String code, HttpServletRequest request) {

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

		final WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		HtmlPage page1 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		try {
			page1 = webClient.getPage(url);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 获取节点
		HtmlInput xm = (HtmlInput) page1.getElementById("titleX");
		xm.setValueAttribute(problem);
		HtmlTextInput dh = (HtmlTextInput) page1.getElementById("txttel");
		dh.setValueAttribute(phone);
		HtmlTextArea hta = (HtmlTextArea) page1.getElementById("contentX");
		hta.setText(textarea);
		final HtmlForm form = page1.getFormByName("consultationQuestionForm");
		HtmlImage hi = (HtmlImage) form.getElementsByTagName("img").get(0);
		// 验证码
		HtmlTextInput yzm = (HtmlTextInput) page1.getElementById("yzm");
		// 下载验证码图片
		InputStream in = null;
		byte[] bytes = null;
		try {
			in = hi.getWebResponse(true).getContentAsStream();
			bytes = InputStreamUtils.InputStreamTOByte(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// 获得验证码
		AutoDiscern autoDiscern = new AutoDiscern();
		String yzmNum = autoDiscern.discernPic(bytes, 2);
		// 填写验证码
		yzm.setAttribute("value", yzmNum);
		// 提交按钮
		HtmlTableDataCell htd = (HtmlTableDataCell) page1.getElementById("button");
		HtmlAnchor ha = (HtmlAnchor) htd.getElementsByTagName("a").get(1);
		// // 提交
		HtmlPage hp = null;
		try {
			hp = ha.click();
			log.debug(hp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (webClient != null) {
				webClient.close();
			}
		}
		if (hp == null) {
			map.put("message", true);
			return map;
		}
		HtmlInput xm1 = (HtmlInput) hp.getElementById("titleX");
		String val = xm1.getValueAttribute();
		if (val.equals("")) {
			map.put("message", true);
		} else {
			map.put("message", false);
		}
		return map;
	}
}
