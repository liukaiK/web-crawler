<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.esd.config.BaseConfig"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.5/themes/default/easyui.css" charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="/js/jquery-easyui-1.4.5/themes/icon.css" charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="/css/core.css" charset="UTF-8" />
<script type="text/javascript" src="/js/jquery-easyui-1.4.5/jquery.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.5/jquery.easyui.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/ajaxfileupload.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/jquery.sortable.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/init.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/pgFile.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/template.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/css.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/js.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/cating.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js" charset="UTF-8"></script>

<title>无障碍云后台管理</title>
</head>
<body class="easyui-layout">
	<div region="north" split="true" style="height:60px;">
		<div id="progressbarAll" class="easyui-progressbar" style="height: 10px;"></div>
		<a href="javascript:catingAll();" class="easyui-linkbutton" iconCls="icon-start">开启采集</a>
		<a href="javascript:cancelCating();" class="easyui-linkbutton" iconCls="icon-cancel">取消采集</a> 
		<input id="domain" value="<%=request.getSession().getAttribute(BaseConfig.DOMAINNAME) %>" class="easyui-textbox" style="width: 15%;">
		<a href="javascript:cating();" class="easyui-linkbutton">一级采集</a>
		预览网址:<input id="url" class="easyui-textbox" style="width: 25%;">
		<a href="javascript:view();" class="easyui-linkbutton" text="预览"></a>
		<input id="time" class="Wdate" onfocus="WdatePicker({dateFmt:'HH:mm:ss'})" readonly="readonly" value="<%=BaseConfig.time%>" />
		<a href="javascript:setTime();" class="easyui-linkbutton" text="开启"></a>
		<a href="javascript:cancelTime();" class="easyui-linkbutton" text="关闭"></a>
		<a href="javascript:window.location.href = '${contextPath}/logout';" class="easyui-linkbutton" text="退出" style="float: right;"></a>
		<div id="progressbar" class="easyui-progressbar" style="height:10px;"></div>
	</div>
	<div id="pgFileList" region="east" title="规则列表" split="true" style="width:180px;padding: 10px;"></div>
	<div region="west" title="主编辑区" split="true" style="width:270px;">
		<div class="easyui-accordion" fit="true">
			<div title="规则编辑区" selected="true" style="padding:5px;">
				<ul id="rules" class="gbin1-list">
				</ul>
			</div>
		    <div title="模板列表" id="templateFileList"></div>
		    <div title="样式列表" id="cssFileList"></div>
		    <div title="脚本列表" id="jsFileList"></div>
		</div>
	</div>
	<div region="center" border="false">
		<div class="easyui-layout" fit="true">
			<div region="north" border="false" split="true" title="采集网址集合" style="height: 30%;">
				<div class="dourls">
					规则名称:
					<input id="pgFileName" class="easyui-textbox"/>
					<a href="javascript:savePgFile();" class="easyui-linkbutton" iconCls="icon-save">保存</a>
		    		<a href="javascript:deletePgFile();" class="easyui-linkbutton" iconCls="icon-clear">删除</a>	
		    		&nbsp;&nbsp;开启脚本:
		    		<select id="javaScriptEnabled" class="easyui-combobox" panelHeight="auto" editable="false">
		    			<option value="false">禁用</option>
		    			<option value="true">启用</option>
		    		</select>
		    		异步加载：
		    		<input id="sleep" class="easyui-textbox" value="0" style="width: 50px;"/>			
				</div>
				<textarea id="urls"></textarea>
			</div>
			<div region="center" split="true" border="false">
				<div id="tabs" class="easyui-tabs" border="false" fit="true" >
				    <div title="模板">
				    	<div class="dofile">
				    		文件名称:
				    		<input id="templateName" class="easyui-textbox"/>
				    		<a href="javascript:saveTemplate();" class="easyui-linkbutton" iconCls="icon-save">保存</a>
				    		<a href="javascript:deleteTemplate();" class="easyui-linkbutton" iconCls="icon-clear">删除</a>
				    		&nbsp;&nbsp;&nbsp;&nbsp;
				    		<input id="myBlogImage" class="easyui-filebox" name="myfiles" data-options="prompt:'选择图片文件',buttonText:'&nbsp;选&nbsp;择&nbsp;'" style="width:170px" />
				    		<a href="javascript:ajaxFileUpload();" class="easyui-linkbutton">上传图片</a>
				    	</div>
				    	<textarea id="template_content"></textarea>
				    </div>
				    <div title="样式">
				    	<div class="dofile">
				    		文件名称:
				    		<input id="cssName" class="easyui-textbox"/>
				    		<a href="javascript:saveCss();" class="easyui-linkbutton" iconCls="icon-save">保存</a>
				    		<a href="javascript:deleteCss();" class="easyui-linkbutton" iconCls="icon-clear">删除</a>
				    	</div>
				        <textarea id="css_content"></textarea>
				    </div>
				    <div title="脚本">
				    	<div class="dofile">
				    		文件名称:
				    		<input id="jsName" class="easyui-textbox"/>
				    		<a href="javascript:saveJs();" class="easyui-linkbutton" iconCls="icon-save">保存</a>
				    		<a href="javascript:deleteJs();" class="easyui-linkbutton" iconCls="icon-clear">删除</a>
				    	</div>
				        <textarea id="js_content"></textarea>
				    </div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="/js/codemirror-5.18.2/lib/codemirror.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/selection/active-line.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/edit/matchtags.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/edit/matchbrackets.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/edit/closetag.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/edit/closebrackets.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/search/searchcursor.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/search/search.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/fold/xml-fold.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/hint/show-hint.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/hint/xml-hint.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/hint/html-hint.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/hint/javascript-hint.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/addon/display/fullscreen.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/keymap/sublime.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/mode/xml/xml.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/mode/css/css.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/mode/javascript/javascript.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/codemirror-5.18.2/mode/htmlmixed/htmlmixed.js" charset="UTF-8"></script>
<link rel="stylesheet" type="text/css" href="/js/codemirror-5.18.2/lib/codemirror.css" charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="/js/codemirror-5.18.2/doc/docs.css" charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="/js/codemirror-5.18.2/addon/hint/show-hint.css" charset="UTF-8" />
<link rel="stylesheet" type="text/css" href="/js/codemirror-5.18.2/addon/display/fullscreen.css" charset="UTF-8" />
<style type="text/css">
.CodeMirror {
	width: 100%;
	height: 100%;
	margin: 0px;
	padding: 0px;
	border: none;
	font-size: 14px;
}
</style>
<script type="text/javascript">
	var urls_content = document.getElementById("urls");
	var urlsTextArea = CodeMirror.fromTextArea(urls_content, {
		keyMap: "sublime",
		styleActiveLine : true,
		lineNumbers : true
	});
	var template_content = document.getElementById("template_content");
	var templateEditor = CodeMirror.fromTextArea(template_content, {
		mode : "text/html",
		keyMap: "sublime",
		matchTags : {
			bothTags : true
		},
		extraKeys : {
			"Ctrl-J" : "toMatchingTag",
			"Ctrl-M" : "autocomplete",
			"F11" : function(cm) {
				cm.setOption("fullScreen", !cm.getOption("fullScreen"));
			},
			"Esc" : function(cm) {
				if (cm.getOption("fullScreen"))
					cm.setOption("fullScreen", false);
			}
		},
		styleActiveLine : true,
		lineNumbers : true,
		autoCloseTags: true,
		autoCloseBrackets: true,
		matchBrackets: true,
		gutters: ["CodeMirror-lint-markers"]
	});
	
	var css_content = document.getElementById("css_content");
	var cssEditor = CodeMirror.fromTextArea(css_content, {
		mode : "text/css",
		keyMap: "sublime",
		matchTags : {
			bothTags : true
		},
		extraKeys : {
			"Ctrl-J" : "toMatchingTag",
			"Ctrl-M" : "autocomplete",
			"F11" : function(cm) {
				cm.setOption("fullScreen", !cm.getOption("fullScreen"));
			},
			"Esc" : function(cm) {
				if (cm.getOption("fullScreen"))
					cm.setOption("fullScreen", false);
			}
		},
		styleActiveLine : true,
		lineNumbers : true,
		autoCloseTags: true,
		autoCloseBrackets: true,
		matchBrackets: true,
		gutters: ["CodeMirror-lint-markers"],
	});
	
	var js_content = document.getElementById("js_content");
	var jsEditor = CodeMirror.fromTextArea(js_content, {
		mode : "text/javascript",
		keyMap: "sublime",
		matchTags : {
			bothTags : true
		},
		extraKeys : {
			"Ctrl-J" : "toMatchingTag",
			"Ctrl-M" : "autocomplete",
			"F11" : function(cm) {
				cm.setOption("fullScreen", !cm.getOption("fullScreen"));
			},
			"Esc" : function(cm) {
				if (cm.getOption("fullScreen"))
					cm.setOption("fullScreen", false);
			}
		},
		styleActiveLine : true,
		lineNumbers : true,
		autoCloseTags: true,
		autoCloseBrackets: true,
		matchBrackets: true,
		gutters: ["CodeMirror-lint-markers"],
	});
</script>
</html>