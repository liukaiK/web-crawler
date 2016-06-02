$(document).ready(function() {
	var timestamp = new Date().getTime();
	$("input[type='text'],textarea").not("#certicode").attr("onfocus", "validate(this)");
	$("input[type='text'],textarea").attr("onblur", "removeValidate(this)");
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
				// alert(data.path);
				// return data;
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

	$("#by_form").validate({
		rules : {
			conName : {
				required : true
			},
			email : {
				required : true,
				email : true
			},
			conContent : {
				required : true
			},
			certicode : {
				required : true,
				digits:true
			}

		},
		messages : {
			conName : {
				required : "请输入表扬人"
			},
			email: "请输入一个正确的邮箱",
			conContent : {
				required : "请输入表扬内容"
			},
			certicode : "请输入数字验证码"

		},
		submitHandler : function() {
			var conTarget = $("#province").val();
			
			var conUnit = $("#city").val();
			
			var conType = $("#city1").val();
			
			var conName = $("#conName").val();
			var email = $("#email").val();
			var conContent = $("#conContent").val();
			var certicode = $("#certicode").val();
			
//			if (conName == '请输入表扬人') {
//				$("#conName").val("");
//			}
//			if (email == '请输入邮箱') {
//				$("#email").val("");
//			}	
//			if (conContent == '请输入表扬内容') {
//				$("#conContent").val("");
//			}	
//			
			$.ajax({
				type : 'post',
				url : '/iac/addPraise',
				data : {
					"conTarget" : conTarget,
					"conUnit" : conUnit,
					"conType" : conType,
					"conName" : conName,
					"email" : email,
					"conContent" : conContent,
					"certicode" : certicode
					},
				dataType : 'json',
				success : function(data) {
					if (data.notice == true) {
						alert(data.message);
						window.location.reload();
					} else {
						alert(data.message);
					}
				}
			});				
			
			
			
		}

	})


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
	if (hh < 10)
		clock += "0";
	clock += hh + ":";
	if (mm < 10)
		clock += '0';
	clock += mm + ":";
	if (ss < 10)
		clock += '0';
	clock += ss;
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
	if (province == 3) {
		cityoption.add(new Option("特价机票退改签","特价机票退改签"));
		cityoption.add(new Option("行李赔偿限额","行李赔偿限额"));
		cityoption.add(new Option("航班取消合理性","航班取消合理性"));
		cityoption.add(new Option("航班取消补偿","航班取消补偿"));
	
		city1option.add(new Option("特价机票退改签问题","特价机票退改签问题"));
		city1option.add(new Option("行李赔偿限额问题","行李赔偿限额问题"));
		city1option.add(new Option("航班取消合理性问题","航班取消合理性问题"));
		city1option.add(new Option("航班取消补偿问题","航班取消补偿问题"));
	}
	if (province == 4) {
		cityoption.add(new Option("其他","其他"));

		city1option.add(new Option("其他问题","其他问题"));
	}

}