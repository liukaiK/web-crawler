
(
	function () {
	if (!window.ESD)
		window.ESD = {};
	function loading(func) {
		if (typeof func != 'function')
			return false;
		if (window.attachEvent)
			window.attachEvent("onload", func);
		else
			window.addEventListener("load", func, false);
	}
	window['ESD']['loading'] = loading;
})();

(function () {
	if (!window.ESD)
		return false;

	function $(id) {
		if (!document.getElementById(id))
			return false;
		else
			return document.getElementById(id);
	}
	window['ESD']['$'] = $;

	function addEvent(type, node, fn) {
		if (node.nodeType != 1 && node != document)
			return false;
		if (window.ActiveXObject) {
			node['e' + type + fn] = fn;
			node[type + fn] = function () {
				node['e' + type + fn](window.event);
				return false;
			}
			node.attachEvent('on' + type, node[type + fn]);
		} else {
			node.addEventListener(type, fn, false);
		}
	}
	window['ESD']['addEvent'] = addEvent;

	function removeEvent(type, node, fn) {
		if (node.nodeType != 1 && node != document)
			return false;
		if (window.ActiveXObject) {
			node.detachEvent("on" + type, node[type + fn]);
		} else if (node.removeEventListener) {
			node.removeEventListener(type, fn, false);
		}
	}
	window['ESD']['removeEvent'] = removeEvent;

	function $$(tag, parent) {
		if (parent) {
			if (parent.getElementsByTagName(tag)[0])
				return parent.getElementsByTagName(tag);
		} else if (document.getElementsByTagName(tag)[0])
			return document.getElementsByTagName(tag);
		else
			return false;
	}
	window['ESD']['$$'] = $$;

	function stopPropagation(e) {
		e = getEventObject(e);
		if (e.stopPropagetion) {
			e.stopPropagetion();
		} else {
			e.cancelBubble = true;
		}
	}
	window['ESD']['stopPropagation'] = stopPropagation;

	function preventDefault(e) {
		if (e.preventDefault) {
			e.preventDefault();
		} else {
			return false;
		}
	}
	window['ESD']['preventDefault'] = preventDefault;
})();

(function () {

	var _this;
	function showImg() {
		_this = this;
		this.div = ESD.$('showImg_1');
		this.arrA = ESD.$$('a', this.div);
		this.img = ESD.$$('img', this.div)[0];
		this.a = ESD.$('maxImg1');
		this.p = ESD.$$('p', this.div)[0];
		this.li = ESD.$$('li', this.div)[0];
		this.addEvent();
		this.setTimes();

	}
	showImg.prototype = {
		j : 1,
		addEvent : function () {
			for (var i = 1; i < this.arrA.length; i++) {
//				ESD.addEvent('click', this.arrA[i], this.show);
				ESD.addEvent('focus', this.arrA[i], this.show);
			}
		},
		show : function (e) {
			e = e || window.event;
			this.id ? clearTimeout(this.id) : '';
			var src = this.getAttribute('rel'),
			con = this.getAttribute('con'),
			href = this.getAttribute('href');
			ESD.preventDefault(e);
			_this.append(this, src, con, href);
		},
		setTimes : function () {
			this.id ? clearTimeout(this.id) : '';
			var src = this.arrA[this.j].getAttribute('rel'),
			con = this.arrA[this.j].getAttribute('con');
			this.append(this.arrA[this.j], src, con, this.arrA[this.j++].href);
			this.id = setTimeout((function (t) {
						return function () {
							t.setTimes();
						}
					})(this), 5000);
		},
		append : function (a, src, con, href) {

			if (this.j > 4)
				this.j = 1;
			_this.li.className = '';
			_this.img.setAttribute('src', src);
			_this.img.setAttribute('title', con);
			_this.a.setAttribute('href', href);
			_this.a.setAttribute('title', con);
			a.setAttribute('title', con);
			a.parentNode.className = 'color';
			_this.p.innerHTML = '<span></span>' + '<em>' + con + '</em>';
			a.className = 'color';
			_this.li = a.parentNode;
		}
	}
	window['ESD']['showImg'] = showImg;

})();

ESD.loading(function () {
	new ESD.showImg();
});
