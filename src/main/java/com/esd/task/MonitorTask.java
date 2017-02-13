package com.esd.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.esd.config.BaseConfig;
import com.esd.core.CollectionAll;
import com.esd.core.CollectionParmary;

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
	
	@Resource
	private CollectionParmary collectionParmary;
	
	private Logger logger = Logger.getLogger(CollectionAll.class);

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