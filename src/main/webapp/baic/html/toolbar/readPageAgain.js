/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 处理异步数据读取  网站没有异步数据处理时不需要引入本js
 * */

jq_1_7_1(document).ready(function(){

	tempurl = {
			localSWF:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.swf,
			alt_0:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.alt0,
			shift_0:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.shift0,
			shift_z:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.shiftz,
			intordus:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.intordus,
			description:ESDWebApp.defalt.fileURL.toolbar + toolbarConfig.speak.description,
			ajaxUrl:toolbarConfig.speak.ajaxUrl,
			batchUrl:toolbarConfig.speak.BatchUrl
		};

	setInterval(function(){
		var patt = new RegExp("[。！？；，、.!?;,]","g");
		var result;
		jq_1_7_1(esd_tool_iframe).find("*:not(.ESDAssetsTextCon,.displayClass,.ymd_split_span,.split_span)").each(function(){
			if(this.tagName!="A"&&!(jq_1_7_1(this).parents().is("a"))){//A链接用ins包住回导致chrome上的A链接按回车打不开  所以就不用包了
				var contents = jq_1_7_1(this).contents();
				jq_1_7_1(this).contents().filter(function() {
					  if(this.nodeType==3){
						  var str = jq_1_7_1(this).text();
						  var h = jq_1_7_1.trim(str);
						  if(str=="["||str=="]"){
							h="";
						  }	
						  if(h!=""){
							  //jq_1_7_1("#log").append(h);
							  jq_1_7_1(this).wrap("<ins style=\"text-decoration:none; font-size:100% \" class='split_span_temp'></ins>");
						 }
					  }
				});
			}
		});
//		jq_1_7_1(esd_tool_iframe).find(".split_span_temp").each(function(){
//			var index=0;
//			var h = jq_1_7_1(this).html();
////			alert(h);
//			var newParagraph="";
//			if (h.length > 30) {
//				while ((result = patt.exec(h)) != null) {
//					if (h.substring(index, patt.lastIndex) != result && h.substring(index, patt.lastIndex) != "&nbsp;") {
//						newParagraph += "<ins style=\"text-decoration:none; font-size:100%; \" class='ymd_split_span'>" + h.substring(index, patt.lastIndex) + "</ins>";
//					} else {
//						newParagraph += h.substring(index, patt.lastIndex);
//						
//					}
//					index = patt.lastIndex;
//				}
////				alert(h+"   "+index+"  "+h.length);
//				//如果最后的标点位置不是文本的最后一位，说明后面还有内容需要添加
//				if(index<h.length){
//					newParagraph += "<ins style=\"text-decoration:none; font-size:100%; \" class='ymd_split_span'>" + h.substring(index, h.length); + "</ins>";
//				}
//				//jq_1_7_1("#log").append(newParagraph);
//				jq_1_7_1(this).html(newParagraph);
//			}else{
//				jq_1_7_1(this).removeClass("split_span_temp").addClass("ymd_split_span");//如果不够长就修改要是否则会一直重复修改
//			}
//		});
		var arr = new Array();
		var i = 1;
		jq_1_7_1(esd_tool_iframe).find("*:not(.ESDAssetsTextCon,.displayClass)").each(function(index){

			var obj = jq_1_7_1(this);
			var text = "";
			text = ESDWebApp.toolbar.manageText(obj[0]);
			
//			if(text != ""){
//				alert(this.tagName+"       "+this.innerHTML);
//			}
			
			if(text != null && text != ""){
				obj.addClass("ESDAssetsTextCon");//为每个带有文本节点的dom增加属性Tabindex，并且增加class属性ESDAssetsTextCon
				i++;
				var de = base64.e64(text);
				arr.push(de);
				
				//保正不能超过5句,每句不能超过100字
			  	if(arr.length>5){
			  		jq_1_7_1.ajax({
					    type:'GET',
					    url:tempurl.batchUrl,
					    dataType:'jsonp',
					    jsonp:"callback",
					    data:{"b":arr,"s":"0"},
					    async: true
			    	});
			  		jq_1_7_1.ajax({
			  			type:'GET',
			  			url:tempurl.batchUrl,
			  			dataType:'jsonp',
			  			jsonp:"callback",
			  			data:{"b":arr,"s":"1"},
			  			async: true
			  		});
			  		jq_1_7_1.ajax({
			  			type:'GET',
			  			url:tempurl.batchUrl,
			  			dataType:'jsonp',
			  			jsonp:"callback",
			  			data:{"b":arr,"s":"-1"},
			  			async: true
			  		});
			    	//清空缓存
			 	 	arr = new Array();
				}
			
				if(statuspointread=="off"){
					jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
						jq_1_7_1(this).unbind("mouseover.pSpeak").unbind("mouseout.pSpeak");
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
					});
				}
			}
		});
		if(arr.length > 0){
	  		jq_1_7_1.ajax({
			    type:'GET',
			    url:tempurl.batchUrl,
			    dataType:'jsonp',
			    jsonp:"callback",
			    data:{"b":arr,"s":"0"},
			    async: true
	    	});
	  		jq_1_7_1.ajax({
	  			type:'GET',
	  			url:tempurl.batchUrl,
	  			dataType:'jsonp',
	  			jsonp:"callback",
	  			data:{"b":arr,"s":"1"},
	  			async: true
	  		});
	  		jq_1_7_1.ajax({
	  			type:'GET',
	  			url:tempurl.batchUrl,
	  			dataType:'jsonp',
	  			jsonp:"callback",
	  			data:{"b":arr,"s":"-1"},
	  			async: true
	  		});
		};
		arr = new Array();	
	}, 6000);
	
});
