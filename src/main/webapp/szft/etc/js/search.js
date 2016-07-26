$(document).ready(function() {
	$("button[id=search-btn]").click(function() {
		var formName = $("#search");
		var serCon = $("#serCon").val();
		if (serCon.length == 0 || serCon == '填入搜索信息') {
			alert("请填写要查询的信息!");
		} else {
			formName.submit();

		}
	});
	$("#serCon").focus();

})
function validate(obj) {
	if (obj.value == obj.defaultValue) {
		obj.value = '';
		obj.style.color = '#000';
	}

}

function removeValidate(obj) {
	if (!obj.value) {
		obj.value = obj.defaultValue;
		obj.style.color = '#999';
	}
}