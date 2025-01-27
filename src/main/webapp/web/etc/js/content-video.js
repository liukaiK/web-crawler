$(document).ready(function() {
	$("#NewsShowConter_nav a:last").focus();
	$("#NewsShowConter_date div:eq(1)").css({
		"padding-top" : "10px"
	})
	var value = $.trim($("#artibody param").attr("value"));
	var url = value.substring(value.indexOf("=") + 1, value.indexOf("&"));
	var flashvars = {
			f : url,// 视频地址
			a : '',// 调用时的参数，只有当s>0的时候有效
			s : '0',// 调用方式，0=普通方法（f=视频地址），1=网址形式,2=xml形式，3=swf形式(s>0时f=网址，配合a来完成对地址的组装)
			c : '0',// 是否读取文本配置,0不是，1是
			x : '',// 调用配置文件路径，只有在c=1时使用。默认为空调用的是ckplayer.xml
			u : '',// 暂停时如果是图片的话，加个链接地址
			r : '',// 前置广告的链接地址，多个用竖线隔开，没有的留空
			y : '',// 这里是使用网址形式调用广告地址时使用，前提是要设置l的值为空
			e : '2',// 视频结束后的动作，0是调用js函数，1是循环播放，2是暂停播放并且不调用广告，3是调用视频推荐列表的插件，4是清除视频流并调用js功能和1差不多，5是暂停播放并且调用暂停广告
			v : '100',// 默认音量，0-100之间
			p : '0',// 视频默认0是暂停，1是播放，2是不加载视频
			h : '0',// 播放http视频流时采用何种拖动方法，=0不使用任意拖动，=1是使用按关键帧，=2是按时间点，=3是自动判断按什么(如果视频格式是.mp4就按关键帧，.flv就按关键时间)，=4也是自动判断(只要包含字符mp4就按mp4来，只要包含字符flv就按flv来)
			q : '',// 视频流拖动时参考函数，默认是start
			m : '',// 让该参数为一个链接地址时，单击播放器将跳转到该地址
			o : '',// 当p=2时，可以设置视频的时间，单位，秒
			w : '',// 当p=2时，可以设置视频的总字节数
			g : '',// 视频直接g秒开始播放
			j : '',// 跳过片尾功能，j>0则从播放多少时间后跳到结束，<0则总总时间-该值的绝对值时跳到结束
			k : '',// 提示点时间，如 30|60鼠标经过进度栏30秒，60秒会提示n指定的相应的文字
			n : '',// 提示点文字，跟k配合使用，如 提示点1|提示点2
			wh : '',// 宽高比，可以自己定义视频的宽高或宽高比如：wh:'4:3',或wh:'1080:720'
			lv : '0',// 是否是直播流，=1则锁定进度栏
	};
	var params = {
			bgcolor : '#FFF',
			allowFullScreen : true,
			allowScriptAccess : 'always'
	};// 这里定义播放器的其它参数如背景色（跟flashvars中的b不同），是否支持全屏，是否支持交互
	var video = [];
	CKobject.embed('ckplayer/ckplayer.swf', 'a1', 'ckplayer_a1', '600', '400', false, flashvars, video, params);
	

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
	
	var title = $("#NewsShowConter_date h1").text();
	
	$(document).attr("title", title);

})

document.onkeydown = function(event) {
	var e = event || window.event || arguments.callee.caller.arguments[0];
	var activeId = document.activeElement.id;
	if (e && e.keyCode == 32) { // 空格
		CKobject.getObjectById('ckplayer_a1').playOrPause();
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