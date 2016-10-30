$(document).ready(function() {
	$("#NewsShowConter_txt img").each(function() {
		$(this).css({
			'max-width' : '540px'
		});
	})

	$("#NewsShowConter_date div:eq(1)").css({
		"padding-top" : "10px"
	});
	

	$("#NewsShowConter_nav a:last").focus();
	var title = $("#NewsShowConter_date h1").text();
	
	$(document).attr("title", title);
	
//	var tabindex = $("#NewsShowConter_date div span ins").attr("tabindex");
//	alert($("#NewsShowConter_date div span").html());
//	alert(tabindex);
	
//	
//	$("#NewsShowConter_date div span").attr("tabindex", "-1");
//	
//	$("#NewsShowConter_date div span").focus();
	

})