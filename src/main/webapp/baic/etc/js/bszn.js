$(document).ready(function() {
	$('#ztff ul li').each(function(){
		$(this).children('a').remove();
	})
	
	var title = $("#NewsShowConter_nav div a:last").text();
	$(document).attr("title", title);
	
})