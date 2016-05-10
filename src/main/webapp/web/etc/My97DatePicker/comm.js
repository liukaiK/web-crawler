/**
 *通用JS方法集合. 
 */
/**
 * 将某个对象中的input 中数据保存起来, objId 对象ID.
 * 
 * 支持以下对象. <input type="button" value="Input Button"/>, <input type="checkbox"
 * />, <input type="file" />, <input type="hidden" />, <input type="image" />,
 * <input type="password" />, <input type="radio" />, <input type="reset" />,
 * <input type="submit" />, <input type="text" />, <select><option>Option</option></select>
 * <textarea></textarea>, <button>Button</button>,
 * 
 */
function initData(objId) {
	// 向需要对象中插入属性,表示 此对象已经初始过数据了.
	$("#" + objId).attr('changed', true);
	// 将每个对象添加个新属性,保存初始数值.
	$("#" + objId).find(":input").each(function() {
		// 根据类型,分别获得值
			switch (jQuery(this).attr("type")) {
			case "checkbox":
				jQuery(this).attr('_value', jQuery(this).attr("checked"));
				break;
			case "radio":
				jQuery(this).attr('_value', jQuery(this).attr("checked"));
				break;
			default:
				jQuery(this).attr('_value', jQuery(this).val());
			}
		});
}
/**
 * 判断对象中的数据是否改动.
 * 
 * @return 是否被改动. true 未改动,false未改动..
 */
function is_form_changed(objId) {
	// 默认未修改.
	var is_changed = false;
	// 循环对象下所有input元素.
	$("#" + objId).find(":input").each(function() {

		var _v = jQuery(this).attr('_value');
		if (typeof (_v) == 'undefined') {
			_v = '';
		}
		// 根据元素类型进入废纸判断.
			switch (jQuery(this).attr("type")) {
			case "checkbox":
				if ($.trim(_v) != $.trim(jQuery(this).attr("checked")) + "") {
					// 返回修改过标志.
					is_changed = true;
				}
				break;
			case "radio":
				if (_v != jQuery(this).attr("checked") + "") {
					// 返回修改过标志.
					is_changed = true;
				}
				break;
			default:
				if (_v != jQuery(this).val() + "") {
					// //返回修改过标志.
					is_changed = true;
				}
				break;
			}
		});
	return is_changed;
}
/**
 * 跳转. formID 查询form ID. pageNo 跳转页.
 */
function gotoPage(pageNo, formId) {
	$('#pageNo', window.parent.document).val(pageNo);
	$('#' + formId, window.parent.document).submit();

}
function chagePerNumPage(obj, formId) {
	$('#pageNo', window.parent.document).val(1);
	$('#pageSize', window.parent.document).val(obj.value);
	$('#' + formId, window.parent.document).submit();
}

/**
 * 通用双层IFrame自适应高度
 * 
 * @return
 */
function setIframeHeight() {
	// 获取结果页iframe
	var iframe_win = parent.$("iframe").get(0);
	var div_framearea = parent.parent.$("#framearea").get(0);
	// 获取index页iframe
	var iframe_frameaif = parent.parent.$("#frameaif").get(0);

	var isIE = navigator.userAgent.toLowerCase().indexOf('msie');
	// 如果有结果页 且结果页的父页面iframe不是 iframe_frameaif
	if ($(iframe_win).attr("id") != "frameaif") {

		if (isIE > 0) {
			iframe_win.height = iframe_win.Document.body.scrollHeight + 35;
			iframe_frameaif.height = parent.document.body.scrollHeight;
		} else {
			if (iframe_win.contentDocument
					&& iframe_win.contentDocument.body.offsetHeight) {
//				alert("scrollHeight"+iframe_win.contentDocument.body.scrollHeight );
//				alert("scrollTop"+iframe_win.contentDocument.body.scrollTop );
//				alert("offsetHeight"+ iframe_win.contentDocument.body.offsetHeight);
//				alert(iframe_win.height);
//				if(iframe_win.height<iframe_win.contentDocument.body.offsetHeight)
//				{
					iframe_win.height = iframe_win.contentDocument.body.offsetHeight + 40;
					iframe_frameaif.height = parent.document.body.offsetHeight;
//				}
			}
		}
	}
}

/**
 * 单个Iframe自适应高度
 * 
 * @param statu
 * @return
 */
function setframeifHeight(statu) {

	// 获取index页iframe
	var iframe_frameaif = parent.$("#frameaif").get(0);
	// 火狐
	var isFF = navigator.userAgent.toLowerCase().indexOf('firefox');
	// 谷歌
	var isChrome = navigator.userAgent.toLowerCase().indexOf('chrome');
	// IE
	var isIE = navigator.userAgent.toLowerCase().indexOf('msie');
	// 苹果
	var isSafari = navigator.userAgent.toLowerCase().indexOf('safari');

	iframe_frameaif.height = "0px";

//	 alert("clientHeight:"+iframe_frameaif.contentDocument.body.clientHeight);
//	 alert("offsetHeight:"+iframe_frameaif.contentDocument.body.offsetHeight);
//	 alert("scrollHeight:"+iframe_frameaif.contentDocument.body.scrollHeight);
	// alert("clientHeight:" + window.document.body.clientHeight);
	// alert("offsetHeight:" + window.document.body.offsetHeight);
	// alert("scrollHeight:" + window.document.body.scrollHeight);
	if (isIE > 0) {
		if (window.document.body.scrollHeight >= 620) {
			if (statu == "add") {
				iframe_frameaif.height = window.document.body.scrollHeight;
			} else {
				iframe_frameaif.height = window.document.body.scrollHeight;
			}
		} else {
			iframe_frameaif.height = 620;
		}
	} else if (isFF > 0) {
		if (iframe_frameaif.contentDocument.body.offsetHeight >= 620) {
			if (statu == "add") {
				iframe_frameaif.height = iframe_frameaif.contentDocument.body.scrollHeight;
			} else {
				iframe_frameaif.height = iframe_frameaif.contentDocument.body.offsetHeight + 20;
			}
		} else {
			iframe_frameaif.height = 620;
		}
	} else if (isChrome > 0) {
		if (iframe_frameaif.contentDocument.body.scrollHeight >= 620) {
			if (statu == "add") {
				// iframeHeight=iframe_frameaif.contentDocument.body.scrollHeight;
				iframe_frameaif.height = iframe_frameaif.contentDocument.body.scrollHeight + 5;

			} else {
				iframe_frameaif.height = iframe_frameaif.contentDocument.body.scrollHeight;
			}
		} else {
			iframe_frameaif.height = 620;
		}
	} else {
		if (iframe_frameaif.contentDocument.body.scrollHeight >= 620) {
			if (statu == "add") {
				iframe_frameaif.height = window.document.body.offsetHeight + 20;
			} else {
				iframe_frameaif.height = window.document.body.scrollHeight;
			}
		} else {
			iframe_frameaif.height = 620;
		}
	}
}

/**
 * JS隔行变色
 * 
 * @return
 */
$(document).ready(function() {
	//是否使用隔行变色  默认使用  如果不使用则在结果页面加id为 isNotUserJO 的隐藏域  value任意值
	if($("#isNotUserJO").val()==undefined){
		var tabl = $(".data");
		if (tabl.length > 0) {
			$(".data tr:nth-child(even)").css("backgroundColor", "#C7D8E2");
		}
		var tabT = $(".dataTable");
		if (tabT.length > 0) {

			$(".dataTable tr:nth-child(even)").css("backgroundColor", "#C7D8E2");
		}
	}
})

// 提示框
function boxyAlert(mes, callback) {
	parent.Boxy.alert(mes, callback, options = {
		unloadOnHide : true
	});
}

// 悬浮提示框
function susAlert(mes) {
	options = {
		closeable : false,
		unloadOnHide : true
	};
	options = parent.$.extend(null, options || {});
	var dialog = new parent.Boxy("<h3>" + mes + "</h3>", options);
	dialog.tween(200, 30);
	setTimeout(function() {
		dialog.hide();
		return false;
	}, 1500)
	return false;
}

/**
 * DIV取消按钮通用方法
 * 
 * @return
 */
function cancleDiv() {
	var str = parent.$(".boxy-modal-blackout").length;
	if(str!=1){
	// 父页面DIV层移除
	parent.$(".boxy-modal-blackout").eq(str-1).remove();
	// 父页面DIV遮罩移除
	parent.$(".boxy-wrapper").eq(str-1).remove();
	}else{
		// 父页面DIV层移除
		parent.$(".boxy-modal-blackout").eq(0).remove();
		// 父页面DIV遮罩移除
		parent.$(".boxy-wrapper").eq(0).remove();
	}
}

function cancleDiv2(p) {
	// 父页面DIV层移除
	parent.$(".boxy-modal-blackout").eq(p).remove();
	// 父页面DIV遮罩移除
	parent.$(".boxy-wrapper").eq(p).remove();
}

function alertResult(msg,index)
{
	var img="";
	if (msg != null && "" != msg) {
		if(msg.indexOf("失败")>0)
		{
			img="<img src='/bsnhmanage/common/Images/cha.png' border='0' align='absmiddle'/>" 
		}
		else
		{
			img="<img src='/bsnhmanage/common/Images/gou.png' border='0' align='absmiddle'/>";
		}
		parent.parent.susAlert(img+msg);
		if(index==null)
		{
			var frameaif=$("#frameaif",window.parent.parent.document);
			frameaif[0].contentWindow.document.forms[0].submit();
		}
	    if(index==0)
		{
			var frameaif=$("#frameaif",window.parent.document);
			frameaif[0].contentWindow.document.forms[0].submit();
			parent.$(".boxy-modal-blackout").eq(index).remove();
			parent.$(".boxy-wrapper").eq(index).remove();
		}
	    if(index==1)
		{
			var frameaif=$("#frameaif",window.parent.document);
			frameaif[0].contentWindow.document.forms[0].submit();
			parent.$(".boxy-modal-blackout").eq(index).remove();
			parent.$(".boxy-wrapper").eq(index).remove();
		}
		
	}
}

