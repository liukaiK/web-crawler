package com.crawler.stuff;

import java.util.List;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class DefaultFilter {

	public void filter(Element element) {
		filterTitle(element);
		removeStyle(element);
		removeAttr(element);
		removeJavascript(element);
		textWrapSpan(element);
		filterHref(element);
		filterSrc(element);
		filterImg(element);
	}

	/**
	 * 删除所有样式
	 * 
	 * @param element
	 */
	private void removeStyle(Element element) {
		if (element == null) {
			return;
		}
		if (element.tagName().equals("iframe")) {
			return;
		}
		Elements styles = element.select("style");
		for (Element style : styles) {
			style.remove();
		}
		styles = element.select("[style]");
		for (Element style : styles) {
			style.removeAttr("style");
		}
	}

	private void textWrapSpan(Element element) {
		if (element == null) {
			return;
		}

		Elements elements = element.select("*");
		for (Element e : elements) {
			if (e.tagName().equals("a") && e.childNodeSize() == 0) {
				String t = e.text().trim();
				if (t == null || t.equals("")) {
					e.remove();
					continue;
				}
			}

			if (e.hasText()) {
				if (e.tagName().equals("a") || e.tagName().equals("p") || e.tagName().equals("span")) {
					continue;
				}
				List<Node> list = e.childNodes();
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
						// node.nodeName());
						node.wrap("<span></span>");
					}
				}

			}
		}

	}
	
	private void removeAttr(Element element) {
		if (element == null) {
			return;
		}
		if (element.tagName().equals("iframe")) {
			return;
		}
		Elements cs = element.select("*");
		String[] filerAttr = { "alt", "title", "src", "href", "type", "value", "rowspan", "colspan", "http-equiv", "content", "flashvars" };
		for (Element c : cs) {
			List<Attribute> list = c.attributes().asList();
			for (Attribute attribute : list) {
				String key = attribute.getKey();
				boolean b = false;
				for (String s : filerAttr) {
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

	/**
	 * 删除所有事件
	 * 
	 * @param element
	 */
	private void removeJavascript(Element element) {
		if (element == null) {
			return;
		}
		Elements scripts = element.select("script");
		if (scripts == null) {
			return;
		}
		for (Element script : scripts) {
			if (script != null) {
				script.remove();
			}
		}
	}

	/**
	 * 处理a 链接的href 替换绝对路径
	 * 
	 * @param element
	 */
	private void filterHref(Element element) {
		if (element == null) {
			return;
		}
		Elements links = element.select("a[href]");
		for (Element link : links) {
			String linkHref = link.attr("abs:href").trim();
			linkHref = linkHref.replace("./", "");
			if (linkHref.isEmpty() || linkHref.equals("")) {
				continue;
			}
			if (linkHref.endsWith("#")) {
				linkHref = linkHref.substring(0, linkHref.length() - 1);
			}
			link.attr("href", linkHref);
		}
	}

	private void filterImg(Element element) {
		if (element == null) {
			return;
		}
		Elements links = element.select("img");
		for (Element link : links) {
			String alt = link.attr("alt").trim();
			String title = link.attr("title").trim();
			if (alt.isEmpty() || alt.equals("")) {
				alt = "图片";
			}
			if (title.isEmpty() || title.equals("")) {
				title = "图片";
			}
			link.attr("alt", alt);
			link.attr("title", title);
		}
	}
	
	/**
	 * 处理a 链接的title 替换绝对路径
	 * 
	 * @param element
	 */
	private void filterTitle(Element element) {
		if (element == null) {
			return;
		}
		Elements links = element.select("a[href]");
		for (Element link : links) {
			String text = link.text().trim();
			String title = link.attr("title").trim();
			if (title == null || title.isEmpty() || title.equals("")) {// 标题为空
				if (text == null || text.equals("") || text.isEmpty()) { // 文本为空
					// String alt = alt2title(link);// 返回的为空。
					link.attr("title", text);
				}
				link.attr("title", text);
			} else {
				link.text(title);
			}
		}
	}

	/**
	 * 处理src资源 替换绝对路径
	 * 
	 * @param element
	 */
	private void filterSrc(Element element) {
		if (element == null) {
			return;
		}
		Elements links = element.select("img,embed");
		for (Element link : links) {
			String linkHref = link.attr("abs:src").trim();
//			String alt = link.attr("alt").trim();
			
			
			
			linkHref = linkHref.replace("./", "");
			if (linkHref.isEmpty() || linkHref.equals("")) {
				continue;
			}
			if (linkHref.endsWith("#")) {
				linkHref = linkHref.substring(0, linkHref.length() - 1);
			}
			link.attr("src", linkHref);
		}
	}

}
