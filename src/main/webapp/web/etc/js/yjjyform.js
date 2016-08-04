$(document).ready(function() {
	var timestamp = new Date().getTime();
	$("input[type='text'],textarea").not("#rid,#certicode").attr("onfocus", "validate(this)");
	$("input[type='text'],textarea").not("#rid").attr("onblur", "removeValidate(this)");
	$("#rid").val(CurentTime());

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

	$.validator.addMethod("passengerName", function(value, element) {   
		var passengerName = "请输入意见人";
		return this.optional(element) || (passengerName != value);
	}, "请输入意见人");	
	
	$.validator.addMethod("shenfenId", function(value, element) {   
		var shenfenId = "请输入证件号";
		return this.optional(element) || (shenfenId != value);
	}, "请输入证件号");	
	
	$.validator.addMethod("flightNo", function(value, element) {   
		var flightNo = "请输入航班号";
		return this.optional(element) || (flightNo != value);
	}, "请输入航班号");	
	
	$.validator.addMethod("passengerTel", function(value, element) {   
		var passengerTel = "请输入联系电话";
		return this.optional(element) || (passengerTel != value);
	}, "请输入联系电话");	
	
	$.validator.addMethod("certicode", function(value, element) {   
		var certicode = "请输入验证码";
		return this.optional(element) || (certicode != value);
	}, "请输入验证码");	
	
	$("#shijianjb_form").validate({
		rules : {
			"passengerName" : {
				required : true,
				maxlength : 50,
				passengerName : true
			},
			"shenfenId" : {
				required : true,
				shenfenId : true
			},
			"flightNo" : {
				required : true,
				maxlength : 6,
				flightNo : true
			},
			"passengerTel" : {
				required : true,
				digits:true,
				maxlength : 12,
				passengerTel : true
			},
			"email" : {
				required : true,
				email : true,
				maxlength : 30
			},
			"certicode" : {
				required : true,
				digits:true,
				certicode : true
			}

		},
		messages : {
			"passengerName" : {
				required : '(必填)',
				maxlength : '长度应在1-50个字符之间'
			},
			"flightNo" : {
				required : '(必填)',
				maxlength : '长度应在1-6个字符之间'
			},
			"passengerTel" : {
				required : '(必填)',
				maxlength : '长度应在1-12个字符之间'
			},
			"email" : {
				required : '(必填)',
				email : '请输入正确格式的电子邮件',
				maxlength : '长度应在1-30个字符之间'
			},
			"certicode" : "请输入数字验证码"
		}

	})
	
    $('#shijianjb_form').ajaxForm({  
    	type : 'POST',
        success: function(data){
        	alert(data);  
        }  
    });  
    
})


function doSubmit() {
	$('#shijianjb_form').submit();
	
}


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

function CurentTime() {
	var now = new Date();
	var year = now.getFullYear(); // 年
	var month = now.getMonth() + 1; // 月
	var day = now.getDate(); // 日
	var hh = now.getHours(); // 时
	var mm = now.getMinutes(); // 分
	var ss = now.getSeconds(); // 秒
	var clock = year + "-";
	if (month < 10)
		clock += "0";
	clock += month + "-";
	if (day < 10)
		clock += "0";
	clock += day + " ";
//	if (hh < 10)
//		clock += "0";
//	clock += hh + ":";
//	if (mm < 10)
//		clock += '0';
//	clock += mm + ":";
//	if (ss < 10)
//		clock += '0';
//	clock += ss;
	return (clock);
}


function Change_Select() {
	var cityoption = document.getElementById("city");
	var city1option = document.getElementById("city1");
	cityoption.options.length = 0;
	city1option.options.length = 0;
	var province = document.getElementById('province').value;
	if (province == 1) {
		cityoption.add(new Option("A奥凯航空有限公司","BK "));
		cityoption.add(new Option("A奥凯航空有限公司","BK "));
		cityoption.add(new Option("B北京首都航空有限公司","JD "));
		cityoption.add(new Option("C成都航空有限公司","EU "));
		cityoption.add(new Option("C春秋航空有限公司","9C "));
		cityoption.add(new Option("C重庆航空有限责任公司","OQ "));
		cityoption.add(new Option("C长安航空有限责任公司","9H "));
		cityoption.add(new Option("D东星航空公司","8C "));
		cityoption.add(new Option("D多彩贵州航空有限公司","GY "));
		cityoption.add(new Option("D大新华航空有限公司","CN "));
		cityoption.add(new Option("D大连航空有限公司","CD "));
		cityoption.add(new Option("D河北航空有限公司","NS "));
		cityoption.add(new Option("F福州航空有限责任公司","FU "));
		cityoption.add(new Option("G广西北部湾航空有限责任公司","GX "));
		cityoption.add(new Option("H华夏航空有限公司","G5 "));
		cityoption.add(new Option("H河南航空有限公司","VD "));
		cityoption.add(new Option("H海南航空股份有限公司","HU "));
		cityoption.add(new Option("J九元航空有限公司","AQ "));
		cityoption.add(new Option("J江西航空有限公司","RY "));
		cityoption.add(new Option("K昆明航空有限公司","KY "));
		cityoption.add(new Option("Q其他","XX "));
		cityoption.add(new Option("Q青岛航空股份有限公司","QW "));
		cityoption.add(new Option("R瑞丽航空有限公司","DR "));
		cityoption.add(new Option("S上海吉祥航空有限公司","HO "));
		cityoption.add(new Option("S上海航空股份有限公司","FM "));
		cityoption.add(new Option("S东海航空有限公司","DZ "));
		cityoption.add(new Option("S四川航空股份有限公司","3U "));
		cityoption.add(new Option("S山东航空股份有限公司","SC "));
		cityoption.add(new Option("S深圳航空有限责任公司","ZH "));
		cityoption.add(new Option("T天津航空有限责任公司","GS "));
		cityoption.add(new Option("W乌鲁木齐航空有限责任公司","UQ "));
		cityoption.add(new Option("X厦门航空有限公司","MF "));
		cityoption.add(new Option("X幸福航空有限责任公司","JR "));
		cityoption.add(new Option("X西藏航空有限公司","TV "));
		cityoption.add(new Option("X西部航空有限责任公司","PN "));
		cityoption.add(new Option("Y云南祥鹏航空有限责任公司","8L "));
		cityoption.add(new Option("Y云南英安航空有限公司","YI "));
		cityoption.add(new Option("Y扬子江快运航空有限公司","Y8 "));
		cityoption.add(new Option("Z中国东方航空股份有限公司","MU "));
		cityoption.add(new Option("Z中国南方航空股份有限公司","CZ "));
		cityoption.add(new Option("Z中国国际航空股份有限公司","CA "));
		cityoption.add(new Option("Z中国联合航空有限公司","KN "));
		cityoption.add(new Option("Z浙江长龙航空公司","GJ "));
		
		city1option.add(new Option("航班问题", "A"));
		city1option.add(new Option("超售", "B"));
		city1option.add(new Option("预定、票务、登机", "C"));
		city1option.add(new Option("票价", "D"));
		city1option.add(new Option("退款", "E"));
		city1option.add(new Option("行李", "F"));
		city1option.add(new Option("旅客服务", "G"));
		city1option.add(new Option("吸烟", "H"));
		city1option.add(new Option("广告", "I"));
		city1option.add(new Option("信用", "J"));
		city1option.add(new Option("旅行或包机", "K"));
		city1option.add(new Option("综合（包括常旅客）", "L"));
		city1option.add(new Option("残疾", "M"));
		city1option.add(new Option("动物：丢失、受伤、死亡", "N"));
		city1option.add(new Option("歧视（残疾人除外）", "O"));
		city1option.add(new Option("短信欺诈", "P"));		
	}
	if (province == 2) {
		cityoption.add(new Option("A埃及航空公司","MS "));
		cityoption.add(new Option("A埃塞俄比亚航空","ET "));
		cityoption.add(new Option("A奥地利航空公司","OS "));
		cityoption.add(new Option("A澳洲航空公司","QF "));
		cityoption.add(new Option("A澳门航空公司","NX "));
		cityoption.add(new Option("A阿塞拜疆航空公司","J2 "));
		cityoption.add(new Option("A阿富汗卡姆航空公司","RQ "));
		cityoption.add(new Option("A阿富汗萨菲航空公司","4Q "));
		cityoption.add(new Option("A阿富汗阿里亚纳航空公司","FG "));
		cityoption.add(new Option("A阿尔及利亚航空公司","AH "));
		cityoption.add(new Option("A阿斯塔纳航空公司","KC "));
		cityoption.add(new Option("A阿联酋航空公司","EK "));
		cityoption.add(new Option("A阿联酋阿提哈德航空公司","EY "));
		cityoption.add(new Option("B北欧航空公司","SK "));
		cityoption.add(new Option("B巴基斯坦沙欣航空公司","NL "));
		cityoption.add(new Option("B巴基斯坦航空公司","PK "));
		cityoption.add(new Option("B巴拿马航空公司","CM "));
		cityoption.add(new Option("B波兰航空公司","LO "));
		cityoption.add(new Option("C朝鲜航空公司","JS "));
		cityoption.add(new Option("D东亚航空公司","3E "));
		cityoption.add(new Option("D大陆航空公司","CO "));
		cityoption.add(new Option("D大韩航空","KE "));
		cityoption.add(new Option("D德国柏林航空","AB "));
		cityoption.add(new Option("E俄罗斯乌拉尔航空公司","U6 "));
		cityoption.add(new Option("E俄罗斯国际航空公司","SU "));
		cityoption.add(new Option("E俄罗斯洲际航空公司","UN "));
		cityoption.add(new Option("E俄罗斯联合航空公司","AU "));
		cityoption.add(new Option("E俄罗斯西伯利亚航空公司","S7 "));
		cityoption.add(new Option("E俄罗斯远东航空公司","H8 "));
		cityoption.add(new Option("F发达飞航空公司","7P "));
		cityoption.add(new Option("F法国航空公司","AF "));
		cityoption.add(new Option("F符拉迪沃斯托克航空公司","XF "));
		cityoption.add(new Option("F芬兰航空公司","AY "));
		cityoption.add(new Option("F菲律宾宿务太平洋航空公司","5J "));
		cityoption.add(new Option("F菲律宾航空公司","PR "));
		cityoption.add(new Option("F菲律宾菲亚航","Z2 "));
		cityoption.add(new Option("F菲律宾飞龙航空","ZZ "));
		cityoption.add(new Option("G国泰航空公司","CX "));
		cityoption.add(new Option("G港龙航空","KA "));
		cityoption.add(new Option("H合众国际航空公司","US "));
		cityoption.add(new Option("H德国汉莎航空公司","LH "));
		cityoption.add(new Option("H海湾航空公司","GF "));
		cityoption.add(new Option("H荷兰皇家航空公司","KL "));
		cityoption.add(new Option("H赫兹航空公司","HZ "));
		cityoption.add(new Option("H韩亚航空","OZ "));
		cityoption.add(new Option("H韩国真航空","LJ "));
		cityoption.add(new Option("J加拿大航空公司","AC "));
		cityoption.add(new Option("J吉尔吉克斯坦航空公司","GI "));
		cityoption.add(new Option("J吉尔吉斯斯坦黄金航空公司","QH "));
		cityoption.add(new Option("J捷达航空(印度)有限公司","9W "));
		cityoption.add(new Option("J柬埔寨吴哥航空公司","K6 "));
		cityoption.add(new Option("J柬埔寨天翼亚洲航空公司","ZA "));
		cityoption.add(new Option("J津巴布韦航空公司","UM "));
		cityoption.add(new Option("K克拉斯诺亚尔斯克股份开放式航空公司","7B "));
		cityoption.add(new Option("K卡塔尔航空公司","QR "));
		cityoption.add(new Option("K肯尼亚航空公司","KQ "));
		cityoption.add(new Option("L老挝航空公司","QV "));
		cityoption.add(new Option("M墨西哥航空公司","AM "));
		cityoption.add(new Option("M墨西哥航空公司","MX "));
		cityoption.add(new Option("M曼谷航空公司","PG "));
		cityoption.add(new Option("M毛里求斯航空公司","MK "));
		cityoption.add(new Option("M美佳航空","5M "));
		cityoption.add(new Option("M美国联合航空公司","UA "));
		cityoption.add(new Option("M美国航空公司","AA "));
		cityoption.add(new Option("M美国西北航空公司","NW "));
		cityoption.add(new Option("M美国达美航空公司","DL "));
		cityoption.add(new Option("M莫杰航空公司","E3 "));
		cityoption.add(new Option("M蒙古国航空公司","MO "));
		cityoption.add(new Option("M蒙古航空公司","OM "));
		cityoption.add(new Option("M马来西亚亚洲航空有限公司","AK "));
		cityoption.add(new Option("M马来西亚亚洲航空（长途）有限公司","D7 "));
		cityoption.add(new Option("M马来西亚航空公司","MH "));
		cityoption.add(new Option("N南非航空公司","SA "));
		cityoption.add(new Option("N尼泊尔航空公司","RA "));
		cityoption.add(new Option("P普尔科沃航空公司","FV "));
		cityoption.add(new Option("Q全日空航空公司","NH "));
		cityoption.add(new Option("R日本航空公司","JL "));
		cityoption.add(new Option("R瑞士国际航空公司","LX "));
		cityoption.add(new Option("S斯卡特航空公司","DV "));
		cityoption.add(new Option("S斯里兰卡航空公司","UL "));
		cityoption.add(new Option("T台湾中华航空公司","CI "));
		cityoption.add(new Option("T台湾华信航空公司","AE "));
		cityoption.add(new Option("T台湾复兴航空公司","GE "));
		cityoption.add(new Option("T台湾立荣航空公司","B7 "));
		cityoption.add(new Option("T台湾长荣航空公司","BR "));
		cityoption.add(new Option("T土库曼斯坦航空公司","T5 "));
		cityoption.add(new Option("T土耳其航空公司","TK "));
		cityoption.add(new Option("T塔吉克斯坦航空公司","7J "));
		cityoption.add(new Option("T泰国东方航空公司","OX "));
		cityoption.add(new Option("T泰国亚洲航空公司","FD "));
		cityoption.add(new Option("T泰国亚洲航空（长途）有限公司","XJ "));
		cityoption.add(new Option("T泰国国际航空公司","TG "));
		cityoption.add(new Option("T泰国微笑航空公司","WE "));
		cityoption.add(new Option("T泰国飞鸟航空","DD "));
		cityoption.add(new Option("W乌克兰国际航空","PS "));
		cityoption.add(new Option("W乌克兰空中世界","VV "));
		cityoption.add(new Option("W乌兹别克斯坦航空公司","HY "));
		cityoption.add(new Option("W吴哥航空公司","G6 "));
		cityoption.add(new Option("W文莱王家航空公司","BI "));
		cityoption.add(new Option("W维珍航空公司","VS "));
		cityoption.add(new Option("X新加坡捷星亚洲航空私人有限公司","3K "));
		cityoption.add(new Option("X新加坡胜安航空公司","MI "));
		cityoption.add(new Option("X新加坡航空公司","SQ "));
		cityoption.add(new Option("X新加坡酷航","TZ "));
		cityoption.add(new Option("X新西兰航空公司","NZ "));
		cityoption.add(new Option("X欣丰虎航空公司","TR "));
		cityoption.add(new Option("X香港快运航空有限公司","UO "));
		cityoption.add(new Option("X香港航空公司","HK "));
		cityoption.add(new Option("Y以色列航空公司","LY "));
		cityoption.add(new Option("Y伊朗航空公司","IR "));
		cityoption.add(new Option("Y伊朗马汉航空公司","W5 "));
		cityoption.add(new Option("Y印度尼西亚亚洲航空有限公司","QZ "));
		cityoption.add(new Option("Y印度尼西亚鹰航空公司","GA "));
		cityoption.add(new Option("Y印度航空公司","AI "));
		cityoption.add(new Option("Y意大利航空公司","AZ "));
		cityoption.add(new Option("Y英国航空公司","BA "));
		cityoption.add(new Option("Y越南航空公司","VN "));
		cityoption.add(new Option("Y雅库茨克航空公司","R3 "));
	
		city1option.add(new Option("航班问题", "A"));
		city1option.add(new Option("超售", "B"));
		city1option.add(new Option("预定、票务、登机", "C"));
		city1option.add(new Option("票价", "D"));
		city1option.add(new Option("退款", "E"));
		city1option.add(new Option("行李", "F"));
		city1option.add(new Option("旅客服务", "G"));
		city1option.add(new Option("吸烟", "H"));
		city1option.add(new Option("广告", "I"));
		city1option.add(new Option("信用", "J"));
		city1option.add(new Option("旅行或包机", "K"));
		city1option.add(new Option("综合（包括常旅客）", "L"));
		city1option.add(new Option("残疾", "M"));
		city1option.add(new Option("动物：丢失、受伤、死亡", "N"));
		city1option.add(new Option("歧视（残疾人除外）", "O"));
		city1option.add(new Option("短信欺诈", "P"));	
	}
	if (province == 3) {
		cityoption.add(new Option("AKESU 阿克苏温宿机场","AKU"));
		cityoption.add(new Option("ALTAY 阿勒泰机场","AAT"));
		cityoption.add(new Option("ANKANG 安康五里铺机场","AKA"));
		cityoption.add(new Option("ANQING 安庆大龙山机场","AQG"));
		cityoption.add(new Option("ANSHAN 鞍山机场","AOG"));
		cityoption.add(new Option("ANSHUN 安顺黄果树机场","AVA"));
		cityoption.add(new Option("ANYANG 安阳机场","AYG"));
		cityoption.add(new Option("BAOSHAN 保山机场","BSD"));
		cityoption.add(new Option("BAOTOU 包头二里半机场","BAV"));
		cityoption.add(new Option("BAYANZHUOE 巴彦淖尔天吉泰机场","RLK"));
		cityoption.add(new Option("BEIHAI 北海福成机场","BHY"));
		cityoption.add(new Option("BEIJING 北京南苑机场","NAY"));
		cityoption.add(new Option("BEIJING 北京首都国际机场","PEK"));
		cityoption.add(new Option("BENGBU 蚌埠机场","BFU"));
		cityoption.add(new Option("CHANGBAISHAN 白山长白山机场","NBS"));
		cityoption.add(new Option("CHANGCHUN 长春龙嘉国际机场","CGQ"));
		cityoption.add(new Option("CHANGDE 常德桃花源机场","CGD"));
		cityoption.add(new Option("CHANGDU 昌都邦达机场","BPX"));
		cityoption.add(new Option("CHANGHAI 长海大长山岛机场","CNI"));
		cityoption.add(new Option("CHANGSHA 长沙黄花国际机场","CSX"));
		cityoption.add(new Option("CHANGZHI 长治王村机场","CIH"));
		cityoption.add(new Option("CHANGZHOU 常州奔牛机场","CZX"));
		cityoption.add(new Option("CHAOYANG 朝阳机场","CHG"));
		cityoption.add(new Option("CHENGDU 成都双流国际机场","CTU"));
		cityoption.add(new Option("CHIFENG 赤峰土城子机场","CIF"));
		cityoption.add(new Option("CHONGQING 重庆江北机场","CKG"));
		cityoption.add(new Option("DALAN 大连周水子机场","DLC"));
		cityoption.add(new Option("DALI 大理机场","DLU"));
		cityoption.add(new Option("DANDONG 丹东浪头机场","DDG"));
		cityoption.add(new Option("DAOCHENG 稻城亚丁机场","DCY"));
		cityoption.add(new Option("DAQING 大庆机场","DQA"));
		cityoption.add(new Option("DATONG 大同倍加皂机场","DAT"));
		cityoption.add(new Option("DAZHOU 达州河市霸机场","DAX"));
		cityoption.add(new Option("DONGYING 东营机场","DOY"));
		cityoption.add(new Option("DUNHUANG 敦煌机场","DNH"));
		cityoption.add(new Option("EERDUOSI 鄂尔多斯伊金霍洛机场","DSN"));
		cityoption.add(new Option("ENSHI 恩施许家坪机场","ENH"));
		cityoption.add(new Option("ERLIANHAOTE 二连浩特赛乌素机场","ERL"));
		cityoption.add(new Option("FUYANG 阜阳西关机场","FUG"));
		cityoption.add(new Option("FUYUN 富蕴可可托托海机场","FYN"));
		cityoption.add(new Option("FUZHOU 福州长乐国际机场","FOC"));
		cityoption.add(new Option("GANZHOU 赣州黄金机场","KOW"));
		cityoption.add(new Option("GOLMUD 格尔木机场","GOQ"));
		cityoption.add(new Option("GUANGHAN 广汉机场","GHN"));
		cityoption.add(new Option("GUANGYUAN 广元盘龙机场","GYS"));
		cityoption.add(new Option("GUANGZHOU 广州白云国际机场","CAN"));
		cityoption.add(new Option("GUILIN 桂林两江国际机场","KWL"));
		cityoption.add(new Option("GUIYANG 贵阳龙洞堡机场","KWE"));
		cityoption.add(new Option("HAIKOU 海口美兰国际机场","HAK"));
		cityoption.add(new Option("HAILAER 海拉尔东山机场","HLD"));
		cityoption.add(new Option("HAMI 哈密机场","HMI"));
		cityoption.add(new Option("HANGZHOU 杭州萧山国际机场","HGH"));
		cityoption.add(new Option("HANZHONG 汉中西关机场","HZG"));
		cityoption.add(new Option("HARBIN 哈尔滨太平国际机场","HRB"));
		cityoption.add(new Option("HECHI 河池金城江机场","HCJ"));
		cityoption.add(new Option("HEFEI 合肥新桥国际机场","HFE"));
		cityoption.add(new Option("HEIHE 黑河机场","HEK"));
		cityoption.add(new Option("HENGYANG 衡阳机场","HNY"));
		cityoption.add(new Option("HETIAN 和田机场","HTN"));
		cityoption.add(new Option("HUAIAN 淮安涟水机场","HIA"));
		cityoption.add(new Option("HUAIHUA 怀化芷江机场","HJJ"));
		cityoption.add(new Option("HUANGSHAN 黄山屯溪机场","TXN"));
		cityoption.add(new Option("HUANGYAN 黄岩路桥机场","HYN"));
		cityoption.add(new Option("HUHEHAOTE 呼和浩特白塔机场","HET"));
		cityoption.add(new Option("JIAMUSI 佳木斯东郊机场","JMU"));
		cityoption.add(new Option("JIAN 吉安机场","KNC"));
		cityoption.add(new Option("JIAYUGUAN 嘉峪关机场","JGN"));
		cityoption.add(new Option("JIEYANG 揭阳潮汕机场","SWA"));
		cityoption.add(new Option("JINAN 济南遥墙国际机场","TNA"));
		cityoption.add(new Option("JINGDEZHEN 景德镇罗家机场","JDZ"));
		cityoption.add(new Option("JINGGANGSHAN 井冈山机场","JGS"));
		cityoption.add(new Option("JINING 济宁机场","JNG"));
		cityoption.add(new Option("JINZHOU 锦州小岭子机场","JNZ"));
		cityoption.add(new Option("JIUJIANG 九江庐山机场","JIU"));
		cityoption.add(new Option("JIUQUAN 酒泉机场","CHW"));
		cityoption.add(new Option("JIUZHAIGOU 九寨沟黄龙机场","JZH"));
		cityoption.add(new Option("JIXI 鸡西机场","JXA"));
		cityoption.add(new Option("KARAMAY 克拉玛依机场","KRY"));
		cityoption.add(new Option("KASHI 喀什机场","KHG"));
		cityoption.add(new Option("KORLA 库尔勒机场","KRL"));
		cityoption.add(new Option("KUNMING 昆明长水国际机场","KMG"));
		cityoption.add(new Option("KUQA 库车机场","KCA"));
		cityoption.add(new Option("LANZHOU 兰州中川机场","LHW"));
		cityoption.add(new Option("LASA 拉萨贡嘎机场","LXA"));
		cityoption.add(new Option("LIANCHENG 连城机场","LCX"));
		cityoption.add(new Option("LIANYUNGANG 连云港白塔埠机场","LYG"));
		cityoption.add(new Option("LIBO 荔波机场","LLB"));
		cityoption.add(new Option("LIJIANG 丽江三义机场","LJG"));
		cityoption.add(new Option("LINCHANG 临沧机场","LNJ"));
		cityoption.add(new Option("LINYI 临沂机场","LYI"));
		cityoption.add(new Option("LINZHI 林芝机场","LZY"));
		cityoption.add(new Option("LIPING 黎平机场","HZH"));
		cityoption.add(new Option("LIUZHOU 柳州白莲机场","LZH"));
		cityoption.add(new Option("LUOYANG 洛阳北郊机场","LYA"));
		cityoption.add(new Option("LUZHOU 泸州蓝田机场","LZO"));
		cityoption.add(new Option("MANGSHI 芒市机场","LUM"));
		cityoption.add(new Option("MANZHOULI 满洲里机场","NZH"));
		cityoption.add(new Option("MEIXIAN 梅县机场","MXZ"));
		cityoption.add(new Option("MIANYANG 绵阳南郊机场","MIG"));
		cityoption.add(new Option("MOHE 漠河机场","OHE"));
		cityoption.add(new Option("MUDANJIANG 牡丹江海浪机场","MDG"));
		cityoption.add(new Option("NANCHANG 南昌昌北机场","KHN"));
		cityoption.add(new Option("NANCHONG 南充高坪机场","NAO"));
		cityoption.add(new Option("NANJING 南京禄口国际机场","NKG"));
		cityoption.add(new Option("NANNING 南宁吴墟机场","NNG"));
		cityoption.add(new Option("NANTONG 南通兴东机场","NTG"));
		cityoption.add(new Option("NANYANG 南阳姜营机场","NNY"));
		cityoption.add(new Option("NINGBO 宁波栎社机场","NGB"));
		cityoption.add(new Option("PANZHIHUA 攀枝花保安营机场","PZI"));
		cityoption.add(new Option("QIANJIANG 黔江武陵山机场","JIQ"));
		cityoption.add(new Option("QIEMO 且末机场","IQM"));
		cityoption.add(new Option("QINGDAO 青岛流亭国际机场","TAO"));
		cityoption.add(new Option("QINGYAN 庆阳西峰镇机场","IQN"));
		cityoption.add(new Option("QINHUANGDAO 秦皇岛机场","SHP"));
		cityoption.add(new Option("QIQIHAER 齐齐哈尔三家子机场","NDG"));
		cityoption.add(new Option("QUANZHOU 泉州晋江机场","JJN"));
		cityoption.add(new Option("QUZHOU 衢州机场","JUZ"));
		cityoption.add(new Option("RIKAZE 日喀则和平机场","RKZ"));
		cityoption.add(new Option("SANYA 三亚凤凰国际机场","SYX"));
		cityoption.add(new Option("SHANGHAI 上海浦东国际机场","PVG"));
		cityoption.add(new Option("SHANGHAI 上海虹桥国际机场","SHA"));
		cityoption.add(new Option("SHASHI 沙市机场","SHS"));
		cityoption.add(new Option("SHENYANG 沈阳桃仙国际机场","SHE"));
		cityoption.add(new Option("SHENZHEN 深圳宝安国际机场","SZX"));
		cityoption.add(new Option("SHIJIAZHUANG 石家庄正定机场","SJW"));
		cityoption.add(new Option("SIMAO 思茅机场","SYM"));
		cityoption.add(new Option("SUZHOU 苏州机场","SZV"));
		cityoption.add(new Option("TACHENG 塔城机场","TCG"));
		cityoption.add(new Option("TAIYUAN 太原武宿机场","TYN"));
		cityoption.add(new Option("TANGSHAN 唐山三女河机场","TVS"));
		cityoption.add(new Option("TIANJIN 天津滨海国际机场","TSN"));
		cityoption.add(new Option("TIANSHUI 天水机场","TSI"));
		cityoption.add(new Option("TONGHUA 通化机场","TNH"));
		cityoption.add(new Option("TONGLIAO 通辽机场","TGO"));
		cityoption.add(new Option("TONGREN 铜仁大兴机场","TEN"));
		cityoption.add(new Option("URUMQI 乌鲁木齐地窝堡机场","URC"));
		cityoption.add(new Option("WANZHOU 万州机场","WXN"));
		cityoption.add(new Option("WEIFANG 潍坊机场","WEF"));
		cityoption.add(new Option("WEIHAI 威海大水泊机场","WEH"));
		cityoption.add(new Option("WENZHOU 温州永强机场","WNZ"));
		cityoption.add(new Option("WUHAI 乌海机场","WUA"));
		cityoption.add(new Option("WUHAN 武汉天河国际机场","WUH"));
		cityoption.add(new Option("WULANHAOTE 乌兰浩特机场","HLH"));
		cityoption.add(new Option("WUXI 无锡硕放机场","WUX"));
		cityoption.add(new Option("WUYISHAN 武夷山机场","WUS"));
		cityoption.add(new Option("WUZHOU 梧州长州岛机场","WUZ"));
		cityoption.add(new Option("XIAMEN 厦门高崎国际机场","XMN"));
		cityoption.add(new Option("XIAN 西安咸阳国际机场","XIY"));
		cityoption.add(new Option("XIANGFAN 襄樊刘集机场","XFN"));
		cityoption.add(new Option("XICHANG 西昌青山机场","XIC"));
		cityoption.add(new Option("XILINHAOTE 锡林浩特机场","XIL"));
		cityoption.add(new Option("XINGYI 兴义机场","ACX"));
		cityoption.add(new Option("XINING 西宁曹家堡机场","XNN"));
		cityoption.add(new Option("XINING 西宁曹家堡机场","XNN"));
		cityoption.add(new Option("XISHUANGBANNA 西双版纳景洪机场","JHG"));
		cityoption.add(new Option("XUZHOU 徐州观音机场","XUZ"));
		cityoption.add(new Option("YANAN 延安二十里铺机场","ENY"));
		cityoption.add(new Option("YANCHENG 盐城南洋机场","YNZ"));
		cityoption.add(new Option("YANGZHOU 扬州泰州机场","YTY"));
		cityoption.add(new Option("YANJI 延吉朝阳川机场","YNJ"));
		cityoption.add(new Option("YANTAI 烟台莱山机场","YNT"));
		cityoption.add(new Option("YIBIN 宜宾菜坝机场","YBP"));
		cityoption.add(new Option("YICHANG 宜昌三峡机场","YIH"));
		cityoption.add(new Option("YICHUN 伊春机场","LDS"));
		cityoption.add(new Option("YINCHUAN 银川河东机场","INC"));
		cityoption.add(new Option("YINGKOU 营口兰旗机场","YKH"));
		cityoption.add(new Option("YINING 伊宁机场","YIN"));
		cityoption.add(new Option("YIWU 义乌机场","YIW"));
		cityoption.add(new Option("YONGZHOU 永州零陵机场","LLF"));
		cityoption.add(new Option("YULIN 榆林西沙机场","UYN"));
		cityoption.add(new Option("YUNCHENG 运城张孝机场","YCU"));
		cityoption.add(new Option("YUSHU 玉树机场","YUS"));
		cityoption.add(new Option("ZHANGJIAJIE 张家界荷花机场","DYG"));
		cityoption.add(new Option("ZHANGJIAKOU 张家口宁远机场","ZQZ"));
		cityoption.add(new Option("ZHANJIANG 湛江机场","ZHA"));
		cityoption.add(new Option("ZHAOTONG 昭通机场","ZAT"));
		cityoption.add(new Option("ZHENGZHOU 郑州新郑机场","CGO"));
		cityoption.add(new Option("ZHONGDIAN 迪庆香格里拉机场","DIG"));
		cityoption.add(new Option("ZHONGWEI 中卫机场","ZHY"));
		cityoption.add(new Option("ZHOUSHAN 舟山普陀山机场","HSN"));
		cityoption.add(new Option("ZHUHAI 珠海三灶机场","ZUH"));
		cityoption.add(new Option("ZUNYI 遵义机场","ZYI"));
	
		city1option.add(new Option("机场设施","A"));
		city1option.add(new Option("引导标志","B"));
		city1option.add(new Option("航班信息","C"));
		city1option.add(new Option("办理乘机手续","D"));
		city1option.add(new Option("安检","E"));
		city1option.add(new Option("行李","F"));
		city1option.add(new Option("货物","G"));
		city1option.add(new Option("航班延误时服务","H"));
		city1option.add(new Option("转机服务","I"));
		city1option.add(new Option("候机环境与秩序","J"));
		city1option.add(new Option("其他商户","K"));
		city1option.add(new Option("地面交通服务","L"));
		city1option.add(new Option("其他","M"));

	}
	if (province == 4) {
		cityoption.add(new Option("BGS","BGS"));

		city1option.add(new Option("BGS问题","BGS问题"));

	}

	if (province == 6) {
		cityoption.add(new Option("政府机关","政府机关"));
		
		city1option.add(new Option("政策","政策"));
		city1option.add(new Option("执法","执法"));
		city1option.add(new Option("法律法规","法律法规"));
		
	}
}