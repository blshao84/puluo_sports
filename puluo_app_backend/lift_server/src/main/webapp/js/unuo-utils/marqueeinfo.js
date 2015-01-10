var marquee_index = 0;
var marquee_size = realtime_orders.length;
//var interval_tag = null;
$(function(){
	setInterval(showMarquee, "5000");
	/*var marqueeInfoDiv = $("<div id='marqueeinfo' style='position: absolute; height: 15px'></div>");
	marqueeInfoDiv.appendTo("body");
	setMarqueePosition();*/
});

function showMarquee(){
	var tmp = realtime_orders[marquee_index%marquee_size];
	if (tmp.n!=""){
		document.getElementById("marqueeinfo").innerHTML = tmp.d + " " + tmp.n + " " + " 通过 " + tmp.sp + " 成功购买 价格￥" + tmp.pr + " " + tmp.pn;
	} else {
		document.getElementById("marqueeinfo").innerHTML = tmp.d + " " + tmp.m + " " + " 通过 " + tmp.sp + " 成功购买 价格￥" + tmp.pr + " " + tmp.pn;
	}
	marquee_index++;
}

/*function setMarqueePosition(){
	var l = document.body.scrollLeft + document.documentElement.scrollLeft + document.documentElement.clientWidth / 2 * 0.2;
	var t = document.body.scrollTop + document.documentElement.scrollTop + document.documentElement.clientHeight - 20;
	document.getElementById("marqueeinfo").style.left = l + "px";
	document.getElementById("marqueeinfo").style.top = t + "px";
	if(interval_tag!=null){
		clearTimeout(interval_tag);
	}
	interval_tag = setInterval(showMarquee, "5000"); //5s
}*/