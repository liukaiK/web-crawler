package com.esd.stuff;

import org.jsoup.nodes.Element;

public class SrcFilter {
	public void filter(Element element) {
		// removeStyle(element);
		// removeJavascript(element);
		// filterHref(element);
		// filterSrc(element);
	}

	/**
	 * 删除所有样式
	 * 
	 * @param element
	 */
//	private void removeStyle(Element element) {
//		Elements styles = element.select("style");
//		for (Element style : styles) {
//			style.remove();
//		}
//		styles = element.select("[style]");
//		for (Element style : styles) {
//			style.removeAttr("style");
//		}
//	}

	/**
	 * 删除所有时间
	 * 
	 * @param element
	 */
//	private void removeJavascript(Element element) {
//		Elements styles = element.select("script");
//		for (Element style : styles) {
//			style.remove();
//		}
//	}

	/**
	 * 处理a 链接的href 替换绝对路径
	 * 
	 * @param element
	 */
//	private void filterHref(Element element) {
//		Elements links = element.select("a");
//		for (Element link : links) {
//			String linkHref = link.attr("abs:href").trim();
//			linkHref = linkHref.replace("./", "");
//			if (linkHref.isEmpty() || linkHref.equals("")) {
//				continue;
//			}
//			if (linkHref.endsWith("#")) {
//				linkHref = linkHref.substring(0, linkHref.length() - 1);
//			}
//			link.attr("href", linkHref);
//		}
//	}

	/**
	 * 处理src资源 替换绝对路径
	 * 
	 * @param element
	 */
//	private void filterSrc(Element element) {
//		Elements links = element.select("img");
//		for (Element link : links) {
//			String linkHref = link.attr("abs:src").trim();
//			linkHref = linkHref.replace("./", "");
//			if (linkHref.isEmpty() || linkHref.equals("")) {
//				continue;
//			}
//			if (linkHref.endsWith("#")) {
//				linkHref = linkHref.substring(0, linkHref.length() - 1);
//			}
//			link.attr("src", linkHref);
//		}
//	}

}
