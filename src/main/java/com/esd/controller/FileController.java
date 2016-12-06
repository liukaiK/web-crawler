package com.esd.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.esd.collection.DbFile;
import com.esd.common.MongoDBUtil;
import com.esd.config.BaseConfig;
import com.esd.controller.site.SiteController;
import com.esd.core.CollectionPage;
import com.esd.util.Md5;
import com.esd.util.MkFile;

@Controller
@RequestMapping("/admin")
public class FileController {
	
	private static Logger logger = Logger.getLogger(CollectionPage.class);
	@Autowired
	private MongoDBUtil mdu;
	/**
	 * 这里这里用的是MultipartFile[] myfiles参数,所以前台就要用<input type="file"
	 * name="myfiles"/>
	 * 上传文件完毕后返回给前台[0`filepath],0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
	 */
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String uploadImage(@RequestParam MultipartFile[] myfiles, Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 可以在上传文件的同时接收其它参数
		System.out.println("收到用户的文件上传请求");
		// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
		// 这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
		String realPath = BaseConfig.IMAGE_ROOT;
		// 设置响应给前台内容的数据格式
		response.setContentType("text/plain; charset=UTF-8");
		// 设置响应给前台内容的PrintWriter对象
		PrintWriter out = response.getWriter();
		// 上传文件的原名(即上传前的文件名字)
		String originalFilename = null;
		// 文件格式
		String type = null;
		// 如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
		// 如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
		// 上传多个文件时,前台表单中的所有<input
		// type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
		for (MultipartFile myfile : myfiles) {
			if (myfile.isEmpty()) {
				out.print("1`请选择文件后上传");
				out.flush();
				return null;
			} else {
				originalFilename = myfile.getOriginalFilename();
				System.out.println("文件原名: " + originalFilename);
				System.out.println("文件名称: " + myfile.getName());
				System.out.println("文件长度: " + myfile.getSize());
				System.out.println("文件类型: " + myfile.getContentType());
				System.out.println("文件格式:" + originalFilename.substring(originalFilename.lastIndexOf(".") + 1));
				System.out.println("========================================");
				type = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
				if (type == null || (!"jpg".equalsIgnoreCase(type) && !"png".equalsIgnoreCase(type))) {
					out.print("1`仅支持jpg,png文件格式");
					out.flush();
					return null;
				}
				try {
					// 这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
					// 此处也可以使用Spring提供的MultipartFile.transferTo(File
					// dest)方法实现文件的上传
					myfile.getBytes();
					FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, originalFilename));
				} catch (IOException e) {
					System.out.println("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
					e.printStackTrace();
					out.print("1`文件上传失败，请重试！！");
					out.flush();
					return null;
				}
			}
		}
		// 此时在Windows下输出的是[D:\Develop\apache-tomcat-6.0.36\webapps\AjaxFileUpload\\upload\愤怒的小鸟.jpg]
		// System.out.println(realPath + "\\" + originalFilename);
		// 此时在Windows下输出的是[/AjaxFileUpload/upload/愤怒的小鸟.jpg]
		// System.out.println(request.getContextPath() + "/upload/" +
		// originalFilename);
		// 不推荐返回[realPath + "\\" + originalFilename]的值
		// 因为在Windows下<img src="file:///D:/aa.jpg">能被firefox显示,而<img
		// src="D:/aa.jpg">firefox是不认的
		out.print("0`" + BaseConfig.IMAGE_ROOT + originalFilename);
		out.flush();
		return null;
	}
	/**
	 * 20161027-cx 文件上傳
	 * @param uploadfiles
	 * @param ex
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/uploadfiles", method = RequestMethod.POST)
	public String uploadFile(@RequestParam MultipartFile[] uploadfiles, Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (uploadfiles.length == 0) {
			return null;
		}
		String fileName = null;
		byte[] b = null;
		String md = null;
		for (int i = 0; i < uploadfiles.length; i++) {
			DbFile df = new DbFile();
			fileName = uploadfiles[i].getOriginalFilename();
			b = uploadfiles[i].getBytes();
			md = Md5.getMd5File(b);
			
			df.setFileName(fileName);
			df.setFileByte(b);
			df.setSiteName(SiteController.siteId);
			df.setMd5File(md);
			
			mdu.insertFile(df,SiteController.siteId+"_plug");
		}
		return null;
	}
	@RequestMapping(value = "/createSite", method = RequestMethod.POST)
	public String createSite(int type,HttpSession session) throws IOException {
		System.out.println("进来了");
		String siteId = session.getAttribute("siteId").toString();
		if(type == 0){
			MkFile.mkdir(BaseConfig.ROOT1+siteId);
			if(mkFile(siteId,"js")){
				System.out.println("完成");
			}else{
				System.out.println("失败了");
			}
			if(mkFile(siteId,"css")){
				System.out.println("完成");
			}else{
				System.out.println("失败了");
			}
			if(mkFile(siteId,"plug")){
				System.out.println("完成");
			}else{
				System.out.println("失败了");
			}
		}
		
		return null;
	}
	public  boolean mkFile(String siteId,String type){
		File file = new File(BaseConfig.ROOT1+siteId+"/resourse/"+type);
		if(!file.exists()){
			file.mkdirs();
		}	
		String collectionName = siteId + "_" + type;
		String dir = BaseConfig.ROOT1+siteId+File.separator+"resourse"+File.separator+type;
		
		List<DbFile> l = mdu.findAll(DbFile.class, collectionName);
		if(l == null){
			return false;
		}
		byte[] b = null;
		String fileName = null;
		String endName = null;
		for (Iterator<DbFile> iterator = l.iterator(); iterator.hasNext();) {
			DbFile dbFile = (DbFile) iterator.next();
			b = dbFile.getFileByte();
			fileName = dbFile.getFileName();
			endName = fileName.split("\\.")[1];
			if(endName.equals(type) || type.equals("plug")){
				try {
					MkFile.writeFile(b,dir + File.separator + fileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}
		return true;
	}
	/**************************************************************************************/
	/*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    public ModelAndView  uploadFiles(HttpServletRequest request,HttpSession session) throws IllegalStateException, IOException{
    	String siteId = session.getAttribute("siteId").toString();
    	long  startTime=System.currentTimeMillis();
    	//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
           //获取multiRequest 中所有的文件名
            Iterator<?> iter=multiRequest.getFileNames();
            String fileName = null;
            String str[] = null;
            String hz = null;
            String collectionName = null;
            while(iter.hasNext()){
                //一次遍历所有文件
                MultipartFile file=multiRequest.getFile(iter.next().toString());
                if(file!=null){  
                    System.out.println(file.getOriginalFilename());
                    fileName = file.getOriginalFilename();
                    str = fileName.split("\\.");
                    hz = str[str.length - 1];
                    if(hz.equals("js") || hz.equals("css")){
                    	collectionName = siteId + "_" + hz;
                    }else{
                    	collectionName = siteId + "_plug";
                    }
                    //上传mongodb
                    //file.transferTo(new File(path));
                    DbFile df = new DbFile();
                    df.setFileByte(file.getBytes());
                    df.setFileName(fileName);
                    df.setMd5File(Md5.getMd5File(file.getBytes()));
                    df.setSiteName(SiteController.siteId);
                    
                    mdu.insertFile(df, collectionName);
                }   
            }  
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法三的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return new ModelAndView("redirect:manage"); 
    }
	/********************--------------------------------------------------------------***********************/
	@ExceptionHandler
	public String doException(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (e instanceof MaxUploadSizeExceededException) {
			long maxSize = ((MaxUploadSizeExceededException) e).getMaxUploadSize();
			out.print("1`上传文件太大，不能超过" + maxSize / 1024 + "k");
			out.flush();
			return null;
		} else if (e instanceof RuntimeException) {
			out.print("1`请选择文件后上传");
			out.flush();
			return null;
		} else {
			out.print("1`文件上传失败，请重试！！");
			out.flush();
		}
		return null;
	}

	@RequestMapping(value = "/uploadImage")
	public void uploadImage(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {
		String fileName = file.getOriginalFilename();
		File targetFile = new File(BaseConfig.IMAGE_ROOT, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
