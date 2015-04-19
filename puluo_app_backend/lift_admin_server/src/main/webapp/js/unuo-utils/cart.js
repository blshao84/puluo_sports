function initCart() {
	/*if (document.documentElement.clientWidth > 768) {*/
		var table_start = "<table class='col-md-12 table table-striped' id='table'>"
						+ "<tr align='center'>"
						+ "<td>选择</td>"
						+ "<td colspan='2'>商品</td>"
						+ "<td colspan='2'>商品信息</td>"
						+ "<td >收货方式</td>"
						+ "<td >单价</td>"
						+ "<td>数量</td>"
						+ "<td>小计</td>"
						//+ "<td>操作</td>"
						+ "</tr>";
		var table_end = "</table>";
		var table_body = "";
		var orders = cart.os;
		var length = orders.length;
		if (length != 0) {
			table_body = table_body + table_start;
			var total_product = 0;
			var total_amount = 0;
			var sub_total_amount = 0;
			var stock = 0;
			for (var i=0, j=length; i<j; i++) {
				stock = checkStock(orders[i].st.ds, orders[i].sz, orders[i].clr);
				table_body = table_body + "<tr><td style='vertical-align:middle;' align='center'><input id='checkbox_of_" + i + "' type='checkbox' onclick=selectOrder(" + i + ")";
				sub_total_amount = parseFloat(orders[i].st.dv)*parseFloat(orders[i].q);
				if (orders[i].checkbox != "") {
					table_body = table_body + " checked";
					total_product += 1;
					total_amount += sub_total_amount;
				}
				table_body = table_body + "></td>";
				table_body = table_body + "<td><a href='/stock?id=" + orders[i].st.id + "'><img class='img img-responsive center-block' style='height:85px;' src='" + orders[i].i + "' /></a></td><td style='vertical-align:middle;' align='left'>" + orders[i].st.prod.b + " " + orders[i].st.prod.n + "<br><a href='/stock?id=" + orders[i].st.id + "'>查看</a></td>";
				table_body = table_body + "<td style='vertical-align:middle;' align='left'>尺码：" + orders[i].sz + "<br>颜色：" + orders[i].clr + "</td><td style='vertical-align:middle;' align='left'><a href='#' onclick=openDialog(" + i + ")>修改</a></td><td style='vertical-align:middle;' align='center'>";
				var payments = orders[i].st.payments;
				for (var a=0, b=payments.length; a<b; a++) {
					if (a!=0) {
						table_body += "<br>";
					}
					table_body += payments[a];
				}
				table_body = table_body + "</td><td style='vertical-align:middle;' align='left'>原价：<del>￥" + parseFloat(orders[i].st.v).toFixed(2) + "</del><br>";
				if (parseFloat(orders[i].pr)!=parseFloat(orders[i].st.dv)) {
					table_body = table_body + "<strong><font color='red'>现价：￥" + parseFloat(orders[i].st.dv).toFixed(2) + "</font></strong>";
				} else {
					table_body = table_body + "现价：￥" + parseFloat(orders[i].st.dv).toFixed(2);
				}
				table_body = table_body + "</td>";
				table_body = table_body + "<td style='vertical-align:middle;' align='center'>";
				// table_body = table_body + "<button style='width:25px;' onclick=amountMinus(" + i + ") disabled='true'><b>-</b></button>";
				// table_body = table_body + "<input style='width:40px;text-align:center' id='quantity_of_" + i + "' value='" + orders[i].q + "' onkeyup=changeAmount(" + i + ") readonly='true'>";
				table_body = table_body + "<input type='hidden' id='quantity_of_" + i + "' value='" + orders[i].q + "'>" + orders[i].q + "件";
				// table_body = table_body + "<button style='width:25px;' onclick=amountPlus(" +i + ") disabled='true'><b>+</b></button>";
				table_body = table_body + "<br>（剩余" + stock + "件）</td>";
				table_body = table_body + "<td style='vertical-align:middle;' align='center'>￥<span id='amount_of_" + i + "'>" + parseFloat(sub_total_amount).toFixed(2) + "</span></td>";
				//table_body = table_body + "<td align='center'><button onclick=moveOrderById(" + i + ")>移入收藏夹</button><br><button onclick=removeOrderById(" + i + ")>移出购物车</button></td>";
				table_body = table_body + "</tr>";
			}
			//table_body = table_body + "<tr><td align='center'><input id='selectOrders' type='checkbox' onclick=selectOrders()></td><td colspan='2' align='center'><a href='#' onclick=moveOrders()>移入收藏夹</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href='#' onclick=removeOrders()>移出购物车</a></td><td colspan='2' align='center'>已选<span id='total_product'>" + total_product + "</span>件商品</td><td colspan='4' align='right'>总计（不含运费）：￥<span id='total_amount'>" + parseFloat(total_amount).toFixed(2) + "</span></td>"
			if (cart.sp_ == undefined) {
				cart.sp_ = 1;
			}
			table_body = table_body + "<tr><td align='center'><input id='selectOrders' type='checkbox' onclick=selectOrders()></td><td colspan='2' align='center'><a href='#' onclick=moveOrders()>移入收藏夹</a>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<a href='#' onclick=removeOrders()>移出购物车</a></td><td colspan='2' align='center'>已选<span id='total_product'>" + total_product + "</span>件商品</td><td colspan='4' align='right'>配送方式：<input name='sp' type='radio' value='" + cart.sp[0] + "' onclick=changeShipping(0)" + (cart.sp_==0 ? ' checked' : '') + ">" + cart.sp[0] + "</input>&nbsp|&nbsp<input name='sp' type='radio' value='" + cart.sp[1] + "' onclick=changeShipping(1)" + (cart.sp_==1 ? ' checked' : '') + ">" + cart.sp[1] + "</input>&nbsp|&nbsp<input name='sp' type='radio' value='" + cart.sp[2] + "' onclick=changeShipping(2)" + (cart.sp_==2 ? ' checked' : '') + ">" + cart.sp[2] + "</input></td>"
			//table_body = table_body + "<tr><td colspan='9' align='right'>配送方式：<input name='sp' type='radio' value='" + cart.sp[0] + "' onclick=changeShipping(0)" + (cart.sp_==0 ? ' checked' : '') + ">" + cart.sp[0] + "</input>&nbsp|&nbsp<input name='sp' type='radio' value='" + cart.sp[1] + "' onclick=changeShipping(1)" + (cart.sp_==1 ? ' checked' : '') + ">" + cart.sp[1] + "</input>&nbsp|&nbsp<input name='sp' type='radio' value='" + cart.sp[2] + "' onclick=changeShipping(2)" + (cart.sp_==2 ? ' checked' : '') + ">" + cart.sp[2] + "</input></td></tr>";
			table_body = table_body + "<tr><td colspan='9' align='right'>总计：￥<span id='total_amount'>" + parseFloat(total_amount).toFixed(2) + "</span>（包含运费：￥<span id='sp'>" + getShippingFee(total_product, total_amount) + "</span>）</td>"
			//table_body = table_body + "<tr><td colspan='3' align='left'><button>继续购物</button></td><td colspan='6' align='right'><button>结算支付</button></td>"
			table_body = table_body + table_end;
			document.getElementById("cart_table").innerHTML = table_body;
			if (total_product==length) {
				document.getElementById('selectOrders').checked = true;
			}
		} else {
			document.getElementById("cart_table").innerHTML = "空购物车，继续购物";
		}
	/*} else {
		var orders = cart.os;
		var length = orders.length;
		if (length != 0) {
			var total_product = 0;
			var total_amount = 0;
			var sub_total_amount = 0;
			var stock = 0;
			var div_body = "";
			for (var i=0, j=length; i<j; i++) {
				stock = checkStock(orders[i].st.ds, orders[i].sz, orders[i].clr);
				sub_total_amount = parseFloat(orders[i].st.dv)*parseFloat(orders[i].q);
				
				div_body += "<div>";
				div_body += "<div>";
				div_body += "<div class='row form-group'>";
				div_body += "<div class='col-xs-4'>";
				div_body += "<strong>选择:</strong>";
				div_body += "</div>";
				div_body += "<div class='col-xs-8'>";
				div_body += "<input id='checkbox_of_" + i + "' type='checkbox' onclick=selectOrder(" + i + ")";
				if (orders[i].checkbox != "") {
					div_body += " checked>";
					total_product += 1;
					total_amount += sub_total_amount;
				} else {
					div_body += ">";
				}
				div_body += "</div>";
				div_body += "</div>";
				div_body += "</div>";
				
				div_body += "<div>";
				div_body += "<div>";
				div_body += "<div class='row form-group'>";
				div_body += "<div class='col-xs-4'>";
				div_body += "<strong>商品:</strong>";
				div_body += "</div>";
				div_body += "<div class='col-xs-8'>";
				div_body += "<img class='stock_link_img img-responsive' src='" + orders[i].i + "' /><br>";
				div_body += "<a class='stock_link' href='/stock?id=5'>" + orders[i].st.prod.b + "<br>";
				div_body += "<a class='stock_link' href='/stock?id=5'>" + orders[i].st.prod.n + "</a>";
				div_body += "</div>";
				div_body += "</div>";
				div_body += "</div>";
				
				div_body += "<div>";
				div_body += "<div>";
				div_body += "<div class='row form-group'>";
				div_body += "<div class='col-xs-4'>";
				div_body += "<strong>尺码:</strong>";
				div_body += "</div>";
				div_body += "<div class='col-xs-8'>";
				div_body += orders[i].sz;
				div_body += "</div>";
				div_body += "</div>";
				div_body += "</div>";
				
				div_body += "<div>";
				div_body += "<div>";
				div_body += "<div class='row form-group'>";
				div_body += "<div class='col-xs-4'>";
				div_body += "<strong>尺码:</strong>";
				div_body += "</div>";
				div_body += "<div class='col-xs-8'>";
				div_body += orders[i].clr;
				div_body += "</div>";
				div_body += "</div>";
				div_body += "</div>";
				
				div_body += "<div>";
				div_body += "<div>";
				div_body += "<div class='row form-group'>";
				div_body += "<div class='col-xs-4'>";
				div_body += "<strong> </strong>";
				div_body += "</div>";
				div_body += "<div class='col-xs-8'>";
				div_body += "<button onclick=openDialog(" + i + ")>修改</button>";
				div_body += "</div>";
				div_body += "</div>";
				div_body += "</div>";
				
				div_body += "<div>";
				div_body += "<div>";
				div_body += "<div class='row form-group'>";
				div_body += "<div class='col-xs-4'>";
				div_body += "<strong>购买方式:</strong>";
				div_body += "</div>";
				div_body += "<div class='col-xs-8'>";
				var payments = orders[i].st.payments;
				for (var a=0, b=payments.length; a<b; a++) {
					if (a!=0) {
						div_body += "<br>";
					}
					div_body += payments[a];
				}
				div_body += "</div>";
				div_body += "</div>";
				div_body += "</div>";

				div_body += "<div>";
				div_body += "<div>";
				div_body += "<div class='row form-group'>";
				div_body += "<div class='col-xs-4'>";
				div_body += "<strong>原价:</strong>";
				div_body += "</div>";
				div_body += "<div class='col-xs-8'>";
				div_body += "<del>￥" + parseFloat(orders[i].st.v).toFixed(2) + "</del>";
				div_body += "</div>";
				div_body += "</div>";
				div_body += "</div>";
				
				div_body += "<div>";
				div_body += "<div>";
				div_body += "<div class='row form-group'>";
				div_body += "<div class='col-xs-4'>";
				div_body += "<strong>现价:</strong>";
				div_body += "</div>";
				div_body += "<div class='col-xs-8'>";
				div_body += "￥" + parseFloat(orders[i].st.dv).toFixed(2);
				div_body += "</div>";
				div_body += "</div>";
				div_body += "</div>";
				
				div_body += "<div>";
				div_body += "<div>";
				div_body += "<div class='row form-group'>";
				div_body += "<div class='col-xs-4'>";
				div_body += "<strong>数量:</strong>";
				div_body += "</div>";
				div_body += "<div class='col-xs-8'>";
				div_body += "<button onclick=amountMinus(" + i + ")><b>-</b></button><input id='quantity_of_" + i + "' value='" + orders[i].q + "' onkeyup=changeAmount(" + i + ")><button onclick=amountPlus(" +i + ")><b>+</b></button>";
				div_body += "</div>";
				div_body += "</div>";
				div_body += "</div>";
				
				div_body += "<div>";
				div_body += "<div>";
				div_body += "<div class='row form-group'>";
				div_body += "<div class='col-xs-4'>";
				div_body += "<strong>库存:</strong>";
				div_body += "</div>";
				div_body += "<div class='col-xs-8'>";
				div_body += "剩余" + stock + "件";
				div_body += "</div>";
				div_body += "</div>";
				div_body += "</div>";
				
				div_body += "<div>";
				div_body += "<div>";
				div_body += "<div class='row form-group'>";
				div_body += "<div class='col-xs-4'>";
				div_body += "<strong>小计:</strong>";
				div_body += "</div>";
				div_body += "<div class='col-xs-8'>";
				div_body += "￥<span id='amount_of_" + i + "'>" + parseFloat(sub_total_amount).toFixed(2) + "</span>";
				div_body += "</div>";
				div_body += "</div>";
				div_body += "</div>";
			}
			
			div_body += "<div>";
			div_body += "<div>";
			div_body += "<div class='row form-group'>";
			div_body += "<div class='col-xs-4'>";
			div_body += "<strong>全选:</strong>";
			div_body += "</div>";
			div_body += "<div class='col-xs-8'>";
			div_body += "<input id='selectOrders' type='checkbox' onclick=selectOrders()>";
			div_body += "</div>";
			div_body += "</div>";
			div_body += "</div>";
			
			div_body += "<div>";
			div_body += "<div>";
			div_body += "<div class='row form-group'>";
			div_body += "<div class='col-xs-4'>";
			div_body += "<strong> </strong>";
			div_body += "</div>";
			div_body += "<div class='col-xs-8'>";
			div_body += "<button onclick=moveOrders()>移入收藏夹</button>";
			div_body += "</div>";
			div_body += "</div>";
			div_body += "</div>";
			
			div_body += "<div>";
			div_body += "<div>";
			div_body += "<div class='row form-group'>";
			div_body += "<div class='col-xs-4'>";
			div_body += "<strong> </strong>";
			div_body += "</div>";
			div_body += "<div class='col-xs-8'>";
			div_body += "<button onclick=removeOrders()>移出购物车</button>";
			div_body += "</div>";
			div_body += "</div>";
			div_body += "</div>";
			
			div_body += "<div>";
			div_body += "<div>";
			div_body += "<div class='row form-group'>";
			div_body += "<div class='col-xs-4'>";
			div_body += "<strong>已选择商品数:</strong>";
			div_body += "</div>";
			div_body += "<div class='col-xs-8'>";
			div_body += "<span id='total_product'>" + total_product + "</span>件";
			div_body += "</div>";
			div_body += "</div>";
			div_body += "</div>";
			
			div_body += "<div>";
			div_body += "<div>";
			div_body += "<div class='row form-group'>";
			div_body += "<div class='col-xs-4'>";
			div_body += "<strong>总计（不含运费）:</strong>";
			div_body += "</div>";
			div_body += "<div class='col-xs-8'>";
			div_body += "￥<span id='total_amount'>" + parseFloat(total_amount).toFixed(2) + "</span>";
			div_body += "</div>";
			div_body += "</div>";
			div_body += "</div>";
			
			if (cart.sp_ == undefined) {
				cart.sp_ = 1;
			}
			div_body += "<div>";
			div_body += "<div>";
			div_body += "<div class='row form-group'>";
			div_body += "<div class='col-xs-4'>";
			div_body += "<strong>配送方式:</strong>";
			div_body += "</div>";
			div_body += "<div class='col-xs-8'>";
			div_body += "<input name='sp' type='radio' value='" + cart.sp[0] + "' onclick=changeShipping(0)" + (cart.sp_==0 ? ' checked' : '') + ">" + cart.sp[0] + "￥0.00</input><br><input name='sp' type='radio' value='" + cart.sp[1] + "' onclick=changeShipping(1)" + (cart.sp_==1 ? ' checked' : '') + ">" + cart.sp[1] + "￥0.00</input><br><input name='sp' type='radio' value='" + cart.sp[2] + "' onclick=changeShipping(2)" + (cart.sp_==2 ? ' checked' : '') + ">" + cart.sp[2] + "￥<span id='sp'>" + getShippingFee(total_product, total_amount) + "</span></input>";
			div_body += "</div>";
			div_body += "</div>";
			div_body += "</div>";
			document.getElementById("cart_table").innerHTML = div_body;
		} else {
			document.getElementById("cart_table").innerHTML = "空购物车，继续购物";
		}
	}*/
	showCart();
}

initCart();

//window.addEventListener("resize", initCart);

var url = "/order/update";

function selectOrder(oindex) {
	var orders = cart.os;
	var length = orders.length;
	var total_product = 0;
	var total_amount = 0;
	var sub_total_amount = 0;
	for (var i=0, j=length; i<j; i++) {
		sub_total_amount = parseFloat(orders[i].st.dv)*parseFloat(document.getElementById('quantity_of_' + i).value);
		document.getElementById('amount_of_' + i).innerHTML = parseFloat(sub_total_amount).toFixed(2);
		if (i==oindex) {
			if (orders[i].checkbox!='checked') {
				orders[i].checkbox = 'checked';
				total_amount += sub_total_amount;
				total_product += 1;
			} else {
				orders[i].checkbox = '';
			}
		} else {
			if (orders[i].checkbox=='checked') {
				total_amount += sub_total_amount;
				total_product += 1;
			}
		}
	}
	if (total_product==length) {
		document.getElementById('selectOrders').checked = true;
	} else {
		document.getElementById('selectOrders').checked = false;
	}
	document.getElementById('total_amount').innerHTML = parseFloat(total_amount + parseFloat(getShippingFee(total_product, total_amount))).toFixed(2);
	document.getElementById('total_product').innerHTML = total_product;
	document.getElementById('sp').innerHTML = getShippingFee(total_product, total_amount);
	showCart();
}

function selectOrders() {
	var orders = cart.os;
	var length = orders.length;
	var total_product = 0;
	var total_amount = 0;
	var sub_total_amount = 0;
	var check = document.getElementById('selectOrders').checked;
	for (var i=0, j=length; i<j; i++) {
		sub_total_amount = parseFloat(orders[i].st.dv)*parseFloat(document.getElementById('quantity_of_' + i).value);
		document.getElementById('amount_of_' + i).innerHTML = parseFloat(sub_total_amount).toFixed(2);
		if (check) {
			orders[i].checkbox = 'checked';
			document.getElementById('checkbox_of_' + i).checked = true;
			total_amount += sub_total_amount;
			total_product += 1;
		} else {
			orders[i].checkbox = '';
			document.getElementById('checkbox_of_' + i).checked = false;
		}
	}
	document.getElementById('total_amount').innerHTML = parseFloat(total_amount + parseFloat(getShippingFee(total_product, total_amount))).toFixed(2);
	document.getElementById('total_product').innerHTML = total_product;
	document.getElementById('sp').innerHTML = getShippingFee(total_product, total_amount);
	showCart();
}

function getShippingFee(total_product, total_amount) {
	if (cart.sp_!=1 || total_product==0 || total_amount>=cart.e) {
		return parseFloat(0).toFixed(2);
	} else {
		return parseFloat(cart.fee).toFixed(2);
	}
}

function amountPlus(i) {
	var q = document.getElementById('quantity_of_' + i).value;
	var stock = checkStock(cart.os[i].st.ds, cart.os[i].sz, cart.os[i].clr);
	if (isNaN(q)) {
		q = 1;
	} else if (parseInt(q) >= parseInt(stock)) {
		q = stock;
	} else if (parseInt(q) < 1) {
		q = 1;
	} else {
		q = parseInt(q) + 1;
	}
	setQuantityByOrderId(i, q);
}

function amountMinus(i) {
	var q = document.getElementById('quantity_of_' + i).value;
	var stock = checkStock(cart.os[i].st.ds, cart.os[i].sz, cart.os[i].clr);
	if (isNaN(q)) {
		q = stock;
	} else if (parseInt(q) > parseInt(stock)) {
		q = stock;
	} else if (parseInt(q) <= 1) {
		q = 1;
	} else {
		q = parseInt(q) - 1;
	}
	setQuantityByOrderId(i, q);
}

function changeAmount(i) {
	var q = document.getElementById('quantity_of_' + i).value;
	var stock = checkStock(cart.os[i].st.ds, cart.os[i].sz, cart.os[i].clr);
	if (isNaN(q)) {
		q = 1;
	} else if (parseInt(q) > parseInt(stock)) {
		q = stock;
	} else if (parseInt(q) < 1) {
		q = 1;
	} else {
		q = parseInt(q);
	}
	setQuantityByOrderId(i, q);
}

function checkStock(ds, sz, clr) {
	var stock = 0;
	var length = ds.length;
	for (var i=0, j=length; i<j; i++) {
		if (ds[i].c == clr && ds[i].s == sz) {
			stock = ds[i].cnt;
		}
	}
	return stock;
}

function setQuantityByOrderId(i, q) {
	var data = {
		"order":[{
			"id": cart.os[i].id,
			"sz": cart.os[i].sz,
			"clr": cart.os[i].clr,
			"q": q
		}],
		"move": [],
		"delete": []
	};
	var json = JSON.stringify(data);
	//alert(json); //对接后注释掉
	$.post(url, {data: json}, function(flag) {
		if (flag.success) {
			document.getElementById('quantity_of_' + i).value = q;
			cart.os[i].q = q;
			getTotalAmount();
			showCart();
		}
	});
}

function getTotalAmount() {
	var orders = cart.os;
	var length = orders.length;
	var total_product = 0;
	var total_amount = 0;
	var sub_total_amount = 0;
	for (var i=0, j=length; i<j; i++) {
		sub_total_amount = parseFloat(orders[i].st.dv)*parseFloat(document.getElementById('quantity_of_' + i).value);
		document.getElementById('amount_of_' + i).innerHTML = parseFloat(sub_total_amount).toFixed(2);
		if (orders[i].checkbox=='checked') {
			total_amount += sub_total_amount;
			total_product += 1;
		}
	}
	document.getElementById('total_amount').innerHTML = parseFloat(total_amount + parseFloat(getShippingFee(total_product, total_amount))).toFixed(2);
	document.getElementById('sp').innerHTML = getShippingFee(total_product, total_amount);
}

function removeOrderById(i) {
	var data = {
		"order": [],
		"move": [],
		"delete": [cart.os[i].id]
	};
	var json = JSON.stringify(data);
	//alert(json); //对接后注释掉
	$.post(url, {data: json}, function(flag) {
		if (flag.success) {
			deleteOrderById(i);
		}
	});
}

function moveOrderById(i) {
	var data = {
		"order": [],
		"move": [cart.os[i].id],
		"delete": []
	};
	var json = JSON.stringify(data);
	//alert(json); //对接后注释掉
	$.post(url, {data: json}, function(flag) {
		if (flag.success) {
			deleteOrderById(i);
		}
	});
}

function removeOrders() {
	var data = {
		"order": [],
		"move": [],
		"delete": getOrders()
	};
	var json = JSON.stringify(data);
	//alert(json); //对接后注释掉
	$.post(url, {data: json}, function(flag) {
		if (flag.success) {
			deleteOrders();
		}
	});
}

function moveOrders() {
	var data = {
		"order": [],
		"move": getOrders(),
		"delete": []
	};
	var json = JSON.stringify(data);
	//alert(json); //对接后注释掉
	$.post(url, {data: json}, function(flag) {
		if (flag.success) {
			deleteOrders();
		}
	});
}

function deleteOrderById(i) {
	cart.os.splice(i, 1);
	initCart();
}

function getOrders() {
	var orders = cart.os;
	var length = orders.length;
	var array = new Array();
	for (var i=0, j=length; i<j; i++) {
		if (orders[i].checkbox == 'checked') {
			array.push(orders[i].id);
		}
	}
	return array;
}

function deleteOrders() {
	var orders = cart.os;
	var length = orders.length;
	for (var i=0, j=length; i<j; i++) {
		if (orders[i].checkbox == 'checked') {
			orders.splice(i, 1);
			i--;
			j--;
		}
	}
	initCart();
}

/*修改【尺码】和【颜色】对话框 <start>*/
var ds;
var colors = new Array();
var sizes = new Array();
var orderIndex;
var colorIndex;
var sizeIndex;
var colorIndex_;
var sizeIndex_;
var stock_;
var q_;

function openDialog(oi) {
	document.getElementById("save_btn").disabled = false;
	var l = document.documentElement.scrollLeft + document.documentElement.clientWidth/2 - 125;
	var t = document.documentElement.scrollTop + document.documentElement.clientHeight/2 - 125;
	document.getElementById("productDialog").style.display = "";
	document.getElementById("productDialog").style.left = l + "px";
	document.getElementById("productDialog").style.top = t + "px";
	var orders = cart.os;
	orderIndex = oi;
	ds = orders[orderIndex].st.ds;
	q_ = orders[orderIndex].q;
	document.getElementById("productName").innerHTML = orders[orderIndex].st.prod.b + " " + orders[orderIndex].st.prod.n;

	colors = new Array();
	color_label: for (var i=0, j=ds.length; i<j; i++) {
		for (var m=0, n=colors.length; m<n; m++) {
			if (colors[m]==ds[i].c)
				continue color_label;
		}
		colors[colors.length]=ds[i].c;
	}
	sizes = new Array();
	size_label: for (var i=0, j=ds.length; i<j; i++) {
		for (var m=0, n=sizes.length; m<n; m++) {
			if (sizes[m]==ds[i].s)
				continue size_label;
		}
		sizes[sizes.length]=ds[i].s;
	}
	var colorButtons = "";
	for (var i=0, j=colors.length; i<j; i++) {
		colorButtons += ("<button id='color_" + i + "' onclick=changeColor(" + i + ") class='unuo-btn-xs' style='color: black; border: 1px solid #bbbbbb;'>" + colors[i] + "</button> ");
		if (colors[i]==orders[orderIndex].clr) {
			colorIndex = i;
			colorIndex_ = i;
		}
	}
	var sizeButtons = "";
	for (var i=0, j=sizes.length; i<j; i++) {
		sizeButtons += ("<button id='size_" + i + "' onclick=changeSize(" + i + ") class='unuo-btn-xs' style='color: black; border: 1px solid #bbbbbb;'>" + sizes[i] + "</button> ");
		if (sizes[i]==orders[orderIndex].sz) {
			sizeIndex = i;
			sizeIndex_ = i;
		}
	}
	document.getElementById("productDetail").innerHTML="<table><tr><td class='col-md-3' align='center'>尺码：</td><td>" + sizeButtons + "</td></tr><tr><td align='center'>颜色：</td><td>" + colorButtons + "</td></tr></table><div class='row text-center'>（剩余<span id='productStock'></span>件）</div>";
	changeColor(colorIndex);
	changeSize(sizeIndex);
}

function changeColor(i) {
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
	colorIndex_ = i;
	checkStock_();
}

function changeSize(i) {
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
	sizeIndex_ = i;
	checkStock_();
}

function checkStock_() {
	stock_ = 0;
	for (var i=0, j=ds.length; i<j; i++) {
		if (colors[colorIndex_]==ds[i].c && sizes[sizeIndex_]==ds[i].s) {
			stock_ = ds[i].cnt;
		}
	}
	document.getElementById("productStock").innerHTML = stock_;
	if (stock_==0) {
		document.getElementById("save_btn").disabled = true;
	} else {
		document.getElementById("save_btn").disabled = false;
	}
}

function saveDialog() {
	if (sizeIndex==sizeIndex_&&colorIndex==colorIndex_) {
		closeDialog();
	}
	if (stock_<q_) {
		q_ = stock_;
	}
	var data = {
		"order":[{
			"id": cart.os[orderIndex].id,
			"sz": sizes[sizeIndex_],
			"clr": colors[colorIndex_],
			"q": q_
		}],
		"move": [],
		"delete": []
	};
	var json = JSON.stringify(data);
	//alert(json); //对接后注释掉
	$.post(url, {data: json}, function(flag) {
		if (flag.success) {
			cart.os[orderIndex].sz = sizes[sizeIndex_];
			cart.os[orderIndex].clr = colors[colorIndex_],
			cart.os[orderIndex].q = q_;
			initCart();
			showCart();
		}
	});
	closeDialog();
}

function closeDialog() {
	document.getElementById("productDialog").style.display = "none";
}
/*修改【尺码】和【颜色】对话框 <end>*/

function changeShipping(num) {
	cart.sp_ = num;
	getTotalAmount();
	showCart();
}

/*获取浏览器宽度和高度相关方法*/
function getAbsoluteLeft(obj) {
	var o = obj;
	var objectLeft = o.offsetLeft;
	var objectParent;
	while(o.offsetParent!=null) {
		objectParent = o.offsetParent;
		objectLeft += objectParent.offsetLeft;
		o = objectParent;
	}
	return objectLeft;
}

function getAbsoluteTop(obj) {
	var o = obj;
	var objectTop = o.offsetTop;
	var objectParent;
	while(o.offsetParent!=null) {
		objectParent = o.offsetParent;
		objectTop += objectParent.offsetTop;
		o = objectParent;
	}
	return objectTop;
}

function checkoutOrders() {
	return {
		"os" : getOrders(),
		"s": cart.sp[cart.sp_]
	};
}

function showCart() {
	/*仅供显示测试数据使用*/
	//document.getElementById("showCart").innerHTML = JSON.stringify(cart);
}