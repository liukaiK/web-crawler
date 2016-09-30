package com.esd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esd.common.CatDao;
import com.esd.config.BaseConfig;
import com.esd.config.NodeConfig;
import com.esd.config.PageConfig;
import com.esd.download.EsdDownLoadHtml;
import com.esd.util.Util;

@Controller
@RequestMapping("/admin/core")
public class PageConfigController {

	private static Logger logger = Logger.getLogger(PageConfigController.class);
	private Map<String, Integer> urlMap = new HashMap<String, Integer>(125);
	private int progressCount = 1;
	private int remainCount;// 进度条剩余多少
	private boolean quitFlag = false;
	private CatDao dao = new CatDao();

	/**
	 * 单层采集
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/cating")
	@ResponseBody
	public Map<String, Object> cating(HttpServletRequest request) {
		String url = request.getParameter("url");
		Map<String, Object> map = new HashMap<String, Object>();
		urlMap.clear();
		if (Util.isOutUrl(url)) {// 如果为外链接
			Util.doWithOutUrl(url);
			logger.debug("view finish");
			map.put("message", true);
			return map;
		}
		catPage(url);
		logger.debug("单层采集完成");
		map.put("message", true);
		return map;
	}

	private void catPage(String url) {
		quitFlag = false; // 开启采集状态
		dao.collectPageConfig();
		PageConfig pageConfig = dao.findPageConfig(url);
		pageConfig.setUrl(url);
		EsdDownLoadHtml down = new EsdDownLoadHtml();// 下载
		Document htmlSource = down.downloadHtml(pageConfig);// 下载源代码
		if (htmlSource == null) {
			return;
		}
		Elements links = htmlSource.select("a[href]");
		progressCount = remainCount = links.size();
		dao.singlCat(pageConfig, url);
		for (Element link : links) {
			remainCount = remainCount - 1;
			if (quitFlag == true) {// 开关 退出采集
				return;
			}
			String href = link.attr("abs:href").trim();
			href = dao.filterSuffix(href);
			if (href == null) {
				continue;
			}
			if (Util.isOutUrl(href)) {
				Util.doWithOutUrl(url);
				continue;
			}
			Integer i = urlMap.get(href);
			if (i != null) {
				continue;
			}
			urlMap.put(href, 1);
			pageConfig = dao.findPageConfig(href);
			if (pageConfig != null) {
				dao.singlCat(pageConfig, href);
			} else {
				logger.debug(href);
			}

		}
	}

	@RequestMapping("/getProgressCount")
	@ResponseBody
	public Map<String, Object> getProgressCount(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Double a1 = (Double.valueOf(remainCount) / Double.valueOf(progressCount)) * 100;
		map.put("message", true);
		map.put("g", a1.intValue());
		return map;
	}

	@RequestMapping("/savePgFile")
	@ResponseBody
	public Map<String, Object> savePgFile(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String pgName = request.getParameter("pgName");
		String javaScriptEnabled = request.getParameter("javaScriptEnabled");
		String template = request.getParameter("template");
		String sleep = request.getParameter("sleep");
		String url = request.getParameter("url");
		String[] rules = request.getParameterValues("rules[]");
		String[] urls = request.getParameterValues("urls[]");
		PageConfig pageConfig = new PageConfig();
		pageConfig.setJavaScriptEnabled(Boolean.valueOf(javaScriptEnabled));
		pageConfig.setSleep(Long.valueOf(sleep));
		pageConfig.setUrl(url);
		if (rules != null) {
			for (String s : rules) {
				String[] rule = s.split("&");
				NodeConfig nc = new NodeConfig();
				nc.setDes(rule[0]);
				nc.setParent(rule[1]);
				nc.setTag(rule[2]);
				nc.setType(rule[3]);
				nc.setName(rule[4]);
				nc.setIndex(rule[5]);
				nc.setAnchorId(rule[6]);
				pageConfig.getList().add(nc);
			}
		}
		pageConfig.setTemplate(template);
		List<String> urlList = new ArrayList<String>();
		for (int i = 0; i < urls.length; i++) {
			urlList.add(urls[i]);
		}
		pageConfig.setUrls(urlList);
		/**
		 * 序列化该实体类
		 */
		ObjectOutputStream out = null;
		File file = new File(BaseConfig.PG_ROOT + File.separator + pgName + ".pg");
		try {
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(pageConfig);
			out.close();
			map.put("notice", true);
			map.put("message", pgName + "规则保存成功");
		} catch (Exception e) {
			logger.error(e);
			map.put("notice", false);
			map.put("message", pgName + "规则保存失败");
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return map;
	}

	/**
	 * 获取pageConfig列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadPgFileList")
	@ResponseBody
	public Map<String, Object> loadPgFileList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(BaseConfig.PG_ROOT);
		String[] files = file.list();
		map.put("list", files);
		return map;
	}

	/**
	 * 载入pageConfig文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadPgFile")
	@ResponseBody
	public Map<String, Object> loadPgFile(String pgFileName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(BaseConfig.PG_ROOT + File.separator + pgFileName);
		try {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
			try {
				PageConfig pgFile = (PageConfig) oin.readObject();
				oin.close();
				map.put("pgFile", pgFile);
				map.put("notice", true);
			} catch (ClassNotFoundException e) {
				logger.error(e);
				map.put("notice", false);
				map.put("message", "系统错误!");
			}
		} catch (FileNotFoundException e) {
			logger.error(e);
			map.put("notice", false);
			map.put("message", pgFileName + "文件不存在!");
		} catch (IOException e) {
			logger.error(e);
			map.put("notice", false);
			map.put("message", "系统错误!");
		}
		return map;
	}

	/**
	 * 获取模版列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadTemplateList")
	@ResponseBody
	public Map<String, Object> loadtemplatelist(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(BaseConfig.TEMPLATE_ROOT);
		if (file.isDirectory()) {
			String[] files = file.list();
			map.put("list", files);
		}

		return map;

	}

}
