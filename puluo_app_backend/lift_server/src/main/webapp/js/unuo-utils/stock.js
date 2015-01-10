var stock_product_id = 0;

var stock_select_color = "0";

var stock_select_size = "0";

var stock_select_stock = 0;

var stock_payment_amount = 0;


function returnInfo() {
	return {
		product: String(stock_product_id),
		color: stock_select_color,
		size: stock_select_size,
		stock: String(stock_select_stock),
		amount: String($('#payment_amount').val())
	};
}

var ds = JSONStock.ds;

var sizes = new Array();

size_label: for (var i=0, j=ds.length; i<j; i++) {
	for (var m=0, n=sizes.length; m<n; m++) {
		if (sizes[m]==ds[i].s)
			continue size_label;
	}
	sizes[sizes.length]=ds[i].s;
}

var sizeButtons = "";

for (var i=0, j=sizes.length; i<j; i++) {
	sizeButtons += ("<button id='size_" + i + "' onclick=changeSize(" + i + ") class='unuo-btn-xs' style='color: black; border: 1px solid #bbbbbb;'>" + sizes[i] + "</button> ");
}

document.getElementById("stock_size").innerHTML=sizeButtons;

changeSize(0);

var colors = new Array();

color_label: for (var i=0, j=ds.length; i<j; i++) {
	for (var m=0, n=colors.length; m<n; m++) {
		if (colors[m]==ds[i].c)
			continue color_label;
	}
	colors[colors.length]=ds[i].c;
}
var colorButtons = "";

for (var i=0, j=colors.length; i<j; i++) {
	colorButtons += ("<button id='color_" + i + "' onclick=changeColor(" + i + ") class='unuo-btn-xs' style='color: black; border: 1px solid #bbbbbb;'>" + colors[i] + "</button> ");
}

document.getElementById("stock_color").innerHTML=colorButtons;

changeColor(0);

//	function checkNum() {
//		document.getElementById("select_amount").value = document.getElementById("payment_amount").value
//		var buyNum = document.getElementById("select_amount").value;
//		var stockNum = document.getElementById("select_stock").value;
//		if (isNaN(buyNum)) {
//			alert("请输入数字");
//		} else if (stockNum==0)	{
//			alert("");
//		} else if (buyNum<1 || buyNum>stockNum) {
//			alert("请输入正确的数量，且不超过库存的数量");
//		} else {
//			return;
//		}
//		return;
//	}

function changeColor(i) {
	stock_select_color = colors[i];
	for (var m=0, n=colors.length; m<n; m++) {
		if (m!=i) {
			document.getElementById('color_' + m).className = "unuo-btn-xs";
			document.getElementById('color_' + m).style.color = "black";
			document.getElementById('color_' + m).style.border = "1px solid #bbbbbb";
		}
		else {
			document.getElementById('color_' + m).className = "unuo-btn-xs active";
			document.getElementById('color_' + m).style.color = "red";
			document.getElementById('color_' + m).style.border = "1px solid #ff0000";
		}
	}
	checkStock();
}

function changeSize(i) {
	stock_select_size = sizes[i];
	for (var m=0, n=sizes.length; m<n; m++) {
		if (m!=i) {
			document.getElementById('size_' + m).className = "unuo-btn-xs";
			document.getElementById('size_' + m).style.color = "black";
			document.getElementById('size_' + m).style.border = "1px solid #bbbbbb";
		}
		else {
			document.getElementById('size_' + m).className = "unuo-btn-xs active";
			document.getElementById('size_' + m).style.color = "red";
			document.getElementById('size_' + m).style.border = "1px solid #ff0000";
		}
	}
	checkStock();
}

function checkStock() {
	if (stock_select_color!="" && stock_select_size!="") {
		for (var i=0, j=ds.length; i<j; i++) {
			if (stock_select_color==ds[i].c && stock_select_size==ds[i].s) {
				if (parseInt(ds[i].cnt) < 0) {
					document.getElementById("payment_stock").innerHTML = 0;
				} else {
					document.getElementById("payment_stock").innerHTML = ds[i].cnt;
				}
				stock_select_stock = ds[i].cnt;
				stock_product_id = ds[i].id;
			}
		}
	}
}

function amountPlus() {
	var buyNum = document.getElementById("payment_amount").value;
	var stockNum = stock_select_stock;
	if (stock_select_color!="" && stock_select_size!="") {
		if (isNaN(buyNum)) {
			document.getElementById("payment_amount").value = 0;
		} else if (parseInt(buyNum) >= parseInt(stockNum)) {
			document.getElementById("payment_amount").value = stockNum;
		} else if (parseInt(buyNum) < 0) {
			document.getElementById("payment_amount").value = 0;
		} else {
			document.getElementById("payment_amount").value = parseInt(buyNum) + 1;
		}
	} else if (stock_select_color!="") {
		alert("请先选择尺码");
	} else if (stock_select_size!="") {
		alert("请先选择颜色");
	} else {
		alert("请先选择颜色和尺码");
	}
}

function amountMinus() {
	var buyNum = document.getElementById("payment_amount").value;
	var stockNum = stock_select_stock;
	if (stock_select_color!="" && stock_select_size!="") {
		if (isNaN(buyNum)) {
			document.getElementById("payment_amount").value = 0;
		} else if (parseInt(buyNum) > parseInt(stockNum)) {
			document.getElementById("payment_amount").value = stockNum;
		} else if (parseInt(buyNum) <= 0) {
			document.getElementById("payment_amount").value = 0;
		} else {
			document.getElementById("payment_amount").value = parseInt(buyNum) - 1;
		}
	} else if (stock_select_color!="") {
		alert("请先选择尺码");
	} else if (stock_select_size!="") {
		alert("请先选择颜色");
	} else {
		alert("请先选择颜色和尺码");
	}
}