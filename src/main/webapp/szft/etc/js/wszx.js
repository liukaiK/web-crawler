/** ****************************意见征集提交********************************* */
var username = "";
var telephone = "";
var idcard = "";
var code = "";
var textarea = "";
var esd_original = $("#esd_original").attr("href");
$.ajaxSetup ({ cache: false });
$(document).ready(function() {
	var timestamp = new Date().getTime();
	$("#codeImage").attr("src", "/iac/random/code?" + timestamp);
	$("#codeImage").load(function() {
		var timestamp = new Date().getTime();
		$.ajax({
			type : 'get',
			url : '/iac/random/getCode?'+ timestamp,
			dataType : 'json',
			success : function(data) {
				$("#voicepath").val(data.path);
			}
		});
	});
	$("#codeImage").click(function() {
		var timestamp = new Date().getTime();
		$("#codeImage").attr("src", "/iac/random/code?" + timestamp);
	});
	submitbtn();
	$("#cl").click(function() {
		cl();
	});
});
function changeImage(){
	var timestamp = new Date().getTime();
	$("#codeImage").attr("src", "/iac/random/code?" + timestamp);
}
function submitbtn() {
	$("#submitbtn").bind("click", function() {
		username = $("#question").val();
		telephone = $("#telephone").val();
		idcard = $("#idcard").val();
		code = $("#code").val();
		textarea = $("#nr").val();
		esd_original = $("#esd_original").attr("href");
		submitPage();
	});
};
function cl() {
	$("#question").val("");
	$("#telephone").val("");
	$("#idcard").val("");
	$("#code").val("");
	$("#nr").val("");
}
function submitPage() {
	$.ajax({
		type : 'post',
		url : '/iac/submitPage2',
		data : {
			// String url,String textarea,String name,String phone,String IdCard
			"problem" : username,
			"phone" : telephone,
			"IdCard" : idcard,
			"code" : code,
			"textarea" : textarea,
			"url" : esd_original
		},
		dataType : 'json',
		success : function(data) {
			if (data.message == true) {
				alert("你的咨询以成功提交，谢谢你的关注，请耐心等待我们的回复！");
			} else {
				if (data.check == false) {
					alert("验证码错误!");
				} else if (data.content) {
					alert("咨询内容不能为空！");
				} else {
					alert("咨询已经提交过了！");
				}
			}
		}
	});
};
