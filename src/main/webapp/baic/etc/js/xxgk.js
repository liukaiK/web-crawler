$(document).ready(function() {
	$(".col .block2 div div table tbody tr").each(function() {
		$(this).find("td:eq(2)").css({
			"float" : "right",
			"margin-right" : "10px"
		});
		$(this).find("td:eq(0)").css({
			"width" : "15%",
			"padding-left" : "10px"
		});
	})
	$("#bmdt div table tbody tr,#ldhd div table tbody tr,#zfgzbg div table tbody tr,#ghsj div table tbody tr").each(function() {
		$(this).find('td:eq(0)').remove();
		$(this).find("td:eq(0)").css({
			"padding-left" : "10px"
		});
	})
})