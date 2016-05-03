var projectName = "/iac";
var root = projectName + "/admin";
var dirName = "/baic";
var template_root = "/template/";
var css_root = "/etc/styles/";
var js_root = "/etc/js/";
$(document).ready(function() {
	load();
	init();
	$("#rules").append(getRuleHtml(1));
	easyuipanel();
	setInterval(catAllStatic, 2000);
	setInterval(catingStatic, 2000);
	disLoad();
})

function init(){
	loadPgFileList();
	loadTemplateFileList();
	loadCssFileList();
	loadJsFileList();
}

function easyuipanel() {
	$('.easyui-panel').panel({
		width : '100%',
		height: '215px',
		tools : [ {
			iconCls : 'icon-add',
			handler : function() {
				$(getRuleHtml(1)).insertAfter($(this).parent().parent().parent().parent());
				easyuipanel();
			}
		} ],
		onClose : function() {
			$(this).parent().parent().remove();
		}
	});
	$.parser.parse($("#rules").parent());
    $('.gbin1-list').sortable();
}

function loadPgFileList() {
	$.ajax({
		url : root + '/core/loadPgFileList',
		type : 'POST',
		sync : false,
		success : function(data) {
			if (data.list != null) {
				$("#pgFileList").empty();
				for (var i = 0; i < data.list.length; i++) {
					$("#pgFileList").append("<p><a href='#' onclick='loadPgFile(this)' >" + data.list[i] + "</a></p>");
				}
			}
		},
		error : function() {
		}
	});
}

function loadTemplateFileList() {
	$.ajax({
		url : root + '/core/loadTemplateList',
		type : 'POST',
		sync : false,
		success : function(data) {
			if (data.list != null) {
				$("#templateFileList").empty();
				for (var i = 0; i < data.list.length; i++) {
					$("#templateFileList").append("<p><a href='#' onclick='loadTemplateFile(this)' >" + data.list[i] + "</a></p>");
				}
			}
		},
		error : function() {

		}
	});
}

function loadCssFileList() {
	$.ajax({
		url : root + '/loadCssList',
		type : 'POST',
		sync : false,
		success : function(data) {
			if (data.list != null) {
				$("#cssFileList").empty();
				for ( var i = 0; i < data.list.length; i++) {
					$("#cssFileList").append("<p><a href='#' onclick='loadCssFile(this)' >" + data.list[i] + "</a></p>");
				}
			}
		},
		error : function() {
		}
	});
}

function loadJsFileList(){
	$.ajax({
		url : root + '/loadJsList',
		type : 'POST',
		sync : false,
		success : function(data) {
			if (data.list != null) {
				$("#jsFileList").empty();
				for ( var i = 0; i < data.list.length; i++) {
					$("#jsFileList").append("<p><a href='#' onclick='loadJsFile(this)' >" + data.list[i] + "</a></p>");
				}
			}
		},
		error : function() {
		}
	});	
}

function load() {  
    $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
}  

function disLoad() {  
    $(".datagrid-mask").remove();  
    $(".datagrid-mask-msg").remove();  
}
