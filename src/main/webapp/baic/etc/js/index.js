$(document).ready(function() {
	$("#htsfwb table:eq(0)").remove();
	var title = $("#slide1 h1 span").text();
	$("#slide1 h1 span").remove();
	$("#slide_title1").text(title);
})