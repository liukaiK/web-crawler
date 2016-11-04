$(document).ready(function() {
	$("#Sbmenu a:last").focus();
	var title = $("#Sbmenu a:last").text();
	$(document).attr("title", title);
	
	
	$("#NewsListConter img").remove();

});
