/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 缩放字体
 * 
 * 
 * */

var changeFont = {};

changeFont.fontZoom = 1;//放大、缩小倍数

changeFont.fontBigger = function(){
	changeFont.fontZoom = (changeFont.fontZoom*10 + 2)/10; // 其实就是changeFont.fontZoom += 0.2;  但是这么直接算小数会出现1.5999999这种不准确的数字
	if(changeFont.fontZoom>2){
		changeFont.fontZoom = 2;
//		alert("文字已放大至最大！");
		return false;
	}
	changeFont.change("bigger");//放大文字
};

changeFont.fontSmaller = function(){
	if(changeFont.fontZoom>1){//当点击放大文字按钮后才能缩小文字
		changeFont.fontZoom = (changeFont.fontZoom*10 - 2)/10; // 理由同上
		changeFont.change("smaller");//缩小文字
	}else{
//		alert("文字已缩小至最小！");
	}
};

changeFont.fontDefault = function(){
	changeFont.fontZoom = 1;
	changeFont.change("fontdefault");//还原文字
};

changeFont.change = function(fontAct){
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
		jq_1_7_1(this).css("font-size",function(index,value){
			if(fontAct == "bigger"){
				var basicPX = parseFloat(value);
				if(changeFont.fontZoom>1.2){
					basicPX = parseFloat(value)/(((changeFont.fontZoom*10)-2)/10); //changeFont.fontZoom-0.2  获取当前字体处于的倍数
				}
				return (basicPX*changeFont.fontZoom)+"px";
			}else if(fontAct == "smaller"){
				var basicPX = parseFloat(value)/(((changeFont.fontZoom*10)+2)/10); //changeFont.fontZoom+0.2  获取当前字体处于的倍数
				return (basicPX*changeFont.fontZoom)+"px";
			}else{
				var basicPX = parseFloat(value)/changeFont.fontZoom;
				return basicPX+"px";
			}
			
		});
	});	
};