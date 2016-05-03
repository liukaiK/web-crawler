$(document).ready(function(){
	$("#NewsShowConter_txt img").each(function(){
		$(this).css({'max-width':'540px'});
	})
	
	var title = $("#NewsShowConter_date span").text();
	
	$(document).attr("title", title);
	
})


