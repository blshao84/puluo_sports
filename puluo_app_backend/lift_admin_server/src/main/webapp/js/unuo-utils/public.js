function resizeEvents(){
	setQQChatPosition();
	if ($("#introDiv").length>0) { // 长度大于0时，代表该对象存在
		setIntroPosition();
	}
	// setLeftSideBarPosition();
	// setRightSideBarPosition();
	// setMarqueePosition();
	// if (more_sizes_flag) {
	// 	more_sizes_flag = false;
	// } else {
	// 	more_sizes_flag = true;
	// }
	showMoreSizes();
}

function scrollEvents(){
	setQQChatPosition();
	if ($("#introDiv").length>0) {
		setIntroPosition();
	}
	// setLeftSideBarPosition();
	// setRightSideBarPosition();
	scrollTracking();
}

$(window).scroll(function(){scrollEvents();});
$(window).resize(function(){resizeEvents();});

/* 这以下是tracking相关 公共部分 开始 */
var trackingInfo = {
	p: getURL(), //所属页面
	a: "", //追踪内容
	at: "" //用户行为
};

function getURL() {
	var fullURL = window.location.href;
	var index_of_path = fullURL.indexOf("/", "http://".length);
	return fullURL.substring(index_of_path);
}

function getImageFileName(source_link) {
	return source_link.substring(source_link.lastIndexOf("/")+1);
}

function submitTracking() {
	var trackingJson = JSON.stringify(trackingInfo);
	//alert(trackingJson); //对接后注释掉
	$.post("/user/tracking", {data: trackingJson}, function() {
		return;
	});
}
/* 这以下是tracking相关 公共部分 结束 */

var $scrollTop_base = 0;

function scrollTracking() {
	var $scroll_range = Math.abs(document.body.scrollTop + document.documentElement.scrollTop - $scrollTop_base) - document.documentElement.clientHeight * 3 / 2;
	if ($scroll_range > 0) {
		//alert(document.body.scrollTop + document.documentElement.scrollTop);
		//alert($scrollTop_base);
		$scrollTop_base = document.body.scrollTop + document.documentElement.scrollTop;
		trackingInfo.a = "scroll: 滚动范围超过半屏";
		trackingInfo.at = "scroll";
		submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
	}
}

$(document).ready(function (){
	$scrollTop_base =  document.body.scrollTop + document.documentElement.scrollTop;
});