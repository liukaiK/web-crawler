$(document).ready(function() {
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
	var phone = $.trim($("#phone").val()); // 联系电话
	var adress = $.trim($("#adress").val()); // 通讯地址
	var email = $.trim($("#email").val()); // 邮件
	var title = $.trim($("#title").val()); // 标题
	var conContent = $.trim($("#conContent").val()); // 内容
	var code = $.trim($("#code").val()); // 验证码
	if (name == "" || name == "请输入姓名") {
		if (flag == true) {
			alert("请输入姓名");
			show_confirm();
		}
		$("#name").focus();
		return false;
	}	
//	if (name.length >= 25) {
//		if (flag == true) {
//			alert("您的姓名长度不能超过25");
//			show_confirm();
//		}
//		$("#name").focus();
//		return false;
//	}	
	if (phone == "" || phone == "请输入电话") {
		if (flag == true) {
			alert("请输入电话");
			show_confirm();
		}
		$("#phone").focus();
		return false;
	}		
//	if (phone.length >= 100) {
//		if (flag == true) {
//			alert("您的联系电话长度不能超过100");
//			show_confirm();
//		}
//		$("#phone").focus();
//		return false;
//	}	
//	if (adress.length >= 100) {
//		if (flag == true) {
//			alert("您的通讯地址长度不能超过100");
//			show_confirm();
//		}
//		$("#adress").focus();
//		return false;
//	}

	
	if (content == "" || content == "请不要超过400个汉字（包括标点）") {
		if (flag == true) {
			alert("请输入简要内容");
			show_confirm();
		}
		$("#content").focus();
		return false;
	}	

	if (content.length >= 400) {
		if (flag == true) {
			alert("简要内容长度不能超过400");
			show_confirm();
		}
		$("#content").focus();
		return false;
	}
	if (code == "" || code == "请输入验证码") {
		if (flag == true) {
			alert("请输入验证码");
			show_confirm();
		}
		$("#code").focus();
		return false;
	}	
	
	if (adress == "请输入地址") {
		adress = "";
	}
	
	if (number == "请输入电话") {
		number = "";
	}
	
	$.ajax({
		type : 'post',
		url : '/iac/complaintSubmit',
		data : {
			"name" : name,
			"phone" : phone,
			"adress" : adress,
			"day2" : day2,
			"content" : content,
			"code" : code
		},
		dataType : 'json',
		success : function(data) {
			if (data.notice == true) {
				alert(data.message);
				location.reload();
				return false;
			} else {
				alert(data.message);
				return false;
			}
		}
	});
	return false;
}








function show_confirm() {
	var r = confirm(hint);
	if (r == false) {
		flag = false;
	}
}