package com.crawler.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.crawler.config.BaseConfig;
import com.crawler.controller.site.SiteController;
import com.crawler.core.CollectionPage;

/**
 * 定时任务
 * 
 * @author liuk
 * 
 */
@Component
public class MonitorTask {

	@Resource
	private CollectionPage collectionPage;

	@Scheduled(cron = "* * * * * ? ")
	public void timerTask() {
		if (new SimpleDateFormat("HH:mm:ss").format(new Date()).equals(BaseConfig.time)) {
			collectionPage.init(BaseConfig.INDEX_URL[0],SiteController.siteId);
			collectionPage.start();
		}
	}
}