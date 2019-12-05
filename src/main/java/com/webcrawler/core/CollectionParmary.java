package com.webcrawler.core;

import com.webcrawler.collection.Downloads;
import com.webcrawler.collection.Urls;
import com.webcrawler.common.CatDao;
import com.webcrawler.common.MongoDBUtil;
import com.webcrawler.config.PageConfig;
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
public class CollectionParmary extends CollectionCore {

    @Resource
    private MongoDBUtil mongoDBUtil;
    @Resource
    private DownLoadHtml downLoadHtml;
    @Resource
    private CatDao catDao;
    private Urls urlsCollection;
    private boolean collectStatic = true;
    private boolean ctrl = true;

    private Thread thread = new Collect();

    public void start() {
        thread = new Collect();
        thread.start();
    }

    private class Collect extends Thread {

        @Override
        public void run() {
            init();
            while (collectStatic && ctrl) {
                ctrl = collect();
            }
            mongoDBUtil.dropTable();// 释放资源
            log.info("采集线程结束！！！！！！！！！！！！！！！！！");
        }

    }

    public void init(String url) {
        collectStatic = true;
        ctrl = true;
        mongoDBUtil.dropTable();
        mongoDBUtil.downloadsInsert(url);// 插入主页
        catDao.collectPageConfig();
    }

    public void init() {
        Downloads bson = mongoDBUtil.downloadsFindAndDeleteOne();
        String url = bson.getUrl();
        PageConfig pageConfig = catDao.findPageConfig(url);
        Document htmlSource = null;
        if (pageConfig != null) {
            pageConfig.setUrl(url);
            try {
                htmlSource = downLoadHtml.downloadHtml(pageConfig);
                Elements links = getLinks(htmlSource);
                for (Element link : links) {
                    String href = link.attr("abs:href").trim();
                    if (href.isEmpty()) {
                        href = link.attr("abs:src").trim();
                    }
                    String s = Util.filterSuffix(href);
                    mongoDBUtil.downloadsInsert(s);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } else {
            log.error("单层采集失败: " + url + "源码下载失败!");
        }
    }

    /**
     * 前置下载
     *
     * @return
     * @throws InterruptedException
     * @throws IOException
     */
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
                urlsCollection.setUrl(url);
                String md5 = catDao.singlCat(pageConfig, htmlSource);
                mongoDBUtil.urlsInsert(urlsCollection);// 插入Urls表中
                mongoDBUtil.historyInsert(url, title, md5, 1); // 插入history表中
                log.debug(url + "===md5[" + md5 + "]===template[" + pageConfig.getTemplate() + "]===rule[" + pageConfig.getDb() + ":" + pageConfig.getRule() + "]");
            } catch (IOException e) {
                log.error("下载页面源代码: " + url + " 失败" + e.getMessage());
                return true;
            } catch (InterruptedException e) {
                log.error("下载页面源代码: " + url + " 失败" + e.getMessage());
                return true;
            }
        } else {
            mongoDBUtil.historyInsert(url, null, null, 0); // 插入history表中
            log.debug(url);
        }
        return true;
    }

    public void setCollectStatic(boolean collectStatic) {
        this.collectStatic = collectStatic;
    }


}
