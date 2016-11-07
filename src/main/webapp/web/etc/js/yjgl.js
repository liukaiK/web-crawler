$(document).ready(function() {
	$("#NewsShowConter_nav a:last").focus();
	var title = $("#NewsShowConter_nav a:last").text();
	$(document).attr("title", title);	
})