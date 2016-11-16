$(document).ready(function() {
	var title = $("#NewsShowConter_nav a:last").text();
	$(document).attr("title", title);
	$("#NewsShowConter_nav a:last").focus();
	var timestamp = new Date().getTime();
	$("input[type='text'],textarea").not("#certicode").attr("onfocus", "validate(this)");
	$("input[type='text'],textarea").attr("onblur", "removeValidate(this)");
	
	$("#verifyImage").attr("src", "/iac/random/code?" + timestamp);
	$("#verifyImage").load(function() {
		var timestamp = new Date().getTime();
		$.ajax({
			type : 'get',
			url : '/iac/random/getCode?' + timestamp,
			dataType : 'json',
			success : function(data) {
				$("#voicepath").val(data.path);
			}
		});
	});
	
	$("#verifyImage").click(function() {
		var timestamp = new Date().getTime();
		$("#verifyImage").attr("src", "/iac/random/code?" + timestamp);

	});
	
	function changeImage() {
		var timestamp = new Date().getTime();
		$("#verifyImage").attr("src", "/iac/random/code?" + timestamp);
	}

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

var hint = "如需继续提供错误提示信息！请选择【确定】; 若不提供！请选择【取消】";
var flag = true;
function submitPage() {
	var name = $.trim($("#name").val()); // 姓名
	var conContent = $.trim($("#conContent").val()); // 内容
	var code = $.trim($("#certicode").val()); // 验证码
	var esd_original = $("#esd_original").attr("href"); //原网址url
	if (name == "" || name == "请输入姓名 必填项") {
		if (flag == true) {
			alert("请输入姓名");
			show_confirm();
		}
		$("#name").focus();
		return false;
	}	
	if (name.length >= 250) {
		if (flag == true) {
			alert("姓名长度不能超过250");
			show_confirm();
		}
		$("#name").focus();
		return false;
	}	
	if (conContent == "" || conContent == "请输入内容 必填项") {
		if (flag == true) {
			alert("请输入内容");
			show_confirm();
		}
		$("#conContent").focus();
		return false;
	}	
	if (conContent.length >= 400) {
		if (flag == true) {
			alert("内容长度不能超过400");
			show_confirm();
		}
		$("#conContent").focus();
		return false;
	}
	if (code == "" || code == "请输入验证码 必填项") {
		if (flag == true) {
			alert("请输入验证码");
			show_confirm();
		}
		$("#code").focus();
		return false;
	}	
	alert("提交成功");
	window.onload();
	return false;
}



function show_confirm() {
	var r = confirm(hint);
	if (r == false) {
		flag = false;
	}
}