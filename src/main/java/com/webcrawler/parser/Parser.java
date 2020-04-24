package com.webcrawler.parser;

import com.esd.config.NodeConfig;
import com.esd.config.PageConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Parser {

	/**
	 * 根据抓取规则抓取相应数据，并填充到相应的src中
	 * 
	 * @param htmlSource
	 * @param pageConfig
	 * @return
	 */
	public PageConfig ParserNode(Element htmlSource, PageConfig pageConfig) {
		List<NodeConfig> nodeConfigs = pageConfig.getList();
		for (NodeConfig nodeConfig : nodeConfigs) {
			Element Ele = null;
			String parent = nodeConfig.getParent();
			if (parent != null && parent.isEmpty() == false) {
				String split[] = parent.split(";");
				Elements elements = null;
				elements = htmlSource.select(split[0]);
				if (elements == null || elements.isEmpty()) {
					nodeConfig.setSrc(null);
					continue;
				}
				if (split.length > 1) {
					Integer index = Integer.valueOf(split[1]);
					if (elements.size() > index) {
						Ele = elements.get(index);
					} else {
						continue;
					}
				} else {
					Ele = elements.get(0);
				}
			}
			if (Ele == null) {
				Ele = htmlSource;
			}
			// 根据ID提取网页内容
			if (nodeConfig.getType().equals("id")) {
				// 取id 内容
				Element element = catById(Ele, nodeConfig);
				nodeConfig.setSrc(element);
				continue;
			}
			// 根据class获取内容(class 标签 顺序)
			if (nodeConfig.getType().equals("class")) {
				Element element = catByClass(Ele, nodeConfig);
				nodeConfig.setSrc(element);
				continue;
			}
			// 没有class和id的情况下只能用tag和顺序还定位要抓取容器
			if (nodeConfig.getType().equals("tag")) {
				Element element = catByTag(Ele, nodeConfig);
				nodeConfig.setSrc(element);
				continue;
			}
			if (nodeConfig.getType().equals("text")) {
				Element element = catByText(Ele, nodeConfig);
				nodeConfig.setSrc(element);
				continue;
			}

		}

		return pageConfig;
	}

	/**
	 * 根据id 抓取
	 * 
	 * @param element
	 * @param config
	 * @return
	 */
	private Element catById(Element element, NodeConfig config) {
		Element e = element.getElementById(config.getName());
		// filter(e);// 处理链接为绝对链接
		return e;
	}

	/**
	 * 根据class 抓取
	 * 
	 * @param element
	 * @param config
	 * @return
	 */
	private Element catByClass(Element element, NodeConfig config) {
		// 获取class集合
		Elements elements = element.getElementsByClass(config.getName());
		// 如何有指定标签，获取标签集合
		if (config.getTag() != null) {
			elements = elements.tagName(config.getTag());
		}

		// -1代表所有
		if (config.getIndex() == -1) {
			Document doc = Jsoup.parse(elements.toString(), element.baseUri());
			return doc;
		}
		// 如何指定顺序，获取顺序，默认为0
		if (elements != null && elements.size() > 0) {
			Element e = elements.get(config.getIndex());
			return e;
		}
		// 处理A 链接
		// filter(e);// 处理链接为绝对链接
		return null;
	}

	/**
	 * 根据标签采集
	 * 
	 * @param element
	 * @param config
	 * @return
	 */
	private Element catByTag(Element element, NodeConfig config) {
		// 获取class集合
		Elements elements = element.select(config.getTag());
		if (elements == null) {
			return null;
		}
		if (elements.size() == 0) {
			return null;
		}
		// -1代表所有
		if (config.getIndex() == -1) {
			Document doc = Jsoup.parse(elements.toString(), element.baseUri());
			return doc;
		}

		// 如何指定顺序，获取顺序，默认为0
		if (elements.size() > config.getIndex()) {
			Element e = elements.get(config.getIndex());
			return e;
		}
		// 处理A 链接
		// filter(e);// 处理链接为绝对链接
		return null;
	}

	/**
	 * 文本节点采集
	 * 
	 * @param element
	 * @param config
	 * @return
	 */
	private Element catByText(Element element, NodeConfig config) {
		// 获取class集合
		Elements elements = element.select(config.getTag());
		if (elements == null) {
			return null;
		}
		if (elements.size() == 0) {
			return null;
		}
		// -1代表所有
		if (config.getIndex() == -1) {
			Document doc = Jsoup.parse(elements.toString(), element.baseUri());
			return doc;
		}

		// 如何指定顺序，获取顺序，默认为0
		if (elements.size() > config.getIndex()) {
			Element e = elements.get(config.getIndex());
			List<TextNode> list = e.textNodes();
			if (list == null || list.size() == 0) {
				return null;
			}
			TextNode tn = list.get(config.getIndex());
			String t = tn.text().trim();
			if (t != null && t.equals("") == false) {
				Document doc = Jsoup.parse(t, element.baseUri());
				return doc;
			}
		}
		// 处理A 链接
		// filter(e);// 处理链接为绝对链接
		return null;
	}

}
