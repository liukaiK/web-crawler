$(document).ready(function() {
	$("#sjfb div table tbody tr").each(function() {
		$(this).find('td:eq(2)').css({
			"float" : "right",
			"margin-right" : "10px"
		})
	})
	$("#searchButton").click(function() {
		var formName = $("#searchForm");
		var serCon = $("#search-text").val();
		if (serCon.length == 0 || serCon == "请输入要搜索的关键字") {
			alert("请填写要查询的信息!");
		} else {
			formName.submit();
		}
	});
})