
/** 初始化方法* */
$(document).ready(function(){
	// 大div上内边距为10px
	$('#NewsListConter').css({'padding-top':'10px'});
	//里面所有td 的margin为10px
//	$('#NewsListConter div table tbody tr th').css({'padding-left':'10px'});
//	$('#NewsListConter div table tbody tr td').css({'padding-left':'20px'});
	// 主题页面左边div宽150px, 右边div宽700px
	$('#NewsListConter').find('div:first').css({'width':'150px','padding-top':'10px'});
	$('#NewsListConter').find('div:last').css({'width':'700px'});
	// 右边div下table第一个td 宽度为350px
	$('#NewsListConter table tbody tr th:eq(0)').css({'width':'350px'});
	
//	$('#NewsListConter table tbody tr td:first').css({'text-align':'left'});
	
	// 右边主体下面 分页那一行样式
//	$('#NewsListConter').find('p').css({'overflow':'hidden','text-overflow':'ellipsis','white-space':'nowrap','width':'350px','height':'30px'});
	// table表格中, 最右边一列隐藏
	$('#NewsListConter table tbody tr th:last').hide();
	$('#NewsListConter table tbody tr td a img').parent().parent().remove();
	
});