/** 初始化方法* */

$(document).ready(function() {
	var str = location.href; // 取得整个地址栏
	var num = str.lastIndexOf("/");
	str = str.substr(num + 1); // 取得所有参数 stringvar.substr(start [, length ]
	//alert(str);
	var url = "wca.html?"+str
	$("#esd_toolbar_ctrl").attr("href",url);
});
