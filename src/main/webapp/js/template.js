function loadTemplateFile(obj) {
	load();
	var fileName = $(obj).text();
	var filePath = template_root;
	$("#templateName").textbox('setValue',fileName);
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
				$("#template_content").val(data.message);
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
	var templateContent = $("#template_content").val();
	$.ajax({
		url : root + '/saveTemplate',
		type : 'POST',
		data : {
			'templateName' : templateName,
			'templateContent' : templateContent
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
