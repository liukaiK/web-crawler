function catingAll() {
	var url = $("#domain").textbox('getValue');
	$.ajax({
		url : root + '/coreAll/catingAll',
		type : 'POST',
		sync : true,
		data : {
			'url' : url
		},
		success : function(data) {
			if (data.notice == true) {
				$.messager.alert('提示', '整站采集开始!', 'info');
			}
		},
		error : function() {
			$.messager.alert('发生错误', '整站采集失败', 'error');
		}
	});
}

function getRuleHtml(n) {
	var addhtml = '<li style="width: 100%;display: block;">'
			+ '<div class="easyui-panel" title=" " closable="true" style="background:#fafafa;">'
			+ '<table>'
			+ '<tr>'
			+ '<td>备注：</td>'
			+ '<td><input class="easyui-textbox" name="des" value=""/></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td>父类：</td>'
			+ '<td><input class="easyui-textbox" name="parent" value=""/></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td>标签：</td>'
			+ '<td><input class="easyui-textbox" name="tag" value=""/></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td>类型：</td>'
			+ '<td><select name="type" class="easyui-combobox" panelHeight="auto" editable="false">'
			+ '<option value="id">id</option>'
			+ '<option value="class">class</option>'
			+ '<option value="tag">tag</option>'
			+ '<option value="text">text</option>'
			+ '</select>'
			+ '</td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td>名称：</td>'
			+ '<td><input class="easyui-textbox" name="name" value=""/></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td>序号：</td>'
			+ '<td><input class="easyui-textbox" name="index" value=""/></td>'
			+ '</tr>'
			+ '<tr>'
			+ '<td>锚点：</td>'
			+ '<td><input class="easyui-textbox" name="anchorId" value=""/></td>'
			+ '</tr>' + '</table>' + '</div>' + '</li>';
	return addhtml;
};



function catAllStatic() {
	var timestamp = new Date().getTime();
	$.ajax({
		url : root + '/coreAll/getdbCount?' + timestamp,
		type : 'get',
		sync : false,
		success : function(data) {
			if (data.notice == true) {
				$('#progressbarAll').progressbar('setValue', data.message);
			} else {
//				window.clearInterval(catAllStaticId);
			}
		},
		error : function() {
			
		}
	});
}


function cancelCating() {
	$.ajax({
		url : root + '/coreAll/cancelCating',
		type : 'POST',
		sync : true,
		success : function(data) {
			if (data.notice == true) {
				$.messager.alert('提示', '取消采集成功', 'info');
			} else {
				$.messager.alert('提示', '取消采集失败', 'info');
			}
		},
		error : function() {
		}
	});
}

function cating() {
	var url = $("#domain").textbox('getValue');
	$.ajax({
		url : root + '/core/cating',
		type : 'POST',
		sync : true,
		data : {
			'url' : url
		},
		success : function(data) {
			if (data.message == true) {
				$.messager.alert('提示', '批量采集完成', 'info');
			}
		},
		error : function() {
			$.messager.alert('发生错误', '批量采集失败!', 'error');
		}
	});
}

function catingStatic(){
	var timestamp = new Date().getTime();
	$.ajax({
		url : root + '/core/getProgressCount?' + timestamp,
		type : 'get',
		sync : false,
		success : function(data) {
			if (data.message == true) {
				$('#progressbar').progressbar('setValue', (100 - data.g));
			} else {
//				window.clearInterval(interId);
			}
		},
		error : function() {
//			window.clearInterval(interId);
		}
	});	
	
}

function view() {
	load();
	var rules = getRules();
	var url = $("#url").textbox('getValue');
	var sleep = $("#sleep").textbox('getValue');
	var javaScriptEnabled = $("#javaScriptEnabled").combobox('getValue');
	var template = $("#templateName").textbox('getValue');
	if (url == "" || url == undefined) {
		disLoad();
		$.messager.alert('提示', '预览链接网址不能为空!', 'info');
		return;
	}
	$.ajax({
		url : root + '/test/view',
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
			disLoad();
			if (data.notice == true) {
				var timestamp = new Date().getTime();
				$.messager.alert('提示', '预览成功', 'info');
				window.open("/baic/test/view.html".split("?")[0] + "?" + timestamp);        
			} else {
				$.messager.alert('发生错误', '预览失败！', 'error');
			}
		},
		error : function() {
			disLoad();
			$.messager.alert('发生错误', '预览失败！', 'error');
		}
	});

}

function setTime(){
	var time = $('#time').val();
	$.ajax({
		url : root + '/setTimeTask',
		type : 'POST',
		sync : false,
		data : {
			'time' : time
		},
		success : function(data) {
			$.messager.alert('提示', data.substring(2), 'info');
		},
		error : function() {
			$.messager.alert('发生错误', '定时任务设置失败!', 'error');
		}
	});	
}

function cancelTime(){
	$.ajax({
		url : root + '/cancelTimeTask',
		type : 'GET',
		sync : false,
		success : function(data) {
			$('#time').val('');
			$.messager.alert('提示', data.substring(2), 'info');
		},
		error : function() {
			$.messager.alert('发生错误', '取消定时任务失败!', 'error');
		}
	});	
}

function exit() {
	window.location.href = root + '/logout';
}