function loadJsFile(obj) {
	load();
	var fileName = $(obj).text();
	var filePath = js_root;
	$("#jsName").textbox('setValue', fileName);
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
//				$("#js_content").val(data.message);
				jsEditor.setValue(data.message);
			} else {
				$.messager.alert('发生错误',data.message,'error');
			}

		},
		error : function() {

		}
	});
	$('#tabs').tabs('select', '脚本');
	disLoad();
}

function saveJs() {
	var jsName = $("#jsName").textbox('getValue');
	var jsContent = jsEditor.getValue();
	$.ajax({
		url : root + '/saveJs',
		type : 'POST',
		data : {
			'jsName' : jsName,
			'jsContent' : jsContent
		},
		sync : false,
		success : function(data) {
			if (data.notice == true) {
				$.messager.alert('保存脚本', data.message, 'info');
				loadJsFileList();
			} else {
				$.messager.alert('保存脚本', data.message, 'error');
			}
		},
		error : function() {

		}
	});
}


function deleteJs() {
	$.messager.confirm('删除脚本', '确认删除脚本文件 ?', function(r) {
		if (r) {
			var jsName = $("#jsName").textbox('getValue');
			$.ajax({
				url : root + '/deleteJs',
				type : 'POST',
				sync : false,
				data : {
					jsName : jsName
				},
				success : function(data) {
					if (data.notice == true) {
						$.messager.alert('删除脚本', data.message, 'info');
						loadJsFileList();
					} else {
						$.messager.alert('删除脚本', data.message, 'error');
					}
				},
				error : function() {
				}
			});
		}
	});
}
