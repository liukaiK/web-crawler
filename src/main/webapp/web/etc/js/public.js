/** 初始化方法* */
var root = "/";
$(document).ready(function() {
//	addPageView();//访问量+1
	//更改工具条开关
	var str = location.href; // 取得整个地址栏
	var num = str.lastIndexOf("/");
	str = str.substr(num + 1); // 取得所有参数 stringvar.substr(start [, length ]
	//alert(str);
	var url = "wca.html?"+str;
	$("#esd_toolbar_ctrl").attr("href",url);
	/** **************必须保持第一时间执行************** */
	$("#service1 li").each(function() {
		$(this).addClass("hoverblack");
	});

	var sIndex = -1;
	var maxIndex = -1;
	var minIndex = 0;

	$(".hoverblack").each(function(index, ehx) {
		maxIndex++;
	});
	$(this).keydown(function(e) {
//		 alert(e.keyCode);
		if (e.altKey && e.shiftKey) {
			if (e.keyCode === 75) { // k
				$("html,body").animate({scrollTop : $("#top-bar").offset().top}, 500); // 1000是ms,也可以用slow代替
				$("#nav").find("a").each(function(index) {
					if (index == 0) {
						$(this).focus();
					}
				});
			}
			if (e.keyCode === 88) { // X
				if ($('#NewsShowConter_txt').length > 0) {
					keySelectScroll("#NewsShowConter_txt");
				}else{
					keySelectScroll(".main");
				}
			}
			if (e.keyCode === 89) { // Y
				keySelectScroll("#footer");
			}
			if (e.keyCode === 34) { // page down
				if (sIndex < maxIndex) {
					sIndex++;
					keyShowSelect(sIndex);
				}
			}
			if (e.keyCode === 33) { // page up
				if (sIndex > minIndex) {
					sIndex--;
					keyShowSelect(sIndex);
				}
			}
			if (e.keyCode === 84) { // T
			// alert("开启辅助浏览模式");
			}
			if (e.keyCode === 74) { // J 开启和关闭无障碍工具
				window.location=$("#esd_toolbar_ctrl").attr("href");
			}
		}
	});

	/**
	 * ****************区域变色
	 * 黑色背景、黄色字、红边框*************************************************
	 */

	$(".hoverblack").mouseover(function() {
		var obj = this;
		$(".hoverblack").each(function(index, ehx) {
			$(this).css("background-color", "");
			$(this).css("color", "");
			$(this).css("border-color", "");
			if (this == obj) {
				sIndex = index;
			}
		});

//		$(this).css("background-color", "#222222");
		$(this).css("color", "yellow");
		$(this).css("border-color", "red");
//		$(this).find("a").each(function(index) {
//			if (index == 0) {
//				$(this).focus();
//			}
//		});

	});

	$(".hoverblack").mouseout(function() {
		$(this).css("background-color", "");
		$(this).css("color", "");
		$(this).css("border-color", "");
	});

	// *****************************************幻灯片添加title*************************************************************/
	$(".slide a").attr("title", $(".slide p").text());
	// *****************************************分页增加回车跳转功能************************************************************/
	$("#page select").keydown(function(e) {
		var e = e || event, keycode = e.which || e.keyCode;
		if (keycode == 13) {
			var zhi = $("select").val();
			if ("转到" != zhi) {
				location.href = zhi;
			}
		}
	});

	$('a').focus(function(){
		$(this).css("background-color", "black");
		$(this).css("color", "yellow");
		$(this).css("border-color", "red");
	})
	$('a').blur(function(){
		$(this).css("background-color", "");
		$(this).css("color", "");
		$(this).css("border-color", "");
	})
//	getPageView();
	
	$("#nav").find("a:eq(0)").focus();
	
});
function keySelectScroll(id) {
	// var obj = document.getElementById('yhdl');
	$("html,body").animate({
		scrollTop : $(id).offset().top}, 500); // 1000是ms,也可以用slow代替
	$(id).find("a").each(function(index) {
		if (index == 0) {
			$(this).focus();
		}
	});
}

function keyShowSelect(i) {
	$(".hoverblack").each(function(index, ehx) {
		$(this).css("background-color", "");
		$(this).css("color", "");
		$(this).css("border-color", "");
	});
	$(".hoverblack").each(function(index, ehx) {
		if (i == index) {
//			$(this).css("background-color", "black");
			$(this).css("color", "yellow");
			$(this).css("border-color", "red");
			$(this).find("a").each(function(index) {
				if (index == 0) {
					$(this).focus();
				}
			});
		}
	});
}

/**
 * 访问量+1
 */
//function addPageView() {
//	var timestamp = new Date().getTime();
//	$.ajax({
//		url : root + '/addPageView?' + timestamp,
//		type : 'GET',
//		sync : true,
//		success : function(data) {
//		},
//		error : function() {
//		}
//	});
//}
//
//
//function getPageView() {
//	var timestamp = new Date().getTime();
//	$.ajax({
//		url : root + '/getPageView?' + timestamp,
//		type : 'GET',
//		sync : true,
//		success : function(data) {
//			$("#pageview").text(data.message);
//			addPageView();
//		},
//		error : function() {
//
//		}
//	});
//}


$(function(){
	if(top.location != location){  
	    return false;
	}  
	$(document.body).find("*").each(function(){
		if(this.tagName!="A"&&!($(this).parents().is("a"))&&this.tagName!="IFRAME"&&this.tagName!="SCRIPT"&&this.tagName!="STYLE"&&this.tagName!="TEXTAREA"){//A链接用ins包住会导致chrome上的A链接按回车打不开  所以就不用包了
			var contents = $(this).contents();
			$(this).contents().filter(function() {
				  if(this.nodeType==3){
					  var str = $(this).text();
					  var h = $.trim(str);
					  if(h!=""){
						  $(this).wrap("<ins style=\"text-decoration:none; font-size:100%; \" class='split_span'></ins>");
					 }
				  }
			});
		}
	});
	$(document.body).find("*").each(function(){
		var obj = $(this);
		var text = "";
		text = manageText(obj[0]);
		
		if(text != null && text != ""){
			obj.addClass("ESDAssetsTextCon");
		}
	});

	if(changeFont.fontZoom>1){		
		$(document.body).find(".ESDAssetsTextCon").each(function(){
			$(this).css("font-size",function(index,value){
				var basicPX = parseFloat(value);
				return (basicPX*changeFont.fontZoom)+"px";
			});
		});	
	}
	
	$(document).keydown(function(e){
		if (e.altKey && e.shiftKey) {
			if (e.keyCode == 52) {
				//(alt+shift+4)=大字
				changeFont.fontBigger();
			} else if (e.keyCode == 53) {
				//(alt+shift+5)=小字
				changeFont.fontSmaller();
			} 
		}
	});
});

//判断元素是否存在于标签集合中。
function findvar(obj,sz){   
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
function manageText(obj){
	var text="";
	if(findvar(obj.tagName,['INS'])){
		if($(obj).children("script").length>0){
			return text;
		}
		if(obj.children.length>0 && obj.children[0].nodeType == 1){
			return text;
		}
		//检查display属性是否为none
		var dis = obj.currentStyle?obj.currentStyle.display/*ie*/:document.defaultView.getComputedStyle(obj,null).display;
		if(dis == "none"){return text;}
		text=obj.innerHTML.replace(/<.+?>/g,'');
		text=text.replace(/\s/g, '');
	//读取a标签中的title内容 如果A链接的子元素是img按下方图片处理
	}else if(obj.tagName == "A"&&!($(obj).find("img").is("img"))&&!($(obj).find("span").is("span"))){
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
				var opt = $(selected.options[i]).children()[0];
				if(opt!=null){
					text = opt.innerHTML;
				}
				text = "列表框："+text;
				break;
			}
		}
		text = text.replace(/\s/g, '');
	}else if(obj.tagName == "INPUT"){
		var type = $(obj).attr("type");
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
			if($(obj).parents().is("a")){
				text = "图片链接："+text;
			}else{
				text = "图片："+text;
			}
			return text;
		}else{
			if($(obj).parents().is("a")){
				text = ($(obj).parents('a').attr("title")!=undefined)? $(obj).parents('a').attr("title") : "";
				text = "图片链接："+text.replace(/\s/g, '');
				return text;
			}
			return "图片";
		}
	}else if (obj.tagName == "OBJECT") {
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
		if($(obj).parents().is("a")) {
			if($(obj).parents('a').attr("title")!=undefined){
				text = $(obj).parents('a').attr("title");
			}else{
				text = obj.innerHTML.replace(/<.+?>/g,'');
			}
			text = "链接："+text;
		}
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
	text = text.replace(/\.\.\./g,'');
	return text;
};



var changeFont = {};

changeFont.fontZoom = 1;

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
	esd_tool_iframe = $(document.body);
	$(esd_tool_iframe).find(".ESDAssetsTextCon").each(function(){
		$(this).css("font-size",function(index,value){
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
		//}
	});	
};
