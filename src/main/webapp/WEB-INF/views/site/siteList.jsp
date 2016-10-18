<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.5/themes/default/easyui.css" charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.5/themes/icon.css" charset="UTF-8" />
<script type="text/javascript" src="/js/jquery-easyui-1.4.5/jquery.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.5/jquery.easyui.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
<title>站点列表</title>
<script type="text/javascript">
	$(function() {
		$('#dg').datagrid(
						{
							title : '站点选择',
							width : 1400,
							height : 600,
							singleSelect : true,
							rownumbers : true,
							fitColumns : true,
							toolbar : '#toolbar',
							url : 'getSiteList',
							columns : [ [
									{
										field : 'siteName',
										title : '站点名',
										width : 50
									},
									{
										field : 'domainName',
										title : '主域名',
										width : 50
									},
									{
										field : 'port',
										title : '端口号',
										width : 50
									},
									{
										field : 'createDate',
										title : '创建时间',
										width : 50
									},
									{
										field : 'updateDate',
										title : '最近修改时间',
										width : 50
									},
									{
										field : 'action',
										title : '操作',
										width : 70,
										align : 'center',
										formatter : function(value, row, index) {
											return '<a href="#" onclick="getSite(this)">进入</a>';
// 											if (row.editing) {
// 												var s = '<a href="#" onclick="saverow(this)">Save</a> ';
// 												var c = '<a href="#" onclick="cancelrow(this)">Cancel</a>';
// 												return s + c;
// 											} else {
// 												var e = '<a href="#" onclick="editrow(this)">Edit</a> ';
// 												var d = '<a href="#" onclick="deleterow(this)">Delete</a>';
// 												return e + d;
// 											}
										}
									} ] ],
						});
	});
	
	function getSite(target) {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
// 			alert(row.id);
			window.location.href = 'getSite?id=' + row.id;
// 			$('#dlg').dialog('open').dialog('setTitle', '编辑站点');
// 			url = 'updateSite?id=' + row.id;
		}
	}

	function newUser() {
		$('#dlg').dialog('open').dialog('setTitle', '新增站点');
		$('#fm').form('clear');
		url = 'addSite';
	}

	function saveUser() {
		$('#fm').form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				// 			var result = eval('('+result+')');
				// 			if (result.errorMsg){
				// 				$.messager.show({
				// 					title: 'Error',
				// 					msg: result.errorMsg
				// 				});
				// 			} else {
				$('#dlg').dialog('close'); // close the dialog
				$('#dg').datagrid('reload'); // reload the user data
				// 			}
			}
		});
	}

	function editUser() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '编辑站点');
			$('#fm').form('load', row);
			url = 'updateSite?id=' + row.id;
		}
	}
	function destroyUser() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确认删除', '删除之后不可恢复,确认删除?', function(r) {
				if (r) {
					$.post('removeSite', {
						id : row.id
					}, function(result) {
						if (result.notice) {
							$('#dg').datagrid('reload'); // reload the user data
						} else {
							$.messager.show({ // show error message
								title : 'Error',
								msg : result.errorMsg
							});
						}
					}, 'json');
				}
			});
		}
	}
</script>
</head>
<body>
    <table id="dg" >
    	<thead>
    		<tr>
    			<th field="siteName" width="50">站点名</th>
    			<th field="domainName" width="50">主域名</th>
    			<th field="port" width="50">端口号</th>
    			<th field="createDate" width="50">创建时间</th>
    			<th field="updateDate" width="50">最近修改时间</th>
    		</tr>
    	</thead>
    </table>
    <div id="toolbar">
    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增站点</a>
    	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑站点</a>
    	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除站点</a>
    </div>
    
    
    
    
    
    
    <!-- 新增时候的弹出框 ************************************************************************************************-->
	<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px" closed="true" buttons="#dlg-buttons" title="新增站点">
		<form id="fm" method="post">
			<div class="fitem">
				<label>站点名:</label>
				<input name="siteName" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>主域名:</label>
				<input name="domainName" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>端口号:</label>
				<input name="port" class="easyui-validatebox" required="true">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	<!-- *************************************************************************************************************** -->
</body>
</html>