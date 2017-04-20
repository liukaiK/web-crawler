package com.esd.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class AppConfig {
//	  public @Bean
//	    MongoFactoryBean mongo() {
//	        MongoFactoryBean mongo = new MongoFactoryBean();
//	        mongo.setHost( "localhost" );
//	        return mongo;
//	    }
//    public @Bean MongoClient mongoClient throws Exception {
//        return new MongoClient("");
//    }
//
//    public @Bean MongoTemplate mongoTemplate() throws Exception {
//        return new MongoTemplate(mongo(), "mydatabase");//还有其它的初始化方法。
//    }
}
