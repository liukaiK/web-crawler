$(document).ready(function() {
	$("#NewsListConter table tbody tr").each(function() {
		$(this).children("td:eq(1)").remove();
	})

});