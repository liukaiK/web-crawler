package com.crawler.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfiguration {
	@SuppressWarnings("deprecation")
	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
        //需要用户名、密码验证
        UserCredentials userCredentials = new UserCredentials("yq", "123");
        //return new SimpleMongoDbFactory(new Mongo(), "exam", userCredentials);
        return (MongoDbFactory) new MongoClient();
        //return new SimpleMongoDbFactory(new Mongo(), "exam");
      }

      public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
      }
      //Mongo mongo = new MongoConfiguration();
      //MongoTemplate mongoTemplate = new MongoTemplate(mongo, "admin", new UserCredentials("yq", "123"));
}
