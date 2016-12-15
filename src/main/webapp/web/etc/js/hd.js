$(document).ready(function() {
//	$(".txt table tbody tr").each(function() {
//		$(this).find("td:eq(1)").remove();
//	})
	
	$("#nav").find("a:eq(4)").focus();
	
	

	var size = $("#sjyq ul li").size();
	console.info(size);
	for ( var i = size; i >= 12; i--) {
		$("#sjyq ul li:eq(" + i + ")").remove();
	}
	
	
	
	
})