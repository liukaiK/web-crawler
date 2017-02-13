function loadPgFile(obj) {
	load();
	var pgFileName = $(obj).text();
	$.ajax({
		url : root + '/loadPgFile',
		type : 'POST',
		sync : false,
		data : {
			pgFileName : pgFileName
		},
		success : function(data) {
			if (data.notice == true) {
				$("#pgFileName").textbox('setValue',pgFileName.split("\.")[0]);
				$("#sleep").textbox('setValue',data.pgFile.sleep);
				// 开启脚本
				data.pgFile.javaScriptEnabled == true ? $("#javaScriptEnabled").combobox("select","true") : $("#javaScriptEnabled").combobox("select","false");
				loadUrls(data);
				loadTemplate(data);
				loadRules(data);
				$('#tabs').tabs('select', '模板');
				$.parser.parse($("#rules"));
				disLoad();
			} else {
				disLoad();
				$.messager.alert('发生错误',data.message,'error');
			}
		},
		error : function() {
			
		}
	});	
}


function loadUrls(data) {
	var urls = data.pgFile.urls;
	if (urls != null) {
		var textUrls = "";
		for (var i = 0; i < urls.length; i++) {
			textUrls = textUrls + urls[i] + "\r\n";
		}
//		$("#urls").val(textUrls);
		urlsTextArea.setValue(textUrls);
	}
}

function loadTemplate(data) {
	var fileName = data.pgFile.template;
	var filePath = template_root;
	$("#templateName").textbox('setValue', fileName)
	$.ajax({
		url : root + '/loadFileContent',
		type : 'POST',
		sync : false,
		data : {
			filePath : filePath,
			fileName : fileName
		},
		success : function(data) {
			if (data.notice == true) {
//				$("#template_content").val(data.message);
				templateEditor.setValue(data.message);
			} else {
				$.messager.alert('发生错误', data.message, 'error');
			}

		},
		error : function() {

		}
	});
}


function loadRules(data) {
	var rules = data.pgFile.list;
	if (rules != null) {
		$("#rules").empty();
		for (var i = 0; i < rules.length; i++) {
			n = i;
			$("#rules").append(loadRule(i, rules[i]));
		}
	}

}



function loadRule(n, rule) {
	ruleFilter(rule);// 过滤掉双引号为单引号
	var type = '<tr>'
			+ '<td>类型：</td>'
			+ '<td><select name="type" class="easyui-combobox" panelHeight="auto" editable="false">'
			+ '<option selected="selected" value="id">id</option>'
			+ '<option value="class">class</option>'
			+ '<option value="tag">tag</option>'
			+ '<option value="text">text</option>' + '</select>' + '</td>'
			+ '</tr>';
	if (rule.type == "class") {
		type = '<tr>'
			+ '<td>类型：</td>'
			+ '<td><select name="type" class="easyui-combobox" panelHeight="auto" editable="false">'
			+ '<option value="id">id</option>'
			+ '<option selected="selected" value="class">class</option>'
			+ '<option value="tag">tag</option>'
			+ '<option value="text">text</option>' + '</select>' + '</td>'
			+ '</tr>';
	} else if(rule.type=="tag"){
		type = '<tr>'
			+ '<td>类型：</td>'
			+ '<td><select name="type" class="easyui-combobox" panelHeight="auto" editable="false">'
			+ '<option value="id">id</option>'
			+ '<option value="class">class</option>'
			+ '<option selected="selected" value="tag">tag</option>'
			+ '<option value="text">text</option>' + '</select>' + '</td>'
			+ '</tr>';
	}else if(rule.type=="text"){
		type = '<tr>'
			+ '<td>类型：</td>'
			+ '<td><select name="type" class="easyui-combobox" panelHeight="auto" editable="false">'
			+ '<option value="id">id</option>'
			+ '<option value="class">class</option>'
			+ '<option value="tag">tag</option>'
			+ '<option selected="selected" value="text">text</option>' + '</select>' + '</td>'
			+ '</tr>';
	}
	var rule = '<li>'
			+ '<div class="easyui-panel" data-options="onClose:function(){$(this).parent().parent().remove();},tools:[{iconCls:\'icon-add\',handler:function(){$(getRuleHtml(1)).insertAfter($(this).parent().parent().parent().parent());$.parser.parse($(this).parent().parent().parent().parent().next());}}]" title=" " closable="true" style="background:#fafafa;width:97%;">'
			+ '<table>' + '<tr>' + '<td>备注：</td>'
			+ '<td><input class="easyui-textbox" style="width: 160px;" name="des" value="'+rule.des+'"/></td>' + '</tr>'
			+ '<tr>' + '<td>父类：</td>'
			+ '<td><input class="easyui-textbox" style="width: 160px;" name="parent" value="'+rule.parent+'"/></td>'
			+ '</tr>' + '<tr>' + '<td>标签：</td>'
			+ '<td><input class="easyui-textbox" style="width: 160px;" name="tag" value="'+rule.tag+'"/></td>' + '</tr>'
			+ type + '<tr>' + '<td>名称：</td>'
			+ '<td><input class="easyui-textbox" style="width: 160px;" name="name" value="'+rule.name+'"/></td>' + '</tr>'
			+ '<tr>' + '<td>序号：</td>'
			+ '<td><input class="easyui-textbox" style="width: 160px;" name="index" value="'+rule.index+'"/></td>' + '</tr>'
			+ '<tr>' + '<td>锚点：</td>'
			+ '<td><input class="easyui-textbox" style="width: 160px;" name="anchorId" value="'+rule.anchorId+'"/></td>'
			+ '</tr>' + '</table>' + '</div>' + '</li>';
	return rule;
};








function ruleFilter(rule) {
	if (rule == null) {
		return;
	}
	if (rule.parent != null) {
		rule.parent = rule.parent.replace(/"([^"]*)"/g, "$1");
		rule.parent = rule.parent.replace(/'([^"]*)'/g, "$1");
	}
	if (rule.tag != null) {
		rule.tag = rule.tag.replace(/"([^"]*)"/g, "$1");
		rule.tag = rule.tag.replace(/'([^"]*)'/g, "$1");
	}
}

function savePgFile() {
	var pgName = $("#pgFileName").textbox('getValue');
	if (pgName == "" || pgName == undefined) {
		$.messager.alert('保存规则', '规则名称不能为空!', 'info');
		return;
	}
//	var urls = $("#urls").val();
	var urls = urlsTextArea.getValue();
	if (urls == "" || urls == undefined) {
		$.messager.alert('保存规则', '链接网址集合不能为空!', 'info');
		return;
	}	
	var arr = urls.split("\n");
	var url_array = new Array();
	for ( var i = 0; i < arr.length; i++) {
		if (arr[i] == "" || arr[i] == null) {
			continue;
		}
		url_array.push(arr[i].replace(/[ ]/g, ""));
	}
	var javaScriptEnabled = $("#javaScriptEnabled").combobox('getValue');
	var sleep = $("#sleep").textbox('getValue');	
	var rules = getRules();
	var template = $("#templateName").textbox('getValue');	
	$.ajax({
		url : root + '/savePgFile',
		type : 'POST',
		data : {
			"sleep" : sleep,
			"javaScriptEnabled" : javaScriptEnabled,
			"template" : template,
			"pgName" : pgName,
			"urls" : url_array,
			"rules" : rules
		},
		sync : false,
		success : function(data) {
			if (data.notice == true) {
				loadPgFileList();
				$.messager.alert('保存规则', data.message, 'info');
			} else {
				$.messager.alert('保存规则', data.message, 'error');
			}
		},
		error : function() {
		}
	});	
	
	
}

function deletePgFile() {
	$.messager.confirm('删除规则', '确认删除规则文件 ?', function(r) {
		if (r) {
			var pgFileName = $("#pgFileName").textbox('getValue');
			$.ajax({
				url : root + '/deletePgFile',
				type : 'POST',
				sync : false,
				data : {
					pgFileName : pgFileName
				},
				success : function(data) {
					if (data.notice == true) {
						$.messager.alert('删除规则', data.message, 'info');
						loadPgFileList();
					} else {
						$.messager.alert('删除规则', data.message, 'error');
					}
				},
				error : function() {
				}
			});
		}
	});
}

function getRules() {
	var arr = new Array();
	$("#rules table").each(
			function() {
				var rule = new Object;
				$(this).find("[name]").each(function() {
					var name = $(this).attr("name");
					var val = $(this).val();
					if (val == "" || val == undefined || val == '') {
						val = "";
					}
					if (name == "des") {
						rule.des = val;
					} else if (name == "parent") {
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
				var str = rule.des + "&" + rule.parent + "&" + rule.tag + "&"
						+ rule.type + "&" + rule.name + "&" + rule.index + "&"
						+ rule.anchorId;
				arr.push(str);
			});
	return arr;
}
