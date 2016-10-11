package com.esd.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * cx-20160927
 * 序列化，反序列化
 * @author Administrator
 *
 */
public final class SerializeUtil {
	 /** 序列化对象

     * @throws IOException */

    public static byte[] serializeObject(Object object) throws IOException{

       ByteArrayOutputStream saos=new ByteArrayOutputStream();

       ObjectOutputStream oos=new ObjectOutputStream(saos);

       oos.writeObject(object);

       oos.flush();

       return saos.toByteArray();

    }
    /** 反序列化对象

     * @throws IOException

     * @throws ClassNotFoundException */

    public static Object deserializeObject(byte[]buf) throws IOException, ClassNotFoundException{

       Object object=null;

       ByteArrayInputStream sais=new ByteArrayInputStream(buf);

       ObjectInputStream ois = new ObjectInputStream(sais);

       object= ois.readObject();

       return object;
    }
    public static Object deserializeObject(String url)  throws Exception, IOException{

        Object object=null;

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(url)));

        object= ois.readObject();

        return object;
     }
}

