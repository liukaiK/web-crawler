package com.esd.bs.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.common.AutoDiscern;
import com.esd.common.InputStreamUtils;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

/**
 * 意见征集
 * 
 * @author liukai
 * 
 */
@Controller
public class YijianzhengjiController {
	
	private static Logger logger = Logger.getLogger(YijianzhengjiController.class);
	
	WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_8);
	
	/**
	 * 市长信箱
	 * @param email
	 * @param adress
	 * @param title
	 * @param name
	 * @param phone
	 * @param conContent
	 * @param code
	 * @param url
	 * @param request
	 * @return
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	@RequestMapping(value = "/shizhangxinxiang", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> shizhangxinxiang(String email, String adress, String title, String name, String phone, String conContent, String code, String url, HttpServletRequest request) {
		String randomCode = (String) request.getSession().getAttribute("randomCode");
		Map<String, Object> map = new HashMap<String, Object>();
		if (!randomCode.equals(code)) {
			map.put("notice", false);
			map.put("message", "验证码错误!");
			return map;
		}
		try {
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			HtmlPage htmlPage = webClient.getPage(url);
			HtmlPage page = (HtmlPage)htmlPage.getFrames().get(1).getEnclosedPage();
//			System.out.println(page.asXml());
			
			HtmlInput name_input = (HtmlInput) page.getElementById("my:group1/my:姓名");
			name_input.setValueAttribute(name);
			
			HtmlInput phone_input = (HtmlInput) page.getElementById("my:group1/my:联系电话");
			phone_input.setValueAttribute(phone);
			
			HtmlInput adress_input = (HtmlInput) page.getElementById("my:group1/my:地址");
			adress_input.setValueAttribute(adress);
			
			HtmlInput email_input = (HtmlInput) page.getElementById("my:group1/my:邮箱");
			email_input.setValueAttribute(email);
			
			HtmlInput title_input = (HtmlInput) page.getElementById("my:group1/my:留言主题");
			title_input.setValueAttribute(title);
			
			HtmlTextArea content_text = (HtmlTextArea) page.getElementById("my:group1/my:内容");
			content_text.setTextContent(conContent);
			
			HtmlButtonInput submitBtn = (HtmlButtonInput) page.getElementById("SubmitButton");
			HtmlPage hp = submitBtn.click();
//			Thread.sleep(1000);
			if (hp != null) {
//				System.out.println(hp.asXml());
				HtmlImage hi = (HtmlImage) hp.getElementById("VerifyCodeImage");
				// 下载验证码图片
				String yzmNum = getCodeNum(hi);
				HtmlInput yzmInput = (HtmlInput)hp.getElementById("VerifyCode");
				yzmInput.setValueAttribute(yzmNum);
				HtmlButton button = (HtmlButton)page.getElementById("ok-VerifyCodeDialog");
				HtmlPage resPage = button.click();
				if (resPage != null) {
					String text = resPage.asText().substring(0, resPage.asText().length() - 4);
					String pageUrl = resPage.getUrl().toString().trim();
					if (pageUrl.equals("http://218.27.19.186:8182/infogate//collect")) {
						map.put("notice", true);
						map.put("message", text);
						return map;
					}
				} else {
					map.put("notice", false);
					map.put("message", "程序出现错误!请稍后重试");
					return map;
				}
			} else {
				map.put("notice", false);
				map.put("message", "程序出现错误!请稍后重试");
				return map;	
			}	
		} catch (FailingHttpStatusCodeException e) {
			logger.error(e.getMessage());
			map.put("notice", false);
			map.put("message", "连接超时!请稍后重试");
			return map;	
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			map.put("notice", false);
			map.put("message", "程序出现错误!请稍后重试");
			return map;	
		} catch (IOException e) {
			logger.error(e.getMessage());
			map.put("notice", false);
			map.put("message", "程序出现错误!请稍后重试");
			return map;	
		} finally {
			if (webClient != null) {
				webClient.close();
			}
		}
		return map;
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
	public Map<String, Object> yijianzhengji7(String title, String name, String phone, String conContent, String code, String url, HttpServletRequest request) throws InterruptedException {
		Map<String, Object> map = new HashMap<String, Object>();
		String randomCode = (String) request.getSession().getAttribute("randomCode");
		if (!randomCode.equals(code)) {
			map.put("notice", false);
			map.put("message", "验证码错误!");
			return map;
		}
		try {
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			HtmlPage htmlPage = webClient.getPage(url);
			HtmlPage page = (HtmlPage)htmlPage.getFrames().get(1).getEnclosedPage();
			
			HtmlInput title_input = (HtmlInput) page.getElementById("my:组1/my:biaoti");
			title_input.setValueAttribute(title);

			HtmlInput name_input = (HtmlInput) page.getElementById("my:组1/my:xingming");
			name_input.setValueAttribute(name);

			HtmlInput phone_input = (HtmlInput) page.getElementById("my:组1/my:lxdh");
			phone_input.setValueAttribute(phone);

			HtmlTextArea content_text = (HtmlTextArea) page.getElementById("my:组1/my:nr");
			content_text.setTextContent(conContent);

			HtmlButtonInput submitBtn = (HtmlButtonInput) page.getElementById("SubmitButton");
			HtmlPage hp = submitBtn.click();
			if (hp != null) {
				String text = hp.asText().substring(0, hp.asText().length() - 4);
				String pageUrl = hp.getUrl().toString().trim();
				if (pageUrl.equals("http://218.27.19.186:8182/infogate//collect")) {
					map.put("notice", true);
					map.put("message", text);
					return map;
				}
			} else {
				map.put("notice", false);
				map.put("message", "程序出现错误!请稍后重试");
				return map;
			}
		} catch (FailingHttpStatusCodeException e) {
			logger.error(e.getMessage());
			map.put("notice", false);
			map.put("message", "连接超时!请稍后重试");
			return map;	
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			map.put("notice", false);
			map.put("message", "程序出现错误!请稍后重试");
			return map;	
		} catch (IOException e) {
			logger.error(e.getMessage());
			map.put("notice", false);
			map.put("message", "程序出现错误!请稍后重试");
			return map;	
		} finally {
			if (webClient != null) {
				webClient.close();
			}
		}
		return map;
	}
	
	private static String getCodeNum(HtmlImage hi) {
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
		String yzmNum = autoDiscern.discernPic(bs);
		// System.out.println("getCode" + (System.currentTimeMillis() - l));
		return yzmNum;
	}
}
