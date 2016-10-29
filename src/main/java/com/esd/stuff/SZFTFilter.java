package com.esd.stuff;

import java.util.List;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class SZFTFilter extends DefaultFilter {

	@Override
	public void filter(Element element) {
		// TODO Auto-generated method stub
		super.filter(element);
		removeAttr(element);
		filterOption(element);
		textWrapSpan(element);
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

	private void filterOption(Element element) {
		if (element == null) {
			return;
		}
		Elements links = element.select("option");
		for (Element link : links) {
			String option = link.attr("abs:value").trim();
			if (option.lastIndexOf(".html") != -1) {
				option = option.replace("./", "");
				if (option.isEmpty() || option.equals("")) {
					continue;
				}
				if (option.endsWith("#")) {
					option = option.substring(0, option.length() - 1);
				}
				link.attr("value", option);
			}
		}
	}
}
