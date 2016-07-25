package com.esd.stuff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.esd.common.CatDao;
import com.esd.config.BaseConfig;
import com.esd.config.NodeConfig;
import com.esd.config.PageConfig;
import com.esd.util.Md5;

public class TemplateStuff {
	private static Logger log = Logger.getLogger(TemplateStuff.class);

	private String include = "include";

	public TemplateStuff() {
	}

	private Document templateLoad(PageConfig pageConfig) {

		String templateName = pageConfig.getTemplate();
		InputStreamReader read = null;
		BufferedReader br = null;
		File file = new File(BaseConfig.TEMPLATE_ROOT + File.separator + templateName);
		try {
			read = new InputStreamReader(new FileInputStream(file), "utf-8");
			br = new BufferedReader(read);
			String s = null;
			StringBuffer sb = new StringBuffer();
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			read.close();
			Document doc = Jsoup.parse(sb.toString());
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (read != null) {
					read.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return null;

	}

	private Document templateLoad(String path) {
		InputStreamReader read = null;
		BufferedReader br = null;
		try {
			File file = new File(path);
			read = new InputStreamReader(new FileInputStream(file), "utf-8");
			br = new BufferedReader(read);
			String s = null;
			StringBuffer sb = new StringBuffer();
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			Document doc = Jsoup.parse(sb.toString());
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (read != null) {
					read.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		return null;
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
			String path = CatDao.interceptDir(href);
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
			String path = CatDao.interceptDir(href);
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
			Md5 md5 = new Md5();
			String m = md5.getMd5(new StringBuffer().append(end));
			link.attr("content", beg + m + ".html");
		}

	}

	private void headFooter(Document doc, PageConfig pageConfig) {
		String templatePath =BaseConfig.TEMPLATE_ROOT + File.separator + pageConfig.getTemplate();
		int sep = templatePath.lastIndexOf(File.separator);
		String sub = templatePath.substring(0, sep);
		Elements elements = doc.getElementsByTag(include);
		if (elements == null || elements.size() == 0) {// 当没有include标签时退出
			return;
		}
		for (Element element : elements) {
			String src = element.attr("src");
			String path = sub + File.separator + src;
			Document srcDoc = templateLoad(path);
			if (srcDoc == null) {
				log.error("没有找到要包括的模版文件！");
				return;
			}
			element.html(srcDoc.html());
			element.unwrap();
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

	public Document templateStuff(PageConfig pageConfig) throws IOException {
		Document doc = templateLoad(pageConfig);
		List<NodeConfig> list = pageConfig.getList();
		SZFTFilter filter = new SZFTFilter();
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
		headFooter(doc, pageConfig);
		hrefToMd5(doc);
		iframeSrc(doc, pageConfig.getUrl());
		String original_url = pageConfig.getUrl();
		doc.select("#esd_original").attr("href", original_url);
		doc.select("#error").attr("href", original_url);
		return doc;
	}
}
