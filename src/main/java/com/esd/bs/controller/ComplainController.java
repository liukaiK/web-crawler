package com.esd.bs.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.esd.config.BaseConfig;
import com.esd.entity.other.Complain;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

/**
 * 我要投诉
 * 
 * @author liukai
 * 
 */
@Controller
public class ComplainController {
	private static Logger logger = Logger.getLogger(ComplainController.class);

	private WebClient webClient = null;

	private final String URL = "http://www.caacts.org.cn:8080/struts2_spring3_hibernate3_1.0/tsTianjia.action";

	public ComplainController() {
		webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
	}
	
	@RequestMapping(value = "/addComplain", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String addComplain(Complain complain, String certicode, @RequestParam MultipartFile[] myfiles, HttpServletRequest request) throws InterruptedException {
		String randomCode = (String) request.getSession().getAttribute("randomCode");
		if (!randomCode.equals(certicode)) {
			return "验证码错误!";
		}
		HtmlPage page1 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		try {
			page1 = webClient.getPage(URL);
			HtmlSelect provinceSelect = (HtmlSelect) page1.getElementById("province");
			provinceSelect.setSelectedAttribute(complain.getConTarget(), true);
			Thread.sleep(500);
			
			HtmlSelect citySelect = (HtmlSelect) page1.getElementById("city");
			citySelect.setSelectedAttribute(complain.getConUnit(), true);
			Thread.sleep(500);
			
			HtmlSelect city1Select = (HtmlSelect) page1.getElementById("city1");
			city1Select.setSelectedAttribute(complain.getConType(), true);
			
			HtmlSelect cptFile_passengerType = (HtmlSelect) page1.getElementById("cptFile_passengerType");
			cptFile_passengerType.setSelectedAttribute(complain.getPassengerType(), true);
			
			HtmlInput passengerName = (HtmlInput) page1.getElementsByName("cptFile.passengerName").get(0);
			passengerName.setValueAttribute(complain.getPassengerName());
			
			HtmlSelect zhengjianid = (HtmlSelect) page1.getElementsByName("cptFile.zhengjianid").get(0);
			zhengjianid.setSelectedAttribute(complain.getZhengjianid(), true);
			
			HtmlInput shenfenId = (HtmlInput) page1.getElementsByName("cptFile.shenfenId").get(0);
			shenfenId.setValueAttribute(complain.getShenfenId());
			
			HtmlInput flightNo = (HtmlInput)page1.getElementsByName("cptFile.flightNo").get(0);
			flightNo.setValueAttribute(complain.getFlightNo());
			
			HtmlInput flightTime = (HtmlInput)page1.getElementsByName("cptFile.flightTime").get(0);
			flightTime.setValueAttribute(complain.getFlightTime());
			
			HtmlInput passengerTel = (HtmlInput)page1.getElementsByName("cptFile.passengerTel").get(0);
			passengerTel.setValueAttribute(complain.getPassengerTel());			
			
			HtmlInput email = (HtmlInput)page1.getElementsByName("cptFile.Email").get(0);
			email.setValueAttribute(complain.getEmail());	
			
			HtmlSelect depPort = (HtmlSelect)page1.getElementById("cptFile_depPort");
			depPort.setSelectedAttribute(complain.getDepPort(), true);
			
			HtmlSelect arrPort = (HtmlSelect)page1.getElementById("cptFile_arrPort");
			arrPort.setSelectedAttribute(complain.getArrPort(), true);			
			
			HtmlTextArea cptContent = (HtmlTextArea)page1.getElementsByName("cptFile.cptContent").get(0);
			cptContent.setTextContent(complain.getCptContent());
			for (int i = 0; i <= 4; i++) {
				if (myfiles[i].isEmpty()) {
					continue;
				} else {
					String originalFilename = myfiles[i].getOriginalFilename();
					try {
						FileUtils.copyInputStreamToFile(myfiles[i].getInputStream(), new File(BaseConfig.TEMP_ROOT, originalFilename));
						HtmlFileInput uploadFile = (HtmlFileInput) page1.getElementsByName("uploadFile").get(i);
						uploadFile.setValueAttribute(BaseConfig.TEMP_ROOT + File.separator + originalFilename);
					} catch (IOException e) {
						System.out.println("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
						e.printStackTrace();
					}
				}

			}
			
			HtmlAnchor submitBtn = (HtmlAnchor) page1.getElementById("submitBtn");
			HtmlPage hp = submitBtn.click();
			HtmlButton tijiaoButton = (HtmlButton) page1.getElementsByTagName("button").get(0);
			hp = tijiaoButton.click();
			if (hp != null) {
				String pageUrl = hp.getUrl().toString().trim();
				if (pageUrl.equals("http://www.caacts.org.cn:8080/struts2_spring3_hibernate3_1.0/add.action")) {
					return "提交成功!";
				}
			}
		} catch (FailingHttpStatusCodeException e) {
			logger.error(e.getMessage());
		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return "提交失败,请稍后再试!";
	}
	
	
	
	
	
	
}
