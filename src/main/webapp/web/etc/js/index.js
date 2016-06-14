$(document).ready(function() {
	var domain = "http://www.caacca.org";
	var src = $("#flash embed").attr("flashvars");
	var pic = src.split("&")[0].substring(5).split("|");
	var text = src.split("&")[2].substring(6).split("|");
	var link = src.split("&")[1].substring(6).split("|");
	
	
	var md5link = $.md5(domain+link[0].substring(1))+".html";

	var html = "<a href=\""+md5link+"\" id=\"maxImg1\" title=\""+text[0]+"\" >"+
				"<img src=\""+pic[0]+"\" title=\""+text[0]+"\" alt=\"图片\"/>"+
				"</a>"+
				"<p><em>"+text[0]+"</em></p>"+
				"<ol>";
	for(var i = 0; i<pic.length;i++){
		md5link = $.md5(domain+link[i].substring(1))+".html";
		html+=  "<li class><a href=\""+md5link+"\" rel=\""+pic[i]+"\" con=\""+text[i]+"\" title=\""+text[i]+"\" ></a>"+
				"</li>";
	}
	html += "</ol>";
	
	$("#showImg_1").html(html);
	
	
	
	
	
	
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
	$("#qyzs-list tr").each(function(){
		$(this).find("td:eq(0)").remove();
	})
	$("#tstb-list tr").each(function(){
		$(this).find("td:eq(0)").remove();
	})	
	$("#hkystj-list tr").each(function(){
		$(this).find("td:eq(0)").remove();
		$(this).find("td:eq(1)").remove();
	})	
	$("#nav").find("a:eq(0)").focus();
	$("#flash").remove();
})