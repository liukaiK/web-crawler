package com.esd.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.esd.config.BaseConfig;
import com.esd.core.CollectionPage;
import com.esd.util.Util;

@Controller
@RequestMapping("/admin")
public class FileController {
	
	private static Logger logger = Logger.getLogger(CollectionPage.class);
	
	
	
	@RequestMapping("/deletePgFile")
	@ResponseBody
	public Map<String, Object> deletePgFile(String pgFileName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(BaseConfig.PG_ROOT + File.separator + pgFileName + ".pg");
		if (file.isFile()) {
			if (file.getAbsoluteFile().delete()) {
				map.put("notice", true);
				map.put("message", pgFileName + "规则文件删除成功!");
			} else {
				map.put("notice", false);
				map.put("message", pgFileName + "规则文件删除失败!");
			}
		} else {
			map.put("notice", false);
			map.put("message", pgFileName + "规则文件删除失败!");
		}
		return map;

	}
	
	@RequestMapping(value = "/saveTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTemplate(String templateContent, String templateName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Util.createNewFile(templateContent, BaseConfig.TEMPLATE_ROOT + File.separator + templateName);
			map.put("notice", true);
			map.put("message", templateName + "模板文件保存成功!");
		} catch (IOException e) {
			map.put("notice", false);
			map.put("message", templateName + "模板文件保存失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "/deleteTemplate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteTemplate(String templateName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(BaseConfig.TEMPLATE_ROOT + File.separator + templateName);
		if (file.isFile()) {
			if (file.getAbsoluteFile().delete()) {
				map.put("notice", true);
				map.put("message", templateName + "模板文件删除成功!");
			} else {
				map.put("notice", false);
				map.put("message", templateName + "模板文件删除失败!");
			}
		} else {
			map.put("notice", false);
			map.put("message", templateName + "模板文件删除失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "/saveCss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveCss(String cssContent, String cssName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Util.createNewFile(cssContent,BaseConfig.CSS_ROOT + File.separator + cssName);
			map.put("notice", true);
			map.put("message", cssName + "样式文件保存成功!");
		} catch (IOException e) {
			map.put("notice", false);
			map.put("message", cssName + "样式文件保存失败!");
		}
		return map;
	}	
	
	
	@RequestMapping(value = "/deleteCss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCss(String cssName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(BaseConfig.CSS_ROOT + File.separator + cssName);
		if (file.isFile()) {
			if (file.getAbsoluteFile().delete()) {
				map.put("notice", true);
				map.put("message", cssName + "样式文件删除成功!");
			} else {
				map.put("notice", false);
				map.put("message", cssName + "样式文件删除失败!");
			}
		} else {
			map.put("notice", false);
			map.put("message", cssName + "样式文件删除失败!");
		}
		return map;
	}
	
	
	@RequestMapping(value = "/saveJs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveJs(String jsContent, String jsName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Util.createNewFile(jsContent,BaseConfig.JS_ROOT + File.separator + jsName);
			map.put("notice", true);
			map.put("message", jsName + "脚本文件保存成功!");
		} catch (IOException e) {
			map.put("notice", false);
			map.put("message", jsName + "脚本文件保存失败!");
		}
		return map;
	}	
	
	
	@RequestMapping(value = "/deleteJs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteJs(String jsName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File file = new File(BaseConfig.JS_ROOT + File.separator + jsName);
		if (file.isFile()) {
			if (file.getAbsoluteFile().delete()) {
				map.put("notice", true);
				map.put("message", jsName + "脚本文件删除成功!");
			} else {
				map.put("notice", false);
				map.put("message", jsName + "脚本文件删除失败!");
			}
		} else {
			map.put("notice", false);
			map.put("message", jsName + "脚本文件删除失败!");
		}
		return map;
	}
	
	@RequestMapping(value = "/loadFileContent", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> loadFileContent(String filePath, String fileName, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		try {
			Reader reader = new InputStreamReader(new FileInputStream(new File(BaseConfig.ROOT + filePath + fileName)), "UTF-8");
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				if (((char) tempchar) != '\r') {
					sb.append((char) tempchar);
				}
			}
			reader.close();
			map.put("notice", true);
			map.put("message", sb.toString());
		} catch (Exception e) {
			logger.error(e);
			map.put("notice", false);
			map.put("message", fileName + "文件载入失败!");
		}
		return map;
	}
	
	
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

	@RequestMapping("/loadJsList")
	@ResponseBody
	public Map<String, Object> loadjslist(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File js_file = new File(BaseConfig.JS_ROOT);
		if (js_file.isDirectory()) {
			String[] files = js_file.list();
			map.put("list", files);
		}
		return map;
	}

	@RequestMapping("/loadCssList")
	@ResponseBody
	public Map<String, Object> loadCssList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		File js_file = new File(BaseConfig.CSS_ROOT);
		if (js_file.isDirectory()) {
			String[] files = js_file.list();
			map.put("list", files);
		}
		return map;
	}

}
