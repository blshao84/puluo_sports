$(function(){
	var QQChatDiv = $("<div class='hidden-xs' id='qqchat' style='position: absolute;'>"
		+ "<a target='_blank' href='http://wpa.qq.com/msgrd?v=3&uin=2250634736&site=qq&menu=yes'>"
		+ "<img class='img img-responsive' style='width:80px;' border='0' src='/images/wpa_style_53.gif' alt='在线咨询' title='在线咨询'>"
		+ "</a>"
		+ "</div>");
	/*QQChatDiv.attr("id", "qqchat");
	QQChatDiv.removeClass();
	QQChatDiv.css({"position": "absolute"});*/
	QQChatDiv.appendTo("body"); // 生成QQ在线聊天的DIV
	setQQChatPosition(); // 设置QQ在线聊天DIV的位置 
});

// QQ在线聊天
function setQQChatPosition(){
	// var qqchatWidth = $("#qqchat").css("width").slice(0, $("#qqchat").css("width").length-2);
	// var qqchatHeight = $("#qqchat").css("height").slice(0, $("#qqchat").css("height").length-2);
	var qqchatWidth = $("#qqchat").width();
	var qqchatHeight = $("#qqchat").height();
	var l = document.body.scrollLeft + document.documentElement.scrollLeft + document.documentElement.clientWidth - parseInt(qqchatWidth);
	var t = document.body.scrollTop + document.documentElement.scrollTop + 1/2 * (document.documentElement.clientHeight - parseInt(qqchatHeight));
	document.getElementById("qqchat").style.left = l + "px";
	document.getElementById("qqchat").style.top = t + "px";
}

//以下注释掉关闭左右两边side bar

// $(function(){
	// var leftSideBarDiv = $("<div class='hidden-xs' id='leftSideBar' style='position: absolute; z-index:9999; width:108px;'>"
	// var leftSideBarDiv = $("<div class='hidden-xs' id='leftSideBar' style='position: absolute; width:108px;'>"
	// 	+ "<div>" +
	// 		"<div style='float:left;width:90px;' id='leftSideBarContent'>" +
	// 		"<label style='background-color:#3D3D3D;height:15px;width:90px;padding-bottom:0px;margin-bottom:-6px;'></label>" +
	// 		"<a href='http://www.dqxmt.com/search?key=%E6%B2%99%E7%A7%80'>" +
	// 			"<img style='border: 1px black solid' class='img-responsive' src='/images/brands/shaxiu.jpg'>" +
	// 		"</a>" +
	// 		"<a href='http://www.dqxmt.com/search?key=%E8%94%93%E5%93%88%E9%A1%BF'>" +
	// 			"<img style='border: 1px black solid' class='img-responsive' src='/images/brands/manhadun.jpg'>" +
	// 		"</a>" +
	// 		"<a href='http://www.dqxmt.com/search?key=%E6%88%B4%E6%A3%AE'>" +
	// 			"<img style='border: 1px black solid' class='img-responsive' src='/images/brands/dyson.png'>" +
	// 		"</a>" +
	// 		"<a href='http://www.dqxmt.com/search?key=%E9%87%91%E5%88%A9%E6%9D%A5'>" +
	// 			"<img style='border: 1px black solid' class='img-responsive' src='/images/brands/jinlilai.jpg'>" +
	// 		"</a>" +
	// 		"<a href='http://www.dqxmt.com/search?key=%E4%B8%9C%E6%96%B9%E5%88%BA%E7%BB%A3'>" +
	// 			"<img style='border: 1px black solid' class='img-responsive' src='/images/brands/dongfangcixiu.jpg'>" +
	// 		"</a>" +
	// 		"<a href='http://www.dqxmt.com/search?key=%E6%A2%85%E5%B0%94%E4%BB%A3%E6%A0%BC'>" +
	// 			"<img style='border: 1px black solid' class='img-responsive' src='/images/brands/meierdaige.jpg'>" +
	// 		"</a>" +
	// 		"<a href='http://www.dqxmt.com/search?key=%E4%BC%8A%E5%BA%A6'>" +
	// 			"<img style='border: 1px black solid' class='img-responsive' src='/images/brands/yidu.jpg'>" +
	// 		"</a>" +
	// 		"<a href='http://www.dqxmt.com/search?key=%E8%A1%A3%E6%81%8B'>" +
	// 			"<img style='border: 1px black solid' class='img-responsive' src='/images/brands/yilian.jpg'>" +
	// 		"</a>" +
	// 		"<label style='background-color:#3D3D3D;height:15px;width:90px;padding-top:0px;margin-top:0px;'></label>" +
	// 		"</div>" +
	// 		"<a href='#' onclick='controlLeftSideBar();return false;'>" +
	// 			"<img style='float:right;' id='left_side_bar_image' src='/images/left-black.png'>" +
	// 		"</a>" +
	// 	"</div>"
	// 	+ "</div>");
	// leftSideBarDiv.appendTo("body"); // 生成左侧栏的DIV
	// setLeftSideBarPosition(); // 设置左侧栏的位置

	// var rightSideBarDiv = $("<div id='rightSideBar' style='position: absolute; z-index:9999; width:120px; border: 1px solid'>"
	// 	+ "<div><a href='#' onclick='controlRightSideBar();return false;'><img style='float:left;width:20px;' id='right_side_bar_image' src='/images/right.png'></a><div style='float:right;width:90px;' id='rightSideBarContent'>内容待定</div></div>"
	// 	+ "</div>");
	// rightSideBarDiv.appendTo("body"); // 生成右侧栏的DIV
	// setRightSideBarPosition(); // 设置右侧栏的位置
// });

// Left Side Bar
/*
var $left_side_bar_close_flag = false;

function controlLeftSideBar() {
	if ($left_side_bar_close_flag) {
		$left_side_bar_close_flag = false;
		$("#left_side_bar_image").attr("src", "/images/left-black.png");
		$("#leftSideBarContent").css({"display": ""});
		$("#leftSideBar").css({"width": "108px"});
	} else {
		$left_side_bar_close_flag = true;
		$("#left_side_bar_image").attr("src", "/images/right-black.png");
		$("#leftSideBarContent").css({"display": "none"});
		$("#leftSideBar").css({"width": "18px"});
	}
	setLeftSideBarPosition();
}

function setLeftSideBarPosition(){
	var l = document.body.scrollLeft + document.documentElement.scrollLeft;
	var t = document.body.scrollTop + document.documentElement.scrollTop + document.documentElement.clientHeight/6;
	document.getElementById("leftSideBar").style.left = l + "px";
	document.getElementById("leftSideBar").style.top = t + "px";
}
*/
// Right Side Bar
/*
var $right_side_bar_close_flag = false;

function controlRightSideBar() {
	if ($right_side_bar_close_flag) {
		$right_side_bar_close_flag = false;
		$("#right_side_bar_image").attr("src", "/images/right.png");
		$("#rightSideBarContent").css({"display": ""});
		$("#rightSideBar").css({"width": "120px"});
	} else {
		$right_side_bar_close_flag = true;
		$("#right_side_bar_image").attr("src", "/images/left.png");
		$("#rightSideBarContent").css({"display": "none"});
		$("#rightSideBar").css({"width": "20px"});
	}
	setRightSideBarPosition();
}

function setRightSideBarPosition(){
	var rightSideBarWidth = $("#rightSideBar").css("width").slice(0, $("#rightSideBar").css("width").length-2);
	var l = document.body.scrollLeft + document.documentElement.scrollLeft + document.documentElement.clientWidth - parseInt(rightSideBarWidth);
	var t = document.body.scrollTop + document.documentElement.scrollTop + document.documentElement.clientHeight/6;
	document.getElementById("rightSideBar").style.left = l + "px";
	document.getElementById("rightSideBar").style.top = t + "px";
}
*/
