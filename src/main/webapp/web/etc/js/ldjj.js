$(document).ready(function() {
//	$(".txt table tbody tr").each(function() {
//		$(this).find("td:eq(1)").remove();
//	})
	
	$("#NewsShowConter_nav a:last").focus();
	var title = $("#NewsShowConter_nav a:last").text();
	$(document).attr("title", title);	
	
})