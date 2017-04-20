package com.esd.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDBLink {
	private static int port;
	
	public static int getPort() {
		return port;
	}

	public static void setPort(int myPort) {
		port = myPort;
	}
	private static int webPort;

	public static MongoTemplate link(){
		System.out.println("我是link");
		if(port > 0 ){
			webPort = port;
		}
		InputStream in = ClassLoader.getSystemResourceAsStream("MongoDB.properties");
		if(in==null){
	        try {
	            String filename = new URI(MongoDBLink.class.getClassLoader().getResource("MongoDB.properties").toString()).toString();
	            File file = new File(filename.replace("file:", ""));
	            //System.out.println("File: " + file.getAbsolutePath());
	        in = new FileInputStream(file);
	        } catch (URISyntaxException e) {
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	    }

		Properties p = new Properties();
		MongoTemplate m = null;
		try {
			System.out.println("in:"+in);
			p.load(in);
			
			String dbName = p.getProperty("mongo.dbname");
			String host = p.getProperty("mongo.host");
			int port = Integer.parseInt(p.getProperty("mongo.port"));
			String userDB = p.getProperty("mongo.userDB");
			int webport_p = Integer.parseInt(p.getProperty("mongo.webport"));
			String username = null;
			String password = null;
			
			System.out.println("dblink_port:"+webPort);
			System.out.println("dbName:"+dbName);
			if(webPort == webport_p){
				username = p.getProperty("mongo.username");
				password = p.getProperty("mongo.password");
			}else{
				username = p.getProperty("mongo.username1");
				password = p.getProperty("mongo.password1");
			}
			//20170110
			String connectionsPerHost = p.getProperty("mongo.connectionsPerHost");
			String maxWaitTime = p.getProperty("mongo.maxWaitTime");
			String socketTimeout = p.getProperty("mongo.socketTimeout");
			String maxConnectionLifeTime = p.getProperty("mongo.maxConnectionLifeTime");
			String connectTimeout = p.getProperty("mongo.connectTimeout");
			boolean socketKeepAlive =  Boolean.parseBoolean(p.getProperty("socketKeepAlive"));
			
			MongoClientOptions options = MongoClientOptions.builder()
		                .connectionsPerHost(Integer.parseInt(connectionsPerHost))  
		                .maxWaitTime(Integer.parseInt(maxWaitTime))  
		                .socketTimeout(Integer.parseInt(socketTimeout))
		                .socketKeepAlive(socketKeepAlive)
		                .maxConnectionLifeTime(Integer.parseInt(maxConnectionLifeTime))  
		                .connectTimeout(Integer.parseInt(connectTimeout)).build(); 
		    //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址  
		    //ServerAddress()两个参数分别为 服务器地址 和 端口  
		    ServerAddress serverAddress = new ServerAddress(host,port);
		    
		    List<ServerAddress> addrs = new ArrayList<ServerAddress>();  
		    addrs.add(serverAddress);    
		    //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码  
		    MongoCredential credential = MongoCredential.createScramSha1Credential(username, userDB, password.toCharArray());  
		    List<MongoCredential> credentials = new ArrayList<MongoCredential>();  
		    credentials.add(credential);            
		    //通过连接认证获取MongoDB连接  
		    MongoClient mongoClient = new MongoClient(addrs,credentials,options); 
		    //连接到数据库  
		    //MongoDatabase mongoDatabase = mongoClient.getDatabase("iac");  
		    //System.out.println("链接成功！");
		    //MongoOperations mongoOps = new MongoTemplate(new MongoDbFactory(new Mongo(), "database"));	   
			m = new MongoTemplate(mongoClient,dbName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m;
	}
}
