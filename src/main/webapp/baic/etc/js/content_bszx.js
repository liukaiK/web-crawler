$(document).ready(function() {
	$("#NewsShowConter_txt img").each(function() {
		$(this).css({
			'max-width' : '900px'
		});
	})

	var title = $("#NewsShowConter_date span").text();

	$(document).attr("title", title);

	var content = $.trim($("#NewsShowConter_txt pre span").text());
	$("#NewsShowConter_txt pre span").text(content)

})


