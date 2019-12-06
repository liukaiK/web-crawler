package com.webcrawler.controller.file;

import com.webcrawler.config.Configure;
import com.webcrawler.config.NodeConfig;
import com.webcrawler.config.PageConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/admin")
public class PgController {

    @RequestMapping("/savePgFile")
    public Map<String, Object> savePgFile(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        String pgName = request.getParameter("pgName");
        String javaScriptEnabled = request.getParameter("javaScriptEnabled");
        String template = request.getParameter("template");
        String sleep = request.getParameter("sleep");
        String url = request.getParameter("url");
        String[] rules = request.getParameterValues("rules[]");
        String[] urls = request.getParameterValues("urls[]");
        PageConfig pageConfig = new PageConfig();
        pageConfig.setJavaScriptEnabled(Boolean.valueOf(javaScriptEnabled));
        pageConfig.setSleep(Long.valueOf(sleep));
        pageConfig.setUrl(url);
        if (rules != null) {
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
        }
        pageConfig.setTemplate(template);
        List<String> urlList = new ArrayList<String>();
        for (int i = 0; i < urls.length; i++) {
            urlList.add(urls[i]);
        }
        pageConfig.setUrls(urlList);
        /**
         * 序列化该实体类
         */
        ObjectOutputStream out = null;
        File file = new File(Configure.PG_ROOT + File.separator + pgName + ".pg");
        try {
            out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(pageConfig);
            out.close();
            map.put("notice", true);
            map.put("message", pgName + "规则保存成功");
        } catch (Exception e) {
            map.put("notice", false);
            map.put("message", pgName + "规则保存失败");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return map;
    }

    @RequestMapping("/deletePgFile")
    public Map<String, Object> deletePgFile(String pgFileName, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        File file = new File(Configure.PG_ROOT + File.separator + pgFileName + ".pg");
        if (file.isFile()) {
            if (file.getAbsoluteFile().delete()) {
                map.put("notice", true);
                map.put("message", pgFileName + "规则文件删除成功!");
            } else {
                map.put("notice", false);
                map.put("message", pgFileName + "规则文件删除失败!");
            }
        } else {
            map.put("notice", false);
            map.put("message", pgFileName + "规则文件删除失败!");
        }
        return map;

    }

    @RequestMapping("/loadPgFileList")
    public Map<String, Object> loadPgFileList(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        File file = new File(Configure.PG_ROOT);
        String[] files = file.list();
        map.put("list", files);
        return map;
    }

    @RequestMapping("/loadPgFile")
    public Map<String, Object> loadPgFile(String pgFileName, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        File file = new File(Configure.PG_ROOT + File.separator + pgFileName);
        try {
            ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
            try {
                PageConfig pgFile = (PageConfig) oin.readObject();
                oin.close();
                map.put("pgFile", pgFile);
                map.put("notice", true);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                map.put("notice", false);
                map.put("message", "系统错误!");
            }
        } catch (FileNotFoundException e) {
            map.put("notice", false);
            map.put("message", pgFileName + "文件不存在!");
        } catch (IOException e) {
            map.put("notice", false);
            map.put("message", "系统错误!");
        }
        return map;
    }


}
