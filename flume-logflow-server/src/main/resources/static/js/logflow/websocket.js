var websocket = null;
var retryMaxCount = 5;
var uuid = UUID.generate();

// 判断当前浏览器是否支持WebSocket
function connectWebSocket() {
	var serviceName = $("#hide-services").val();
	if ('WebSocket' in window) {
		var uri = "ws://localhost:8080/websocket/" + uuid;
		if (serviceName.trim()) {
			uri += ("/" + serviceName);
		} else {
			uri += ("/ALL");
		}
		websocket = new WebSocket(uri);
		console.log("connect to:" + uri);
	} else {
		alert('Not support websocket')
	}

	// 连接成功建立的回调方法
	websocket.onopen = function(event) {
		retryMaxCount = 5;
		console.log("websocket connected!");
	}

	// 连接发生错误的回调方法
	websocket.onerror = function() {
		if (retryMaxCount > 0) {
			--retryMaxCount;
			connectWebSocket();
		}
	};

	// 连接关闭的回调方法
	websocket.onclose = function() {
		console.log("connect close!");
	}

	// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function() {
		websocket.close();
	}

	// 接收到消息的回调方法
	websocket.onmessage = function(event) {
		var $item = $(makeWsRow(event.data));
		$('.vertical-list').prepend($item).isotope('prepended',$item);
		$('.vertical-list').isotope('layout');
		loadContentEvent($item);
		//console.log(event.data);
	}
}

// 关闭连接
function closeWebSocket() {
	websocket.close();
}

// 发送消息
/*
 * function send() { var message = document.getElementById('text').value;
 * websocket.send(message); }
 */

// 将消息显示在网页上
/*
 * function setMessageInnerHTML(innerHTML) {
 * document.getElementById('message').innerHTML += innerHTML + '<br/>'; }
 */
