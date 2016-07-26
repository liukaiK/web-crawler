
/** 初始化方法* */
$(document).ready(function(){
	$('#NewsListConter div table tbody tr td').css({'margin':'10px'});
	$('#NewsListConter').children('div:eq(0)').css({'width':'180px'});
	$('#NewsListConter').children('div:eq(1)').css({'width':'680px'});
	//移除掉 地图图片img标签的 父项的父项的div
	$('#NewsListConter').children('div:eq(1)').children('div').find('img').parent().parent().remove();
});
