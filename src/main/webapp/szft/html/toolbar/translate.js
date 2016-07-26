/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/**
 * 语言翻译模块
 * 
 */

var trans = {};
trans.status=false;

// 翻译对应的内容
// 参数：text - 要翻译的文字
// callback - 翻译的结果，返回的是字符串内容
trans.doTranslate = function(text,posX,posY) {

	var src = text;
	var b64 = base64.e64(src);
	url = toolbarConfig.trans.ajaxUrl;
	jq_1_7_1.ajax({
		url : url,
		type : 'POST',
		data : {src:b64},
		success : function(e) {
		    window.clearTimeout(transTimeOutId);
			jq_1_7_1(esd_tool_iframe).find("#translating").hide();
			// alert(e.trans_result[0].dst);
			//callback(0, e.trans_result[0].dst, element);
			jq_1_7_1(esd_tool_iframe).find("#translating").html("<p>"+e.trans_result[0].dst+"</p>");
			var transWidth = jq_1_7_1(esd_tool_iframe).find("#translating").width();
			var transHeight = jq_1_7_1(esd_tool_iframe).find("#translating").height()+25;
			jq_1_7_1(esd_tool_iframe).find("#translating").css({"left":posX-(transWidth/2)+"px","top":posY-transHeight+"px"}).show();
		},
		error : function() {
			//callback(1, textStatus, element);
			jq_1_7_1(esd_tool_iframe).find("#translating").html("<p>翻译出错</p>");
		},
		dataType : 'jsonp',
		async : true
	});
	
};

/**
 * 
 * 显示翻译结果
 * 
 * 
 */
trans.toggleTranslate = function() {
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	//如果开启指读,则关闭 指读.
	/*if(speak.speakerStatus){
		jq_1_7_1("#toolbar_Pointread").trigger("click");
		speak.thisdom=null;
	}*/
	if(trans.status==false){
		trans.status=true;
		jq_1_7_1("#toolbar_translation").find("img").attr("src", "toolbar/img/translationOn.png");
	}else{
		trans.status=false;
		jq_1_7_1("#toolbar_translation").find("img").attr("src", "toolbar/img/translationOff.png");
	}
	trans.translate();
};
var transTimeOutId;
trans.translate = function(){
	if(!trans.status){
		jq_1_7_1("#toolbar_translation").removeClass("translateOn");
		jq_1_7_1(esd_tool_iframe).find("#translating").remove();
		jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
			jq_1_7_1(this).unbind("mouseenter.trans").unbind("mouseleave.trans");
		});
	}else{
		window.clearTimeout(transTimeOutId);
		jq_1_7_1(esd_tool_iframe).find("#translating").hide();
		jq_1_7_1("#toolbar_translation").addClass("translateOn");
		jq_1_7_1(esd_tool_iframe).append("<div id='translating'></div>");
		jq_1_7_1(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
			var obj = jq_1_7_1(this);
			//var offsetValue = obj.offset();
			obj.bind("mouseenter.trans",function(event){
				var transImgWidth = 16;
				var transImgHeight = 32;
				var transTextWidth;
				var transTextHeight;
				var posX = event.pageX;
				var posY = event.pageY;
				var transObj = jq_1_7_1(this);
				var transText = transObj.text();
				//trans.doTranslate(transText,posX,posY);

				transTimeOutId = setTimeout(function(){
					jq_1_7_1(esd_tool_iframe).find("#translating")
					.css({"left":posX-transImgWidth+"px","top":posY-transImgHeight+"px"})
					.html("<img src='toolbar/img/translating.gif'/>")
					.show();
					//trans.doTranslate(transText,posX,posY);
					//setTimeout(function(){
						trans.doTranslate(transText,posX,posY);
					//},1000);
				},500);

			});
			obj.bind("mouseleave.trans",function(){
				window.clearTimeout(transTimeOutId);
				jq_1_7_1(esd_tool_iframe).find("#translating").hide();
			});
		});
	}
	
};
//trans.translate = function() {
//	
//	
//	
//	if(trans.status==false){
//		jq_1_7_1(".ESDAssetsTextCon").each(function(){
//			jq_1_7_1(this).RemoveBubblePopup();
//		});
//		return;
//	}
//	// 翻译功能
//	if (jq_1_7_1(".ESDAssetsTextCon").HasBubblePopup()) {
//		jq_1_7_1(".ESDAssetsTextCon").RemoveBubblePopup();
//	} else {
//	    // VOIDMAIN
//		jq_1_7_1("span,h1,h2,h3,h4,font,a").each(function(){
//			var obj = jq_1_7_1(this);
//			var text = "";
//			text = ESDWebApp.toolbar.manageText(obj[0]);
//			
//			if(text !=null && text != ""){
//				obj.addClass("ESDAssetsTextCon").CreateBubblePopup({
//			        innerHtml: '<img src="assets/img/translating.gif"/><p>正在翻译...</p>',
//			        themePath: 'assets/jquerybubblepopup-themes/',
//			        themeName: 'black',
//			        position: 'top',
//			        align: 'left',
//			        openingDelay: 0,
//			        // hack: 这些并不是泡泡类库的标准方法，但是因为它实际就是一个JS对象，
//			        // 所以可以动态添加一些属性
//			        // oriElement表示的是鼠标移动到哪个元素对应的那一个泡泡
//			        // text2trans表示的是对应的哪个泡泡的文字内容
//			        oriElement: obj,
//			        text2trans: text,
//			        afterShown: function() {
//			          // VOIDMAIN hack：这里引入一个saved方法记录是否已经翻译过（或正在翻译）这个内容
//			          if (!this.saved) {
//			            this.saved = true;
//			            var text = this.text2trans;
//			            trans.doTranslate(text, trans.showTransResult, this.oriElement);
//			          };
//			        }
//			    });
//			}
//		});
//	}
//};
//// VOIDMAIN
//// 回调的回调，用于显示结果
//trans.showTransResult = function(statusCode, result, element) {
//  if (!statusCode) {
//    result = "<p class='trans_succ'>" + result + "</p>";
//  } else {
//    result = "<p class='trans_fail'>" + result + "</p>";
//  }
//  element.SetBubblePopupInnerHtml(result);
//};
