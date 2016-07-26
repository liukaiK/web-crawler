/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 放大镜功能
 * 
 */

var magnifier = {};
var statusfont = "simplified";
var statusmagnifier = "on";
var strtext = "";
magnifier.pyzt = false;//拼音功能状态

magnifier.toolbarMagnifier = function(){
	if (statusmagnifier == "off") {
		ESDWebApp.toolbar.magnifierClose();
		statusmagnifier = "on";
	} else {
		//alert(statusmagnifier);
		ESDWebApp.toolbar.magnifierOpen();
		statusmagnifier = "off";
	}
};
magnifier.setText=function(text){
	if(jq_1_7_1("#ymd_magnifier").length>0){
		if(magnifier.pyzt){
			var pys=new Array();
			var str=new String(magnifier.getpinyin(text));
			pys=str.split(",");
			if(pys.length!=text.length){
				return;
			}
			if (statusfont == "traditional") {
				strtext = traditionalized(text);
			} else {
				strtext = text;
			}
			jq_1_7_1("#ymd_screen tr").remove();
			jq_1_7_1("#ymd_screen tbody").append("<tr></tr><tr></tr><tr></tr><tr></tr>");
			for(var i=0;i<pys.length;i++){
				if(i<20){
					jq_1_7_1("#ymd_screen tr:eq(0)").append("<td >"+pys[i]+"</td>");
					jq_1_7_1("#ymd_screen tr:eq(1)").append("<td >"+strtext.charAt(i)+"</td>");
				}else{
					//设置空td 保证下方tr属于居中状态。
					if(i==20){
						for(var k=0.5;k<(40-pys.length)/2;k++){
							jq_1_7_1("#ymd_screen tr:eq(2)").append("<td ></td>");
							jq_1_7_1("#ymd_screen tr:eq(3)").append("<td ></td>");
						}
					}
					jq_1_7_1("#ymd_screen tr:eq(2)").append("<td >"+pys[i]+"</td>");
					jq_1_7_1("#ymd_screen tr:eq(3)").append("<td >"+strtext.charAt(i)+"</td>");
				}
			}
		}else{
			if (statusfont == "traditional") {
				strtext = traditionalized(text);
				jq_1_7_1("#ymd_magnifier span").html(strtext);
			} else {
				jq_1_7_1("#ymd_magnifier span").html(text);
			}
		}
	}
};
//打开放大镜功能
magnifier.openMagnifier = function(){
	var text="";

	/*var str ='<div id="ymd_magnifier">'+
				'<div id="ymd_screen">'+
					'<div id="ymd_magnifier_left">'+
						'<span></span>'+
					'</div>'+
					'<div id="ymd_magnifier_right">'+
						'<input type="button" value="关闭放大镜" id="ymd_magnifierClose" />'+
						'<input type="button" value="开启拼音" id="ymd_zhpy" />'+
					'</div>'+
				'</div>'+
			'</div>';*/

	var str ='<div id="ymd_magnifier">'+
				'<div id="ymd_screen">'+
					'<div id="ymd_magnifier_left">'+
						'<span></span>'+
					'</div>'+
					'<div id="ymd_magnifier_right">'+
						'<ul>'+
							'<li id="showscreen_right_closs">'+
								'<a href="javascript:void(0);" id="ymd_magnifierClose" title="关闭" ><img class="ESDAssetsTextCon" src="toolbar/img/closed1.png" title="关闭" alt="关闭"/></a>'+
							'</li>'+
							'<li id="showscreen_right_screen_zh">'+
								'<a href="javascript:void(0);" id="toolbar_simplified"  title="简体" ><img class="ESDAssetsTextCon" src="toolbar/img/zh1.png" title="简体" alt="简体"/></a>'+
								'<a href="javascript:void(0);" id="toolbar_traditional"  title="繁体" ><img class="ESDAssetsTextCon" src="toolbar/img/tw1.png" title="繁体" alt="繁体"/></a>'+
							'</li >'+
							'<li id="showscreen_right_screen_py">'+
								'<a href="javascript:void(0);" id="ymd_zhpy" title="拼音开关" ><img class="ESDAssetsTextCon" src="toolbar/img/py1.png" title="拼音开关" alt="拼音开关"/></a>'+
							'</li >'+
						'</ul>'+
					'</div>'+
				'</div>'+
			'</div>';

	jq_1_7_1("body").append(str);
	
	toolbarIframeHeight = toolbarIframeHeight - 140;
	//alert(toolbarIframeHeight);
	//document.getElementById("iframe").style.height = toolbarIframeHeight;
	jq_1_7_1("#iframe").attr("height", toolbarIframeHeight);
	
	jq_1_7_1("#ymd_magnifierClose").click(function(){jq_1_7_1("#toolbar_magnifier").trigger("click");});
	
	jq_1_7_1("#ymd_zhpy").click(function(){magnifier.showpinyin();});
	
	jq_1_7_1("#toolbar_simplified").click(function(){
		statusfont = "simplified";
	});
	
	jq_1_7_1("#toolbar_traditional").click(function(){
		statusfont = "traditional";
	});
	
	document.body.onmousemove = function(e){
		e = e || window.event;
		var GuTarget = e.srcElement ? e.srcElement : e.target;
		text = ESDWebApp.toolbar.manageText(GuTarget);

		if(jq_1_7_1("#ymd_magnifier").length>0){
//			if(text.length>40){
//				return;
//			};
			if(magnifier.pyzt){
				var pys=new Array();
				var str=new String(magnifier.getpinyin(text));
				pys=str.split(",");
				if(pys.length!=text.length){
					return;
				}
				if (statusfont == "traditional") {
					strtext = traditionalized(text);
				} else {
					strtext = text;
				}
				jq_1_7_1("#ymd_screen tr").remove();
				jq_1_7_1("#ymd_screen tbody").append("<tr></tr><tr></tr><tr></tr><tr></tr>");
				for(var i=0;i<pys.length;i++){
					if(i<20){
						jq_1_7_1("#ymd_screen tr:eq(0)").append("<td >"+pys[i]+"</td>");
						jq_1_7_1("#ymd_screen tr:eq(1)").append("<td >"+strtext.charAt(i)+"</td>");
					}else{
						//设置空td 保证下方tr属于居中状态。
						if(i==20){
							for(var k=0.5;k<(40-pys.length)/2;k++){
								jq_1_7_1("#ymd_screen tr:eq(2)").append("<td ></td>");
								jq_1_7_1("#ymd_screen tr:eq(3)").append("<td ></td>");
							}
						}
						jq_1_7_1("#ymd_screen tr:eq(2)").append("<td >"+pys[i]+"</td>");
						jq_1_7_1("#ymd_screen tr:eq(3)").append("<td >"+strtext.charAt(i)+"</td>");
					}
				}
			}else{
				if (statusfont == "traditional") {
					strtext = traditionalized(text);
					jq_1_7_1("#ymd_magnifier span").html(strtext);
				} else {
					jq_1_7_1("#ymd_magnifier span").html(text);
				}
			}
		}
	};
	document.getElementById("iframe").contentWindow.document.body.onmousemove = function(e){
		e = e || window.document.getElementById("iframe").contentWindow.event;
		var GuTarget = e.srcElement ? e.srcElement : e.target;
		text = ESDWebApp.toolbar.manageText(GuTarget);
		if(text==""){
			return;
		}
		magnifier.setText(text);
	};
};

//关闭放大镜功能
magnifier.closeMagnifier = function(){
	jq_1_7_1("#ymd_magnifier").remove();
	magnifier.pyzt = false;
	statusfont = "simplified";
	toolbarIframeHeight = toolbarIframeHeight + 140;
	//alert(toolbarIframeHeight);
	//document.getElementById("iframe").style.height = toolbarIframeHeight;
	jq_1_7_1("#iframe").attr("height", toolbarIframeHeight);
};
//开启拼音功能
magnifier.showpinyin = function(){
	if(!magnifier.pyzt){
		magnifier.pyzt=true;
		jq_1_7_1("#ymd_screen span").remove();
		jq_1_7_1("#ymd_zhpy").val("关闭拼音");
		jq_1_7_1("#ymd_magnifier_left").append("<table border='0'  align='center' cellspacing='0' cellpadding='0' style='margin:0 auto;margin-top:10px;' valign='middle'><tbody></tbody></table>");
	}else{
		magnifier.pyzt=false;
		jq_1_7_1("#ymd_screen table").remove();
		jq_1_7_1("#ymd_zhpy").val("开启拼音");
		jq_1_7_1("#ymd_magnifier_left").append("<span></span>");
	}
};
//字符串转化拼音，需要引入pinyin.js文件。
magnifier.getpinyin = function (obj){
	if(obj==""||obj==null){
		return;
	}
	var py="";
	for(var i=0;i<obj.length;i++){
	var z=obj.charAt(i);
		//正则校验，非已知汉字用符号占位。用于下步切割、
	
	 if(/^[A-Za-z]+jq_1_7_1/.test(z)){
		py+=",";
	 }else if(/[,\.;\:"'!，。！《》【】[\]<>；]/.test(z)){
		py+=",";
	 }else if(z in pinyin){
		py=py+pinyin[z]+",";
	}else{
		py+=",";
	}
  }
    //去除最后一个逗号
  	py=py.substr(0,py.length-1);
	return py;
	
};