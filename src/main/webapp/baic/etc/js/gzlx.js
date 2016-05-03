/** ****************************公众来信********************************* */
var author = "";
var email = "";
var mobile = "";
var address = "";
var type = "";
var show = "";
var title = "";
var content = "";
var certicode = "";
var esd_original = $("#esd_original").attr("href");
$.ajaxSetup({
	cache : false
});
$(document).ready(function() {
	var timestamp = new Date().getTime();
	$("#verifyImage").attr("src", "/iac/random/code?" + timestamp);
	$("#verifyImage").load(function() {
		var timestamp = new Date().getTime();
		$.ajax({
			type : 'get',
			url : '/iac/random/getCode?' + timestamp,
			dataType : 'json',
			success : function(data) {
				$("#voicepath").val(data.path);
				// alert(data.path);
				// return data;
			}
		});
	});
	$("#verifyImage").click(function() {
		var timestamp = new Date().getTime();
		$("#verifyImage").attr("src", "/iac/random/code?" + timestamp);

	});
	submitbtn();
});
function changeImage() {
	var timestamp = new Date().getTime();
	$("#verifyImage").attr("src", "/iac/random/code?" + timestamp);
}
function submitbtn() {
	$("#submitbtn").bind("click", function() {
		author = $("#author").val();
		email = $("#email").val();
		mobile = $("#mobile").val();
		address = $("#address").val();
		type = $("input[name='type']:checked").val();
		show = $("input[name='show']:checked").val();
		title = $("#title").val();
		content = $("#content").val();
		certicode = $("#certicode").val();
		esd_original = $("#esd_original").attr("href");
		submitPage();
	});
};
function submitPage() {
	if ($.trim(author) == "" || author == "请输入用户姓名") {
		alert("用户姓名不能为空");
		return;
	}
	if ($.trim(email) == "" || email == "请输入邮箱") {
		alert("邮箱不能为空");
		return;
	}
	if ($.trim(mobile) == "" || mobile == "请输入手机号码") {
		alert("联系方式不能为空");
		return;
	}
	if ($.trim(address) == "" || address == "请输入通信地址") {
		alert("通信地址不能为空");
		return;
	}
	if ($.trim(title) == "" || title == "请输入主题") {
		alert("主题不能为空");
		return;
	}
	if ($.trim(content) == "" || content == "请输入内容(不超过1000个中文字符)") {
		alert("内容不能为空");
		return;
	}
	if ($.trim(certicode) == "") {
		alert("验证码不能为空");
		return;
	}
	$.ajax({
		type : 'post',
		url : '/iac/submitPage',
		data : {
			"author" : author,
			"email" : email,
			"mobile" : mobile,
			"address" : address,
			"type" : type,
			"show" : show,
			"title" : title,
			"content" : content,
			"certicode" : certicode,
			"url" : esd_original
		},
		dataType : 'json',
		success : function(data) {
			if (data.message == true) {
				alert("提交成功");
				window.top.location = data.m + ".html";
			} else {
				if (data.check == false) {
					alert("验证码错误!");
				} else if (data.content) {
					alert("内容不能为空！");
				} else {
					alert("提交失败");
				}
			}
		}
	});
};

function authors() {
	author = $("#author").val();
	if (author == "请输入用户姓名") {
		$("#author").val("");
	}
}

function blurname() {
	author = $("#author").val();
	if ($.trim(author) == "") {
		$("#author").val("请输入用户姓名");
	}
}

function onemail() {
	email = $("#email").val();
	if (email == "请输入邮箱") {
		$("#email").val("");
	}
}

function bluremail() {
	email = $("#email").val();
	if ($.trim(email) == "") {
		$("#email").val("请输入邮箱");
	}
}
function onmobile() {
	mobile = $("#mobile").val();
	if (mobile == "请输入手机号码") {
		$("#mobile").val("");
	}
}

function blurmobile() {
	mobile = $("#mobile").val();
	if ($.trim(mobile) == "") {
		$("#mobile").val("请输入手机号码");
	}
}

function onaddress() {
	address = $("#address").val();
	if (address == "请输入通信地址") {
		$("#address").val("");
	}
}

function bluraddress() {
	address = $("#address").val();
	if ($.trim(address) == "") {
		$("#address").val("请输入通信地址");
	}
}

function ontitle() {
	title = $("#title").val();
	if (title == "请输入主题") {
		$("#title").val("");
	}
}
function blurtitle() {
	title = $("#title").val();
	if ($.trim(title) == "") {
		$("#title").val("请输入主题");
	}
}

function oncontent() {
	content = $("#content").val();
	if (content == "请输入内容(不超过1000个中文字符)") {
		$("#content").val("");
	}
}

function blurcontent() {
	content = $("#content").val();
	if ($.trim(content) == "") {
		$("#content").val("请输入内容(不超过1000个中文字符)");
	}
}

//function blurcerticode(){
//	certicode = $("#certicode").val();
//	if ($.trim(certicode) == "") {
//		$("#certicode").val("请输入验证码");
//	}
//}
