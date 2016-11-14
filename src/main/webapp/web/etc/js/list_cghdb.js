$(document).ready(
		function() {
			$("#Sbmenu a:last").focus();
			var currentPage = $("#NewsListConter table:last input:eq(-2)").val();// 获取当前页面的页数
			$("#NewsListConter table:last input:eq(-2)").remove();
			var originalHref = $("#esd_original").attr("href").substring(0,$("#esd_original").attr("href").lastIndexOf("/") + 1);
			var content = $("#NewsListConter table:last").text();// 获取关于页数的文字,用于获取总页数
			var countPage = content.substring(content.indexOf("共") + 1, content.indexOf("页"));
			var selectHTML = "<select>";
			for ( var i = 0; i < countPage; i++) {
				var j = i + 1;
				var href = $.md5(originalHref + "index_" + i  + ".html") + ".html";
				if (i == 0) {
					href = $.md5(originalHref) + ".html";
					if(currentPage == i){
						selectHTML += "<option value=\"" + href + "\" selected=\"selected\">" + j + "</option>";
					}else {
						selectHTML += "<option value=\"" + href + "\">" + j + "</option>";
					}
				}else {
					if(currentPage == j){
						selectHTML += "<option value=\"" + href + "\" selected=\"selected\">" + j + "</option>";
					}else {
						selectHTML += "<option value=\"" + href + "\">" + j + "</option>";
					}
				}
				
			}
			selectHTML += "</select>";
			$("#NewsListConter table:last span").append(selectHTML);
			$("#NewsListConter table:last select").keydown(function(e) {
				var e = e || event, keycode = e.which || e.keyCode;
				if (keycode == 13) {
					var zhi = $("select").val();
					if ("转到" != zhi) {
						location.href = zhi;
					}
				}
			});
			
			
			$("#NewsListConter table:last input:last").click(function(){
				
				var zhi = $("#NewsListConter table:last select option:selected").val();
				location.href = zhi;
				
			})
			
			$("#NewsListConter ul li:eq(5)").remove();
			$("#NewsListConter ul li:eq(10)").remove();
			$("#NewsListConter ul li:eq(15)").remove();
			$("#NewsListConter ul li:eq(20)").remove();
			$("#NewsListConter img").remove();
			var title = $("#Sbmenu a:last").text();
			$(document).attr("title", title);	
			
		});


