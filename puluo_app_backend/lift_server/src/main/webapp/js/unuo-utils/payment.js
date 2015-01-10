var shippingSting = "线上支付，邮寄到家";

var addrId = 0;

var dateList = [];

var delivery = {
	"id" : addrId, // 在非邮寄状态和邮寄新地址未保存状态下，返回的是0
	"name" : "",
	"date" : "",
	"phone" : "",
	"msg" : ""
}

var addr_ri = 0;

var addr_ai = -1;

var data = {
	"id" : "",
	"p" : "",
	"c" : "",
	"a" : "",
	"n" : "",
	"m" : ""
};

/* 邮寄 BEGIN*/
function initAddress() {
	if (oc.sp!=shippingSting) return;
	var ri = addr_ri;
	var ai = addr_ai;
	var addresses = oc.as;
	var length = addresses.length;
	var address_div = "";
	/*if (document.documentElement.clientWidth >= 768) {*/
		for (var i = 0, j = length; i < j; i++) {
			address_div += "<div>";
			address_div += "<div class='row form-group'>";
			address_div += "<div class='col-xs-2' style='width:30px;'>";
			address_div += "<input name='address' type='radio'" + (i == ri ? " checked" : " onclick='setAddress(" + i + ", -1);initAddress();'") + ">";
			address_div += "</div>";
			address_div += "<div class='col-md-1 col-xs-10'>";
			address_div += "<a href='#' onclick='setAddress(" + (ri!=-1 ? ri : i) + ", " + i + ");initAddress();'>修改</a>/<a href='#' onclick='deleteAddress(" + i + ", " + ri + ")'>删除</a>";
			address_div += "</div>";
			address_div += "<div class='col-md-7 col-xs-12'>";
			if (i != ai) {
				address_div += "姓名：" + addresses[i].n + " / 电话：" + addresses[i].m + "<br>";
				address_div += "地址：" + addresses[i].p + addresses[i].c + addresses[i].a;
			} else {
				address_div += "<div class='row form-group'>";
				address_div += "<label class='col-md-2 control-label'>姓名：</label>";
				address_div += "<div class='col-md-4'>";
				address_div += "<input class='form-control' id='an' type='text' value='" + addresses[i].n + "'>";
				address_div += "</div>";
				address_div += "</div>";
				address_div += "<div class='row form-group'>";
				address_div += "<label class='col-md-2 control-label'>电话：</label>";
				address_div += "<div class='col-md-4'>";
				address_div += "<input class='form-control' id='am' type='text' value='" + addresses[i].m + "'>";
				address_div += "</div>";
				address_div += "</div>";
				address_div += "<div class='row form-group'>";
				address_div += "<label class='col-md-2 control-label'>省份：</label>";
				address_div += "<div class='col-md-4'>";
				address_div += "<input class='form-control' id='ap' type='text' value='" + addresses[i].p + "'>";
				address_div += "</div>";
				address_div += "</div>";
				address_div += "<div class='row form-group'>";
				address_div += "<label class='col-md-2 control-label'>城市：</label>";
				address_div += "<div class='col-md-4'>";
				address_div += "<input class='form-control' id='ac' type='text' value='" + addresses[i].c + "'>";
				address_div += "</div>";
				address_div += "</div>";
				address_div += "<div class='row form-group'>";
				address_div += "<label class='col-md-2 control-label'>详细地址：</label>";
				address_div += "<div class='col-md-4'>";
				address_div += "<input class='form-control' id='aa' type='text' value='" + addresses[i].a + "'>";
				address_div += "</div>";
				address_div += "</div>";
				address_div += "<input type='button' value='保存' onclick='updateAddress(" + i + ", " + ri + ")'> <input type='button' value='取消' onclick='setAddress(" + ri + ", -1);initAddress();'>";
			}
			address_div += "</div>";
			address_div += "</div>";
		}
		var flag = (length != 0 && ri != -1);
		address_div += "<div>";
		address_div += "<div class='row form-group'>";
		address_div += "<div class='col-xs-2' style='width:30px;'>";
		address_div += "<input name='address' type='radio'" + (flag ? " onclick='setAddress(-1, -1);initAddress();'" : " checked") + ">";
		address_div += "</div>";
		address_div += "<div class='col-md-1 col-xs-10'>";
		address_div += "<a href='#' " + (flag ? " onclick='setAddress(-1, -1);initAddress();'" : "") + ">增加地址</a>";
		address_div += "</div>";
		address_div += "<div class='col-md-7 col-xs-12'>";
		if (!flag) {
			address_div += "<div class='row form-group'>";
			address_div += "<label class='col-md-2 control-label'>姓名：</label>";
			address_div += "<div class='col-md-4'>";
			address_div += "<input class='form-control' id='an' type='text'>";
			address_div += "</div>";
			address_div += "</div>";
			address_div += "<div class='row form-group'>";
			address_div += "<label class='col-md-2 control-label'>电话：</label>";
			address_div += "<div class='col-md-4'>";
			address_div += "<input class='form-control' id='am' type='text'>";
			address_div += "</div>";
			address_div += "</div>";
			address_div += "<div class='row form-group'>";
			address_div += "<label class='col-md-2 control-label'>省份：</label>";
			address_div += "<div class='col-md-4'>";
			address_div += "<input class='form-control' id='ap' type='text'>";
			address_div += "</div>";
			address_div += "</div>";
			address_div += "<div class='row form-group'>";
			address_div += "<label class='col-md-2 control-label'>城市：</label>";
			address_div += "<div class='col-md-4'>";
			address_div += "<input class='form-control' id='ac' type='text'>";
			address_div += "</div>";
			address_div += "</div>";
			address_div += "<div class='row form-group'>";
			address_div += "<label class='col-md-2 control-label'>详细地址：</label>";
			address_div += "<div class='col-md-4'>";
			address_div += "<input class='form-control' id='aa' type='text'>";
			address_div += "</div>";
			address_div += "</div>";
			address_div += "<input type='button' value='保存' onclick='addAddress()'> <input type='button' value='取消' onclick='setAddress(0, -1);initAddress();'>";
		}
		address_div += "</div>";
		address_div += "</div>";
	/*} else {
		for (var i = 0, j = length; i < j; i++) {
			address_div += "<div>";
			address_div += "<div class='row form-group'>";
			address_div += "<div class='col-xs-1'>";
			address_div += "<input name='address' type='radio'" + (i == ri ? " checked" : " onclick='setAddress(" + i + ", -1);initAddress();'") + ">";
			address_div += "</div>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<a onclick='setAddress(" + (ri!=-1 ? ri : i) + ", " + i + ");initAddress();'>修改</a>/<a onclick='deleteAddress(" + i + ", " + ri + ")'>删除</a>";
			address_div += "</div>";
			if (i != ai) {
				address_div += "<div class='col-xs-11'>";
				address_div += "姓名: " + addresses[i].n + "; 电话：" + addresses[i].m + "<br>";
				address_div += "地址: " + addresses[i].p + "省" + addresses[i].c + "市" + addresses[i].a;
				address_div += "</div>";
			} else {
				address_div += "<div class='col-xs-11'>";
				address_div += "<strong>姓名：</strong>";
				address_div += "</div>";
				address_div += "<div class='col-xs-11'>";
				address_div += "<input class='form-control' id='an' type='text' value='" + addresses[i].n + "'>";
				address_div += "</div>";
				address_div += "<div class='col-xs-11'>";
				address_div += "<strong>电话：</strong>";
				address_div += "</div>";
				address_div += "<div class='col-xs-11'>";
				address_div += "<input class='form-control' id='am' type='text' value='" + addresses[i].m + "'>";
				address_div += "</div>";
				address_div += "<div class='col-xs-11'>";
				address_div += "<strong>省份：</strong>";
				address_div += "</div>";
				address_div += "<div class='col-xs-11'>";
				address_div += "<input class='form-control' id='ap' type='text' value='" + addresses[i].p + "'>";
				address_div += "</div>";
				address_div += "<div class='col-xs-11'>";
				address_div += "<strong>城市：</strong>";
				address_div += "</div>";
				address_div += "<div class='col-xs-11'>";
				address_div += "<input class='form-control' id='ac' type='text' value='" + addresses[i].c + "'>";
				address_div += "</div>";
				address_div += "<div class='col-xs-11'>";
				address_div += "<strong>详细地址：</strong>";
				address_div += "</div>";
				address_div += "<div class='col-xs-11'>";
				address_div += "<input class='form-control' id='aa' type='text' value='" + addresses[i].a + "'>";
				address_div += "</div>";
				address_div += "<div class='col-xs-11'>";
				address_div += "<input type='button' value='保存' onclick='updateAddress(" + i + ", " + ri + ")'> <input type='button' value='取消' onclick='setAddress(" + ri + ", -1);initAddress();'>";
				address_div += "</div>";
			}
			address_div += "</div>";
			address_div += "</div>";
			address_div += "</div>";
		}
		var flag = (length != 0 && ri != -1);
		address_div += "<div>";
		address_div += "<div class='row form-group'>";
		address_div += "<div class='col-xs-1'>";
		address_div += "<input name='address' type='radio'" + (flag ? " onclick='setAddress(-1, -1);initAddress();'" : " checked") + ">";
		address_div += "</div>";
		address_div += "<div class='col-xs-11'>";
		address_div += "<div class='col-xs-11'>";
		address_div += "<a" + (flag ? " onclick='setAddress(-1, -1);initAddress();'" : "") + ">增加地址</a>";
		address_div += "</div>";
		if (!flag) {
			address_div += "<div class='col-xs-11'>";
			address_div += "<strong>姓名：</strong>";
			address_div += "</div>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<input class='form-control' id='an' type='text'>";
			address_div += "</div>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<strong>电话：</strong>";
			address_div += "</div>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<input class='form-control' id='am' type='text'>";
			address_div += "</div>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<strong>省份：</strong>";
			address_div += "</div>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<input class='form-control' id='ap' type='text'>";
			address_div += "</div>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<strong>城市：</strong>";
			address_div += "</div>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<input class='form-control' id='ac' type='text'>";
			address_div += "</div>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<strong>详细地址：</strong>";
			address_div += "</div>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<input class='form-control' id='aa' type='text'>";
			address_div += "</div>";
			address_div += "<div class='col-xs-11'>";
			address_div += "<input type='button' value='保存' onclick='addAddress()'> <input type='button' value='取消' onclick='setAddress(0, -1);initAddress();'>";
			address_div += "</div>";
		}
		address_div += "</div>";
		address_div += "</div>";
		address_div += "</div>";
	}*/
	document.getElementById("address_table").innerHTML = address_div;
	if (ri!=-1) {
		addrId = addresses[ri].id;
	} else {
		addrId = 0;
	}
	showJson();
}

function setAddress(ri, ai) {
	addr_ai = ai;
	addr_ri = ri;
}

function addAddress() {
	data.id = 0;
	data.p = document.getElementById("ap").value;
	data.c = document.getElementById("ac").value;
	data.a = document.getElementById("aa").value;
	data.n = document.getElementById("an").value;
	data.m = document.getElementById("am").value;
	var addrInfo = JSON.stringify(data);
	//alert(addrInfo); //对接后注释掉
	$.post("/user/address/add", {data: addrInfo}, function(json) {
		//alert(json); //对接后注释掉
		json = $.parseJSON(json);
		if (json.success) {	
			var newAddress = {
				"id" : json.addr,
				"p" : document.getElementById("ap").value,
				"c" : document.getElementById("ac").value,
				"a" : document.getElementById("aa").value,
				"n" : document.getElementById("an").value,
				"m" : document.getElementById("am").value
			}
			oc.as[oc.as.length] = newAddress;
			setAddress(oc.as.length - 1, -1);
			initAddress();
		} else {
			showSerInfo(json.msg);
		}
	});
}

function updateAddress(i, ri) {
	data.id = oc.as[i].id;
	data.p = document.getElementById("ap").value;
	data.c = document.getElementById("ac").value;
	data.a = document.getElementById("aa").value;
	data.n = document.getElementById("an").value;
	data.m = document.getElementById("am").value;
	var addrInfo = JSON.stringify(data);
	//alert(addrInfo); //对接后注释掉
	$.post("/user/address/update", {data: addrInfo}, function(json) {
		//alert(json); //对接后注释掉
		json = $.parseJSON(json);
		if (json.success) {	
			oc.as[i].p = document.getElementById("ap").value;
			oc.as[i].c = document.getElementById("ac").value;
			oc.as[i].a = document.getElementById("aa").value;
			oc.as[i].n = document.getElementById("an").value;
			oc.as[i].m = document.getElementById("am").value;
			setAddress(ri, -1);
			initAddress();
		} else {
			showSerInfo(json.msg);
		}
	});
}

function deleteAddress(i, ri) {
	data.id = oc.as[i].id;
	data.p = "";
	data.c = "";
	data.a = "";
	data.n = "";
	data.m = "";
	var addrInfo = JSON.stringify(data);
	//alert(addrInfo); //对接后注释掉
	$.post("/user/address/delete", {data: addrInfo}, function(json) {
		//alert(json); //对接后注释掉
		json = $.parseJSON(json);
		if (json.success) {
			oc.as.splice(i, 1);
			var ri_;
			if (i == ri) {
				ri_ = 0;
			} else if (i > ri) {
				ri_ = ri;
			} else {
				ri_ = ri - 1;
			}
			setAddress(ri_, -1);
			initAddress();
		} else {
			showSerInfo(json.msg);
		}
	});
}

function showSerInfo(msg) {
	alert(msg);
}

function showJson() {
	//document.getElementById("showJson").innerHTML = JSON.stringify(oc.as);
}
/* 邮寄 END*/

/*线上线下 BEGIN*/
function getDateList() {
	for (var i=0; i<7; i++) {
		dateList[i] = getDate(oc.date, i);
	}
}

function getDate(date, days){
	var o = new Date(date);
	o = o.valueOf();
	o = o + days * 24 * 60 * 60 * 1000;
	o = new Date(o);
	var y = o.getFullYear();
	var m = o.getMonth()+1;
	var d = o.getDate();
	var n = y + "-" + m + "-" + d;
	return n;
}

function initO2OInfo() {
	if (oc.sp==shippingSting) return;
	var o2oInfo = "";
	/*if (document.documentElement.clientWidth < 768) {
		o2oInfo += "<div>";
		o2oInfo += "<div class='col-xs-12'>";
		o2oInfo += "<strong>取货人姓名:</strong>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='col-xs-12'>";
		o2oInfo += "<input class='form-control' id='pickup_name' value='' type='text'>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='col-xs-12'>";
		o2oInfo += "<strong>联系电话:</strong>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='col-xs-12'>";
		o2oInfo += "<input class='form-control' id='pickup_mobile' value='' type='text'>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='col-xs-12'>";
		o2oInfo += "<h5>请您填写一个有效手机号码方便接收提货短信</h5>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='col-xs-12'>";
		o2oInfo += "<strong>提货日期:</strong>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='col-xs-12'>";
		o2oInfo += "<select class='form-control' id='pickup_date'><option value=''></option>";
		for (var i=0, j=dateList.length; i<j; i++) {
			o2oInfo += "<option value='" + dateList[i] + "'>" + dateList[i] + "</option>";
		}
		o2oInfo += "</select>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='col-xs-12'>";
		o2oInfo += "<h5>请您指定一个预计提货日期方便商场按时备货</h5>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='col-xs-12'>";
		o2oInfo += "<strong>备注:</strong>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='col-xs-12'>";
		o2oInfo += "<input id='pickup_comments' class='form-control'>";
		o2oInfo += "</div>";
		o2oInfo += "</div>";
	} else {*/
		o2oInfo += "<div>";
		o2oInfo += "<div class='row form-group'>";
		o2oInfo += "<label class='col-md-1 control-label'>姓名:</label>";
		o2oInfo += "<div class='col-md-2'>";
		o2oInfo += "<input class='form-control' id='pickup_name' value='' type='text'>";
		o2oInfo += "</div>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='row form-group'>";
		o2oInfo += "<label class='col-md-1 control-label'>联系电话:</label>";
		o2oInfo += "<div class='col-md-2'>";
		o2oInfo += "<input class='form-control' id='pickup_mobile' value='''>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='col-md-6'>";
		o2oInfo += "<h5>请您填写一个有效手机号码方便接收提货短信</h5>";
		o2oInfo += "</div>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='row form-group'>";
		o2oInfo += "<label class='col-md-1 control-label'>提货日期:</label>";
		o2oInfo += "<div class='col-md-2'>";
		o2oInfo += "<select class='form-control' id='pickup_date'><option value=''></option>";
		for (var i=0, j=dateList.length; i<j; i++) {
			o2oInfo += "<option value='" + dateList[i] + "'>" + dateList[i] + "</option>";
		}
		o2oInfo += "</select>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='col-md-6'>";
		o2oInfo += "<h5>请您指定一个预计提货日期方便商场按时备货</h5>";
		o2oInfo += "</div>";
		o2oInfo += "</div>";
		o2oInfo += "<div class='row form-group'>";
		o2oInfo += "<label class='col-md-1 control-label'>备注:</label>";
		o2oInfo += "<div class='col-md-4'>";
		o2oInfo += "<input id='pickup_comments' class='form-control'>";
		o2oInfo += "</div>";
		o2oInfo += "</div>";
		o2oInfo += "</div>";
	/*}*/
	document.getElementById("address_table").innerHTML = o2oInfo;
}
/*线上线下 END*/

document.getElementById("payment_header").innerHTML = "收货方式：" + oc.sp;

if (oc.sp!=shippingSting) {
	getDateList();
	initO2OInfo();
} else {
	initAddress();
}

//window.addEventListener("resize", initO2OInfo);

//window.addEventListener("resize", initAddress);

function getResponse() {
	if (oc.sp!=shippingSting) {
		return {
			"as" : 0, // 在非邮寄状态和邮寄新地址未保存状态下，返回的是0
			"name" : document.getElementById("pickup_name").value,
			"date" : document.getElementById("pickup_date").value,
			"phone" : document.getElementById("pickup_mobile").value,
			"msg" :  document.getElementById("pickup_comments").value
		};
	} else {
		return {
			"as" : addrId, // 在非邮寄状态和邮寄新地址未保存状态下，返回的是0
			"name" : "",
			"date" : "",
			"phone" : "",
			"msg" : ""
		};
	}
}