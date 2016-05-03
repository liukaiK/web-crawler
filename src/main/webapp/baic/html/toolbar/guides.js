/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 *辅助线模块 
 * 
 */

var guides = {};

var statusguides = "on";

/*guides.open = function(){
	jq_1_7_1('body').append('<div id="guides_horiz"></div><div id="guides_verti"></div>').mousemove(function(pos){
		jq_1_7_1('#guides_horiz').css('top', pos.clientY + 10 + jq_1_7_1(document).scrollTop());
		jq_1_7_1('#guides_verti').css('left', pos.clientX + 10).css('top', jq_1_7_1(document).scrollTop());
	});
	if (jq_1_7_1.browser.msie && jq_1_7_1.browser.version.substr(0, 1) < 7) {
		jq_1_7_1('#guides_verti').css('height', jq_1_7_1(window).height());
		jq_1_7_1(window).resize(function(){
			('#guides_verti').css('height', jq_1_7_1(window).height());
		});
	}

	jq_1_7_1('#guides').addClass('on');
};
guides.close = function(){
	jq_1_7_1('#guides_horiz,#guides_verti').remove();
	jq_1_7_1('body').unbind("mousemove");
	if (jq_1_7_1.browser.msie && jq_1_7_1.browser.version.substr(0, 1) < 7) {
		jq_1_7_1(window).unbind("resize");
	}

	jq_1_7_1('#guides').removeClass('on');
};*/
/*guides.open = function(){
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	jq_1_7_1(esd_tool_iframe).append('<div id="guides_horiz"></div><div id="guides_verti"></div>').mousemove(function(pos){
		jq_1_7_1(esd_tool_iframe).find('#guides_horiz').css('top', pos.clientY + 10 + jq_1_7_1(esd_tool_iframe).scrollTop());
		jq_1_7_1(esd_tool_iframe).find('#guides_verti').css('left', pos.clientX + 10).css('top', jq_1_7_1(esd_tool_iframe).scrollTop());
	});
	if (jq_1_7_1.browser.msie && jq_1_7_1.browser.version.substr(0, 1) < 7) {
		jq_1_7_1(esd_tool_iframe).find('#guides_verti').css('height', jq_1_7_1(esd_tool_iframe).height());
		jq_1_7_1(esd_tool_iframe).resize(function(){
			(esd_tool_iframe).find('#guides_verti').css('height', jq_1_7_1(esd_tool_iframe).height());
		});
	}

	jq_1_7_1(esd_tool_iframe).find('#guides').addClass('on');
};
guides.close = function(){
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	jq_1_7_1(esd_tool_iframe).find('#guides_horiz,#guides_verti').remove();
	jq_1_7_1(esd_tool_iframe).unbind("mousemove");
	if (jq_1_7_1.browser.msie && jq_1_7_1.browser.version.substr(0, 1) < 7) {
		jq_1_7_1(esd_tool_iframe).unbind("resize");
	}

	jq_1_7_1(esd_tool_iframe).find('#guides').removeClass('on');
};*/
guides.toolbarGuides = function(){
	if (statusguides == "on") {
		guides.open();
		statusguides = "off";
	} else if (statusguides == "off") {
		guides.close();
		statusguides = "on";
	}
};
guides.open = function(){
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	var childHeight = 0;
	childHeight = document.getElementById("iframe").contentWindow.document.documentElement.scrollHeight || document.getElementById("iframe").contentWindow.document.body.scrollHeight;
	var str = "<div id=\"slideLateral\"></div><div id=\"slideLongitudinal\"></div>";
	jq_1_7_1("#iframe").contents().find("body").append(str);
	document.getElementById("iframe").contentWindow.document.onmousemove = function(e){
		var pointer = getCoordInDocument(e);
		jq_1_7_1(esd_tool_iframe).find("#slideLateral").css("top", pointer.y + 10);
		jq_1_7_1(esd_tool_iframe).find("#slideLongitudinal").css("left", pointer.x + 10).css("height", childHeight);
	};
};
var getCoordInDocument = function(e) {
	e = e || document.getElementById("iframe").contentWindow.event;
	var x = e.pageX || (e.clientX + (document.getElementById("iframe").contentWindow.document.documentElement.scrollLeft || document.getElementById("iframe").contentWindow.document.body.scrollLeft));
	var y = e.pageY || (e.clientY + (document.getElementById("iframe").contentWindow.document.documentElement.scrollTop || document.getElementById("iframe").contentWindow.document.body.scrollTop));
	return {'x':x,'y':y};
};
guides.close = function(){
	jq_1_7_1("#iframe").contents().find("#slideLateral, #slideLongitudinal").remove();
};
