$(document).ready(function() {
	$("#NewsListConter div table tbody tr").each(function() {
		$(this).find("td:eq(2) span").css({
			"float" : "right"
		});
		$(this).find("td:eq(2)").css({
			"display" : "inline"
		});
		$(this).find("td:eq(0)").css({
			"width" : "16px",
			"float" : "left"
		});
		$(this).find('td:eq(1)').css({
			"width" : "85%",
			"overflow" : "hidden",
			"text-overflow" : "ellipsis",
			"white-space" : "nowrap"
		});
		$(this).children("td").children("a").css({
			"width" : "660px",
			"white-space" : "nowrap",
			"text-overflow" : "ellipsis",
			"-o-text-overflow" : "ellipsis",
			"overflow" : "hidden",
			"display" : "inline-block"
		})
	})

	$("#NewsListConter div table:last tbody tr").each(function() {
		$(this).find("td:eq(0)").css({
			"width" : "auto"
		});
		$(this).find("td:eq(2) span").css({
			"float" : "none"
		});
		$(this).find("td:eq(2)").css({
			"display" : ""
		})
		$(this).find("td:eq(1)").css({
			"width" : "auto"
		});
		$(this).find("td a").css({
			"width" : "auto",
			"white-space" : "none",
			"-o-text-overflow" : "none",
			"overflow" : "none",
			"display" : "inline"
		});
		$(this).find("span").css({
			"float" : "none"
		})

	});

	$("#NewsListConter div table:last tbody tr td:eq(-1)").css({
		"width" : "100px"
	})
	$("#NewsListConter div table:last tbody tr td:eq(-2)").css({
		"width" : "100px"
	})
	$("#NewsListConter div table:last tbody tr td:eq(-2) li:eq(1)").css({
		"float" : "left"
	})
	$("#NewsListConter div table:last tbody tr td:eq(-2) li:eq(0)").css({
		"float" : "right",
		"margin-right" : "5px"
	})
	$("#NewsListConter div table:last tbody tr td:eq(-1) li:eq(1)").css({
		"float" : "left",
		"margin-left" : "5px"
	})
	$("#NewsListConter div table:last tbody tr td:eq(-1) li:eq(0)").css({
		"float" : "right"
	})
	$("#NewsListConter div table:last").css({
		"width" : "auto",
		"margin" : "auto",
		"text-align" : "center"
	})
	
	var title = $("#Sbmenu div a:last").text();
	$(document).attr("title", title);
	

});