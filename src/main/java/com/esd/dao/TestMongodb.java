package com.esd.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.esd.collection.Site;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class TestMongodb {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			long  startTime=System.currentTimeMillis();
            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址  
            //ServerAddress()两个参数分别为 服务器地址 和 端口  
            ServerAddress serverAddress = new ServerAddress("localhost",27017);  
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();  
            addrs.add(serverAddress);    
            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码  
            MongoCredential credential = MongoCredential.createScramSha1Credential("root", "admin", "root".toCharArray());  
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();  
            credentials.add(credential);            
            //通过连接认证获取MongoDB连接  
            MongoClient mongoClient = new MongoClient(addrs,credentials);               
            //连接到数据库  
            //MongoDatabase mongoDatabase = mongoClient.getDatabase("iac");  
            System.out.println("链接成功！");
            //MongoOperations mongoOps = new MongoTemplate(new MongoDbFactory(new Mongo(), "database"));
           
			MongoTemplate m = new MongoTemplate(mongoClient,"iac");
			long  Time2=System.currentTimeMillis();
			 System.out.println("链接时间："+(Time2 - startTime));
            Site s  = m.findById("58227c43c2dd7a4db2cf3ecd", Site.class, "sites");
           
            System.out.println("成功吧！"+ s.getSiteName());
            long  endTime=System.currentTimeMillis();
            System.out.println(endTime - Time2);
            //检索所有文档  
            /** 
            * 1. 获取迭代器FindIterable<Document> 
            * 2. 获取游标MongoCursor<Document> 
            * 3. 通过游标遍历检索出的文档集合 
            * */
//            MongoCollection<Document> collection = mongoDatabase.getCollection("sites");
//            FindIterable<Document> findIterable = collection.find();  
//            MongoCursor<Document> mongoCursor = findIterable.iterator();  
//            while(mongoCursor.hasNext()){  
//               System.out.println(mongoCursor.next());  
//            }  
        } catch (Exception e) {  
            System.err.println( e.getClass().getName() + ": " + e.getMessage());  
        }  
	}
}
