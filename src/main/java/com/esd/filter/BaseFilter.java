package com.esd.filter;

import java.util.List;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.esd.config.BaseConfig;

@Component()
public class BaseFilter {
	
	public void filter(Element element) {
		filterTitle(element);// 处理a链接的title属性
		removeStyle(element);
		removeJavascript(element);
		removeAttr(element);
		filterHref(element);
		filterSrc(element);
		filterImg(element);
		filterOption(element);
		textWrapSpan(element);
	}

	private void filterOption(Element element) {
		Elements links = element.select("option");
		for (Element link : links) {
			String option = link.attr("abs:value").trim();
			if (option.lastIndexOf(".html") != -1) {
				option = option.replace("./", "");
				if (option.isEmpty()) {
					continue;
				}
				if (option.endsWith("#")) {
					option = option.substring(0, option.length() - 1);
				}
				link.attr("value", option);
			}
		}
	}
	
	private void textWrapSpan(Element element) {
		Elements elements = element.getAllElements();
		for (Element ele : elements) {
			if (ele.tagName().equals("a") && ele.childNodeSize() == 0) {
				String text = ele.text().trim();
				if (text == null || text.isEmpty()) {
					ele.remove();
					continue;
				}
			}
			if (ele.hasText()) {
				if (ele.tagName().equals("a") || ele.tagName().equals("p") || ele.tagName().equals("span")) {
					continue;
				}
				List<Node> list = ele.childNodes();
				for (Node node : list) {
					if (node.childNodeSize() > 0) {// 有子节点不处理
						continue;
					}
					String outerHtml = node.outerHtml().trim();
					if (node.nodeName().equals("#text") == false) {// 文本节点
						continue;
					}
					if (outerHtml.indexOf("&") != -1) {// 非转意符
						continue;
					}
					if (outerHtml.trim().equals("") == false) {
						node.wrap("<span></span>");
					}
				}

			}
		}

	}
	
	private void filterTitle(Element element) {
		Elements links = element.select("a[href]");
		for (Element link : links) {
			String text = link.text().trim();
			String title = link.attr("title").trim();
			if (title == null || title.isEmpty()) {
				link.attr("title", text);
			} else {
				link.text(title);
			}
		}
	}
	
	private void removeStyle(Element element) {
		Elements styles = element.select("style");
		for (Element style : styles) {
			style.remove();
		}
	}

	private void removeJavascript(Element element) {
		Elements scripts = element.select("script");
		for (Element script : scripts) {
			script.remove();
		}
	}

	private void filterHref(Element element) {
		Elements links = element.select("a[href]");
		for (Element link : links) {
			String href = link.attr("abs:href").trim();
			href = href.replace("./", "");
			if (href.isEmpty()) {
				continue;
			}
			if (href.endsWith("#")) {
				href = href.substring(0, href.length() - 1);
			}
			link.attr("href", href);
		}
	}

	private void filterImg(Element element) {
		Elements links = element.select("img");
		for (Element link : links) {
			String alt = link.attr("alt").trim();
			String title = link.attr("title").trim();
			if (alt.isEmpty()) {
				alt = "图片";
			}
			if (title.isEmpty()) {
				title = "图片";
			}
			link.attr("alt", alt);
			link.attr("title", title);
		}
	}
	
	private void filterSrc(Element element) {
		Elements links = element.select("img,embed");
		for (Element link : links) {
			String linkHref = link.attr("abs:src").trim();
			linkHref = linkHref.replace("./", "");
			if (linkHref.isEmpty()) {
				continue;
			}
			if (linkHref.endsWith("#")) {
				linkHref = linkHref.substring(0, linkHref.length() - 1);
			}
			link.attr("src", linkHref);
		}
	}

	private void removeAttr(Element element) {
		Elements elements = element.getAllElements();
		for (Element c : elements) {
			List<Attribute> list = c.attributes().asList();
			for (Attribute attribute : list) {
				String key = attribute.getKey();
				boolean b = false;
				for (String s : BaseConfig.filerAttr) {
					if (key.equals(s)) {
						b = true;
						break;
					}
				}
				if (b == false) {
					c.removeAttr(attribute.getKey());
				}
			}

		}
	}
	
}
