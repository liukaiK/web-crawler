/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 朗读模块
 * 
 */

var iframeSpeak = {};
iframeSpeak.sound = {};
// 点读播放
iframeSpeak.sound.startPointSpeak = function(GuTarget) {
	alert(window.parent.esd_tool_iframe);
	setTimeout(function() {
	window.parent.jq_1_7_1(window.parent.esd_tool_iframe).find(".ESDAssetsTextCon").each(function() {
		var psObj = window.parent.jq_1_7_1(this);
		psObj.bind("mouseover.pSpeak", function() {
			window.parent.pointSpeakTimeId = setTimeout(function() {
				window.parent.speak.sound.callSM2(psObj[0]);
			}, 1000);
			
		});
		psObj.bind("mouseout.pSpeak", function() {
			window.clearTimeout(window.parent.pointSpeakTimeId);
		});
		
	});
	}, 1000);
	//window.parent.jq_1_7_1(window.parent.esd_tool_iframe).find(".ESDAssetsTextCon").each(function() {
	//});

};
function ESDtoolbarInit() {
	//alert("ESDtoolbarInit");
	alert("fff");
	iframeSpeak.sound.startPointSpeak();
}
/**
 * windows.parent.jq_1_7_1(document).ready(function(){
 * iframeSpeak.sound.startPointSpeak(); });
 */
