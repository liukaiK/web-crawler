package com.esd.stuff;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.esd.common.MongoDBUtil;
import com.esd.config.NodeConfig;
import com.esd.config.PageConfig;
import com.esd.util.Util;

public class TemplateStuff {
	private static Logger logger = Logger.getLogger(TemplateStuff.class);

	private String include = "include";

	public TemplateStuff() {
	}

	/**
	 * 把所有链接处理成md5
	 * 
	 * @param doc
	 */
	private void hrefToMd5(Document doc) {
		String static_url = "static:";
		Elements elements = doc.select("a[href]");
		for (Element link : elements) {
			String href = link.attr("href").trim();
			if (href.startsWith(static_url) == true) {
				String replace = href.replaceAll(static_url, "");
				link.attr("href", replace);
				// link.attr("target", "_blank");
				continue;
			}
			//String path = Util.interceptUrl(href);
			//link.attr("href", path);
			//2016-10-20 加访问方法名传递参数
			String fileName =  Util.interceptUrl(href);
			String path = "showPage?fileName="+fileName.split("\\.")[0];
			link.attr("href", path);
		}
		pageMd5(doc);
	}

	private void pageMd5(Document doc) {
		Element element = doc.getElementById("page");
		if (element == null) {
			return;
		}
		Elements elements = element.select("option[value]");
		for (Element link : elements) {
			String href = link.attr("value").trim();
			String path = Util.interceptUrl(href);
			link.attr("value", path);
		}
	}

	private void headFooter(Document doc, PageConfig pageConfig,String siteId,MongoDBUtil mongoDBUtil) {
//		String templatePath =BaseConfig.TEMPLATE_ROOT + File.separator + pageConfig.getTemplate();
//		int sep = templatePath.lastIndexOf(File.separator);
//		String sub = templatePath.substring(0, sep);
		Elements elements = doc.getElementsByTag(include);
		if (elements == null || elements.size() == 0) {// 当没有include标签时退出
			return;
		}
		for (Element element : elements) {
			String src = element.attr("src");
			//String path = sub + File.separator + src;
			Document srcDoc;
			try {
				//2016-11-9 cx 从mongodb中取template
				srcDoc = Util.loadTemplate(src,siteId,2,mongoDBUtil);
				if (srcDoc == null) {
					logger.error("没有找到要包括的模版文件！");
					return;
				}
				element.html(srcDoc.html());
				element.unwrap();
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}

	private void iframeSrc(Element element, String src) {
		Elements elements = element.select("iframe");
		for (Element e : elements) {
			if (e.tagName().equals("iframe")) {
				e.attr("src", src);
			}
		}
	}

	public Document templateStuff(PageConfig pageConfig,String siteId,MongoDBUtil mongoDBUtil) throws IOException {
		//Document doc = Util.loadTemplate(BaseConfig.TEMPLATE_ROOT + File.separator + pageConfig.getTemplate());
		Document doc = Util.loadTemplate(pageConfig.getTemplate(),siteId,2,mongoDBUtil);
		List<NodeConfig> list = pageConfig.getList();
		DefaultFilter filter = new DefaultFilter();
		for (Iterator<NodeConfig> iterator = list.iterator(); iterator.hasNext();) {
			NodeConfig it = (NodeConfig) iterator.next();
			if (it.getSrc() == null) {
				// log.error(pageConfig.getUrl() + " > get:" + it.getAnchorId()
				// + "----------------error");
				continue;
			}
			String anchorId = it.getAnchorId();
			if (anchorId != null || !"".equals(anchorId)) {
				// 锚点为Id
				if (anchorId.startsWith("#")) {
					// log.debug("锚点: " + anchorId + " 为id");
					Element element_id = doc.getElementById(it.getAnchorId().substring(1));
					if (element_id != null) {
						filter.filter(it.getSrc());
						element_id.append(it.getSrc().outerHtml());
					}
				} else if (anchorId.startsWith(".")) {// 锚点为class
					Elements elements_class = doc.getElementsByClass(it.getAnchorId().substring(1));
					if (elements_class.size() > 0) {
						// log.debug("锚点: " + anchorId + " 为class");
						for (Element ele_class : elements_class) {
							filter.filter(it.getSrc());
							ele_class.append(it.getSrc().outerHtml());
						}
					}
				} else if (!anchorId.startsWith(".") && !anchorId.startsWith("#")) {// 锚点为标签
					Elements element_tag = doc.getElementsByTag(it.getAnchorId());
					if (element_tag.size() > 0) {
						// log.debug("锚点: " + anchorId + " 为tag");
						for (Element ele_tag : element_tag) {
							filter.filter(it.getSrc());
							ele_tag.append(it.getSrc().outerHtml());
						}
					}
				} else {
					// log.error(pageConfig.getUrl() + " > get:" +
					// it.getAnchorId() + "----------------error");
				}
			}
		}
		headFooter(doc, pageConfig,siteId,mongoDBUtil);
		hrefToMd5(doc);
		iframeSrc(doc, pageConfig.getUrl());
		String original_url = pageConfig.getUrl();
		doc.select("#esd_original").attr("href", original_url);
		doc.select("#error").attr("href", original_url);
		return doc;
	}
}
