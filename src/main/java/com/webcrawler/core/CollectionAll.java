package com.webcrawler.core;

import com.esd.config.BaseConfig;
import com.esd.config.PageConfig;
import com.webcrawler.collection.Downloads;
import com.webcrawler.collection.Urls;
import com.webcrawler.common.CatDao;
import com.webcrawler.common.MongoDBUtil;
import com.webcrawler.download.DownLoadHtml;
import com.webcrawler.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Component
public class CollectionAll extends CollectionCore {

    @Resource
    private MongoDBUtil mongoDBUtil;
    @Resource
    private DownLoadHtml downLoadHtml;
    @Resource
    private CatDao catDao;
    private Urls urlsCollection;

    private boolean collectionFlag = true;
    private boolean collectionStatic = true;

    private Thread thread = new A();

    public void start() {
        thread = new A();
        thread.start();
    }

    private class A extends Thread {
        @Override
        public void run() {
            while (collectionFlag == true && collectionStatic == true) {
                collectionStatic = collect();
            }
            mongoDBUtil.dropTable();// 释放资源
            log.info("采集线程结束！！！！！！！！！！！！！！！！！");
        }

    }

    public void init(String domain) {
        collectionFlag = true;
        collectionStatic = true;
        mongoDBUtil.dropTable();
        mongoDBUtil.downloadsInsert(domain);// 插入主页
        for (int i = 0; i < BaseConfig.str.length; i++) {
            mongoDBUtil.downloadsInsert(BaseConfig.str[i]);
        }
        catDao.collectPageConfig();
    }

    public boolean collect() {
        Downloads bson = mongoDBUtil.downloadsFindAndDeleteOne();
        if (bson == null) {
            return false;
        }
        String url = bson.getUrl();
        if (Util.isOutUrl(url)) {
            try {
                Util.doWithOutUrl(url);
            } catch (IOException e) {
                log.error("处理外部链接: " + url + " 失败" + e.getMessage());
            }
            return true;
        }
        PageConfig pageConfig = catDao.findPageConfig(url);
        Document htmlSource = null;
        urlsCollection = new Urls();
        if (pageConfig != null) {
            pageConfig.setUrl(url);
            try {
                htmlSource = downLoadHtml.downloadHtml(pageConfig);
                String title = getTitle(htmlSource);
                Elements links = getLinks(htmlSource);
                for (Element link : links) {
                    String href = link.attr("abs:href").trim();
                    if (href.isEmpty()) {
                        href = link.attr("abs:src").trim();
                    }
                    // 过滤
                    String s = Util.filterSuffix(href);
                    mongoDBUtil.downloadsInsert(s);
                }
                // 插入数据库
                urlsCollection.setUrl(url);
                String md5 = catDao.singlCat(pageConfig, htmlSource);
                mongoDBUtil.urlsInsert(urlsCollection);// 插入Urls表中
                mongoDBUtil.historyInsert(url, title, md5, 1); // 插入history表中
                log.debug(url + "===md5:[" + md5 + "]===template[" + pageConfig.getTemplate() + "]===rule[" + pageConfig.getDb() + ":" + pageConfig.getRule() + "]");
            } catch (IOException e) {
                log.error("===[下载页面源代码: " + url + " 失败]===" + e.getMessage());
                return true;
            } catch (InterruptedException e) {
                log.error("===[下载页面源代码: " + url + " 失败]===" + e.getMessage());
                return true;
            }
        } else {
            try {
                String md5 = Util.do404Page(url);
                mongoDBUtil.historyInsert(url, null, null, 0); // 插入history表中
                log.debug(url + "===md5:[" + md5 + "]");
            } catch (IOException e) {
                log.error("===[处理链接: " + url + " 失败]===" + e.getMessage());
                return true;
            }
        }
        return true;
    }

    public void setCollectionFlag(boolean collectionFlag) {
        this.collectionFlag = collectionFlag;
    }

    public int getTotal() {
        Long downCount = mongoDBUtil.getDownloadsCount();
        Long urlsCount = mongoDBUtil.getUrlsCount();
        if (urlsCount == 0) {
            return 100;
        } else {
            Double a1 = Double.valueOf(urlsCount) / Double.valueOf((downCount + urlsCount)) * 100;
            return a1.intValue();
        }
    }

}
