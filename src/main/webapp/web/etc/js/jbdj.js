var name = "";
var gender = "";
var phone = "";
var adress = "";
var password = "";
var affirmpassword = "";
var designation = "";
var city = "";
var local = "";
var number = "";
var goods = "";
var goods_name = "";
var look_into = "";
var award = "";
var secrecy = "";
var communication = "";
var investigate = "";
var happen_time = "";
var content = "";
var code = "";




$(document).ready(function() {
	var timestamp = new Date().getTime();
	$("#verifyImage").attr("src", "/iac/random/code?" + timestamp);
	$("#verifyImage").load(function() {
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
	
	$("#SubmitButton").bind("click", function() {
		name = $("#name").val();
		gender = $("input[name='gender']:checked").val();
		phone = $("#phone").val();
		adress = $("#adress").val();
		password = $("#password").val();
		affirmpassword = $("#affirmpassword").val();
		designation = $("#designation").val();
		city = $("#city option:selected").val();
		local = $("#local").val();
		number = $("#number").val();
		goods = $("input[name='goods']:checked").val();
		goods_name = $("#goods_name").val();
		look_into = $("input[name='look_into']:checked").val();
		award = $("input[name='award']:checked").val();
		secrecy = $("input[name='secrecy']:checked").val();
		communication = $("input[name='communication']:checked").val();
		investigate = $("input[name='investigate']:checked").val();
		happen_time = $("#happen_time").val();
		content = $("#content").val();
		code = $("#code").val();
		submitbtn();
	});
})

function submitbtn() {
	if (designation == "请输入名称") {
		alert("请输入名称");
		return;
	}
	if (city == "请选择") {
		alert("请选择地址");
		return;
	}
	if (local == "请输入地址") {
		alert("请输入地址");
		return;
	}	
	if (goods_name == "请输入商品或服务名称") {
		alert("请输入商品或服务名称");
		return;
	}	
	if (happen_time == "") {
		alert("请输入事件发生时间");
		return;
	}	
	if (content == "请不要超过400个汉字（包括标点）") {
		alert("请输入内容");
		return;
	}
	if (name == document.getElementById("name").defaultValue) {
		name = "";
	}	
	if (phone == document.getElementById("phone").defaultValue) {
		phone = "";
	}	
	if (adress == document.getElementById("adress").defaultValue) {
		adress = "";
	}	
	if (password == document.getElementById("password").defaultValue) {
		password = "";
	}	
	if (affirmpassword == document.getElementById("affirmpassword").defaultValue) {
		affirmpassword = "";
	}	
	if (number == document.getElementById("number").defaultValue) {
		number = "";
	}	
	
	
	
	
	
	$.ajax({
		type : 'post',
		url : '/iac/reportSubmit',
		data : {
			"name" : name,
			"gender" : gender,
			"phone" : phone,
			"adress" : adress,
			"password" : password,
			"affirmpassword" : affirmpassword,
			"designation" : designation,
			"city" : city,
			"local" : local,
			"number" : number,
			"goods" : goods,
			"goods_name" : goods_name,
			"look_into" : look_into,
			"award" : award,
			"secrecy" : secrecy,
			"communication" : communication,
			"investigate" : investigate,
			"happen_time" : happen_time,
			"content" : content,
			"code" : code
		},
		dataType : 'json',
		success : function(data) {
			if (data.message == true) {
				alert("提交成功");
				location.reload();
			} else {
				if (data.check == false) {
					alert("验证码错误!");
				} else {
					alert("提交失败");
				}
			}
		}
	});
}