var projectName = "/iac";
var root = projectName + "/admin";
var dirName = "web";
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
		url : root + '/loadPgFileList',
		type : 'POST',
		sync : false,
		success : function(data) {
			if (data.list != null) {
				$("#pgFileList").empty();
				$.each(data.list,function(i,item){
					$("#pgFileList").append("<p><a href='#' onclick='loadPgFile("+'"'+item.fileName+'"'+")' >" + item.fileName + "</a></p>");		
				});
			}
		},
		error : function() {
		}
	});
}

function loadTemplateFileList() {
	$.ajax({
		url : root + '/loadTemplateList',
		type : 'POST',
		sync : false,
		data : {
			fileType : "template"
		},
		success : function(data) {
			if (data.list != null) {
				$("#templateFileList").empty();
				$.each(data.list,function(i,item){
					$("#templateFileList").append("<p><a href='#' onclick='loadTemplateFile("+'"'+item.fileName+'"'+")' >" + item.fileName + "</a></p>");
				});
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
		data : {
			fileType : "css"
		},
		success : function(data) {
			if (data.list != null) {
				$("#cssFileList").empty();
				$.each(data.list,function(i,item){
					$("#cssFileList").append("<p><a href='#' onclick='loadCssFile("+'"'+item.fileName+'"'+")' >" + item.fileName + "</a></p>");
				});
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
		data : {
			fileType : "js"
		},
		success : function(data) {
			if (data.list != null) {
				$("#jsFileList").empty();
				$.each(data.list,function(i,item){
					$("#jsFileList").append("<p><a href='#' onclick='loadJsFile("+'"'+item.fileName+'"'+")' >" + item.fileName + "</a></p>");
				});
			}
		},
		error : function() {
		}
	});	
}

function ajaxFileUpload(){ 
	  $.ajaxFileUpload({
	      // 处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
	      url:root + '/fileUpload',  
	      secureuri:false,                           // 是否启用安全提交,默认为false

	      fileElementId:'filebox_file_id_1',               // 文件选择框的id属性

	      dataType:'text',                           // 服务器返回的格式,可以是json或xml等

	      success:function(data, status){           // 服务器响应成功时的处理函数
	          data = data.replace(/<pre.*?>/g, '');  // ajaxFileUpload会对服务器响应回来的text内容加上<pre
														// style="....">text</pre>前后缀
	          data = data.replace(/<PRE.*?>/g, '');  
	          data = data.replace("<PRE>", '');  
	          data = data.replace("</PRE>", '');  
	          data = data.replace("<pre>", '');  
	          data = data.replace("</pre>", '');     // 本例中设定上传文件完毕后,服务端会返回给前台[0`filepath]
	          if(data.substring(0, 1) == 0){         // 0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
// $("img[id='uploadImage']").attr("src", data.substring(2));
	              alert('上传成功');
	          }else{  
	              alert(data.substring(2));
	          }  
	      },  
	      error:function(data, status, e){ // 服务器响应失败时的处理函数
	    	  alert('图片上传失败，请重试！！');
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
