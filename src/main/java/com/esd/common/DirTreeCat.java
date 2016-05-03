package com.esd.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.esd.config.BaseConfig;
import com.esd.config.NodeConfig;
import com.esd.config.PageConfig;
import com.esd.stuff.TemplateStuff;
import com.esd.util.Md5;

@SuppressWarnings("rawtypes")
public class DirTreeCat {
	private Map<String, Object> root = new HashMap<String, Object>();
	private static Logger log = Logger.getLogger(DirTreeCat.class);
	List<PageConfig> pcList = new ArrayList<PageConfig>();
	
	
	public static void main(String[] args) throws IOException {
		Connection con = Jsoup.connect("http://www.baic.gov.cn/xxgk/gsjjj/201401/t20140108_1043413.html");
		Document htmlSource = con.get();
		Elements elements = htmlSource.select(".sdmenu div");
		Element e2 = elements.select("div").first();
		System.out.println(e2.select("p").attr("onclick"));
		
	}

	public void download(String catUrl) {
		try {
			Connection con = Jsoup.connect(catUrl);
			Document htmlSource = null;
			try {
				htmlSource = con.get();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			Elements elements = htmlSource.select(".sdmenu");
			// System.out.println(elements.first().tagName());
			// Element e1 = elements.first();
			// Element title = e1.select("a").first();
			Element e2 = elements.select("div").first();
			Element title = e2.select("a").first();
			title.absUrl("href");
			root.put(title.absUrl("href"), e2);
			Elements list = e2.siblingElements();
			for (Element esb : list) {
				Element a = esb.select("a").first();
				String url = a.absUrl("href");
				// System.out.println("sub=====" + a);
				root.put(url, esb);
			}
			// iterator(root);
			List<Map> l = new ArrayList<Map>();
			l.add(root);
			iterator2(l);
			// System.out.println(pcList);

			for (Iterator iterator = pcList.iterator(); iterator.hasNext();) {
				PageConfig p = (PageConfig) iterator.next();
				TemplateStuff ts = new TemplateStuff();// 创建模版填充器
				Document doc1 = null;
				try {
					doc1 = ts.templateStuff(p);
				} catch (IOException e) {
					e.printStackTrace();
				}// 填充后返回代码
				Md5 md5 = new Md5();
				String m = md5.getMd5(new StringBuffer().append(p.getUrl()));
				String path = BaseConfig.HTML_ROOT + File.separator + m + ".html";
				updateHtml(doc1.html(), path);
				// log.debug(p.getUrl() + "-----------" + m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(catUrl + "目录树生成失败!");
		}

	}

	private void iterator2(List list) {
		if (list == null) {
			return;
		}
		List<Map> maplist = new ArrayList<Map>();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			if (map == null || map.isEmpty()) {
				continue;
			}
			for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, Object> it = (Map.Entry<String, Object>) iterator.next();
				if (it == null) {
					continue;
				}
				Element a = (Element) it.getValue();
				// String text = a.select("a").first().text();
				// System.out.println(it.getKey() +
				// "*************************************" + text);
				Map m = dir(a);
				maplist.add(m);
			}

		}
		if (maplist.isEmpty()) {
			return;
		}
		iterator2(maplist);
	}


	private Map dir(Element element) {
		List<NodeConfig> nodeList = new ArrayList<NodeConfig>();

		Map<String, Object> map = new HashMap<String, Object>();
		PageConfig pc = new PageConfig();

		if (element == null) {
			return null;
		}
		Elements title = element.select("a");
		if (title == null) {
			return null;
		}
		if (title.first() == null) {
			return null;
		}
		// System.out.println("====================================================================");
		// System.out.println("title====" + title.first());
		NodeConfig nc = new NodeConfig();
		nc.setAnchorId("#sub_nav_title");
		nc.setSrc(title.first());
		nodeList.add(nc);
		String url = title.first().absUrl("href");
		pc.setUrl(url);
		List<String> list = new ArrayList<String>();
		list.add(url);
		pc.setUrls(list);
		pc.setTemplate(BaseConfig.TEMPLATE_ROOT + File.separator + "dir.html");
//		pc.setOutHtmlPath(BaseConfig.HTML_ROOT);
		Elements subli = element.select("ul li");
		if (subli == null) {
			return null;

		}
		Element sibfirst = subli.first();
		if (sibfirst == null) {
			return null;
		}

		nc = new NodeConfig();
		nc.setAnchorId("#sub_nav_txt");
		Elements sib = sibfirst.siblingElements();
		// System.out.println("sub=====" + sibfirst.select("a").first());
		String href = subli.select("a").first().absUrl("href");
		// System.out.println("sub=====" + sibfirst.select("a").first());
		nc.setSrc(sibfirst.select("a").first());
		nodeList.add(nc);
		map.put(href, sibfirst);
		for (Element esb : sib) {
			nc = new NodeConfig();
			nc.setAnchorId("#sub_nav_txt");
			// System.out.println("sub=====" + esb.select("a").first());
			nc.setSrc(esb.select("a").first());
			href = esb.select("a").first().absUrl("href");
			nodeList.add(nc);
			map.put(href, esb);
		}
		pc.setList(nodeList);
		pcList.add(pc);
		// System.out.println("====================================================================");
		return map;
	}

	private void updateHtml(String content, String templatePath) {
		OutputStreamWriter write = null;
		BufferedWriter bw = null;
		File file = new File(templatePath);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			write = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			bw = new BufferedWriter(write);
			bw.write(content);
			bw.close();
			write.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
				if (write != null) {
					write.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
