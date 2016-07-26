/*--
 Copyright (c) 2013 哈尔滨亿时代数码科技开发有限公司（www.hrbesd.com）. All rights reserved.
  
  HRBESD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
--*/
var keybinding = {};
keybinding.bind = function(){
	if(iframe_change==1){  //保证document只绑定一次keydown事件
		jq_1_7_1(document).keydown(function(e){
			if (ESDWebApp.toolbar.toolbarStatus.opened) {
				esd_tool_iframe = jq_1_7_1(document.getElementById("iframe").contentWindow.document.body);
	
				if (e.altKey && e.shiftKey) {
					if (e.keyCode == 74) {
						//(alt+shift+j)=开关工具条
						jq_1_7_1(esd_tool_iframe).find("#openOrCloseToolbar").trigger("click");
					} else if (e.keyCode == 90) {
						//(alt+shift+z)=朗读工具条功能
						speak.sound.voicePrompt.description();
					} else if (e.keyCode == 192) {
						//(alt+shift+`)=首页
						jq_1_7_1("#toolbar_home").trigger("click");
					} else if (e.keyCode == 191) {
						//(alt+shift+/)=帮助
						jq_1_7_1("#toolbar_help").trigger("click");
					} else if (e.keyCode == 81) {
						//(alt+shift+q)=退出
						jq_1_7_1("#toolbar_exit").trigger("click");
					} else if (e.keyCode == 188) {
						//(alt+shift+,)=后退
						jq_1_7_1("#toolbar_retreat").trigger("click");
					} else if (e.keyCode == 190) {
						//(alt+shift+.)=前进
						jq_1_7_1("#toolbar_ahead").trigger("click");
					} else if (e.keyCode == 49) {
						//(alt+shift+1)=箭头
	//					jq_1_7_1("#toolbar_refresh").trigger("click");
						
						//当前划勾的img标签
						var current = jq_1_7_1("#cy_arrow_dl dt").find("img").filter(function(){
							return jq_1_7_1(this).css("display") == "block";
						});
						//上一个dt
						var pre = jq_1_7_1(current).parent().parent().prev().prev();
						//整个dl中第一个dt中的a标签的id
						var first = jq_1_7_1("#cy_arrow_dl dt a").first().attr("id");
						//当前划勾img所在a标签的id
						var currentA = jq_1_7_1(current).parent().attr("id");
						if (first != currentA) {
	//						jq_1_7_1(pre).children("a").children("img").css("display", "block");
	//						jq_1_7_1(current).css("display", "none");
							//上一个dt中a标签的id
							var preAinDt = jq_1_7_1(pre).children("a").attr('id');
							jq_1_7_1('#'+preAinDt).trigger("click");
						}else{
							jq_1_7_1('#arrow_5').trigger("click");
						}
						
						
						
						
							
					} else if (e.keyCode == 50) {
						//(alt+shift+2)=光标
						jq_1_7_1("#toolbar_guides").trigger("click");
					} else if (e.keyCode == 51) {
						//(alt+shift+3)=纯文本
						jq_1_7_1("#toolbar_textMode").trigger("click");
					} else if (e.keyCode == 52) {
						//(alt+shift+4)=大字
						jq_1_7_1("#toolbar_text_bigger").trigger("click");
					} else if (e.keyCode == 53) {
						//(alt+shift+5)=小字
						jq_1_7_1("#toolbar_text_smaller").trigger("click");
					} else if (e.keyCode == 54) {
						//(alt+shift+6)=配色
						hide("cy_color_dl");
					} else if (e.keyCode == 55) {
						//(alt+shift+7)=页面放大
						jq_1_7_1("#toolbar_pageZoomIc").trigger("click");
					} else if (e.keyCode == 56) {
						//(alt+shift+8)=页面缩小
						jq_1_7_1("#toolbar_pageZoomDc").trigger("click");
					} else if (e.keyCode == 57) {
						//(alt+shift+9)=语音开关
						jq_1_7_1("#toolbar_speakOnOff").trigger("click");
					} else if (e.keyCode == 48) {
						//(alt+shift+0)=语音调节
						if (statusspeakOnOff == "off") {
							hide("cy_playSpeech_dl");
						}
					} else if (e.keyCode == 219) {
						//(alt+shift+[)=显示屏
						jq_1_7_1("#toolbar_magnifier").trigger("click");
					} else if (e.keyCode == 221) {
						//(alt+shift+])=全屏
						jq_1_7_1("#toolbar_fullScreen").trigger("click");
					} else if (e.keyCode == 38) {
						//(alt+shift+↑)=向上
	//					if (jq_1_7_1("#cy_color_dl").attr("class") == "xian_1") {
							//当前划勾的img标签
							var current = jq_1_7_1("#cy_color_dl").find("img").filter(function(){
								return jq_1_7_1(this).css("display") == "block";
							});
							
							//上一个dt
							var pre = jq_1_7_1(current).parent().parent().prev().prev();
							
							//整个dl中第一个dt中的a标签的id
							var first = jq_1_7_1("#cy_color_dl dt a").first().attr("id");
							//当前划勾img所在a标签的id
							var currentA = jq_1_7_1(current).parent().attr("id");
	
							if (first != currentA) {
								jq_1_7_1(pre).children("a").children("img").css("display", "block");
								jq_1_7_1(current).css("display", "none");
								//上一个dt中a标签的id
								var preAinDt = jq_1_7_1(pre).children("a").attr('id');
								jq_1_7_1('#'+preAinDt).trigger("click");
							}
	//					}
					} else if (e.keyCode == 40) {
						//(alt+shift+↓)=向下
	//					if (jq_1_7_1("#cy_color_dl").attr("class") == "xian_1") {
							//当前划勾的img标签
							var current = jq_1_7_1("#cy_color_dl").find("img").filter(function(){
								return jq_1_7_1(this).css("display") == "block";
							});
							
							//下一个dt
							var next = jq_1_7_1(current).parent().parent().next().next();
							
							//整个dl中最后一个dt中的a标签的id
							var last = jq_1_7_1("#cy_color_dl dt a").last().attr("id");
							//当前划勾img所在a标签的id
							var currentA = jq_1_7_1(current).parent().attr("id");
	
							if (last != currentA) {
								jq_1_7_1(next).children("a").children("img").css("display", "block");
								jq_1_7_1(current).css("display", "none");
								//下一个dt中a标签的id
								var nextAinDt = jq_1_7_1(next).children("a").attr('id');
								jq_1_7_1('#'+nextAinDt).trigger("click");
							}
	//					}
					} else if (e.keyCode == 37) {
						//(alt+shift+←)=减缓语速
	//					if (jq_1_7_1("#cy_playSpeech_dl").attr("class") == "xian_1") {
							speak.speed("-1");
	//					}
					} else if (e.keyCode == 39) {
						//(alt+shift+→)=加快语速
	//					if (jq_1_7_1("#cy_playSpeech_dl").attr("class") == "xian_1") {
							speak.speed("1");
	//					}
					}
					if (navigator.userAgent.indexOf("Firefox") >= 0) {
						if (e.keyCode == 173) {
							//(alt+shift+-)=指读
							jq_1_7_1("#toolbar_Pointread").trigger("click");
						} else if (e.keyCode == 61) {
							//(alt+shift+=)=连读
							jq_1_7_1("#toolbar_batchRead").trigger("click");
						}
					} else {
						if (e.keyCode == 189) {
							//(alt+shift+-)=指读
							jq_1_7_1("#toolbar_Pointread").trigger("click");
						} else if (e.keyCode == 187) {
							//(alt+shift+=)=连读
							jq_1_7_1("#toolbar_batchRead").trigger("click");
						}
					}
				}
			}
	
			/*//打开/关闭工具栏Altt+0
			if(e.altKey&&e.keyCode == 48){
				jq_1_7_1("#toolbar_action").trigger("click");
			}
	
			if(ESDWebApp.toolbar.toolbarStatus.opened){//判断是否打开工具栏
				if(e.altKey){
					if(e.keyCode == 49){//alt+1 正常模式
						jq_1_7_1("#toolbar_highContrastDefalt").trigger("click");
					}else if(e.keyCode == 50){//alt+2 高对比模式1
						jq_1_7_1("#toolbar_highContrastYellow").trigger("click");
					}else if(e.keyCode == 51){//alt+3 高对比模式2
						jq_1_7_1("#toolbar_highContrastWhite").trigger("click");
					}else if(e.keyCode == 52){//alt+4 放大字体
						jq_1_7_1("#toolbar_text_bigger").trigger("click");
					}else if(e.keyCode == 53){//alt+5 还原字体
						jq_1_7_1("#toolbar_text_default").trigger("click");
					}else if(e.keyCode == 54){//alt+6 缩小字体
						jq_1_7_1("#toolbar_text_smaller").trigger("click");
					}else if(e.keyCode == 55){//alt+7 放大页面
						jq_1_7_1("#toolbar_pageZoomIc").trigger("click");
					}else if(e.keyCode == 56){//alt+8 还原页面
						jq_1_7_1("#toolbar_pageZoomDefalt").trigger("click");
					}else if(e.keyCode == 57){//alt+9 缩小页面
						jq_1_7_1("#toolbar_pageZoomDc").trigger("click");
					}
				}else if(e.shiftKey){
					if(e.keyCode == 48){//shift+0  朗读工具栏功能
						speak.sound.voicePrompt.intordus();
					}else if(e.keyCode == 49){//shift+1 文本模式
						jq_1_7_1("#toolbar_textMode_on").trigger("click");
					}else if(e.keyCode == 50){//shift+2 图文模式
						jq_1_7_1("#toolbar_textMode_off").trigger("click");
					}else if(e.keyCode == 51){//shift+3 开启/关闭在线翻译
						jq_1_7_1("#toolbar_translate").trigger("click");
					}else if(e.keyCode == 52){//shift+4 开启/关闭自动朗读
						jq_1_7_1("#toolbar_batchRead").trigger("click");
					}else if(e.keyCode == 53){//shift+5 开启/关闭即指即读
						jq_1_7_1("#toolbar_Pointread").trigger("click");
					}else if(e.keyCode == 54){//shift+6 开启/关闭键盘朗读
						jq_1_7_1("#toolbar_keybordRead").trigger("click");
					}else if(e.keyCode == 55){//shift+7 开启/关闭放大镜功能
						jq_1_7_1("#toolbar_magnifier").trigger("click");
					}else if(e.keyCode == 56){//shift+8 开启/关闭辅助线功能
						jq_1_7_1("#toolbar_guides").trigger("click");
					}else if(e.keyCode == 57){//shift+9 无障碍说明
						jq_1_7_1("#toolbar_instruction").trigger("click");
					}
				}
			}*/
		});
	}
	jq_1_7_1(document.getElementById("iframe").contentWindow.document).keydown(function(e){
		if (ESDWebApp.toolbar.toolbarStatus.opened) {
			if (e.altKey && e.shiftKey) {
				if (e.keyCode == 90) {
					//(alt+shift+z)=朗读工具条功能
					speak.sound.voicePrompt.description();
				} else if (e.keyCode == 192) {
					//(alt+shift+`)=首页
					jq_1_7_1("#toolbar_home").trigger("click");
				} else if (e.keyCode == 191) {
					//(alt+shift+/)=帮助
					jq_1_7_1("#toolbar_help").trigger("click");
				} else if (e.keyCode == 81) {
					//(alt+shift+q)=退出
					jq_1_7_1("#toolbar_exit").trigger("click");
				} else if (e.keyCode == 188) {
					//(alt+shift+,)=后退
					jq_1_7_1("#toolbar_retreat").trigger("click");
				} else if (e.keyCode == 190) {
					//(alt+shift+.)=前进
					jq_1_7_1("#toolbar_ahead").trigger("click");
				} else if (e.keyCode == 49) {
					//(alt+shift+1)=刷新
//					jq_1_7_1("#toolbar_refresh").trigger("click");
					
					//当前划勾的img标签
					var current = jq_1_7_1("#cy_arrow_dl dt").find("img").filter(function(){
						return jq_1_7_1(this).css("display") == "block";
					});
					//上一个dt
					var pre = jq_1_7_1(current).parent().parent().prev().prev();
					//整个dl中第一个dt中的a标签的id
					var first = jq_1_7_1("#cy_arrow_dl dt a").first().attr("id");
					//当前划勾img所在a标签的id
					var currentA = jq_1_7_1(current).parent().attr("id");
					if (first != currentA) {
//						jq_1_7_1(pre).children("a").children("img").css("display", "block");
//						jq_1_7_1(current).css("display", "none");
						//上一个dt中a标签的id
						var preAinDt = jq_1_7_1(pre).children("a").attr('id');
						jq_1_7_1('#'+preAinDt).trigger("click");
					}else{
						jq_1_7_1('#arrow_5').trigger("click");
					}
					
					
					
					
					
					
					
				} else if (e.keyCode == 50) {
					//(alt+shift+2)=光标
					jq_1_7_1("#toolbar_guides").trigger("click");
				} else if (e.keyCode == 51) {
					//(alt+shift+3)=纯文本
					jq_1_7_1("#toolbar_textMode").trigger("click");
				} else if (e.keyCode == 52) {
					//(alt+shift+4)=大字
					jq_1_7_1("#toolbar_text_bigger").trigger("click");
				} else if (e.keyCode == 53) {
					//(alt+shift+5)=小字
					jq_1_7_1("#toolbar_text_smaller").trigger("click");
				} else if (e.keyCode == 54) {
					//(alt+shift+6)=配色
					hide("cy_color_dl");
				} else if (e.keyCode == 55) {
					//(alt+shift+7)=页面放大
					jq_1_7_1("#toolbar_pageZoomIc").trigger("click");
				} else if (e.keyCode == 56) {
					//(alt+shift+8)=页面缩小
					jq_1_7_1("#toolbar_pageZoomDc").trigger("click");
				} else if (e.keyCode == 57) {
					//(alt+shift+9)=语音开关
					jq_1_7_1("#toolbar_speakOnOff").trigger("click");
				} else if (e.keyCode == 48) {
					//(alt+shift+0)=语音调节
					if (statusspeakOnOff == "off") {
						hide("cy_playSpeech_dl");
					}
				}else if (e.keyCode == 219) {
					//(alt+shift+[)=显示屏
					jq_1_7_1("#toolbar_magnifier").trigger("click");
				} else if (e.keyCode == 221) {
					//(alt+shift+])=全屏
					jq_1_7_1("#toolbar_fullScreen").trigger("click");
				} else if (e.keyCode == 38) {
					//(alt+shift+↑)=向上
//					if (jq_1_7_1("#cy_color_dl").attr("class") == "xian_1") {
						//当前划勾的img标签
						var current = jq_1_7_1("#cy_color_dl").find("img").filter(function(){
							return jq_1_7_1(this).css("display") == "block";
						});
						
						//上一个dt
						var pre = jq_1_7_1(current).parent().parent().prev().prev();
						
						//整个dl中第一个dt中的a标签的id
						var first = jq_1_7_1("#cy_color_dl dt a").first().attr("id");
						//当前划勾img所在a标签的id
						var currentA = jq_1_7_1(current).parent().attr("id");

						if (first != currentA) {
							jq_1_7_1(pre).children("a").children("img").css("display", "block");
							jq_1_7_1(current).css("display", "none");
							//上一个dt中a标签的id
							var preAinDt = jq_1_7_1(pre).children("a").attr('id');
							jq_1_7_1('#'+preAinDt).trigger("click");
						}
//					}
				} else if (e.keyCode == 40) {
					//(alt+shift+↓)=向下
//					if (jq_1_7_1("#cy_color_dl").attr("class") == "xian_1") {
						//当前划勾的img标签
						var current = jq_1_7_1("#cy_color_dl").find("img").filter(function(){
							return jq_1_7_1(this).css("display") == "block";
						});
						
						//下一个dt
						var next = jq_1_7_1(current).parent().parent().next().next();
						
						//整个dl中最后一个dt中的a标签的id
						var last = jq_1_7_1("#cy_color_dl dt a").last().attr("id");
						//当前划勾img所在a标签的id
						var currentA = jq_1_7_1(current).parent().attr("id");

						if (last != currentA) {
							jq_1_7_1(next).children("a").children("img").css("display", "block");
							jq_1_7_1(current).css("display", "none");
							//下一个dt中a标签的id
							var nextAinDt = jq_1_7_1(next).children("a").attr('id');
							jq_1_7_1('#'+nextAinDt).trigger("click");
						}
//					}
				} else if (e.keyCode == 37) {
					//(alt+shift+←)=减缓语速
//					if (jq_1_7_1("#cy_playSpeech_dl").attr("class") == "xian_1") {
						speak.speed("-1");
//					}
				} else if (e.keyCode == 39) {
					//(alt+shift+→)=加快语速
//					if (jq_1_7_1("#cy_playSpeech_dl").attr("class") == "xian_1") {
						speak.speed("1");
//					}
				}
				if (navigator.userAgent.indexOf("Firefox") >= 0) {
					if (e.keyCode == 173) {
						//(alt+shift+-)=指读
						jq_1_7_1("#toolbar_Pointread").trigger("click");
					} else if (e.keyCode == 61) {
						//(alt+shift+=)=连读
						jq_1_7_1("#toolbar_batchRead").trigger("click");
					}
				} else {
					if (e.keyCode == 189) {
						//(alt+shift+-)=指读
						jq_1_7_1("#toolbar_Pointread").trigger("click");
					} else if (e.keyCode == 187) {
						//(alt+shift+=)=连读
						jq_1_7_1("#toolbar_batchRead").trigger("click");
					}
				}
			}
		}

		/*//打开/关闭工具栏Altt+0
		if(e.altKey&&e.keyCode == 48){
			jq_1_7_1("#toolbar_action").trigger("click");
		}

		if(ESDWebApp.toolbar.toolbarStatus.opened){//判断是否打开工具栏
			if(e.altKey){
				if(e.keyCode == 49){//alt+1 正常模式
					jq_1_7_1("#toolbar_highContrastDefalt").trigger("click");
				}else if(e.keyCode == 50){//alt+2 高对比模式1
					jq_1_7_1("#toolbar_highContrastYellow").trigger("click");
				}else if(e.keyCode == 51){//alt+3 高对比模式2
					jq_1_7_1("#toolbar_highContrastWhite").trigger("click");
				}else if(e.keyCode == 52){//alt+4 放大字体
					jq_1_7_1("#toolbar_text_bigger").trigger("click");
				}else if(e.keyCode == 53){//alt+5 还原字体
					jq_1_7_1("#toolbar_text_default").trigger("click");
				}else if(e.keyCode == 54){//alt+6 缩小字体
					jq_1_7_1("#toolbar_text_smaller").trigger("click");
				}else if(e.keyCode == 55){//alt+7 放大页面
					jq_1_7_1("#toolbar_pageZoomIc").trigger("click");
				}else if(e.keyCode == 56){//alt+8 还原页面
					jq_1_7_1("#toolbar_pageZoomDefalt").trigger("click");
				}else if(e.keyCode == 57){//alt+9 缩小页面
					jq_1_7_1("#toolbar_pageZoomDc").trigger("click");
				}
			}else if(e.shiftKey){
				if(e.keyCode == 48){//shift+0  朗读工具栏功能
					speak.sound.voicePrompt.intordus();
				}else if(e.keyCode == 49){//shift+1 文本模式
					jq_1_7_1("#toolbar_textMode_on").trigger("click");
				}else if(e.keyCode == 50){//shift+2 图文模式
					jq_1_7_1("#toolbar_textMode_off").trigger("click");
				}else if(e.keyCode == 51){//shift+3 开启/关闭在线翻译
					jq_1_7_1("#toolbar_translate").trigger("click");
				}else if(e.keyCode == 52){//shift+4 开启/关闭自动朗读
					jq_1_7_1("#toolbar_batchRead").trigger("click");
				}else if(e.keyCode == 53){//shift+5 开启/关闭即指即读
					jq_1_7_1("#toolbar_Pointread").trigger("click");
				}else if(e.keyCode == 54){//shift+6 开启/关闭键盘朗读
					jq_1_7_1("#toolbar_keybordRead").trigger("click");
				}else if(e.keyCode == 55){//shift+7 开启/关闭放大镜功能
					jq_1_7_1("#toolbar_magnifier").trigger("click");
				}else if(e.keyCode == 56){//shift+8 开启/关闭辅助线功能
					jq_1_7_1("#toolbar_guides").trigger("click");
				}else if(e.keyCode == 57){//shift+9 无障碍说明
					jq_1_7_1("#toolbar_instruction").trigger("click");
				}
			}
		}*/
	});
};
