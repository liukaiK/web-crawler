package com.webcrawler.controller;

import com.esd.config.Configure;
import com.esd.config.NodeConfig;
import com.esd.config.PageConfig;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.webcrawler.download.DownLoadHtml;
import com.webcrawler.parser.Parser;
import com.webcrawler.stuff.TemplateStuff;
import com.webcrawler.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/test")
public class TestConfigController {

    @Resource
    private DownLoadHtml downLoadHtml;

    @Resource
    private TemplateStuff templateStuff;

    @Resource
    private Parser parser;

    /**
     * 抓取预览方法
     *
     * @param request
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
    @RequestMapping("/view")
    @ResponseBody
    public Map<String, Object> view(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String url = request.getParameter("url");
        String templateName = request.getParameter("template");
        if (Util.isOutUrl(url)) {// 网址为外部链接
            try {
                Util.doWithOutUrl(url);
                map.put("notice", true);
                map.put("message", "预览成功!");
            } catch (IOException e) {
                map.put("notice", false);
                map.put("message", "预览失败!");
            }
        } else {
            String javaScriptEnabled = request.getParameter("javaScriptEnabled");
            String sleep = request.getParameter("sleep");
            String[] rules = request.getParameterValues("rules[]");
            PageConfig pageConfig = new PageConfig();
            pageConfig.setJavaScriptEnabled(Boolean.valueOf(javaScriptEnabled));
            pageConfig.setSleep(Long.valueOf(sleep));
            pageConfig.setUrl(url);
            for (String s : rules) {
                String[] rule = s.split("&");
                NodeConfig nc = new NodeConfig();
                nc.setDes(rule[0]);
                nc.setParent(rule[1]);
                nc.setTag(rule[2]);
                nc.setType(rule[3]);
                nc.setName(rule[4]);
                nc.setIndex(rule[5]);
                nc.setAnchorId(rule[6]);
                pageConfig.getList().add(nc);
            }
            Document htmlSource;
            try {
                try {
                    htmlSource = downLoadHtml.downloadHtml(pageConfig);
                    pageConfig = parser.ParserNode(htmlSource, pageConfig);
                } catch (InterruptedException e) {
                }
                pageConfig.setTemplate(templateName);
                Document templateDoc = templateStuff.templateStuff(pageConfig);
                Util.createNewFile(templateDoc.html(), Configure.TEST_ROOT + File.separator + "view.html");
                map.put("notice", true);
                map.put("message", "预览成功!");
            } catch (FailingHttpStatusCodeException e) {
                map.put("notice", false);
                map.put("message", e.getMessage());
            } catch (MalformedURLException e) {
                map.put("notice", false);
                map.put("message", e.getMessage());
            } catch (IOException e) {
                map.put("notice", false);
                map.put("message", e.getMessage());
            }
        }
        return map;
    }
}
