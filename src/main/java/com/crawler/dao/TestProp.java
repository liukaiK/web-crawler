package com.crawler.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestProp {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		InputStream in = ClassLoader.getSystemResourceAsStream("MongoDB.properties");
		Properties p = new Properties();  
		p.load(in);  
		String dbName = p.getProperty("mongo.dbname");  
		System.out.println(dbName);
	}
}
