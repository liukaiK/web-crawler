
/** ��ʼ������* */
$(document).ready(function(){
	$('#NewsListConter div table tbody tr td').css({'margin':'10px'});
	$('#NewsListConter').children('div:eq(0)').css({'width':'180px'});
	$('#NewsListConter').children('div:eq(1)').css({'width':'680px'});
	//�Ƴ��� ��ͼͼƬimg��ǩ�� ����ĸ����div
	$('#NewsListConter').children('div:eq(1)').children('div').find('img').parent().parent().remove();
});
