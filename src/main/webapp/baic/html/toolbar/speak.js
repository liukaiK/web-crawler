/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 朗读模块
 * 
 */

var speak={};

var statusbatchRead = "on";
var statuspointread = "on";
var statusspeakOnOff = "on";

speak.iframeChange=0;
speak.speedVal = 0;
speak.speedFont = function() {
	if (speak.speedVal > 0) {
		jq_1_7_1(".toolbar_speakSpeed").each(function () {
			jq_1_7_1(this).html("加快语速");
        });
	} else if (speak.speedVal == 0) {
		jq_1_7_1(".toolbar_speakSpeed").each(function () {
			jq_1_7_1(this).html("正常语速");
        });
	} else if (speak.speedVal < 0) {
		jq_1_7_1(".toolbar_speakSpeed").each(function () {
			jq_1_7_1(this).html("减缓语速");
        });
	}
};
speak.speed = function(s){
	if (statusbatchRead == "off" || statuspointread == "off") {
		if (s == 1) {
			if (speak.speedVal < 1) {
				speak.speedVal = speak.speedVal + 1;
			}
		} else if (s == -1) {
			if (speak.speedVal > -1) {
				speak.speedVal = speak.speedVal - 1;
			}
		}
		speak.speedFont();
	}
	//speak.speedVal=s;
};
speak.sound={};
speak.sound.url = {
	localSWF:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.swf,
	alt_0:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.alt0,
	shift_0:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.shift0,
	shift_z:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.shiftz,
	intordus:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.intordus,
	description:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.description,
	fullscreen:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.fullscreen,
	ajaxUrl:toolbarConfig.speak.ajaxUrl,
	batchUrl:toolbarConfig.speak.BatchUrl
};
speak.sound.mp3Object=null;
speak.sound.autoSpeakObj = null;//自动朗读对象
speak.sound.voicePromptObject = null;//播放语音提示对象
speak.index = 0;//页面信息索引 用于自动朗读
speak.source = new Array();//预存页面信息  用于自动朗读
speak.thisdomAuto;//自动朗读对象
speak.thisdomAutoParent;
speak.thisdomAutoParent_type="css";
speak.thisdomAutoParent_back;
speak.thisdom;//指读/键盘朗读对象
speak.thisdomAuto_type="css";//
speak.thisdomAuto_back;//自动朗读对象CSS
speak.thisdomAuto_font;//自动朗读文字对像CSS
speak.thisdom_type="css";//
speak.thisdom_back=null;//指读/键盘朗读对象CSS
speak.thisdom_font=null;//指读/键盘朗读对象

//初始化"WebRoot/swf"
speak.sound.init = function(){
	speak.sound.sendText2Cloud();
	/**
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
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
		ESDWebApp.toolbar.toolbarOnOff();
		setTimeout(function(){
			jq_1_7_1(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'}, 400);
			jq_1_7_1(".overlay").css({'display':'none', 'opacity':'0'});
		}, 500);
   });
   **/
   
};
//将页面中的内容批量发给云
speak.sound.sendText2Cloud = function(){
	var arr = new Array();
	var i = 1;
	//jq_1_7_1("*:not(.displayClass)").each(function(index){
	//jq_1_7_1("#iframe").contents().find("*:not(.displayClass)").each(function(index){
	jq_1_7_1(esd_tool_iframe).find("*:not(.displayClass)").each(function(index){
		var obj = jq_1_7_1(this);
		var text = "";
		text = ESDWebApp.toolbar.manageText(obj[0]);
		
		if(text != null && text != ""){
			obj.attr("tabindex",i).addClass("ESDAssetsTextCon");//为每个带有文本节点的dom增加属性Tabindex，并且增加class属性ESDAssetsTextCon
			i++;
			var de = base64.e64(text);
			arr.push(de);
			
			//保正不能超过5句,每句不能超过100字
		  	if(arr.length>5){
		  		jq_1_7_1.ajax({
				    type:'GET',
				    url:speak.sound.url.batchUrl,
				    dataType:'jsonp',
				    jsonp:"callback",
				    data:{"b":arr,"s":"0"},
				    async: true
		    	});
		  		jq_1_7_1.ajax({
		  			type:'GET',
		  			url:speak.sound.url.batchUrl,
		  			dataType:'jsonp',
		  			jsonp:"callback",
		  			data:{"b":arr,"s":"1"},
		  			async: true
		  		});
		  		jq_1_7_1.ajax({
		  			type:'GET',
		  			url:speak.sound.url.batchUrl,
		  			dataType:'jsonp',
		  			jsonp:"callback",
		  			data:{"b":arr,"s":"-1"},
		  			async: true
		  		});
		    	//清空缓存
		 	 	arr = new Array();
			}
		}
	});
	
	jq_1_7_1("*:not(.displayClass)").each(function(index){
		var obj = jq_1_7_1(this);
		var text = "";
		text = ESDWebApp.toolbar.manageText(obj[0]);
		
		if(text != null && text != ""){
			obj.addClass("ESDAssetsTextCon");//为每个带有文本节点的dom增加属性Tabindex，并且增加class属性ESDAssetsTextCon
			i++;
			var de = base64.e64(text);
			arr.push(de);
			
			//保正不能超过5句,每句不能超过100字
		  	if(arr.length>5){
		  		jq_1_7_1.ajax({
				    type:'GET',
				    url:speak.sound.url.batchUrl,
				    dataType:'jsonp',
				    jsonp:"callback",
				    data:{"b":arr,"s":"0"},
				    async: true
		    	});
		  		jq_1_7_1.ajax({
		  			type:'GET',
		  			url:speak.sound.url.batchUrl,
		  			dataType:'jsonp',
		  			jsonp:"callback",
		  			data:{"b":arr,"s":"1"},
		  			async: true
		  		});
		  		jq_1_7_1.ajax({
		  			type:'GET',
		  			url:speak.sound.url.batchUrl,
		  			dataType:'jsonp',
		  			jsonp:"callback",
		  			data:{"b":arr,"s":"-1"},
		  			async: true
		  		});
		    	//清空缓存
		 	 	arr = new Array();
			}
		}
	});
	
	if(arr.length > 0){
  		jq_1_7_1.ajax({
		    type:'GET',
		    url:speak.sound.url.batchUrl,
		    dataType:'jsonp',
		    jsonp:"callback",
		    data:{"b":arr,"s":"0"},
		    async: true
    	});
  		jq_1_7_1.ajax({
  			type:'GET',
  			url:speak.sound.url.batchUrl,
  			dataType:'jsonp',
  			jsonp:"callback",
  			data:{"b":arr,"s":"1"},
  			async: true
  		});
  		jq_1_7_1.ajax({
  			type:'GET',
  			url:speak.sound.url.batchUrl,
  			dataType:'jsonp',
  			jsonp:"callback",
  			data:{"b":arr,"s":"-1"},
  			async: true
  		});
	};
	arr = new Array();
};

speak.toolbarSpeakOnOff = function(){
	if (statusspeakOnOff == "on") {
		jq_1_7_1("#toolbar_speakOnOff").find("img").attr("src", "toolbar/img/sound1.png");
		jq_1_7_1("#toolbar_batchRead").find("img").attr("src", "toolbar/img/continuous_start1.png");
		jq_1_7_1("#toolbar_Pointread").find("img").attr("src", "toolbar/img/point4.png");
		jq_1_7_1("#toolbar_playSpeech").attr("onclick", "hide('cy_playSpeech_dl')");
		statusspeakOnOff = "off";
		
	} else if (statusspeakOnOff == "off") {
		jq_1_7_1("#toolbar_speakOnOff").find("img").attr("src", "toolbar/img/sound3.png");
		jq_1_7_1("#toolbar_batchRead").find("img").attr("src", "toolbar/img/continuous1.png");
		if (statusbatchRead == "off") {
			jq_1_7_1("#toolbar_batchRead").removeClass("batchReadOn");
			ESDWebApp.toolbar.speakAutoClose();
			statusbatchRead = "on";
		}
		jq_1_7_1("#toolbar_Pointread").find("img").attr("src", "toolbar/img/point2.png");
		if (statuspointread == "off") {
			jq_1_7_1("#toolbar_Pointread").removeClass("PointreadOn");
			ESDWebApp.toolbar.speakPointClose();
			statuspointread = "on";
		}
		jq_1_7_1("#toolbar_playSpeech").removeAttr("onclick");
		if (document.getElementById("cy_playSpeech_dl").className == "xian_1") {
			hide("cy_playSpeech_dl");
		}
		speak.speedVal = 0;
		speak.index = 0;
		speak.sound.autoSpeakObj = null;
		speak.source = new Array();
		jq_1_7_1(".toolbar_speakSpeed").each(function () {
			jq_1_7_1(this).html("语速调节");
        });
		statusspeakOnOff = "on";
	}
		 
};

speak.toolbarBatchRead = function(){
	if (statusbatchRead == "on") {
		jq_1_7_1("#toolbar_batchRead").find("img").attr("src", "toolbar/img/continuous_stop1.png");
		jq_1_7_1("#toolbar_batchRead").addClass("batchReadOn");
		//更改只读状态
		statuspointread="off";
		speak.toolbarPointread();
		pageRebuild.findDisplay();//连读时不需要读出隐藏的二级菜单
		//开启连读
		ESDWebApp.toolbar.speakAutoOpen();
		statusbatchRead = "off";
	} else if (statusbatchRead == "off") {
		jq_1_7_1("#toolbar_batchRead").find("img").attr("src", "toolbar/img/continuous_start1.png");
		jq_1_7_1("#toolbar_batchRead").removeClass("batchReadOn");
		pageRebuild.addESDAssetsTextCon();//指读时需要读出隐藏的二级菜单 
		statusbatchRead = "on";   //ESDWebApp.toolbar.speakAutoClose();需要根据这个状态来判断是否需要关闭连读 所以这句应该在上面
		ESDWebApp.toolbar.speakAutoClose();
	}
};

speak.toolbarPointread = function(){
	if (statuspointread == "on") {
		jq_1_7_1("#toolbar_Pointread").find("img").attr("src", "toolbar/img/point3.png");
		jq_1_7_1("#toolbar_Pointread").addClass("PointreadOn");
		//更改连读状态
		statusbatchRead="off";
		speak.toolbarBatchRead();
		//开启只读
		ESDWebApp.toolbar.speakPointOpen();
		statuspointread = "off";
	} else if (statuspointread == "off") {
		jq_1_7_1("#toolbar_Pointread").find("img").attr("src", "toolbar/img/point4.png");
		jq_1_7_1("#toolbar_Pointread").removeClass("PointreadOn");
		ESDWebApp.toolbar.speakPointClose();
		statuspointread = "on";
	}
};

//自动朗读
speak.sound.automateSpeak = function(){

	if(speak.speakerStatus){//如果开启指读,则关闭 指读.
		jq_1_7_1("#toolbar_Pointread").trigger("click");
		speak.thisdomPoint=null;
	}
	if(speak.keybordSpeakStatus){//如果开启键盘朗读则关闭
		jq_1_7_1("#toolbar_keybordRead").trigger("click");
		speak.thisdomKB=null;
	}
	
	jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
		jq_1_7_1(this).unbind("mouseover.pSpeak").unbind("mouseout.pSpeak");
	});
	
	jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
		var psObj = jq_1_7_1(this);
		psObj.bind("mouseover.pSpeak",function(){
			pointSpeakTimeId = setTimeout(function(){
				speak.sound.automateSpeakClose();
				speak.sound.automateSpeak1(psObj[0]);
			},1000);
			
		});
		psObj.bind("mouseout.pSpeak",function(){
			window.clearTimeout(pointSpeakTimeId);
		});
	});

};
//自动朗读
speak.sound.automateSpeak1 = function(t){
	speak.iframeChange=iframe_change;
	speak.speedFont();
	speak.automateSpeakStatus = true;
	
	if(speak.sound.autoSpeakObj!=null){
		speak.sound.autoSpeakObj.destruct();
	}
	
	if(speak.speakerStatus){//如果开启指读,则关闭 指读.
		jq_1_7_1("#toolbar_Pointread").trigger("click");
		speak.thisdomPoint=null;
	}
	if(speak.keybordSpeakStatus){//如果开启键盘朗读则关闭
		jq_1_7_1("#toolbar_keybordRead").trigger("click");
		speak.thisdomKB=null;
	}
	if(speak.index ==0 || speak.index < speak.source.length){
		//如果数组中没有内容 ，则向里添加页面所有对象
		if(speak.source.length==0){
			jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
				speak.source.push(this);
			});
		}
		if(t!=undefined){
			for(var j=0;j<speak.source.length;j++){
				//alert(speak.source[j]);
				if(speak.source[j]==t){
					speak.index=j;
					//alert(speak.index);
				}
			}
		}
		speak.thisdomAuto=speak.source[speak.index];
		var parent=jq_1_7_1(speak.thisdomAuto).parent();
		var tagName=jq_1_7_1(parent).get(0).tagName;
		if(tagName=='P'||tagName=='DIV'||tagName=='SPAN'||tagName=='INS'){
			var tab1=jq_1_7_1(speak.thisdomAutoParent).find(".ESDAssetsTextCon:first").attr("tabIndex");
			var tab2=jq_1_7_1(parent).find(".ESDAssetsTextCon:first").attr("tabIndex");
			if(tab1!=tab2){
				jq_1_7_1(speak.thisdomAutoParent).css("background-color",speak.thisdomAutoParent_back);
				speak.thisdomAutoParent_back = jq_1_7_1(parent).css("background-color");
				jq_1_7_1(parent).css("background-color","#CCC");
				speak.thisdomAutoParent = parent;
			}
		}else{
			jq_1_7_1(speak.thisdomAutoParent).css("background-color",speak.thisdomAutoParent_back);
		}
		//获取当前index下的dom对象。
		
		var text=ESDWebApp.toolbar.manageText(speak.thisdomAuto);
		//设置同步屏幕
		magnifier.setText(text);
		//jq_1_7_1("#message").html(text.length);
		//如果转换后内容为空，则不朗读,进行下一个内容。
		if(text.length==0){
			//alert(text);
			speak.index++;
			speak.sound.automateSpeak1();
			return;
		}
		jq_1_7_1(speak.thisdomAuto).focus();
		if(speak.thisdomAuto.innerHTML.replace(/<.+?>/g,'').replace(/\s/g, '').length>0){
			//取出保存样式
			var style=jq_1_7_1(speak.thisdomAuto).attr("style");
			if(style==undefined){
				speak.thisdomAuto_type="css";
			}else{
				var splits=style.split(";");
				if(splits.length>0){
					for(var i=0;i<splits.length;i++){
						var c =splits[i].split(":");
						if(c.length>0&&c[0].indexOf("color")){
							speak.thisdomAuto_type="style";
							speak.thisdomAuto_font=jq_1_7_1(speak.thisdomAuto).css("color");
							speak.thisdomAuto_back=jq_1_7_1(speak.thisdomAuto).css("background-color");
						}
					}
				}
			}
			//alert(speak.thisdomAuto_back);
			//设置样式
			jq_1_7_1(speak.thisdomAuto).css("background-color","black");
			jq_1_7_1(speak.thisdomAuto).css("color","white");
		}
		
		var de= base64.e64(text);
		jq_1_7_1.ajax({
			type:'GET',
			url:speak.sound.url.ajaxUrl,
			dataType:'jsonp',
			jsonp:"callback",
			data:{"b":de,"s":speak.speedVal},
			async: true,
			success:function(data){
				if(speak.automateSpeakStatus==false){
					return;
				}
				speak.sound.autoSpeakObj=soundManager.createSound({
					id:'tool',
					url:data.u,
					onload: function(bSuccess) {
						if(!bSuccess){
							automateSpeak();
						}
						//soundManager._writeDebug('sound '+(bSuccess?'loaded!': 'did NOT load.'));
					},
					onfinish:function(){
						speak.sound.autoSpeakObj.destruct();
						speak.sound.autoSpeakObj=null;
						//还原样式
						if(speak.thisdomAuto_type=="css"){
							jq_1_7_1(speak.thisdomAuto).css("background-color","");
							jq_1_7_1(speak.thisdomAuto).css("color","");
						}
						if(speak.thisdomAuto_type=="style"){
							jq_1_7_1(speak.thisdomAuto).css("background-color",speak.thisdomAuto_back);
							jq_1_7_1(speak.thisdomAuto).css("color","");
						}
						speak.index++;
						speak.sound.automateSpeak1();
					},
					onload:function(l){
						  if(l==0){
								speak.sound.autoSpeakObj.destruct();
								speak.sound.autoSpeakObj=null;
								//还原样式
								if(speak.thisdomAuto_type=="css"){
									jq_1_7_1(speak.thisdomAuto).css("background-color","");
									jq_1_7_1(speak.thisdomAuto).css("color","");
								}
								if(speak.thisdomAuto_type=="style"){
									jq_1_7_1(speak.thisdomAuto).css("background-color",speak.thisdomAuto_back);
									jq_1_7_1(speak.thisdomAuto).css("color","");
								}
								speak.index++;
								speak.sound.automateSpeak1();
						  }
						  
					}
				});
				speak.sound.autoSpeakObj.play();
			}
		});
	}else{//如果读完全文，则关闭全文朗读，并清空thisdom
		speak.index = 0;
		//jq_1_7_1("#toolbar_batchRead").trigger("click");
		return;
	}
};
//关闭自动朗读
speak.sound.automateSpeakClose = function(){
	speak.automateSpeakStatus=false;
	if(speak.sound.autoSpeakObj!=null){
		speak.sound.autoSpeakObj.destruct();
	}
	speak.source=new Array();
	speak.index=0;
	if(speak.iframeChange==iframe_change){
		//还原样式
		if(speak.thisdomAuto_type=="css"){
			jq_1_7_1(speak.thisdomAuto).css("background-color","");
			jq_1_7_1(speak.thisdomAuto).css("color","");
		}
		if(speak.thisdomAuto_type=="style"){
			jq_1_7_1(speak.thisdomAuto).css("background-color",speak.thisdomAuto_back);
			jq_1_7_1(speak.thisdomAuto).css("color","");
		}
		//jq_1_7_1(speak.thisdomAutoParent).css("background-color",speak.thisdomAutoParent_back);
	}
	speak.thisdomAuto=null;
	speak.thisdomAuto_back=null;
	speak.thisdomAuto_font=null;
	//连读每次鼠标移动后重新指定时 都会调用speak.sound.automateSpeakClose
	//只有当statusbatchRead=="on"时，也就是点击了连读关闭才可以清除mouseover事件否则无法重新指定连读内容
	if(statusbatchRead == "on"){		
		jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
			jq_1_7_1(this).unbind("mouseover.pSpeak").unbind("mouseout.pSpeak");
		});
	}
};
//点读与键盘朗读调用sound
speak.sound.callSM2 = function(GuTarget){
	var text = "";
	text = ESDWebApp.toolbar.manageText(GuTarget);
	//为鼠标指中，并有文本内容的元素更换背景色，（还原）speakFousOn
	if(speak.thisdom!=null&&(speak.thisdom.innerHTML.replace(/<.+?>/g,'').replace(/\s/g, '').length>0||(speak.thisdom.tagName == "INPUT" && speak.thisdom.type != "hidden"))){
		//恢复之前选第一句只变黑第一句不是一段变黑  放开了上方if的注释
		if(speak.thisdom_type=="css"){
			jq_1_7_1(speak.thisdom).css("background-color","");
			jq_1_7_1(speak.thisdom).css("color","");
		}
		if(speak.thisdom_type=="style"){
			jq_1_7_1(speak.thisdom).css("background-color",speak.thisdom_back);
			jq_1_7_1(speak.thisdom).css("color","");
		}
	};
	speak.thisdom=GuTarget; //恢复之前选第一句只变黑第一句不是一段变黑  添加这句和注释了下面一段
//	var parent=jq_1_7_1(GuTarget).parent();
//	var tagName=jq_1_7_1(parent).get(0).tagName;
//	if(tagName!='P'&&tagName!='DIV'&&tagName!='SPAN'&&tagName!='INS'){
//		speak.thisdom=GuTarget;
//	}else{
//		var chidernIndex = jq_1_7_1(parent).find(".ESDAssetsTextCon").length;
//		var firstIndex=jq_1_7_1(parent).find(".ESDAssetsTextCon:first").attr("tabIndex");
//		var tagIndex=jq_1_7_1(GuTarget).attr("tabIndex");
//		if(chidernIndex!=1&&firstIndex==tagIndex){
//			speak.thisdom=jq_1_7_1(GuTarget).parent();
//		}else{
//			speak.thisdom=GuTarget;
//		}
//	}
	//为鼠标指中，并有文本内容的元素更换背景色，（变为蓝色）
	if(speak.thisdom.innerHTML.replace(/<.+?>/g,'').replace(/\s/g, '').length>0||(speak.thisdom.tagName == "INPUT" && speak.thisdom.type != "hidden")){
		//恢复之前选第一句只变黑第一句不是一段变黑  放开了上方if的注释
		var style=jq_1_7_1(speak.thisdom).attr("style");
		if(style==undefined){
			speak.thisdom_type="css";
		}else{
			var splits=style.split(";");
			if(splits.length>0){
				for(var i=0;i<splits.length;i++){
					var c =splits[i].split(":");
					if(c.length>0&&c[0].indexOf("color")){
						speak.thisdom_type="style";
						speak.thisdomfont=jq_1_7_1(speak.thisdom).css("color");
						speak.thisdom_back=jq_1_7_1(speak.thisdom).css("background-color");
					}
				}
			}
		}
		jq_1_7_1(speak.thisdom).css("background-color","black");
		jq_1_7_1(speak.thisdom).css("color","white");

	}
		
	if(text.replace(/[ ]/g,"").length==0){
		return;
	}
	//设置同步屏幕
	magnifier.setText(text);
	var de= base64.e64(text);
	jq_1_7_1.ajax({
		type:'GET',
		url:speak.sound.url.ajaxUrl,
		dataType:'jsonp',
		jsonp:"callback",
		data:{"b":de,"s":speak.speedVal},
		async: false,
		success:function(data){
			if(speak.sound.mp3Object!=null){
				speak.sound.mp3Object.destruct();
			}
			speak.sound.mp3Object=soundManager.createSound({
						id:'tool',
						url:data.u,
						  onload:function(l){
							  if(l==0){
									if(speak.sound.mp3Object!=null){
										speak.sound.mp3Object.destruct();
									}
							  }
							  
						}
					/**
						  onfinish:function(){alert("onfinish");},
						  onplay:function(){alert("onplay");},
						  onstop:function(){alert("onstop");},
						  onpause:function(){alert("onpause");},
						  onresume:function(){alert("onresume");},
						  whileplaying:function(){alert("whileplaying");},
						  onbeforefinish:function(){alert("onbeforefinish");},
						  onbeforefinishcomplete:function(){alert("onbeforefinishcomplete");},
						  onjustbeforefinish:function(){alert("onjustbeforefinish");},
						  onjustbeforefinishcomplete:function(){alert("onjustbeforefinishcomplete");}
						  **/
				});
			speak.sound.mp3Object.play();
		}
	});
	
};
var pointSpeakTimeId;
//点读播放
speak.sound.startPointSpeak = function(GuTarget){
	speak.iframeChange=iframe_change;
	speak.speedFont();
	speak.speakerStatus=true;

	if(speak.automateSpeakStatus){
		jq_1_7_1("#toolbar_batchRead").trigger("click");
		speak.thisdom=null;	
	}
	if(speak.keybordSpeakStatus){
		jq_1_7_1("#toolbar_keybordRead").trigger("click");
		speak.thisdom=null;
	}
	//重复检测，如果鼠标移动时在同一元素上则不触发以后方法。
	if(speak.thisdom!=null&&speak.thisdom==GuTarget){
		return;
	}

	jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
		jq_1_7_1(this).unbind("mouseover.pSpeak").unbind("mouseout.pSpeak");
		jq_1_7_1(this).unbind("focus.fSpeak").unbind("blur.fSpeak");
	});
	jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
		var psObj = jq_1_7_1(this);
		psObj.bind("mouseover.pSpeak",function(){
			pointSpeakTimeId = setTimeout(function(){
				speak.sound.callSM2(psObj[0]);
			},500);
			
		});
		psObj.bind("mouseout.pSpeak",function(){
			window.clearTimeout(pointSpeakTimeId);
		});
		psObj.bind("focus.fSpeak",function(){
			pointSpeakTimeId = setTimeout(function(){
				speak.sound.callSM2(psObj[0]);
			},500);
			
		});
		psObj.bind("blur.fSpeak",function(){
			window.clearTimeout(pointSpeakTimeId);
		});
	});
	
};
//关闭点读播放
speak.sound.closePointSpeak = function(){
	window.clearTimeout(pointSpeakTimeId);
	if(speak.sound.mp3Object!=null){
		speak.sound.mp3Object.destruct();
	}
	speak.speakerStatus=false;
	//为鼠标指中，并有文本内容的元素更换背景色，（还原）speakFousOn
	if(speak.iframeChange==iframe_change){
			if(speak.thisdom_type=="css"){
				jq_1_7_1(speak.thisdom).css("background-color","");
				jq_1_7_1(speak.thisdom).css("color","");
			}
			if(speak.thisdom_type=="style"){
				jq_1_7_1(speak.thisdom).css("background-color",speak.thisdom_back);
				jq_1_7_1(speak.thisdom).css("color","");
			}
	}
	speak.thisdom_type=null;
	speak.thisdom=null;
	speak.thisdom_back=null;

	jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
		jq_1_7_1(this).unbind("mouseover.pSpeak").unbind("mouseout.pSpeak");
		jq_1_7_1(this).unbind("focus.fSpeak").unbind("blur.fSpeak");
	});
};
//打开键盘朗读
speak.sound.openKeybordSpeak = function(){

};
//关闭键盘朗读
speak.sound.closeKeybordSpeak = function(){

};
//语音提示
speak.sound.voicePrompt = {};
speak.sound.voicePrompt.play = function(voiceId,voiceUrl){
	if(speak.sound.voicePromptObject != null){
		speak.sound.voicePromptObject.destruct();
	}
	speak.sound.voicePromptObject=soundManager.createSound({
		id:voiceId,
		url:voiceUrl,
		onfinish:function(){
			speak.sound.voicePromptObject.destruct();
		}
	});
	speak.sound.voicePromptObject.play();
};
//播放提示Alt+0
speak.sound.voicePrompt.alt0 = function(){
	var id = "prompt_alt0";
	var url = speak.sound.url.alt_0;
	speak.sound.voicePrompt.play(id,url);
};
//播放提示Shift+0
speak.sound.voicePrompt.shift0 = function(){
	var id = "prompt_shift0";
	var url = speak.sound.url.shift_0;
	speak.sound.voicePrompt.play(id,url);
};
//播放提示Shift+z
speak.sound.voicePrompt.shiftz = function(){
	var id = "prompt_shiftz";
	var url = speak.sound.url.shift_z;
	speak.sound.voicePrompt.play(id,url);
};
//播放工具栏介绍
speak.sound.voicePrompt.intordus = function(){
	var id = "prompt_intordus";
	var url = speak.sound.url.intordus;
	speak.sound.voicePrompt.play(id,url);
};
//播放工具栏介绍
speak.sound.voicePrompt.description = function(){
	var id = "prompt_description";
	var url = speak.sound.url.description;
	speak.sound.voicePrompt.play(id,url);
};
//F11切换全屏模式
speak.sound.voicePrompt.fullscreen = function(){
	var id = "prompt_fullscreen";
	var url = speak.sound.url.fullscreen;
	speak.sound.voicePrompt.play(id,url);
};
