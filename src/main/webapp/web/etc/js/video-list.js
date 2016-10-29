$(document).ready(
		function() {
			$("#Sbmenu a:last").focus();
			var currentPage = $("#page div font input:eq(-2)").val();// 获取当前页面的页数
			$("#page div font input:eq(-2)").remove();
			var originalHref = $("#esd_original").attr("href").substring(0,$("#esd_original").attr("href").lastIndexOf("/") + 1);
			var content = $("#page div").text();// 获取关于页数的文字,用于获取总页数
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
			$("#page div font span").append(selectHTML);
			$("#page select").keydown(function(e) {
				var e = e || event, keycode = e.which || e.keyCode;
				if (keycode == 13) {
					var zhi = $("select").val();
					if ("转到" != zhi) {
						location.href = zhi;
					}
				}
			});
			
			
			$("#page div font input:last").click(function(){
				
				var zhi = $("#page div font select option:selected").val();
				location.href = zhi;
				
			})
			
			$("#NewsListConter ul li:eq(5)").remove();
			$("#NewsListConter ul li:eq(10)").remove();
			$("#NewsListConter ul li:eq(15)").remove();
			$("#NewsListConter ul li:eq(20)").remove();
			
			
			
			
		});


