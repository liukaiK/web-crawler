package com.crawler.util;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class ByteToObject {
	public static Object ByteToObject(byte[] bytes) {  
		Object obj = null;  
		try {  
		    // bytearray to object  
		    ByteArrayInputStream bi = new ByteArrayInputStream(bytes);  
		    ObjectInputStream oi = new ObjectInputStream(bi);  
		  
		    obj = oi.readObject();  
		    bi.close();  
		    oi.close();  
		} catch (Exception e) {  
		    System.out.println("translation" + e.getMessage());  
		    e.printStackTrace();  
		}  
		       return obj;  
		   }
}
