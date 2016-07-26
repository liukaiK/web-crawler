/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
cursor = {};
cursor.flg = "";
cursor.open = function() {
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	// jq_1_7_1(esd_tool_iframe).addClass("mouseBoldBlack");
	var ico = ESDWebApp.defalt.fileURL.toolbar + "img/shubiao.ico";
	jq_1_7_1(esd_tool_iframe).css("cursor", "url(" + ico + "), auto");
	jq_1_7_1(esd_tool_iframe).find("a").css("cursor", function() {
		return "url(" + ico + "), auto";
	});
};
cursor.close = function() {
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	// jq_1_7_1(esd_tool_iframe).removeClass("mouseBoldBlack");
	jq_1_7_1(esd_tool_iframe).css("cursor", "auto");
	jq_1_7_1(esd_tool_iframe).find("a").css("cursor", function() {
		return "auto";
	});
};

cursor.changeArrow = function(arrowType){
	cursor.flg = arrowType;
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	jq_1_7_1("#cy_arrow_dl dt img").css({"display":"none"});
	jq_1_7_1("#"+arrowType+" img").css({"display":"block"});
	if (arrowType == 'arrow_1') {
		var ico = ESDWebApp.defalt.fileURL.toolbar + "img/shubiao1.cur";
		jq_1_7_1(esd_tool_iframe).css("cursor", "url(" + ico + "), auto");
		jq_1_7_1(esd_tool_iframe).find("a").css("cursor", function() {
			return "url(" + ico + "), auto";
		});
	}
	if(arrowType == 'arrow_2'){
		var ico = ESDWebApp.defalt.fileURL.toolbar + "img/shubiao2.cur";
		jq_1_7_1(esd_tool_iframe).css("cursor", "url(" + ico + "), auto");
		jq_1_7_1(esd_tool_iframe).find("a").css("cursor", function() {
			return "url(" + ico + "), auto";
		});
	}
	if(arrowType == 'arrow_3'){
		var ico = ESDWebApp.defalt.fileURL.toolbar + "img/shubiao3.cur";
		jq_1_7_1(esd_tool_iframe).css("cursor", "url(" + ico + "), auto");
		jq_1_7_1(esd_tool_iframe).find("a").css("cursor", function() {
			return "url(" + ico + "), auto";
		});
	}
	if(arrowType == 'arrow_4'){
		var ico = ESDWebApp.defalt.fileURL.toolbar + "img/shubiao4.cur";
		jq_1_7_1(esd_tool_iframe).css("cursor", "url(" + ico + "), auto");
		jq_1_7_1(esd_tool_iframe).find("a").css("cursor", function() {
			return "url(" + ico + "), auto";
		});
	}
	if(arrowType == 'arrow_5'){
		jq_1_7_1(esd_tool_iframe).css("cursor", "auto");
		jq_1_7_1(esd_tool_iframe).find("a").css("cursor", function() {
			return "auto";
		});
	}
}