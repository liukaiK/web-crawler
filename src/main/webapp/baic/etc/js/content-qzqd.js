$(document).ready(function(){
	$('#NewsShowConter_txt1 table tbody tr td span').css({'font-size':'11px'});
	$('#NewsShowConter_txt1 table tbody tr td').css({'padding':'4px'});
	$('#NewsShowConter_txt1 table tbody tr td:eq(0)').css({'width':'90px'});
	$('#NewsShowConter_txt1 table tbody tr td:eq(2)').css({'width':'90px'});
	if($('.main').children('div').children('div').children('pre')){
		$.each($('.main pre'),function(index,item){
			var html_pre = $(item).html();
			$(item).parent().append(html_pre);
			$(item).remove();
			
		});
	};
	$('#NewsShowConter_txt6 div table tbody tr td:eq(0)').css({'width':'140px'});
});