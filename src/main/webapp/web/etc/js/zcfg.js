$(document).ready(function() {
	$(".txt table tbody tr").each(function() {
		$(this).find("td:eq(1)").remove();
	})
	$("#nav").find("a:eq(4)").focus();
})