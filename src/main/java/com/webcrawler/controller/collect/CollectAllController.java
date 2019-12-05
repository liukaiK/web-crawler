package com.webcrawler.controller.collect;

import com.webcrawler.core.CollectionAll;
import com.webcrawler.core.CollectionParmary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin")
public class CollectAllController {

    @Resource
    private CollectionAll CollectionAll;

    @Resource
    private CollectionParmary collectionParmary;

    @RequestMapping("/catingAll")
    @ResponseBody
    public Map<String, Object> catingAll(@RequestParam(required = true) String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (url == null || url.trim().isEmpty()) {
            map.put("notice", false);
            map.put("message", "链接不能为空!");
        } else {
            CollectionAll.init(url);
            CollectionAll.start();
            map.put("notice", true);
            map.put("message", "整站采集开始!");
        }
        return map;
    }

    @RequestMapping("/cancelCating")
    @ResponseBody
    public Map<String, Object> cancelCating(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        CollectionAll.setCollectionFlag(false);
        collectionParmary.setCollectStatic(false);
        map.put("notice", true);
        map.put("message", "取消采集成功!");
        log.debug("Collection cancel");
        return map;
    }

    @RequestMapping("/getdbCount")
    @ResponseBody
    public Map<String, Object> getDownload(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        int total = CollectionAll.getTotal();
        map.put("notice", true);
        map.put("message", total);
        return map;
    }
}
