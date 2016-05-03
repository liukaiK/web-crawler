$(document).ready(function() {
	$('#boxcenter table tbody tr').each(function() {
		$(this).find('td:eq(1)').css({
			'text-align' : 'left'
		})
		$(this).find('td:eq(2)').css({
			'text-align' : 'right'
		})
		$(this).find('td:eq(2) span').css({
			'margin-right' : '8px'
		})
	})

	$('#xfts_list table tbody tr').each(function() {
		$(this).find('td:eq(1)').css({
			'text-align' : 'left'
		})
	})
	
	
	$('#yjzj_list table tbody tr').each(function() {
		$(this).find('td:eq(1)').css({
			'text-align' : 'left'
		})
	})
	
	
	
	
	
});
