$(document).ready(function() {
	$("#NewsShowConter_txt img").each(function() {
		$(this).css({
			'max-width' : '540px'
		});
	})

	$("#NewsShowConter_date div:eq(1)").css({
		"padding-top" : "10px"
	})
	
	

	if ($("embed").length < 1) {
		$("#bottom").css({
			"display" : "none"
		})
	}
	
	
	
	
	
	
	
	
	
	
	var title = $("#NewsShowConter_date span").text();
	$("#digest").text(title);
	
	document.getElementById('button1').onclick = function() {
		CKobject.getObjectById('ckplayer_a1').videoPlay();
	}
	document.getElementById('button2').onclick = function() {
		CKobject.getObjectById('ckplayer_a1').videoPause();
	}
	document.getElementById('button3').onclick = function() {
		CKobject.getObjectById('ckplayer_a1').fastNext();
	}
	document.getElementById('button4').onclick = function() {
		CKobject.getObjectById('ckplayer_a1').fastBack();
	}
	var url = "http://www.caacca.org/rdgz/jqrd/" + $("embed").attr("src").substring(2);

})

document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	var activeId = document.activeElement.id;
	if (e && e.keyCode == 32) { // 空格
		swfobject.getObjectById('ckplayer_a1').playOrPause();
		return false;
	}
	if (e && e.keyCode == 38) { // up
		var volume = CKobject.getObjectById('ckplayer_a1').getStatus().volume;
		CKobject.getObjectById('ckplayer_a1').changeVolume(volume + 10);
		return false;
	}
	if (e && e.keyCode == 40) { // down
		var volume = CKobject.getObjectById('ckplayer_a1').getStatus().volume;
		CKobject.getObjectById('ckplayer_a1').changeVolume(volume - 10);
		return false;
	}
	if (e && e.keyCode == 37) { // left
		CKobject.getObjectById('ckplayer_a1').fastBack();
		return false;
	}
	if (e && e.keyCode == 39) { // right
		CKobject.getObjectById('ckplayer_a1').fastNext();
		return false;
	}
}