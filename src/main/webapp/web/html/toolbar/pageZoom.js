/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 页面放大缩小模块
 * 
 * */


var pageZoom = {};

pageZoom.zoom = 1;

var readZoom = 0;



pageZoom.increasePage = function(){
	pageZoom.pageZoom("increase");
};
pageZoom.decreasePage = function(){
	pageZoom.pageZoom("decrease");
};
pageZoom.defaultPage = function(){
	pageZoom.pageZoom("default");
};

pageZoom.pageZoom = function(action){
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	
	var mozilla = ESDWebApp.defalt.browser.mozilla;

	var opera = ESDWebApp.defalt.browser.opera;
	
	var nowZoom = 0;
	if(action == "increase"){
		nowZoom = parseFloat(pageZoom.zoom)+0.1;
		pageZoom.zoom+=0.1;
		toolbar_percent+=10;
		jq_1_7_1("#toolbar_percent").each(function () {
			jq_1_7_1(this).html("页面"+toolbar_percent+"%");
        });

	}else if(action == "decrease"){
		nowZoom = parseFloat(pageZoom.zoom)-0.1;
		pageZoom.zoom-=0.1;
		toolbar_percent-=10;
		jq_1_7_1("#toolbar_percent").each(function () {
			jq_1_7_1(this).html("页面"+toolbar_percent+"%");
        });

	}else{
		nowZoom = 1;
		pageZoom.zoom = 1;
	}
	
	readZoom = nowZoom;
	
	if(mozilla||opera){
//		jq_1_7_1("body > *:not(#toolbar,#toolbar *,#ymd_magnifier,#ymd_magnifier *)").css({"transform":"scale("+nowZoom+")","transform-origin":"top"});
		//jq_1_7_1("body").css({"transform":"scale("+nowZoom+")","transform-origin":"top"});
		//jq_1_7_1("#toolbar").css({"transform":"scale("+barZoom+")","transform-origin":"top"});
		jq_1_7_1(esd_tool_iframe).css({"transform":"scale("+nowZoom+")","transform-origin":"top"});
		
	}else{
//		jq_1_7_1("body > *:not(#toolbar,#toolbar *,#ymd_magnifier,#ymd_magnifier *)").css("zoom",nowZoom);
		//jq_1_7_1("body").css({"zoom":nowZoom,"overflow":"scroll"});
		//jq_1_7_1("#toolbar").css({"zoom":barZoom});
		jq_1_7_1(esd_tool_iframe).css({"zoom":nowZoom,"overflow":"scroll"});
	}
};