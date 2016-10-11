package com.esd.util;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;


public class UtilFile {

	//private static Logger logger = Logger.getLogger(UtilDb.class);
	/**
	 * 扫描指定目录
	 * @param content
	 * @throws IOException
	 */
	public static void scanFile(String url) throws IOException {
		url =url + "web/template/"; 
		File fold = new File(url);
		System.out.println(fold.exists());
		if (fold.exists()) {
			//文件组
			File[] file = fold.listFiles();
			for (int i = 0; i < file.length; i++) {
				if(!file[i].isDirectory()){
					String zipName = new String();
					zipName = file[i].getName();
					System.out.println(i+":"+zipName);
					
//				}else{// 如果上传的是文件夹  第二层才是包  包名为: 第一层/第二层     
//					String firstFileName = new String();
//					firstFileName = file[i].getName();
//					File file2 = new File(file[i].getPath());
//					if(file2.exists()){
//						File[] fileArray = file2.listFiles();
//						for (int j = 0; j < fileArray.length; j++) {
//							String fileName = new String();
//							fileName = fileArray[j].getName();
//							String packName = firstFileName+"/"+fileName; //换成其他分隔符会导致下面好多代码出问题 因为packName在后面代码中作为路径使用
//							
//						}
//					}
				}
			}
		}
	}
	/**
	 * 文件转成byte[]
	 * 
	 * @param file
	 * @return
	 */
	public static byte[] FiletoBytes(File file) {
		byte[] ret = null;
		try {
			if (file == null) {
				// log.error("helper:the file is null!");
				return null;
			}
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream((int)file.length());
			byte[] b = new byte[(int)file.length()];
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			in.close();
			out.close();
			ret = out.toByteArray();
		} catch (IOException e) {
			// log.error("helper:get bytes from file process error!");
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * 读取file的内容转成字符串
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String url) throws IOException {
		String str = null;
		
		System.out.println("testFile进来了");
		//String url = FileDbController.url(request);
		url =url + "web/template/"; 
		File fold = new File(url);

		System.out.println(fold.exists());
		if (fold.exists()) {
			//文件组
			File[] file = fold.listFiles();
			BufferedReader reader = null; 
			for (int i = 0; i < file.length; i++) {
				if(!file[i].isDirectory()){
					String zipName = new String();
					zipName = file[i].getName();
					
					System.out.println(i+":"+zipName);
					//按行读取文件内容
					 reader = new BufferedReader(new FileReader(file[i]));  
			            String tempString = null;
			            
			            int line = 1;  
			            // 一次读入一行，直到读入null为文件结束  
			            while ((tempString = reader.readLine()) != null) {  
			            	if(str == null){
			            		str = tempString;
			            	}
			            	str = str +"\n"+ tempString;
			            }  
			            reader.close();  
				}
			}
		}	
		return str;
	}
}
