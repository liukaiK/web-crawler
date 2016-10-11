function loadTemplateFile(obj) {
	load();
	var fileName = obj;
	$("#templateName").textbox('setValue',fileName);
	$.ajax({
		url : root + '/loadTemplate',
		type : 'POST',
		sync : false,
		data : {
			fileType : "template",
			fileName : fileName
		},
		success : function(data) {
			if (data.notice == true) {
				templateEditor.setValue(data.message);
			} else {
				$.messager.alert('发生错误',data.message,'error');
			}

		},
		error : function() {

		}
	});
	$('#tabs').tabs('select', '模板');
	disLoad();
}

function saveTemplate() {
	var templateName = $("#templateName").textbox('getValue');
	var templateContent = templateEditor.getValue();
	$.ajax({
		url : root + '/saveTemplate',
		type : 'POST',
		data : {
			templateName : templateName,
			templateContent : templateContent
		},
		sync : false,
		success : function(data) {
			if (data.notice == true) {
				$.messager.alert('保存模板', data.message, 'info');
				loadTemplateFileList();
			} else {
				$.messager.alert('保存模板', data.message, 'error');
			}
		},
		error : function() {

		}
	});
}


function deleteTemplate() {
	$.messager.confirm('删除模板', '确认删除模板文件 ?', function(r) {
		if (r) {
			var templateName = $("#templateName").textbox('getValue');
			$.ajax({
				url : root + '/deleteTemplate',
				type : 'POST',
				sync : false,
				data : {
					templateName : templateName
				},
				success : function(data) {
					if (data.notice == true) {
						$.messager.alert('删除模板', data.message, 'info');
						loadTemplateFileList();
					} else {
						$.messager.alert('删除模板', data.message, 'error');
					}
				},
				error : function() {
				}
			});
		}
	});
}
