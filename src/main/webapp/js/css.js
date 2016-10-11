function loadCssFile(obj) {
	load();
	var fileName = obj;
	$("#cssName").textbox('setValue', fileName);
	$.ajax({
		url : root + '/loadFileContent',
		type : 'POST',
		sync : false,
		data : {
			fileType : "css",
			fileName : fileName
		},
		success : function(data) {
			if (data.notice == true) {
//				$("#css_content").val(data.message);
				cssEditor.setValue(data.message);
			} else {
				$.messager.alert('发生错误',data.message,'error');
			}

		},
		error : function() {

		}
	});
	$('#tabs').tabs('select', '样式');
	disLoad();
}

function saveCss() {
	var cssName = $("#cssName").textbox('getValue');
	var cssContent = cssEditor.getValue();
	$.ajax({
		url : root + '/saveCss',
		type : 'POST',
		data : {
			cssName : cssName,
			cssContent : cssContent
		},
		sync : false,
		success : function(data) {
			if (data.notice == true) {
				$.messager.alert('保存样式', data.message, 'info');
				loadCssFileList();
			} else {
				$.messager.alert('保存样式', data.message, 'error');
			}
		},
		error : function() {

		}
	});
}


function deleteCss() {
	$.messager.confirm('删除样式', '确认删除样式文件 ?', function(r) {
		if (r) {
			var cssName = $("#cssName").textbox('getValue');
			$.ajax({
				url : root + '/deleteCss',
				type : 'POST',
				sync : false,
				data : {
					cssName : cssName
				},
				success : function(data) {
					if (data.notice == true) {
						$.messager.alert('删除样式', data.message, 'info');
						loadCssFileList();
					} else {
						$.messager.alert('删除样式', data.message, 'error');
					}
				},
				error : function() {
				}
			});
		}
	});
}
