$(document).ready(function() {
	$("#zfxx table tbody tr").each(function() {
		$(this).find("td:eq(1)").remove();
	})
	$("#nav").find("a:eq(5)").focus();
})