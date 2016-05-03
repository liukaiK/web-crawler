/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/

/**
 * 手写功能模块
 * 
 */
var handwriting = {};
var statushandwriting = "on";

// 开启/关闭手写按钮
handwriting.toolbarHandwriting = function(){
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	if (statushandwriting == "on") {
		handwriting.openHandwriting();
		statushandwriting = "off";
	} else if (statushandwriting == "off") {
		handwriting.closeHandwriting();
		statushandwriting = "on";
	}
};

// 向所有type=text的input插入onfocus事件
handwriting.openHandwriting = function(){
	jq_1_7_1(esd_tool_iframe).find(".d-outer").css("display", "block");
	jq_1_7_1(esd_tool_iframe).find("input[type='text']").attr("onfocus", "openYBZ('on', this.id)");
};

// 关闭手写输入并且把所有type=text的input去掉onfocus事件
handwriting.closeHandwriting = function(){
	iframe.window.closeYBZ();
	jq_1_7_1(esd_tool_iframe).find("input[type='text']").removeAttr("onfocus");
	jq_1_7_1(esd_tool_iframe).find(".d-outer").css("display", "none");
};
