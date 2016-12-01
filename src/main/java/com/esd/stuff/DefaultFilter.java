package com.esd.stuff;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DefaultFilter {

	public void filter(Element element) {
		filterTitle(element);
		removeStyle(element);
		removeJavascript(element);
		filterMeta(element);
		filterHref(element);
		filterSrc(element);
		filterImg(element);
	}

	/**
	 * Meta的相对路径替换为绝对路径
	 * 
	 * @param element
	 */
	private void filterMeta(Element element) {
		if (element == null) {
			return;
		}

		Elements links = element.select("meta");
		for (Element link : links) {
			Element content = link.attr("http-equiv", "refresh");
			String con = content.attr("content");
			String beg = con.substring(0, con.indexOf("=") + 1);
			String end = con.substring(con.indexOf("/") + 1);
			content.attr("content", beg + element.baseUri() + end);
		}

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

	/**
	 * 删除所有时间
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
//			String alt = link.attr("alt").trim();
//			String title = link.attr("title").trim();
//			if (alt.isEmpty() || alt.equals("")) {
//				alt = "图片";
//			}
//			if (title.isEmpty() || title.equals("")) {
//				title = "图片";
//			}
			
			String alt = "图片", title = "图片";
			link.attr("alt", alt);
			link.attr("title", title);
		}
	}
	
//	private String alt2title(Element link) {
//		Elements childs = link.children();
//		if (childs == null || childs.size() == 0) {
//			return null;
//		}
//		for (Element element : childs) {
//			if (element.tagName().equals("img")) {
//				String alt = element.attr("alt");
//				if (alt != null) {
//					return alt;
//				} else {
//					String title = element.attr("title");
//					if (title != null) {
//						return title;
//					}
//				}
//				return "图片链接";
//			}
//		}
//		return "空链接";
//	}

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
