package com.webcrawler.stuff;

import com.esd.config.Configure;
import com.esd.config.NodeConfig;
import com.esd.config.PageConfig;
import com.webcrawler.filter.Filter;
import com.webcrawler.util.Md5;
import com.webcrawler.util.Util;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

@Component
public class TemplateStuff {
//	private static Logger log = Logger.getLogger(TemplateStuff.class);

	private String include = "include";

	@Resource
	private Filter filter;

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
			String path = Util.interceptDir(href);
			link.attr("href", path);
		}
		pageMd5(doc);
		md5URL(doc);

	}

	private void pageMd5(Document doc) {
		Element element = doc.getElementById("page");
		if (element == null) {
			return;
		}
		Elements elements = element.select("option[value]");
		for (Element link : elements) {
			String href = link.attr("value").trim();
			String path = Util.interceptDir(href);
			link.attr("value", path);
		}
	}

	/**
	 * 处理meta中跳转链接 进行md5加密
	 */
	private void md5URL(Document doc) {
		// 0;URL=http://www.szft.gov.cn/zf/ftxx/xwdt/
		Elements links = doc.select("meta[http-equiv=refresh]");
		for (Element link : links) {
			String con = link.attr("content");
			String beg = con.substring(0, con.indexOf("=") + 1);
			String end = con.substring(con.indexOf("=") + 1);
			String m = Md5.getMd5(new StringBuffer().append(end));
			link.attr("content", beg + m + ".html");
		}

	}

	private void headFooter(Document doc, PageConfig pageConfig) throws IOException {
		String templatePath = Configure.TEMPLATE_ROOT + File.separator + pageConfig.getTemplate();
		int sep = templatePath.lastIndexOf(File.separator);
		String sub = templatePath.substring(0, sep);
		Elements elements = doc.getElementsByTag(include);
		if (elements == null || elements.size() == 0) {// 当没有include标签时退出
			return;
		}
		for (Element element : elements) {
			String src = element.attr("src");
			String path = sub + File.separator + src;
			Document srcDoc = Util.downLoadTemple(path);
			element.html(srcDoc.html());
			element.unwrap();
		}
	}

//	private void iframeSrc(Element element, String src) {
//		Elements elements = element.select("iframe");
//		for (Element e : elements) {
//			if (e.tagName().equals("iframe")) {
//				e.attr("src", src);
//			}
//		}
//	}

	public Document templateStuff(PageConfig pageConfig) throws IOException {
		Document templateContent = Util.downLoadTemple(pageConfig);
		for (NodeConfig nodeConfig: pageConfig.getList()) {
			if (nodeConfig.getSrc() == null) {
				continue;
			}
			String anchorId = nodeConfig.getAnchorId();
			if (anchorId != null) {
				// 锚点为Id
				if (anchorId.startsWith("#")) {
					// log.debug("锚点: " + anchorId + " 为id");
					Element element_id = templateContent.getElementById(nodeConfig.getAnchorId().substring(1));
					if (element_id != null) {
						filter.filter(nodeConfig.getSrc());
						element_id.append(nodeConfig.getSrc().outerHtml());
					}
				} else if (anchorId.startsWith(".")) {// 锚点为class
					Elements elements_class = templateContent.getElementsByClass(nodeConfig.getAnchorId().substring(1));
					if (elements_class.size() > 0) {
						// log.debug("锚点: " + anchorId + " 为class");
						for (Element ele_class : elements_class) {
							filter.filter(nodeConfig.getSrc());
							ele_class.append(nodeConfig.getSrc().outerHtml());
						}
					}
				} else if (!anchorId.startsWith(".") && !anchorId.startsWith("#")) {// 锚点为标签
					Elements element_tag = templateContent.getElementsByTag(nodeConfig.getAnchorId());
					if (element_tag.size() > 0) {
						// log.debug("锚点: " + anchorId + " 为tag");
						for (Element ele_tag : element_tag) {
							filter.filter(nodeConfig.getSrc());
							ele_tag.append(nodeConfig.getSrc().outerHtml());
						}
					}
				} else {
					// log.error(pageConfig.getUrl() + " > get:" +
					// it.getAnchorId() + "----------------error");
				}
			}
		}
		headFooter(templateContent, pageConfig);
		hrefToMd5(templateContent);
//		iframeSrc(doc, pageConfig.getUrl());
		String original_url = pageConfig.getUrl();
		templateContent.select("#esd_original").attr("href", original_url);
		templateContent.select("#error").attr("href", original_url);
		return templateContent;
	}
}
