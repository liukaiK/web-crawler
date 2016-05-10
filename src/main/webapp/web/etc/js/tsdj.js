var name = "";
var gender = "";
var phone = "";
var adress = "";
var designation = "";
var city = "";
var local = "";
var number = "";
var goods = "";
var goods_name = "";
var brand = "";
var model = "";
var count = "";
var price = "";
var voucher = "";
var buy_time = "";
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
		designation = $("#designation").val();
		city = $("#city option:selected").val();
		local = $("#local").val();
		number = $("#number").val();
		goods = $("input[name='goods']:checked").val();
		goods_name = $("#goods_name").val();
		brand = $("#brand").val();
		model = $("#model").val();
		count = $("#count").val();
		price = $("#price").val();
		voucher = $("#voucher").val();
		buy_time = $("#buy_time").val();
		happen_time = $("#happen_time").val();
		content = $("#content").val();
		code = $("#code").val();
		submitbtn();
	});
})

function submitbtn() {
	if (name == document.getElementById("name").defaultValue) {
		alert("请输入姓名");
		return;
	}
	if (phone == document.getElementById("phone").defaultValue) {
		alert("请输入联系电话");
		return;
	}
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
	if (voucher == "请选择") {
		alert("请选择凭证");
		return;
	}
	if (buy_time == "") {
		alert("请输入购买时间");
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
	if (adress == document.getElementById("adress").defaultValue) {
		adress = "";
	}
	if (number == document.getElementById("number").defaultValue) {
		number = "";
	}
	if (brand == document.getElementById("brand").defaultValue) {
		brand = "";
	}
	if (model == document.getElementById("model").defaultValue) {
		model = "";
	}
	if (count == document.getElementById("count").defaultValue) {
		count = "";
	}
	if (price == document.getElementById("price").defaultValue) {
		price = "";
	}

	$.ajax({
		type : 'post',
		url : '/iac/complaintSubmit',
		data : {
			"name" : name,
			"gender" : gender,
			"phone" : phone,
			"adress" : adress,
			"designation" : designation,
			"city" : city,
			"local" : local,
			"number" : number,
			"goods" : goods,
			"goods_name" : goods_name,
			"brand" : brand,
			"model" : model,
			"count" : count,
			"price" : price,
			"voucher" : voucher,
			"buy_time" : buy_time,
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