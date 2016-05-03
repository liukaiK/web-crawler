/** 初始化方法* */
$(document).ready(function() {
    $.ajax({ 
    	async:false, 
    	url: 'http://job.cdpsn.org.cn/joblist.php?&kbn=new', 
    	type: 'GET', 
    	dataType: 'jsonp', 
    	jsonp: "jsoncallback",
    	timeout: 5000,
    	success:function(data){
    		alert(data);
    	}
    });

});
function callback(data){
	alert(data);
}
/**
 * 动态添加采集规则的表格
 */
var n = 1;
function getRuleHtml(n) {
	var addhtml = '<table id="' + n + '">' + '<tr><td>编号：</td><td><span>' + n + '</span><input class="remove" type="button" value="删除" onclick="removeRule(' + n + ')"/></td></tr>'
			+ '<tr><td>父类：</td><td><input type="text" name="parent" value="" /></td></tr>' + '<tr><td>标签：</td><td><input type="text" name="tag" value="" /></td></tr>'
			+ '<tr><td>类型：</td><td><select name="type" ><option value="id" >id</option><option value="class" >class</option><option value="tag" >tag</option></select></td></tr>'
			+ '<tr><td>名称：</td><td><input type="text" name="name" value="" /></td></tr>' + '<tr><td>序号：</td><td><input type="text" name="index" value="" /></td></tr>'
			+ '<tr><td>锚点：</td><td><input type="text" name="anchorId" value="" /></td></tr>' + '</table>';
	return addhtml;
};
function loadRules(n, rule) {
	var type = '<tr><td>类型：</td><td><select name="type" ><option selected="selected" >id</option><option>class</option><option>tag</option></select></td></tr>';
	if (rule.type == "class") {
		type = '<tr><td>类型：</td><td><select name="type" ><option value="id" >id</option><option value="class" selected="selected" >class</option><option value="tag" >tag</option></select></td></tr>';
	} else {
		type = '<tr><td>类型：</td><td><select name="type" ><option value="id" >id</option><option value="class" >class</option><option value="tag" selected="selected" >tag</option></select></td></tr>';
	}

	var rule = '<table id="' + n + '">' + '<tr><td>编号：</td><td><span>' + n + '</span><input class="remove" type="button" value="删除" onclick="removeRule(' + n + ')"/></td></tr>'
			+ '<tr><td>父类：</td><td><input type="text" name="parent" value="'+ rule.parent +'" /></td></tr>' + '<tr><td>标签：</td><td><input type="text" name="tag" value="' + rule.tag + '" /></td></tr>' + type
			+ '<tr><td>名称：</td><td><input type="text" name="name" value="' + rule.name + '" /></td></tr>' + '<tr><td>序号：</td><td><input type="text" name="index" value="' + rule.index
			+ '" /></td></tr>' + '<tr><td>锚点：</td><td><input type="text" name="anchorId" value="' + rule.anchorId + '" /></td></tr>' + '</table>';
	return rule;
};

function cating() {
	$.ajax({
		url : '/htmlUnit/core/cating',
		type : 'POST',
		sync : true,
		success : function(data) {
			if (data.message == true) {
				alert("抓取");
			}
		},
		error : function() {
			alert("模版修改失败！");
		}
	});
}

function removeRule(index) {
	$("#" + index).remove();

}

/** 保存修改后的模版文件* */
function saveTemplate() {
	var html = $("#template_src").val();
	var name = $("#template").val();
	$.ajax({
		url : '/htmlUnit/core/saveTemplate',
		type : 'POST',
		data : {
			'name' : name,
			'html' : html
		},
		sync : false,
		success : function(data) {
			if (data.message == true) {
				alert("模版修改成功！");
			}
		},
		error : function() {
			alert("模版修改失败！");
		}
	});
}
/**
 * 获取采集规则列表
 */
function loadList() {
	$.ajax({
		url : '/htmlUnit/core/list',
		type : 'POST',
		sync : false,
		success : function(data) {
			if (data.list != null) {
				$("#pglist").empty();
				for ( var i = 0; i < data.list.length; i++) {
					// $("#list").append("<option
					// value="+data.list+">"+data.list+"</option>");
					$("#pglist").append("<li><a href='#' onclick='loadingPg(this)' >" + data.list[i] + "</a></li>");

				}
			}
		},
		error : function() {
			alert("保存失败！");
		}
	});
}
/**
 * 获取模版列表
 */
function loadTemplateList() {
	$.ajax({
		url : '/htmlUnit/core/loadTemplateList',
		type : 'POST',
		sync : false,
		success : function(data) {
			if (data.list != null) {
				$("#templateList").empty();
				for ( var i = 0; i < data.list.length; i++) {
					$("#templateList").append("<li><a href='#' onclick='loadingTemplate(this)' >" + data.list[i] + "</a></li>");

				}
			}
		},
		error : function() {
			alert("保存失败！");
		}
	});
}
function delTemplate() {
	var b = confirm("删除是不可恢复的，你确认要删除吗？");
	if (b == false) {
		return;
	}
	var val = $("#template").val();
	$.ajax({
		url : '/htmlUnit/core/delTemplate',
		type : 'POST',
		sync : false,
		data : {
			template : val
		},
		success : function(data) {
			if (data.message == true) {
				loadTemplateList();
				alert("删除成功！");
			} else {
				alert("删除失败！");
			}
		},
		error : function() {
			alert("删除失败！");
		}
	});
}
/**
 * 加载模版
 * 
 * @param e
 */
function loadingTemplate(e) {
	var val = $(e).text();
	$("#template").val(val);
	$.post("wcag/hd135/template/" + val, function(data) {
		$("#template_src").text(data);
	});
}

/**
 * 加载单个采集规则文件
 * 
 * @param e
 */
function loadingPg(e) {
	var val = $(e).text();
	$("#pgName").val(val.split("\.")[0]);
	$.ajax({
		url : '/htmlUnit/core/load',
		type : 'POST',
		sync : false,
		data : {
			pg : val
		},
		success : function(data) {
			// alert(data.config);
			// 是否开启脚本
			var javaScriptEnabled = data.config.javaScriptEnabled;
			$("#javaScriptEnabled").find("option[value='" + javaScriptEnabled + "']").attr("selected", true);
			var sleep = data.config.sleep;
			$("#sleep").val(sleep);
			// 加载模版
			var template = data.config.template;
			var a = template.lastIndexOf("\\");
			var tmp = template.substring(a + 1, template.length);
			$("#template").val(tmp);
			$.post("wcag/hd135/template/" + tmp, function(data) {
				$("#template_src").text(data);
			});
			// 加载规则
			var rules = data.config.list;// 获取采集规则集合
			if (rules != null) {
				$("#rules").empty();
				for ( var i = 0; i < rules.length; i++) {
					$("#rules").append(loadRules(i, rules[i]));
				}
			}
			// 加载urls
			var urls = data.config.urls;
			if (urls != null) {
				$("#url").val(urls[0]);
				view();
				var textUrls = "";
				for ( var i = 0; i < urls.length; i++) {
					textUrls = textUrls + urls[i] + "\r\n";
				}
				$("#urls").val(textUrls);
			}
		},
		error : function() {
			alert("保存失败！");
		}
	});
}

/**
 * 删除规则
 */
function delPg() {
	var pgName = $("#pgName").val();
	if (pgName == "" || pgName == undefined) {
		alert("规则名称不能为空");
		return;
	}
	$.ajax({
		url : '/htmlUnit/core/delPg',
		type : 'POST',
		sync : true,
		data : {
			pgName : pgName
		},
		success : function(data) {
			if (data.message == true) {
				loadList();
				alert("删除成功");
			}
		},
		error : function() {
			alert("删除失败！");
		}
	});
}
function publish() {

}

/** 保存PageConfig 文件* */
function save() {
	var pgName = $("#pgName").val();
	if (pgName == "" || pgName == undefined) {
		alert("规则名称不能为空");
		return;
	}
	var urls = $("#urls").val();
	if (urls == "" || urls == undefined) {
		alert("链接网址集合不能为空!");
		return;
	}
	var arr = urls.split("\n");
	var url_array = new Array();
	for ( var i = 0; i < arr.length; i++) {
		if (arr[i] == "" || arr[i] == null) {
			continue;
		}
		// alert(arr[i]);
		url_array.push(arr[i].replace(/[ ]/g, ""));
	}

	var javaScriptEnabled = $("#javaScriptEnabled").find("option:selected").val();
	var sleep = $("#sleep").val();
	var outpath = $("#outpath").val();
	var rules = getRules();
	var template = $("#template").val();

	$.ajax({
		url : '/htmlUnit/core/save',
		type : 'POST',
		data : {
			"sleep" : sleep,
			"outpath" : outpath,
			"javaScriptEnabled" : javaScriptEnabled,
			"template" : template,
			"pgName" : pgName,
			"urls" : url_array,
			"rules" : rules
		},
		sync : false,
		success : function(data) {
			if (data.message == true) {
				loadList();
				alert("保存成功！");
			}
		},
		error : function() {
			alert("保存失败！");
		}
	});

}
/**
 * 新建模版
 */
function addTemplate() {
	var fn = $("#template").val();
	alert(fn);
	$.ajax({
		url : '/htmlUnit/core/addTemplate',
		type : 'POST',
		data : {
			"fn" : fn,
		},
		sync : false,
		success : function(data) {
			if (data.message == true) {
				alert("新建模版成功！");
				loadTemplateList();
			}
		},
		error : function() {
			alert("新建模版失败！重名模版已存在");
		}
	});

}

/** 预览采集页面* */
function view() {
	var rules = getRules();
	var url = $("#url").val();
	var sleep = $("#sleep").val();
	var javaScriptEnabled = $("#javaScriptEnabled").find("option:selected").val();
	var template = $("#template").val();
	if (url == "" || url == undefined) {
		alert("预览链接网址不能为空!");
		return;
	}
	$.ajax({
		url : '/htmlUnit/test/view',
		type : 'POST',
		data : {
			"url" : url,
			"javaScriptEnabled" : javaScriptEnabled,
			"sleep" : sleep,
			"template" : template,
			"rules" : rules
		},
		sync : false,
		success : function(data) {
			// alert(data.message);
			if (data.message == true) {
				var timestamp = new Date().getTime();
				var src = $("#iframe").attr("src");
				$("#iframe").attr("src", src.split("?")[0] + "?" + timestamp);
				$("#pop").html("<a href=" + src.split("?")[0] + "?" + timestamp + " target=\"blank\">弹出预览</a>");
				alert("预览页面刷新！");
			}
		},
		error : function() {
			alert("预览失败！");
		}
	});

}

/** 获取当前模版所有采集规则转换成数组* */
function getRules() {
	var arr = new Array();
	$("#rules table").each(function() {
		var rule = new Object;
		$(this).find("[name]").each(function() {
			var name = $(this).attr("name");
			var val = $(this).val();
			if (val == "" || val == undefined || val == '') {
				val = "";
			}
			if (name == "parent") {
				rule.parent = val;
			} else if (name == "tag") {
				rule.tag = val;
			} else if (name == "type") {
				rule.type = val;
			} else if (name == "name") {
				rule.name = val;
			} else if (name == "index") {
				rule.index = val;
			} else if (name == "anchorId") {
				rule.anchorId = val;
			}
		});

		var str = rule.parent + "&" + rule.tag + "&" + rule.type + "&" + rule.name + "&" + rule.index + "&" + rule.anchorId;
		arr.push(str);
	});
	return arr;
}
