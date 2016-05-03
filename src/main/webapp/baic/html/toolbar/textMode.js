/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 文本模式
 * 
 * */
var textMode = {};
var status = "on";

/*textMode.textModeON = function(){
	esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	//jq_1_7_1("style").remove();
	//jq_1_7_1("link[type='text/css']:not(#toolbarCss)").remove();
	//jq_1_7_1("*:not(#toolbar_action,#toolbar,#toolbar *)").each(function(){
	jq_1_7_1(esd_tool_iframe).find("style").remove();
	jq_1_7_1(esd_tool_iframe).find("link[type='text/css']:not(#toolbarCss)").remove();
	jq_1_7_1(esd_tool_iframe).find("*:not(#toolbar_action,#toolbar,#toolbar *)").each(function(){
		if(this.tagName == "IMG"){
			var imgAlt = "";
			if(this.alt !=null && this.alt !="" && this.alt != "无意义图片"){
				imgAlt = this.alt;
			}
			jq_1_7_1(this).replaceWith('<span>'+imgAlt+'</span>');
		}else if(this.tagName == "A" && this.innerHTML == ""){
			var aText = "" ;
			if(this.title !="" && this.title !=null){
				aText = this.title;
			}else if(this.alt !="" && this.alt !=null){
				aText = this.alt;
			}
			this.innerHTML = aText;
		}else{
			jq_1_7_1(this).removeAttr("class").removeAttr("style").removeAttr("background").removeAttr("valign").removeAttr("align").removeAttr("height").removeAttr("width");
		}
		
		
	});
	//jq_1_7_1("#toolbar").css("position","relative");
	//jq_1_7_1("a:not(div#toolbar a),span,h1,h2,h3,h4,h5,input").css("padding","5px");
	jq_1_7_1(esd_tool_iframe).find("a:not(div#toolbar a),span,h1,h2,h3,h4,h5,input").css("padding","5px");
};
textMode.textModeOff = function(){
	ESDWebApp.defalt.pageReload();
	jq_1_7_1("#toolbar_textMode_on").removeClass("textMode_off");
	jq_1_7_1("#toolbar_textMode_on").addClass("textMode_on");
};*/


textMode.textMode = function(){
	if (status == "on") {
		esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
		jq_1_7_1(esd_tool_iframe).find("style:not(#d-outer,#d-outer *)").remove();
		jq_1_7_1(document.getElementById("iframe").contentWindow.document.getElementsByTagName("head")[0]).find("link[type='text/css']").remove();
		jq_1_7_1(document.getElementById("iframe").contentWindow.document.getElementsByTagName("head")[0]).find("style[type='text/css']").remove();
		jq_1_7_1(esd_tool_iframe).find(".d-outer").css("visibility", "hidden");
		jq_1_7_1(esd_tool_iframe).find("link[type='text/css']:not(#toolbarCss,#toolbarMainCss,#YBZ-css-id)").remove();
		jq_1_7_1(esd_tool_iframe).find("*:not(#toolbar_action,#toolbar,#toolbar *,#d-outer,#d-outer *)").each(function(){
			if(this.tagName == "IMG"){
				var imgAlt = "";
				if(this.alt !=null && this.alt !="" && this.alt != "无意义图片"){
					imgAlt = this.alt;
				} else {
					textMode.imgAlt(imgAlt);
				}
				jq_1_7_1(this).replaceWith('<span>'+imgAlt+'</span>');
			}else if(this.tagName == "A" && this.innerHTML == ""){
				var aText = "" ;
				if(this.title !="" && this.title !=null){
					aText = this.title;
				}else if(this.alt !="" && this.alt !=null){
					aText = this.alt;
				}
				this.innerHTML = aText;
			}else{
				jq_1_7_1(this).removeAttr("style").removeAttr("background").removeAttr("valign").removeAttr("align").removeAttr("height").removeAttr("width");
				if(this.tagName != "A"){
					jq_1_7_1(this).css("text-decoration","none");
				}
			}
		});
		jq_1_7_1(esd_tool_iframe).find("a:not(div#toolbar a),span,h1,h2,h3,h4,h5,input").css("padding","5px");
		status = "off";
	} else if (status == "off") {
		ESDWebApp.defalt.iframeReload();
		//ESDWebApp.defalt.pageReload();
		status = "on";
	}
};

textMode.imgAlt = function(imgAlt) {
	var imgAltName = "";
	imgAlt = imgAltName;
};
