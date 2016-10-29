$(document).ready(function() {
	/*********************************轮播图************************************/
	var href = $("#flash div ul:first li:eq(0) a").attr("href");
	var title = $("#flash div div:last ul li:eq(0) a").attr("title");
	var src = $("#flash div ul:first li:eq(0) a img").attr("src");
	var html = "<a href=\"" + href + "\" id=\"maxImg1\" title=\"" + title + "\" >";
	html += "<img src=\"" + src + "\" title=\"" + title + "\" alt=\"图片\"/>";
	html += "</a>";
	html += "<p><em>" + title + "</em></p>";
	html += "<ol>";
	for ( var i = 0; i <= 4; i++) {
		var md5link = $("#flash div div:last ul li:eq(" + i + ") a").attr("href");
		var pic = $("#flash div ul:first li:eq(" + i + ") a img").attr("src");
		var text = $("#flash div div:last ul li:eq(" + i + ") a").attr("title");
		html += "<li class><a href=\"" + md5link + "\" rel=\"" + pic + "\" con=\"" + text + "\" title=\"" + text + "\" ></a></li>";
	}
	html += "</ol>";
	$("#showImg_1").html(html);
	/*********************************轮播图************************************/
	
	$("#flash").remove();
})