$(document).ready(function() {
	var src = $("#flash embed").attr("flashvars");
	var pic = src.split("&")[0].split("|")[0].substring(5);
	var link = $.md5("http://www.caacca.org" + src.split("&")[1].split("|")[0].substring(7)) + ".html";
	var text = src.split("&")[2].split("|")[0].substring(6);
	var html = "<a href=" + link + " title=" + text + "><img src=" + pic + " alt=" + text + " title=" + text + "></a>"
	var titleHtml = "<p>"+text+"</p>";
	$("#slide1").html(html);
	$("#slide_title").html(titleHtml);
	
	
	
	
	
	
	
	$("#gggs-list table").remove();
	$("#gggs-list li span").remove();
	$("#sqtz-list span").remove();
	$("#bzzn-list span").remove();
	$("#zjyj-list span").remove();
	$("#jqrd-list tr").each(function(){
		$(this).find("td:eq(0)").remove();
	})
	$("#mhdt-list tr").each(function(){
		$(this).find("td:eq(0)").remove();
	})
	
	
	
})