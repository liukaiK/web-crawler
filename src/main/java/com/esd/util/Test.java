package com.esd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String url = "G:/apache-tomcat-7.0.70/webapps/iac/szft/template"; 
		File fold = new File(url);
		if (fold.exists()) {
			System.out.println("存在进来了");
			//文件组
			File[] file = fold.listFiles();
			BufferedReader reader = null;
			byte[] b = null;
			Md5 md5 = new Md5();
			for (int i = 0; i < file.length; i++) {
				if(!file[i].isDirectory()){
					
					b = UtilFile.FiletoBytes(file[i]);
					
					String m = md5.getMd5File(b);
					System.out.println((i+1)+":"+file[i].getName()+"------" + b +"------"+ m);
//					reader = new BufferedReader(new FileReader(file[i]));  
//		            String tempString = null;
//		            String str = null;
//		            int line = 1;  
		            // 一次读入一行，直到读入null为文件结束  
//		            while ((tempString = reader.readLine()) != null) {  
//		                // 显示行号  
//		                //System.out.println("line " + line + ": " + tempString);  
//		                //line++;
//		            	if(str == null){
//		            		str = tempString;
//		            	}
//		            	str = str +"\n"+ tempString;
//		            }
//		            System.out.println(str);
		            if(i>1000000){
		            	break;
		            }
//		            reader.close();
					
				}else{// 如果上传的是文件夹  第二层才是包  包名为: 第一层/第二层     
					String firstFileName = new String();
					firstFileName = file[i].getName();
					System.out.println(firstFileName);
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

}
