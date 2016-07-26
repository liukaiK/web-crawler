/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 高对比度模式
 * 
 * 
 * */

var highContrast = {};
highContrast.pageColor = "";

highContrast.defaltMode = function(){
	highContrast.changeTheme("defaltMode");
};
/*highContrast.yellowMode = function(){
	highContrast.changeTheme("yellow");
};*/
highContrast.whiteMode = function(){
	highContrast.changeTheme("white");
};

highContrast.whiteBlackMode = function(){
	highContrast.changeTheme("whiteBlackMode");
};
highContrast.blueYellowMode = function(){
	highContrast.changeTheme("blueYellowMode");
};
highContrast.yellowBlackMode = function(){
	highContrast.changeTheme("yellowBlackMode");
};
highContrast.yellowMode = function(){
	highContrast.changeTheme("yellowMode");
};

/*ESDWebApp.defalt.iframeReload = function(){
	window.frames["iframe"].location.reload();
};*/

highContrast.changeTheme = function(modeStyle){
	highContrast.pageColor = modeStyle;
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	var thmeMode = modeStyle;
	/*if(thmeMode == "defaltMode"){
		ESDWebApp.defalt.pageReload();
	}else{
		jq_1_7_1("body,body *").each(function(){
			if(!jq_1_7_1(this).is("#toolbar,#toolbar *")){
				jq_1_7_1(this).css({"background-color":"rgb(0, 0, 0)","color":""+thmeMode+"","background-image":"none"});
			}
		});
		jq_1_7_1(esd_tool_iframe).find("*").each(function(){
		//jq_1_7_1("#iframe").contents().find("body,body *").each(function(){
			jq_1_7_1(this).css({"background-color":"rgb(0, 0, 0)","color":""+thmeMode+"","background-image":"none"});
		});
	}*/
	if (thmeMode == "defaltMode") {
		ESDWebApp.defalt.iframeReload();
		//ESDWebApp.defalt.pageReload();
		jq_1_7_1(".toolbar_highContrastWhiteBlack img").css({"display":"none"});
		jq_1_7_1(".toolbar_highContrastBlueYellow img").css({"display":"none"});
		jq_1_7_1(".toolbar_highContrastYellowBlack img").css({"display":"none"});
		jq_1_7_1(".toolbar_highContrastYellow img").css({"display":"none"});
		jq_1_7_1(".toolbar_highContrastDefalt img").css({"display":"block"});
	} else if (thmeMode == "whiteBlackMode") {
		jq_1_7_1(esd_tool_iframe).find("*:not(#slideLateral,#slideLongitudinal)").each(function(){
			jq_1_7_1(this).css({"background-color":"#FFFFFF","color":"#000000","background-image":"none"});
			jq_1_7_1(".toolbar_highContrastWhiteBlack img").css({"display":"block"});
			jq_1_7_1(".toolbar_highContrastBlueYellow img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastYellowBlack img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastYellow img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastDefalt img").css({"display":"none"});
		});
	} else if (thmeMode == "blueYellowMode") {
		jq_1_7_1(esd_tool_iframe).find("*:not(#slideLateral,#slideLongitudinal)").each(function(){
			jq_1_7_1(this).css({"background-color":"#0000FF","color":"#FFFF00","background-image":"none"});
			jq_1_7_1(".toolbar_highContrastWhiteBlack img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastBlueYellow img").css({"display":"block"});
			jq_1_7_1(".toolbar_highContrastYellowBlack img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastYellow img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastDefalt img").css({"display":"none"});
		});
	} else if (thmeMode == "yellowBlackMode") {
		jq_1_7_1(esd_tool_iframe).find("*:not(#slideLateral,#slideLongitudinal)").each(function(){
			jq_1_7_1(this).css({"background-color":"#FFFF00","color":"#000000","background-image":"none"});
			jq_1_7_1(".toolbar_highContrastWhiteBlack img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastBlueYellow img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastYellowBlack img").css({"display":"block"});
			jq_1_7_1(".toolbar_highContrastYellow img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastDefalt img").css({"display":"none"});
		});
	} else if (thmeMode == "yellowMode") {
		jq_1_7_1(esd_tool_iframe).find("*:not(#slideLateral,#slideLongitudinal)").each(function(){
			jq_1_7_1(this).css({"background-color":"#000000","color":"#FFFF00","background-image":"none"});
			jq_1_7_1(".toolbar_highContrastWhiteBlack img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastBlueYellow img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastYellowBlack img").css({"display":"none"});
			jq_1_7_1(".toolbar_highContrastYellow img").css({"display":"block"});
			jq_1_7_1(".toolbar_highContrastDefalt img").css({"display":"none"});
		});
	}
	
};