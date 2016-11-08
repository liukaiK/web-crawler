$(document).ready(function() {
	$(".txt table tbody tr").each(function() {
		$(this).find("td:first span").remove();
	})
	$("#NewsShowConter_nav a:last").focus();
})