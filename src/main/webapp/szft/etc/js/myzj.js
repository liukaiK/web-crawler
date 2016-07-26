
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
				// alert(data.path);
				// return data;
			}
		});
	});
	$("#codeImage").click(function() {
		var timestamp = new Date().getTime();
		$("#codeImage").attr("src", "/iac/random/code?" + timestamp);

	});
	submitbtn();
	
	$("#lookbtn").click(function(){
		lookPage();
	});
	$("#NewsShowConter_txt img").each(function(){
		$(this).css({'max-width':'900px'});
	})
});
function changeImage(){
	var timestamp = new Date().getTime();
	$("#codeImage").attr("src", "/iac/random/code?" + timestamp);
}
function submitbtn() {
	$("#submitbtn").bind("click", function() {
		username = $("#username").val();
		telephone = $("#telephone").val();
		idcard = $("#idcard").val();
		code = $("#code").val();
		textarea = $("#nr").val();
		esd_original = $("#esd_original").attr("href");
		submitPage();
	});
};
function submitPage() {
	$.ajax({
		type : 'post',
		url : '/iac/submitPage',
		data : {
			// String url,String textarea,String name,String phone,String IdCard
			"name" : username,
			"phone" : telephone,
			"IdCard" : idcard,
			"code" : code,
			"textarea" : textarea,
			"url" : esd_original
		},
		dataType : 'json',
		success : function(data) {
			if (data.message == true) {
				window.top.location = data.m + ".html";
			}else{
				if(data.check==false){
					alert("验证码错误!");
				}else if(data.content){
					alert("征集意见不能为空！");
				}else{
					alert("征集结束！");
				}
			}
		}
	});
};
function lookPage() {
	var esd_original = $("#esd_original").attr("href");
	//alert(esd_original);
	$.ajax({
		type : 'post',
		url : '/iac/lookPage',
		dataType : 'json',
		data : {
			"url" : esd_original
		},
		success : function(data) {
			if (data.message == true) {
				window.top.location = data.m + ".html";
			}else{
				if(data.check==false){
					alert("验证码错误!");
				}else if(data.content){
					alert("征集意见不能为空！");
				}else{
					alert("征集结束！");
				}
			}
		}
	});
};
