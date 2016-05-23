/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 初始化模块
 * */


var jq_1_7_1 = $.noConflict(true);
var esd_tool_iframe = null;
var esd_index_url="wca.html?414912d3ea79ba25087138cbef813934.html";
var iframe_change=0;

jq_1_7_1(document).ready(function(){
	var timestamp3 = new Date().getTime(); 
	var src = jq_1_7_1("iframe").attr("src");
	jq_1_7_1("iframe").attr("src",src+"?"+timestamp3);
	jq_1_7_1(".showbox").css("display", "block");
	var h = jq_1_7_1(top.document).height();
	//在哪页打开工具条就在哪页加载
	var str = location.href; // 取得整个地址栏
	var num = str.lastIndexOf("?");
	str = str.substr(num + 1); // 取得所有参数 stringvar.substr(start [, length ]
	jq_1_7_1("#iframe").attr("src", str);
	
	jq_1_7_1(".overlay").css({"height": h});
	jq_1_7_1(".overlay").css({'display':'block','opacity':'0.8'});
	jq_1_7_1(".showbox").stop(true).animate({'margin-top':'300px','opacity':'1'}, 200);
	jq_1_7_1.ajaxSetup ({ cache: false });
	jq_1_7_1("iframe").load(function(){
		iframe_change++;//为了控制iframe点击翻页后只读对象还原对象样式时产生的权限问题，在IE下问题会出现
		esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
//		jq_1_7_1(esd_tool_iframe).find("*").removeAttr("target");
		ESDWebApp.toolbar.initContent();
		if(iframe_change==1){//初始化第一次加载时
			statuspointread="off";
			ESDWebApp.toolbar.toolbarPointread();
		}
		setTimeout(function(){
			jq_1_7_1(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'}, 400,function(){
				jq_1_7_1(".showbox").css("display", "none");//将div样式设为隐藏 否则偶尔会挡到字读不出来
			});
			jq_1_7_1(".overlay").css({'display':'none', 'opacity':'0'});
		}, 500);
		// 为子页面添加id=esd_toolbar_ctrl即可达到关闭工具栏的目的
		jq_1_7_1(esd_tool_iframe).find("#esd_toolbar_ctrl").text("关闭无障碍辅助工具");
		jq_1_7_1(esd_tool_iframe).find("#esd_toolbar_ctrl").attr("title", "关闭无障碍辅助工具");
		var url = window.frames[0].location.href;
		var t = jq_1_7_1(esd_tool_iframe).find("#esd_toolbar_ctrl").attr("href", "javascript:window.top.location.href='" + url + "'");
		if(document.getElementById("iframe").contentWindow.document.title!=""){
			document.title=document.getElementById("iframe").contentWindow.document.title;			
		}
	});
	soundManager.setup({
		useFlashBlock: false,
		url: speak.sound.url.localSWF, 
		debugMode: false,
		consoleOnly: false
	});
	soundManager.useFastPolling = false;
	soundManager.onready(function() {
		var toolbarStatus = "";
		var soundUrl = "";
		toolbarStatus = storage.getCookie("toolbarStatus");
		speak.sound.voicePrompt.shiftz();
		//if(statuspointread=="on"){
			//ESDWebApp.toolbar.toolbarPointread();
		//}
			
   });
	getToolbarHeight();
	ESDWebApp.toolbar.toolbarSpeakOnOff();
	ESDWebApp.toolbar.toolbarOnOff();
});

var toolbar_percent = 100;
var statustoolbar = "on";
var toolbarnum = 1;

ESDWebApp.toolbar = {};
ESDWebApp.toolbar.toolbarStatus = {
	open:"open",
	close:"close",
	opened:false
};

ESDWebApp.toolbar.openMehtods = 0;//打开工具栏的方式，0为未设置cookie，1为已设cookie
ESDWebApp.toolbar.splitBody = function(){
};

ESDWebApp.toolbar.openOrCloseToolbar = function(){
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	jq_1_7_1(esd_tool_iframe).find("#openOrCloseToolbar").each(function () {
		jq_1_7_1(this).html("关闭工具");
		jq_1_7_1(this).attr("onclick", "openOrCloseToolbar('close')");
	});
};

//初始化toolbar
ESDWebApp.toolbar.initContent = function(){
	
	//判断浏览器
	ESDWebApp.defalt.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
	ESDWebApp.defalt.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
	ESDWebApp.defalt.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
	ESDWebApp.defalt.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());
	
	//ESDWebApp.toolbar.openOrCloseToolbar();
	
	ESDWebApp.toolbar.pageRebuild();
	
	ESDWebApp.toolbar.speakInit();
	
	ESDWebApp.toolbar.keyBinding();
	
	ESDWebApp.toolbar.addToolbarBtn();
	
	ESDWebApp.defalt.iframeLink();
	
	ESDWebApp.toolbar.pageContent();
};

ESDWebApp.toolbar.pageContent = function(){
	if (statusguides == "off") {
		guides.open();
	} else if (status == "off") {
		status = "on";
		textMode.textMode();
	}
	
//	jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
//		if (changeFont.statusFont != 0) {
//			jq_1_7_1(this).css("font-size", changeFont.statusFont + "px");
//		}
//	});
	if(changeFont.fontZoom>1){		
		jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
			jq_1_7_1(this).css("font-size",function(index,value){
				var basicPX = parseFloat(value);
				return (basicPX*changeFont.fontZoom)+"px";
			});
		});	
	}
	
	if (highContrast.pageColor != "") {
		highContrast.changeTheme(highContrast.pageColor);
	}

	if (readZoom != 0) {
		if (ESDWebApp.defalt.browser.mozilla || ESDWebApp.defalt.browser.opera) {
			jq_1_7_1(esd_tool_iframe).css({"transform":"scale(" + readZoom + ")","transform-origin":"top"});
		} else {
			jq_1_7_1(esd_tool_iframe).css({"zoom":readZoom, "overflow":"scroll"});
		}
	}

	
	if (statusspeakOnOff == "off") {
		var zdRead = statusbatchRead;
		var sdRead = statuspointread;

		//jq_1_7_1("#toolbar_speakOnOff").trigger("click");
		//jq_1_7_1("#toolbar_speakOnOff").trigger("click");
		speak.sound.automateSpeakClose();
		speak.sound.closePointSpeak();
		if (zdRead == "off") {
			//jq_1_7_1("#toolbar_batchRead").trigger("click");
			statusbatchRead="on";
			speak.toolbarBatchRead();
		}
		
		else if(sdRead == "off") {
			statuspointread="on";
			speak.toolbarPointread();
			//jq_1_7_1("#toolbar_Pointread").trigger("click");
		}
		
	}
	
	if (statusmagnifier == "off") {
		var pykg = magnifier.pyzt;
		var jfkg = statusfont;
		ESDWebApp.toolbar.magnifierClose();
		ESDWebApp.toolbar.magnifierOpen();
		if (pykg == true) {
			magnifier.showpinyin();
		}
		if (jfkg == "traditional") {
			jq_1_7_1("#toolbar_traditional").trigger("click");
		}
	}
	
	/*
	if (trans.status) {
		trans.status = false;
		jq_1_7_1("#toolbar_translation").trigger("click");
	}
	
	if (statushandwriting == "off") {
		statushandwriting = "on";
		jq_1_7_1("#toolbar_handwriting").trigger("click");
	}
	*/
};

//修改页面
ESDWebApp.toolbar.pageRebuild = function(){
	pageRebuild.rebuild();
};

ESDWebApp.toolbar.toolbarOnOff = function(){
	//if (toolbarnum == 1) {
		ESDWebApp.toolbar.opentoolbar();
		//speak.sound.voicePrompt.shift0();
		//speak.sound.voicePrompt.shiftz();
		//toolbarnum++;
	//}
};

//添加工具栏开关按钮
ESDWebApp.toolbar.addToolbarBtn = function(){
	var toolbarStatus = "";
	
	toolbarStatus = storage.getCookie("toolbarStatus");
	//如果cookie中状态为"open"，则打开工具栏
	if(toolbarStatus == ESDWebApp.toolbar.toolbarStatus.open){
		ESDWebApp.toolbar.openMehtods = 1;
		jq_1_7_1("#toolbar_action").trigger("click");
	};
	
	ESDWebApp.toolbar.toolbarStatus.opened = true;
	storage.setCookie("toolbarStatus","open",360);
};

//开启工具栏
ESDWebApp.toolbar.opentoolbar = function(){
	//jq_1_7_1("body"). prepend(str);//将工具栏写入页面
	ESDWebApp.toolbar.toolbarStatus.opened = true;//设置工具栏状态为“打开”
	ESDWebApp.toolbar.bindAction();//绑定各功能
	storage.setCookie("toolbarStatus",ESDWebApp.toolbar.toolbarStatus.open,360);
};

ESDWebApp.toolbar.closetoolbar = function(){
	
	jq_1_7_1("#toolbar").remove();
	jq_1_7_1("body").css("margin-top","0px");
	jq_1_7_1("#toolbar_action").html("加载工具栏").removeClass("toolbarOpen");
	
	ESDWebApp.toolbar.openMehtods = 0;
	
	ESDWebApp.toolbar.toolbarStatus.opened = false;
	
	storage.setCookie("toolbarStatus","close",360);
	
	magnifier.toolbarMagnifier();
};


//判断元素是否存在于标签集合中。
ESDWebApp.toolbar.findvar = function(obj,sz){   
	var b = false;
	for (var i = 0; i < sz.length; i++) {
		if (sz[i] == obj) {
			b = true;
			break;
		}
	}
	return b;
};
//处理获取元素内容 	  传入JS对象。
ESDWebApp.toolbar.manageText = function(obj){
	var text="";
	//if(obj.tagName =="P"){
		//text = jq_1_7_1(obj).text();
		//alert(text);
	//}
	//if(ESDWebApp.toolbar.findvar(obj.tagName,['P','SPAN','A','H1','H2','H3','H4','H5','H6','FONT','EM','STRONG','U','INS'])){
	if(ESDWebApp.toolbar.findvar(obj.tagName,['INS'])){
		if(jq_1_7_1(obj).children("script").length>0){
			return text;
		}
		if(obj.children.length>0 && obj.children[0].nodeType == 1){
			return text;
		}
		//检查display属性是否为none
		var dis = obj.currentStyle?obj.currentStyle.display/*ie*/:document.defaultView.getComputedStyle(obj,null).display;
		if(dis == "none"){return text;}
		//处理innerHTML内容，
//	 if(obj.tagName == "A"){//读取a标签中的title内容            //A链接不被INS包了 这段代码失效
//			if(obj.title!=null&&obj.title.length!=""){
//				text = obj.title.replace(/<.+?>/g,'');
//			}else {
//				text = obj.innerHTML.replace(/<.+?>/g,'');
//			}
//			text = "链接："+text;
//		}else{
			text=obj.innerHTML.replace(/<.+?>/g,'');
//		}
		
		text=text.replace(/\s/g, '');
	//读取a标签中的title内容 如果A链接的子元素是img按下方图片处理
	}else if(obj.tagName == "A"&&!(jq_1_7_1(obj).find("img").is("img"))){
		if(obj.title!=null&&obj.title.length!=""){
			text = obj.title.replace(/<.+?>/g,'');
		}else {
			text = obj.innerHTML.replace(/<.+?>/g,'');
		}
		text = "链接："+text;
	}else if(obj.tagName == "SELECT"){
		var selected = obj;
		for(var i=0;i<selected.options.length;i++){
			if(selected.options[i].selected){
//				text = selected.options[i].value;   //应该读文字不是value
				text = jq_1_7_1(selected.options[i]).children()[0].innerHTML;
				text = "列表框："+text;
				break;
			}
		}
		//var selected = obj.selectedOptions[0].text;
		text = text.replace(/\s/g, '');
	}else if(obj.tagName == "INPUT"){
		var type = jq_1_7_1(obj).attr("type");
		if(type == "hidden"){
			return text;
		}else{
			text=obj.value.replace(/\s/g, '');
			if(type=="text"){
				text = "文本输入框："+text;
			}else if(type=="radio"){
				text = "丹选按钮："+text;
			}else if(type=="image"){
				text = "图像式的提交按钮："+text;
			}else if(type=="checkbox"){
				text = "复选框："+text;
			}else if(type=="file"){
				text = "文件上传按钮："+text;
			}else if(type=="password"){
				text = "密码输入框："+text;
			}else if(type=="reset"){
				text = "重置按钮："+text;
			}else if(type=="submit"){
				text = "提交按钮："+text;
			}else if(type=="button"){
				text = "按钮："+text;
			}else{
				text = "文本输入框："+text;
			}
		}
	}else if(obj.tagName == "IMG"){
		if(obj.alt!=null&&obj.alt!=""&&obj.alt.replace(/\s/g, '') != "无意义图片"){
			text = obj.alt.replace(/\s/g, '');
			if(jq_1_7_1(obj).parents().is("a")){
				text = "图片链接："+text;
			}else{
				text = "图片："+text;
			}
			return text;
		}else{
			if(jq_1_7_1(obj).parents().is("a")){
				text = (jq_1_7_1(obj).parents('a').attr("title")!=undefined)? jq_1_7_1(obj).parents('a').attr("title") : "";
				text = "图片链接："+text.replace(/\s/g, '');
				return text;
			}
			return "图片";
		}
	}else if (obj.tagName == "OBJECT") {
		//alert(obj.title.replace(/\s/g, ''));
		text = obj.title.replace(/\s/g, '');
		text = "多媒体："+text;
		
	}else if(obj.tagName == "BUTTON"){
		if(obj.innerHTML!=null&&obj.innerHTML.length!=""){
			text = obj.innerHTML.replace(/<.+?>/g,'');
			text = "按钮："+text;
		}else if(obj.title!=null&&obj.title.length!=""){
			text = obj.title.replace(/<.+?>/g,'');
			text = "按钮："+text;
		}else{
			return "按钮";
		}
		return text;
	}else if(obj.tagName == "SPAN"){//针对在<a><img><span>工作动态</span></a>这种形式的链接的调整
		if(jq_1_7_1(obj).parents().is("a")) {
			if(jq_1_7_1(obj).parents('a').attr("title")!=undefined){
				text = jq_1_7_1(obj).parents('a').attr("title");
			}else{
				text = obj.innerHTML.replace(/<.+?>/g,'');
			}
			text = "链接："+text;
		}
	}else if(obj.tagName == "TEXTAREA"){
		text=obj.value.replace(/\s/g, '');
		text = "文本域："+text;
	}
	/*--------------------------测试Iframe------------------------------------------------------*/
	/*else if(obj.title!=null&&obj.title.length!=""){
		text=obj.title.replace(/\s/g, '');
	}else if(obj.alt!=null&&obj.alt!=""){
		text=obj.alt.replace(/\s/g, '');
	}*/;
	text = text.replace(/\|/g,'');
	text = text.replace(/\·/g,'');
	text = text.replace(/&lt;/g,'');
	text = text.replace(/&gt;/g,'');
	text = text.replace(/&nbsp;/g,'');
	text = text.replace(/\-/g,'');
	text = text.replace(/\→/g,'');
	return text;
};
//检查是否有文本节点，并返回文本内容
ESDWebApp.toolbar.checkNodeType = function(obj){
	var text = "";
	for(var i=0;i<obj.length;i++){
		if(obj[i].nodeType == 3){//nodeType = 3 为文本节点
			text += obj[i].nodeValue.replace(/\s/g, '');
		}
	}
	return text;
};

ESDWebApp.toolbar.homeMode = function(){
	window.location.href=esd_index_url;
};

ESDWebApp.toolbar.helpMode = function(){
	window.open(ESDWebApp.defalt.fileURL.toolbar + "help.html");
};

ESDWebApp.toolbar.exitMode = function(){
//	var src=jq_1_7_1("#iframe").attr("src");
	var src = window.frames[0].location.href;
	window.location.href=src;
};

ESDWebApp.toolbar.retreatMode = function(){
	//document.getElementById("iframe").contentWindow.history.back();
	//alert(document.getElementById("iframe").contentWindow.history.length);
	//return false;
	if (window.frames["iframe"].history) {
		window.frames["iframe"].history.back();
    }
	
};

ESDWebApp.toolbar.aheadMode = function(){
	//document.getElementById("iframe").contentWindow.history.forward();
	if (window.frames["iframe"].history) {
		window.frames["iframe"].history.forward();
    }
};


ESDWebApp.toolbar.changeArrow_1 = function(){
	cursor.changeArrow('arrow_1');
};

ESDWebApp.toolbar.changeArrow_2 = function(){
	cursor.changeArrow('arrow_2');
};

ESDWebApp.toolbar.changeArrow_3 = function(){
	cursor.changeArrow('arrow_3');
};

ESDWebApp.toolbar.changeArrow_4 = function(){
	cursor.changeArrow('arrow_4');
};

ESDWebApp.toolbar.changeArrow_5 = function(){
	cursor.changeArrow('arrow_5');
};

ESDWebApp.toolbar.refreshMode = function(){
	ESDWebApp.defalt.iframeReload();
	//ESDWebApp.defalt.pageReload();
};

ESDWebApp.toolbar.fullScreenMode = function(){
	fullScreenMode.fullScreenMode();
};

ESDWebApp.toolbar.highContrastYellow = function(){
	highContrast.yellowMode();
};

ESDWebApp.toolbar.highContrastWhiteBlack = function(){
	highContrast.whiteBlackMode();
};
ESDWebApp.toolbar.highContrastBlueYellow = function(){
	highContrast.blueYellowMode();
};
ESDWebApp.toolbar.highContrastYellowBlack = function(){
	highContrast.yellowBlackMode();
};

ESDWebApp.toolbar.highContrastWhite = function(){
	highContrast.whiteMode();
};
ESDWebApp.toolbar.highContrastDefalt = function(){
	highContrast.defaltMode();
};
ESDWebApp.toolbar.changeFontBigger = function(){
	changeFont.fontBigger();
};
ESDWebApp.toolbar.changeFontSmaller = function(){
	changeFont.fontSmaller();
};
ESDWebApp.toolbar.changeFontDefault = function(){
	changeFont.fontDefault();
};
ESDWebApp.toolbar.pageZoomIc = function(){
	pageZoom.increasePage();
};
ESDWebApp.toolbar.pageZoomDc = function(){
	pageZoom.decreasePage();
};
ESDWebApp.toolbar.pageZoomDefalt = function(){
	pageZoom.defaultPage();
};
ESDWebApp.toolbar.textModeOn = function(){
	textMode.textModeON();
};
ESDWebApp.toolbar.textModeOff = function(){
	textMode.textModeOff();
};

ESDWebApp.toolbar.textMode = function() {
	textMode.textMode();
};

ESDWebApp.toolbar.speakInit = function(){
	speak.sound.init();
};
ESDWebApp.toolbar.speakAutoOpen = function(){
	speak.sound.automateSpeak();
};
ESDWebApp.toolbar.speakAutoClose = function(){
	speak.sound.automateSpeakClose();
};
ESDWebApp.toolbar.speakPointOpen = function(){
	speak.sound.startPointSpeak();
};
ESDWebApp.toolbar.speakPointClose = function(){
	speak.sound.closePointSpeak();
};
ESDWebApp.toolbar.speakKeybordOpen = function(){
	speak.sound.openKeybordSpeak();
};
ESDWebApp.toolbar.speakKeybordClose = function(){
	speak.sound.closeKeybordSpeak();
};
ESDWebApp.toolbar.magnifierOpen = function(){
	magnifier.openMagnifier();
};
ESDWebApp.toolbar.magnifierClose = function(){
	magnifier.closeMagnifier();
};
ESDWebApp.toolbar.guidesOpen = function(){
	guides.open();
};
ESDWebApp.toolbar.guidesClose = function(){
	guides.close();
};
ESDWebApp.toolbar.translate = function(){
	trans.toggleTranslate();
};
ESDWebApp.toolbar.keyBinding = function(){
	keybinding.bind();
};

ESDWebApp.toolbar.toolbarMagnifier = function(){
	magnifier.toolbarMagnifier();
};

ESDWebApp.toolbar.toolbarGuides = function(){
	guides.toolbarGuides();
};

ESDWebApp.toolbar.toolbarBatchRead = function(){
	speak.toolbarBatchRead();
};

ESDWebApp.toolbar.toolbarPointread = function(){
	speak.toolbarPointread();
};

ESDWebApp.toolbar.toolbarSpeakOnOff = function(){
	speak.toolbarSpeakOnOff();
};

ESDWebApp.toolbar.handwriting = function(){
	handwriting.toolbarHandwriting();
};

ESDWebApp.toolbar.bindAction = function(){
	/*//文本模式开启
	jq_1_7_1("#toolbar_textMode_on").click(function(){
		ESDWebApp.toolbar.textModeOn();
	});
	//文本模式关闭
	jq_1_7_1("#toolbar_textMode_off").click(function(){
		alert(2);
		ESDWebApp.toolbar.textModeOff();
	});*/
	
	//首页
	jq_1_7_1("#toolbar_home").click(function(){
		ESDWebApp.toolbar.homeMode();
	});
	
	//帮助
	jq_1_7_1("#toolbar_help").click(function(){
		ESDWebApp.toolbar.helpMode();
	});
	
	//退出工具条
	jq_1_7_1("#toolbar_exit").click(function(){
		ESDWebApp.toolbar.exitMode();
	});
	
	//页面后退
	jq_1_7_1("#toolbar_retreat").click(function(){
		ESDWebApp.toolbar.retreatMode();
	});
	
	//页面前进
	jq_1_7_1("#toolbar_ahead").click(function(){
		ESDWebApp.toolbar.aheadMode();
	});
	
	//页面刷新 修改为箭头功能
//	jq_1_7_1("#toolbar_refresh").click(function(){
//		ESDWebApp.toolbar.refreshMode();
//	});
	// 箭头
	jq_1_7_1(".arrow_1").click(function() {
		ESDWebApp.toolbar.changeArrow_1();
	});
	jq_1_7_1(".arrow_2").click(function() {
		ESDWebApp.toolbar.changeArrow_2();
	});
	jq_1_7_1(".arrow_3").click(function() {
		ESDWebApp.toolbar.changeArrow_3();
	});
	jq_1_7_1(".arrow_4").click(function() {
		ESDWebApp.toolbar.changeArrow_4();
	});
	jq_1_7_1(".arrow_5").click(function() {
		ESDWebApp.toolbar.changeArrow_5();
	});

	//页面全屏
	jq_1_7_1("#toolbar_fullScreen").click(function(){
		ESDWebApp.toolbar.fullScreenMode();
	});
	
	//文本模式开关
	jq_1_7_1("#toolbar_textMode").click(function(){
		ESDWebApp.toolbar.textMode();
	});
	
	//增加字体大小
	jq_1_7_1("#toolbar_text_bigger").click(function(){
		ESDWebApp.toolbar.changeFontBigger();
	});
	//减小字体
	jq_1_7_1("#toolbar_text_smaller").click(function(){
		ESDWebApp.toolbar.changeFontSmaller();
	});
	//还原字体
	jq_1_7_1("#toolbar_text_default").click(function(){
		ESDWebApp.toolbar.changeFontDefault();
	});
	//放大页面
	jq_1_7_1("#toolbar_pageZoomIc").click(function(){
		ESDWebApp.toolbar.pageZoomIc();
	});
	//缩小页面
	jq_1_7_1("#toolbar_pageZoomDc").click(function(){
		ESDWebApp.toolbar.pageZoomDc();
	});
	//还原页面
	jq_1_7_1("#toolbar_pageZoomDefalt").click(function(){
		ESDWebApp.toolbar.pageZoomDefalt();
	});
	/*//辅助线控制
	jq_1_7_1("#toolbar_guides").toggle(
		function(){jq_1_7_1("#toolbar_guides").addClass("guidesOn");ESDWebApp.toolbar.guidesOpen();},
		function(){jq_1_7_1("#toolbar_guides").removeClass("guidesOn");ESDWebApp.toolbar.guidesClose();}
	);*/
	
	jq_1_7_1("#toolbar_guides").click(function(){
		ESDWebApp.toolbar.toolbarGuides();
	});
	
	//高对比模式1，黑底黄字白链接
	//jq_1_7_1("#toolbar_highContrastYellow").click(function(){
	jq_1_7_1(".toolbar_highContrastYellow").click(function(){
		ESDWebApp.toolbar.highContrastYellow();
	});
	
	//白底黑字蓝链接
	jq_1_7_1(".toolbar_highContrastWhiteBlack").click(function(){
		ESDWebApp.toolbar.highContrastWhiteBlack();
	});
	//蓝底黄字白链接
	jq_1_7_1(".toolbar_highContrastBlueYellow").click(function(){
		ESDWebApp.toolbar.highContrastBlueYellow();
	});
	//黄底黑字蓝链接
	jq_1_7_1(".toolbar_highContrastYellowBlack").click(function(){
		ESDWebApp.toolbar.highContrastYellowBlack();
	});
	
	//高对比模式2
	jq_1_7_1("#toolbar_highContrastWhite").click(function(){
		ESDWebApp.toolbar.highContrastWhite();
	});
	//正常模式，页面原始配色
	//jq_1_7_1("#toolbar_highContrastDefalt").click(function(){
	jq_1_7_1(".toolbar_highContrastDefalt").click(function(){
		ESDWebApp.toolbar.highContrastDefalt();
	});
	//放大镜
	/*jq_1_7_1("#toolbar_magnifier").toggle(
		function(){jq_1_7_1("#toolbar_magnifier").addClass("magnifierOn");ESDWebApp.toolbar.magnifierOpen();},
		function(){jq_1_7_1("#toolbar_magnifier").removeClass("magnifierOn");ESDWebApp.toolbar.magnifierClose();}
	);*/
	jq_1_7_1("#toolbar_magnifier").click(function(){
		ESDWebApp.toolbar.toolbarMagnifier();
	});
	
	jq_1_7_1("#toolbar_Pointread").click(function(){
		if (statusspeakOnOff == "off") {
			ESDWebApp.toolbar.toolbarPointread();
		}
	});
	
	//绑定自动朗读事件
	/*jq_1_7_1("#toolbar_batchRead").toggle(
		function(){jq_1_7_1("#toolbar_batchRead").addClass("batchReadOn");ESDWebApp.toolbar.speakAutoOpen();},
		function(){jq_1_7_1("#toolbar_batchRead").removeClass("batchReadOn");ESDWebApp.toolbar.speakAutoClose();}
	);*/
	
	jq_1_7_1("#toolbar_batchRead").click(function(){
		if (statusspeakOnOff == "off") {
			ESDWebApp.toolbar.toolbarBatchRead();
		}
	});
	
	//语音开关
	jq_1_7_1("#toolbar_speakOnOff").click(function(){
		ESDWebApp.toolbar.toolbarSpeakOnOff();
	});
	
	//键盘朗读
	jq_1_7_1("#toolbar_keybordRead").toggle(
		function(){jq_1_7_1("#toolbar_keybordRead").addClass("keybordReadOn");ESDWebApp.toolbar.speakKeybordOpen();},
		function(){jq_1_7_1("#toolbar_keybordRead").removeClass("keybordReadOn");ESDWebApp.toolbar.speakKeybordClose();}
	);
	//在线翻译
	jq_1_7_1("#toolbar_translation").click(function(){
		ESDWebApp.toolbar.translate();
	});
	
	//手写功能
	jq_1_7_1("#toolbar_handwriting").click(function(){
		ESDWebApp.toolbar.handwriting();
	});
};

function getToolbarHeight() {
	var clientHeight=0;
	clientHeight = document.documentElement.clientHeight;
	if (statusmagnifier == "on") {
		toolbarIframeHeight = clientHeight - 107;
	} else if (statusmagnifier == "off") {
		toolbarIframeHeight = clientHeight - 247;
	}
	jq_1_7_1("#iframe").attr("height", toolbarIframeHeight);
}

jq_1_7_1(document).ready(function(){
	jq_1_7_1(window).resize(function(){
		getToolbarHeight();
	});
});

//站外链接提示
jq_1_7_1(document).ready(function() {
	jq_1_7_1('#iframe').load(function() {
		jq_1_7_1(this).contents().find('a:not([href^="javascript"])').click(function() {
			//获取当前网址为http:localhost:18080/toolbarmini/wca.html
			//获取当前网址按/切割后的数组：http:,,localhost:18080,toolbarmini,
			var curWwwPathArray=window.document.location.href.split('/');
			var aHrefArray = this.href.split('/');
			//curWwwPathArray[2]+"/"+curWwwPathArray[3]    localhost:18080/toolbarmini
			if(curWwwPathArray[2]!=aHrefArray[2]){
				var href = this.href;
//				jq_1_7_1("#zwlj_prompt").show();
//				jq_1_7_1("#zwlj_bt1").click(function(){
					window.open(href);
//					jq_1_7_1("#zwlj_prompt").hide();
//				});
//				jq_1_7_1("#zwlj_bt2").click(function(){
//					jq_1_7_1("#zwlj_prompt").hide();
//				});
				return false;
			}
		});
	});
});

//按F5后只刷新iframe    
jq_1_7_1(document).ready(function(){
	//当焦点在工具条时按F5的拦截事件
	jq_1_7_1(document).keydown(function(event){
		if(event.keyCode==116){
	       ESDWebApp.toolbar.refreshMode();
	       return false; 
		}
	});
	
	//当焦点在iframe时按F5的拦截事件
	jq_1_7_1('#iframe').load(function() {
		var iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
		jq_1_7_1(iframe).keydown(function(event){
			if(event.keyCode==116){
		       ESDWebApp.toolbar.refreshMode();
		       return false; 
			}
		});
	});
});