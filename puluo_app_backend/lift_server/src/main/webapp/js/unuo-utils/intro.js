// $.cookie('dismissIntro', null);

// alert($.cookie('dismissIntro'));

var $indexIntroPic = 1;

var $introSwitch = true;

$(function(){
	var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/iPad/i)=="ipad" || ua.match(/iPhone/i)=="iphone") {
        return;
    }
	if ($.cookie('dismissIntro') != 'true') {
		// $.blockUI({ message: null });
		var introDiv = $("<div id='introDiv' class='modal-dialog' style='display: none; position: absolute; width: 700px;'>"
			+ "<div class='modal-content'>"
			+ "<div class='modal-header text-center'><h4 class='modal-title'>在线预订流程介绍</h4></div>"
			+ "<div class='modal-body'><img id='introPic' class='img img-responsive' border='0' src=''></div>"
			+ "<div class='modal-footer' style = 'text-align: center;'><div class='row' id='introIndexButtonsDiv'>"
			+ "<input type='button' class='btn btn-primary' value='上一张' onclick='changeIntroPicIndex(-1);'>"
			+ "<input title='introIndexButtons' type='button' class='btn btn-info' value='1' onclick='showIntroPicByIndex(1);'>"
			+ "<input title='introIndexButtons' type='button' class='btn btn-info' value='2' onclick='showIntroPicByIndex(2);'>"
			+ "<input title='introIndexButtons' type='button' class='btn btn-info' value='3' onclick='showIntroPicByIndex(3);'>"
			+ "<input title='introIndexButtons' type='button' class='btn btn-info' value='4' onclick='showIntroPicByIndex(4);'>"
			+ "<input title='introIndexButtons' type='button' class='btn btn-info' value='5' onclick='showIntroPicByIndex(5);'>"
			+ "<input type='button' class='btn btn-primary' value='下一张' onclick='changeIntroPicIndex(1);'>"
			+ "</div><br><div class='row form-group' id='closeIntroDiv'><input style='vertical-align:middle;' id='checkDismissIntro' type='checkbox'>勾选关闭后不再显示"
			+ "&nbsp&nbsp&nbsp&nbsp&nbsp<input type='button' class='btn btn-danger' value='关闭' onclick='closeIntroDiv();'></div>"
			+ "<div class='pull-right'><a href='/footers/shopping'>更多信息点击查看</a></div>"
			+ "</div></div></div>");
		introDiv.appendTo("body"); // 初始化
		showIntroPicByIndex($indexIntroPic);
		// $('#introDiv').show(); // 显示
		setIntroPosition(); // 设置窗口位置
	}
});

function setIntroPosition(){
	var introDivWidth = $("#introDiv").width();
	var introDivHeight = $("#introDiv").height();
	// alert(document.documentElement.clientWidth + ", " + introDivWidth + ", " + document.documentElement.clientHeight + ", " + introDivHeight);
	if (document.documentElement.clientWidth >= introDivWidth && document.documentElement.clientHeight >= introDivHeight) {
		var l = document.body.scrollLeft + document.documentElement.scrollLeft + 1/2 * (document.documentElement.clientWidth - introDivWidth);
		// alert(l);
		// var t = document.body.scrollTop + document.documentElement.scrollTop + 1/2 * (document.documentElement.clientHeight - introDivHeight);
		var t = document.body.scrollTop + document.documentElement.scrollTop + 3;
		// alert(t);
		document.getElementById("introDiv").style.left = l + "px"
		document.getElementById("introDiv").style.top = t + "px";
		if ($introSwitch) {
			$.blockUI({ message: null });
			$('#introDiv').show();
		} else { // 若已经关闭流程介绍，即便浏览器可视范围不小于窗口大小，仍隐藏窗口
			$.unblockUI();
			$('#introDiv').hide();
		}
	} else { // 当浏览器可视范围小于窗口大小时，隐藏窗口
		$.unblockUI();
		$('#introDiv').hide();
	}
}

function changeIntroPicIndex(m) {
	if (m<0) {
		if ($indexIntroPic!=1) {
			showIntroPicByIndex($indexIntroPic - 1);
		} else {
			alert("当前图片已经为首张了！");
		}
	} else {
		if ($indexIntroPic!=5) {
			showIntroPicByIndex($indexIntroPic + 1);
		} else {
			alert("当前图片已经为末张了！");
		}
	}
}

function showIntroPicByIndex(i) {
	$indexIntroPic = i;
	$('#introPic').attr('src', '/images/intro/0' + i +'.jpg');
	$('input[title=introIndexButtons]').attr('class', 'btn btn-info');
	$('input[title=introIndexButtons]:eq('+ (i-1) +')').attr('class', 'btn btn-danger');
}

function closeIntroDiv() {
	$.unblockUI();
	$('#introDiv').hide();
	if ($('#checkDismissIntro').is(':checked')) {
		$.cookie('dismissIntro', 'true');
	}
	$introSwitch = false;
	// alert($.cookie('dismissIntro'));
}