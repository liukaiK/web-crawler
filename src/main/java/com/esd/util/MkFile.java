package com.esd.util;

import java.io.File;
import java.io.FileOutputStream;

public class MkFile {
	/**
	 * 建文件夹
	 * @param dir
	 * @return
	 */
	public static boolean mkdir (String dir){
		File file = new File(dir);
		if  (!file .exists()  && !file .isDirectory())      
		{       
		    System.out.println("//不存在");  
		    file .mkdir();    
		} else   
		{  
		    System.out.println("//目录存在");  
		}
		return true;
	}
	
	public static boolean writeFile(byte[] b,String dir)throws Exception{
		
		  File f = new File(dir);
		  if(!f.exists()){
			  f.createNewFile();
		  }else{
			  f.delete();
			  f.createNewFile();
		  }
		  boolean flag=false;  
		  FileOutputStream o=null;
		  try {  
			  o = new FileOutputStream(f);  
		      o.write(b);  
		      o.close();  
		      flag=true;  
		  } catch (Exception e) {  
		   // TODO: handle exception  
		   e.printStackTrace();  
		  }
		  return flag;  
	 }
}
