/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
/*
 * 页面重构
 * */

var pageRebuild = {};
pageRebuild.rebuild = function(){
	//esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	pageRebuild.addSpanTag();
	//pageRebuild.findDisplay();  //需要指读二级菜单  不能再此时将隐藏的二级菜单添加displayClass
	//pageRebuild.splitParagraph();
};

//查找CSS为diplay=none的标签，并添加标示“displayClass”
pageRebuild.findDisplay = function(){
	//jq_1_7_1("body *:hidden").addClass("displayClass");
	//jq_1_7_1("#iframe").contents().find("body *:hidden").addClass("displayClass");
	//连读时不需要读取隐藏的二级菜单
	jq_1_7_1(esd_tool_iframe).find("*:hidden").removeClass("ESDAssetsTextCon").addClass("displayClass");
};

pageRebuild.addESDAssetsTextCon = function(){
	//下面这句有问题不能这么加 会导致样式乱掉
	//jq_1_7_1(esd_tool_iframe).find("*:hidden").removeClass("displayClass").addClass("ESDAssetsTextCon");
	//当指读时需要将连读时修改为displayClass的样式修改回ESDAssetsTextCon
	//因为页面加载的时候隐藏的二级菜单也被加入了tabindex属性  可以根据这个属性和displayClass查找出需要修改的部分
	jq_1_7_1(esd_tool_iframe).find("* [tabindex][class$='displayClass']").removeClass("displayClass").addClass("ESDAssetsTextCon");
};

//切割段落
pageRebuild.splitParagraph = function(){
	var patt = new RegExp("[。！？；，、.!?;,]","g");
	var result;
	//jq_1_7_1("body p").each(function(){
	//jq_1_7_1("#iframe").contents().find("body p").each(function(){
	jq_1_7_1(esd_tool_iframe).find("p").each(function(){
	    var index=0;  
	    var newParagraph="";
		if(jq_1_7_1(this).children("script").length>0){
		//if(jq_1_7_1(esd_tool_iframe).children("script").length>0){
			return;
		}
		var t=jq_1_7_1(this).html();

		var contents = jq_1_7_1(this).contents();
		jq_1_7_1(contents).each(function(i, content){
			jq_1_7_1(this).contents().filter(function() {
				  return this.nodeType == 3;
			}).wrap("<span class='split_span'></span>");
			
			jq_1_7_1(this).find(".split_span").each(function(){
				var h = jq_1_7_1(this).html();
				if (h.length > 30) {
					while ((result = patt.exec(h)) != null) {
						if (h.substring(index, patt.lastIndex) != result && h.substring(index, patt.lastIndex) != "&nbsp;") {
							newParagraph += "<span class='ymd_split_span'>" + h.substring(index, patt.lastIndex) + "</span>";
						} else {
							newParagraph += h.substring(index, patt.lastIndex);
						}
						index = patt.lastIndex;
					}
					jq_1_7_1(this).html(newParagraph);
				}
			});
		});

		/*//var t=jq_1_7_1(esd_tool_iframe).html();
		if(t.length>30){
			  while ((result = patt.exec(t)) !== null)  {
				  //class为了自动朗读时区分内容。
				newParagraph+="<span class='ymd_split_span'>"+t.substring(index,patt.lastIndex)+"</span>";
				index=patt.lastIndex;
			 }
			jq_1_7_1(this).html(newParagraph);
			//jq_1_7_1(esd_tool_iframe).html(newParagraph);
		}*/
	});
};
//为div、td中的文字增加span标签
pageRebuild.addSpanTag = function(){
	var patt = new RegExp("[。！？；，、.!?;,]","g");
	var result;
	//jq_1_7_1(esd_tool_iframe).find("div:not(#toolbar *), td, span, form,p").each(function(){
	jq_1_7_1(esd_tool_iframe).find("*").each(function(){
		if(this.tagName!="A"&&!(jq_1_7_1(this).parents().is("a"))&&this.tagName!="TEXTAREA"&&!(jq_1_7_1(this).parents().is("textarea"))){//A链接用ins包住回导致chrome上的A链接按回车打不开  所以就不用包了
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
						  jq_1_7_1(this).wrap("<ins style=\"text-decoration:none; font-size:100% \" class='split_span'></ins>");
					 }
				  }
			});
		}
	});
	
	jq_1_7_1(esd_tool_iframe).find(".split_span").each(function(){
		var index=0;
		var h = jq_1_7_1(this).html();
		var newParagraph="";
		if (h.length > 30) {
			while ((result = patt.exec(h)) != null) {
				if (h.substring(index, patt.lastIndex) != result && h.substring(index, patt.lastIndex) != "&nbsp;") {
					newParagraph += "<ins style=\"text-decoration:none; font-size:100%; \" class='ymd_split_span'>" + h.substring(index, patt.lastIndex) + "</ins>";
				} else {
					newParagraph += h.substring(index, patt.lastIndex);
					
				}
				index = patt.lastIndex;
			}
//			alert(h+"   "+index+"  "+h.length);
			//如果最后的标点位置不是文本的最后一位，说明后面还有内容需要添加
			if(index<h.length){
				newParagraph += "<ins style=\"text-decoration:none; font-size:100%; \" class='ymd_split_span'>" + h.substring(index, h.length); + "</ins>";
			}
			//jq_1_7_1("#log").append(newParagraph);
			jq_1_7_1(this).html(newParagraph);
		}
	});
	
	
	
};
