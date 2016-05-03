/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 亿时代无障碍工具栏v1.0
 * author lyp
 */

var ESDWebApp = {};
ESDWebApp.defalt = {};
ESDWebApp.defalt.fileURL = {};
ESDWebApp.defalt.pageLocation = window.location.href;//本页面链接地址
ESDWebApp.defalt.fileURL.toolbar = "";
ESDWebApp.defalt.browser = {};

//加载文件
ESDWebApp.loadFiles = function(){
	var toolbarFileUrl = document.getElementById("ESDWebAppToolbar").getAttribute("src");
	if(toolbarFileUrl.indexOf("/")!=-1){
		ESDWebApp.defalt.fileURL.toolbar = toolbarFileUrl.substring(0,toolbarFileUrl.lastIndexOf("/")+1);
	}else{
		ESDWebApp.defalt.fileURL.toolbar = "";
	}
};

//加载模块文件
ESDWebApp.loadFiles();

ESDWebApp.defalt.iframeLink = function(){
	//获取当前网址，如： http://localhost:18080/toolbarmini/wca.html
	var curWwwPath=window.document.location.href;
	//获取主机地址之后的目录，如： /toolbarmini/wca.html
	var pathName=window.document.location.pathname;
	var pos=curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:18080
	var localhostPaht=curWwwPath.substring(0,pos);
	//获取带"/"的项目名，如：/toolbarmini
	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	
	var childlink = "<link id='toolbarCss' href='toolbar/css/toolbar.css' type='text/css' rel='stylesheet'>";
	jq_1_7_1("#iframe").contents().find("body").append(childlink);
};

//刷新页面
ESDWebApp.defalt.pageReload = function(){
	window.location.href = ESDWebApp.defalt.pageLocation;
	window.location.reload();
};

ESDWebApp.defalt.iframeReload = function(){
	window.frames["iframe"].location.reload();
};
