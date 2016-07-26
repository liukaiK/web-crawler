
/** ��ʼ������* */
$(document).ready(function(){
	//��һ����table������ʽ
	$('#NewsShowConter_txt1 table tbody tr td span').css({'font-size':'11px'});
	$('#NewsShowConter_txt1 table tbody tr td').css({'padding':'4px'});
	$('#NewsShowConter_txt1 table tbody tr td:eq(0)').css({'width':'90px'});
	$('#NewsShowConter_txt1 table tbody tr td:eq(2)').css({'width':'90px'});
	//��������pre��ǩ, �����е�����ֱ�ӷŵ�pre�ĸ���ǩ��, ����pre��ǩɾ��~~~
	if($('.main').children('div').children('div').children('pre')){
		$.each($('.main pre'),function(index,item){
			//pre�е�html����
			var html_pre = $(item).html();
			// ��pre�ĸ���ǩ��������html_pre
			$(item).parent().append(html_pre);
			//�Ƴ�pre��ǩ
			$(item).remove();
			
		});
	};
	//ְȨ��Ϣ�� ��ʽ����
	$('#NewsShowConter_txt6 div table tbody tr td:eq(0)').css({'width':'140px'});
});