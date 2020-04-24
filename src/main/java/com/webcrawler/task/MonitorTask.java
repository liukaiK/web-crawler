package com.webcrawler.task;

import com.esd.config.BaseConfig;
import com.webcrawler.core.CollectionAll;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务
 * 
 * @author liuk
 * 
 */
@Component
public class MonitorTask {

	@Resource
	private CollectionAll collectionPage;
	
//	@Resource
//	private CollectionParmary collectionParmary;
	
	@Scheduled(cron = "* * * * * ? ")
	public void CollectAll() {
		if (new SimpleDateFormat("HH:mm:ss").format(new Date()).equals(BaseConfig.time)) {
			collectionPage.init(BaseConfig.INDEX_URL[0]);
			collectionPage.start();
		}
	}
	  
			
//	@Scheduled(cron = "0 */10 * * * ? ")		
//	public void CollectionParmary(){
//		logger.debug("-----------定时采集开始--------------");
//		collectionParmary.init(BaseConfig.INDEX_URL[0]);
//		collectionParmary.start();
//	}
}