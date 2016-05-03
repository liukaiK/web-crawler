/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
function hide(obj){ 
	if(document.getElementById(obj).className=="yin_1"){
		jq_1_7_1(".cy_toolbar_bg_table ul li dl.xian_1").removeClass("xian_1").addClass("yin_1");
		document.getElementById(obj).className="xian_1";
		
	}else{
		//document.getElementById(obj).className="yin_1";
		jq_1_7_1(".cy_toolbar_bg_table ul li dl.xian_1").removeClass("xian_1").addClass("yin_1");
	}
}
function hideremove(){
	jq_1_7_1(".cy_toolbar_bg_table ul li dl.xian_1").attr("class","yin_1");
}
function hideover(obj) {
	document.getElementById(obj).className="xian_1";
}

var fullScreenMode = {};
var statusfull = "on";
var toolbarIframeHeight = 0;

fullScreenMode.fullScreenMode = function(){
	var browserName = navigator.userAgent.toLowerCase();
	if (statusfull == "on") {
		if (/msie/i.test(browserName) && !/opera/.test(browserName)) {
		//if (navigator.appName == "Microsoft Internet Explorer") {
			/*if (navigator.appVersion.match(/8./i)=="8.") {
				alert("IE 8.0");
			}*/
			/*var WshShell = new ActiveXObject("WScript.Shell");
			WshShell.SendKeys('{F11}');
			return;*/
			speak.sound.voicePrompt.fullscreen();
			jq_1_7_1("#toolbar_prompt").fadeIn(1000, function() {
				jq_1_7_1("#toolbar_prompt").fadeOut(5000);
			});
		} else {
			var el = document.documentElement;
			var rfs = el.requestFullScreen || el.webkitRequestFullScreen || 
			el.mozRequestFullScreen || el.msRequestFullScreen;
			if(typeof rfs != "undefined" && rfs) {
				rfs.call(el);
			} else if(typeof window.ActiveXObject != "undefined") {
				//for IE，这里其实就是模拟了按下键盘的F11，使浏览器全屏
				var wscript = new ActiveXObject("WScript.Shell");
				if(wscript != null) {
					wscript.SendKeys("{F11}");
				}
			}
		}
		statusfull = "off";
	} else if (statusfull == "off") {
		if (/msie/i.test(browserName) && !/opera/.test(browserName)) {
		//if (navigator.appName == "Microsoft Internet Explorer") {
			/*if (navigator.appVersion.match(/8./i)=="8.") {
				alert("IE 8.0");
			}*/
			/*var WshShell = new ActiveXObject("WScript.Shell");
			WshShell.SendKeys('{F11}');
			return;*/
			speak.sound.voicePrompt.fullscreen();
			jq_1_7_1("#toolbar_prompt").fadeIn(1000, function() {
				jq_1_7_1("#toolbar_prompt").fadeOut(5000);
			});
		} else {
			var el = document;
			var cfs = el.cancelFullScreen || el.webkitCancelFullScreen || 
			el.mozCancelFullScreen || el.exitFullScreen;
			if(typeof cfs != "undefined" && cfs) {
				cfs.call(el);
			} else if(typeof window.ActiveXObject != "undefined") {
				//for IE，这里和fullScreen相同，模拟按下F11键退出全屏
				var wscript = new ActiveXObject("WScript.Shell");
				if(wscript != null) {
					wscript.SendKeys("{F11}");
				}
			}
		}
		statusfull = "on";
	}
};

function closeToolbar() {
	location.href = esd_index_url;
}

/*function getClientHeight() {
	var clientHeight=0;
	if(document.body.clientHeight&&document.documentElement.clientHeight) {
		var clientHeight = (document.body.clientHeight<document.documentElement.clientHeight)?document.body.clientHeight:document.documentElement.clientHeight;
	} else {
		var clientHeight = (document.body.clientHeight>document.documentElement.clientHeight)?document.body.clientHeight:document.documentElement.clientHeight;
	}
	//alert(clientHeight);
	toolbarIframeHeight = clientHeight - 107;
	//alert(toolbarIframeHeight);
	document.getElementById("iframe").style.height = toolbarIframeHeight;
	//return clientHeight;
	jq_1_7_1(esd_tool_iframe).find("a").click(function(){
		//ESDWebApp.defalt.pageReload();
		parent.location.reload();
	});
}*/
