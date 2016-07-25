
/** ��ʼ������* */
$(document).ready(function(){
	//隐藏那个莫名的数字
	$('#NewsShowConter_txt').children('div').children('div').children('li').hide();
	
	
	$.each($('#NewsShowConter_txt').children('div').children('div').children('span'),function(index,item){
		var spanText = $(item).html();
		if(spanText != undefined && spanText != null && spanText != '' && spanText.length < 10){
			$(item).css({'font-size':'16px','font-weight':'bold','color':'rgb(76, 76, 76)'});
		}
	});
	
});