/** 初始化方法* */
var root = "/iac/";
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
			if (e.keyCode == 75) { // k
				$("html,body").animate({scrollTop : $("#top-bar").offset().top}, 500); // 1000是ms,也可以用slow代替
				$("#nav").find("a").each(function(index) {
					if (index == 0) {
						$(this).focus();
					}
				});
			}
			if (e.keyCode == 88) { // X
				if ($('#NewsShowConter_txt').length > 0) {
					keySelectScroll("#NewsShowConter_txt");
				}else{
					keySelectScroll(".main");
				}
			}
			if (e.keyCode == 89) { // Y
				keySelectScroll("#footer");
			}
			if (e.keyCode == 34) { // page down
				if (sIndex < maxIndex) {
					sIndex++;
					keyShowSelect(sIndex);
				}
			}
			if (e.keyCode == 33) { // page up
				if (sIndex > minIndex) {
					sIndex--;
					keyShowSelect(sIndex);
				}
			}
			if (e.keyCode == 84) { // T
			// alert("开启辅助浏览模式");
			}
			if (e.keyCode == 74) { // J 开启和关闭无障碍工具
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
			$(this).css("color", "")
			$(this).css("border-color", "")
			if (this == obj) {
				sIndex = index;
			}
		});

		$(this).css("background-color", "#222222");
		$(this).css("color", "yellow")
		$(this).css("border-color", "red")
		$(this).find("a").each(function(index) {
			if (index == 0) {
				$(this).focus();
			}
		});

	});

	$(".hoverblack").mouseout(function() {
		$(this).css("background-color", "");
		$(this).css("color", "")
		$(this).css("border-color", "")
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
		$(this).css("color", "yellow")
		$(this).css("border-color", "red")
	})
	$('a').blur(function(){
		$(this).css("background-color", "");
		$(this).css("color", "")
		$(this).css("border-color", "")
	})
	getPageView();
	
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
		$(this).css("color", "")
		$(this).css("border-color", "")
	});
	$(".hoverblack").each(function(index, ehx) {
		if (i == index) {
			$(this).css("background-color", "black");
			$(this).css("color", "yellow")
			$(this).css("border-color", "red")
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
function addPageView() {
	var timestamp = new Date().getTime();
	$.ajax({
		url : root + '/addPageView?' + timestamp,
		type : 'GET',
		sync : true,
		success : function(data) {
		},
		error : function() {
		}
	});
}


function getPageView() {
	var timestamp = new Date().getTime();
	$.ajax({
		url : root + '/getPageView?' + timestamp,
		type : 'GET',
		sync : true,
		success : function(data) {
			$("#pageview").text(data.message);
			addPageView();
		},
		error : function() {

		}
	});
}
