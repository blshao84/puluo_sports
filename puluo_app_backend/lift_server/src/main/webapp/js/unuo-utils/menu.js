var selectedBrands = existingFilterOptions.filters.brands;
for (var i=0, j=selectedBrands.length; i<j; i++) {
	if (i != 0) {
		document.getElementById("brand_").value += ",";
	}
	document.getElementById("brand_").value += selectedBrands[i].replace(/\s+/g, String.fromCharCode(32));
}

var selectedSizes = existingFilterOptions.filters.sizes;
for (var i=0, j=selectedSizes.length; i<j; i++) {
	if (i != 0) {
		document.getElementById("size_").value += ",";
	}
	document.getElementById("size_").value += selectedSizes[i].replace(/\s+/g, String.fromCharCode(32));
}

var selectedColors = existingFilterOptions.filters.colors;
for (var i=0, j=selectedColors.length; i<j; i++) {
	if (i != 0) {
		document.getElementById("color_").value += ",";
	}
	document.getElementById("color_").value += selectedColors[i].replace(/\s+/g, String.fromCharCode(32));
}

// 请保留下段注释代码
/*var brands = filterOptions.brands;
var brandButtons = "";
for (var i=0, j=brands.length; i<j; i++) {
	if (getIndexOf("brand_", brands[i])!=-1) {
		brandButtons += ("<button class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;' onclick=addBrand('" + brands[i].replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;'>" + brands[i] + "</button>&nbsp;");
	} else {
		brandButtons += ("<button class='unuo-btn-xs' style='margin-bottom:5px;' onclick=addBrand('" + brands[i].replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;'>" + brands[i] + "</button>&nbsp;");
	}
}
document.getElementById("brands").innerHTML = brandButtons;*/

function addBrand(brand) {
	if (document.getElementById("brand_").value != "") {
		//var flag = document.getElementById("brand_").value.indexOf(brand.replace(/\s+/g, String.fromCharCode(32)));
		if (getIndexOf("brand_", brand) == -1) {
			document.getElementById("brand_").value += "," + brand.replace(/\s+/g, String.fromCharCode(32));
		}
	} else {
		document.getElementById("brand_").value += brand.replace(/\s+/g, String.fromCharCode(32));
	}
	submitForm();
}

/*var sizes = filterOptions.sizes;
var sizeButtons = "";
for (var i=0, j=sizes.length; i<j; i++) {
	if (getIndexOf("size_", sizes[i])!=-1) {
		sizeButtons += ("<a href='#' class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;margin-right:10px;' onclick=addSize('" + sizes[i].replace(/\s+/g,  "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + sizes[i] + "</a>&nbsp;");
	} else {
		sizeButtons += ("<a href='#' class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;' onclick=addSize('" + sizes[i].replace(/\s+/g,  "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + sizes[i] + "</a>&nbsp;");
	}
}
document.getElementById("sizes").innerHTML = sizeButtons;*/

var more_sizes_flag = false;
var sizeButtons = "";
var sizes = filterOptions.sizes;
var oldSizes = filterOptions.oldSizes;
if (oldSizes.length!=0) {
	if (sizes.length!=0) {
		sizes[sizes.length] = "<br>";
	}
	for (var i=0, j=oldSizes.length; i<j; i++) {
		sizes[sizes.length] = oldSizes[i];
	}
}
	

function showMoreSizes() {
	sizeButtons = "";
	var tmpSizeButtons = "";
	var sizes_string = "";
	var more_sizes_index = 0;
	var td_sizes_width = $('#filterTable').width() - 140;
	var div_sizes_width = 0;
	if (more_sizes_flag) {
		for (var i=0, j=sizes.length; i<j; i++) {
			/*if (getIndexOf("size_", sizes[i])!=-1) {
				sizeButtons += ("<a href='#' class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;margin-right:10px;' onclick=addSize('" + sizes[i].replace(/\s+/g,  "&nbsp;") + "')>" + sizes[i] + "</a>");
			} else {
				sizeButtons += ("<a href='#' class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;' onclick=addSize('" + sizes[i].replace(/\s+/g,  "&nbsp;") + "')>" + sizes[i] + "</a>");
			}*/

			if (sizes[i]=="<br>") { // <br>是新旧sizes的分隔符，无论是否铺满一行，均换下一行
				if (sizeButtons.length - sizeButtons.lastIndexOf("<br>") != 4) { // 排除两次换行的情况
					sizeButtons = sizeButtons + "<br>";
				}
				sizes_string = "";
				more_sizes_index = i + 1;
				continue;
			}


			tmpSizeButtons = sizeButtons;
			if (getIndexOf("size_", sizes[i])!=-1) {
				sizeButtons += ("<a href='#' class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;margin-right:10px;' onclick=addSize('" + sizes[i].replace(/\s+/g,  "&nbsp;") + "')>" + sizes[i] + "</a>");
			} else {
				sizeButtons += ("<a href='#' class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;' onclick=addSize('" + sizes[i].replace(/\s+/g,  "&nbsp;") + "')>" + sizes[i] + "</a>");
			}
			sizes_string += sizes[i];
			$('#more_sizes_div').html(sizes_string + "〇〇");
			div_sizes_width = $('#more_sizes_div').width();
			if ((div_sizes_width + 10 * (i - more_sizes_index)) > td_sizes_width) {
				if (i!=more_sizes_index) { // 防止单个size的link长度超过td的宽度，进入无限循环
					sizeButtons = tmpSizeButtons + "<br>";
					sizes_string = "";
					more_sizes_index = i;
					i--;
				} else {
					sizeButtons = "";
					break;
				}
			}
		}
		document.getElementById("sizes").innerHTML = sizeButtons;
		document.getElementById("more_sizes_link").innerHTML = "隐藏";
		more_sizes_flag = false;
	} else {
		for (var i=0, j=sizes.length; i<j; i++) {

			if (sizes[i]=="<br>") // <br>是新旧sizes的分隔符，无论是否铺满一行，均结束循环
				break;

			tmpSizeButtons = sizeButtons;
			if (getIndexOf("size_", sizes[i])!=-1) {
				sizeButtons += ("<a href='#' class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;margin-right:10px;' onclick=addSize('" + sizes[i].replace(/\s+/g,  "&nbsp;") + "')>" + sizes[i] + "</a>");
			} else {
				sizeButtons += ("<a href='#' class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;' onclick=addSize('" + sizes[i].replace(/\s+/g,  "&nbsp;") + "')>" + sizes[i] + "</a>");
			}
			sizes_string += sizes[i];
			$('#more_sizes_div').html(sizes_string + "〇〇");
			div_sizes_width = $('#more_sizes_div').width();
			if ((div_sizes_width + 10 * i) > td_sizes_width) {
				sizeButtons = tmpSizeButtons;
				break;
			}
			more_sizes_index = i + 1;
		}
		document.getElementById("sizes").innerHTML = sizeButtons;
		if (more_sizes_index!=sizes.length) {
			document.getElementById("more_sizes_link").innerHTML = "更多";
		} else {
			document.getElementById("more_sizes_link").innerHTML = "";
		}
		more_sizes_flag = true;
	}
}

function addSize(size) {
	if (document.getElementById("size_").value != "") {
		//var flag = document.getElementById("size_").value.indexOf(size.replace(/\s+/g, String.fromCharCode(32)));
		if (getIndexOf("size_", size) == -1) {
			document.getElementById("size_").value += "," + size.replace(/\s+/g, String.fromCharCode(32));
		}
	} else {
		document.getElementById("size_").value += size.replace(/\s+/g, String.fromCharCode(32));
	}
	submitForm();
}

var colors = filterOptions.colors;
var colorButtons = "";
for (var i=0, j=colors.length; i<j; i++) {
	if (getIndexOf("color_", colors[i])!=-1) {
		colorButtons += ("<a href='#' class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;margin-right:10px;' onclick=addColor('" + colors[i].replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + colors[i] + "</a>&nbsp;");
	} else {
		colorButtons += ("<a href='#' class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;' onclick=addColor('" + colors[i].replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + colors[i] + "</a>&nbsp;");
	}
}
document.getElementById("colors").innerHTML = colorButtons;

function addColor(color) {
	if (document.getElementById("color_").value != "") {
		//var flag = document.getElementById("color_").value.indexOf(color.replace(/\s+/g, String.fromCharCode(32)));
		if (getIndexOf("color_", color) == -1) {
			document.getElementById("color_").value += "," + color.replace(/\s+/g, String.fromCharCode(32));
		}
	} else {
		document.getElementById("color_").value += color.replace(/\s+/g, String.fromCharCode(32));
	}
	submitForm();
}

function getIndexOf(options_, selectOption_) {
	var arr = document.getElementById(options_).value.split(",");
	for (var i=0, j=arr.length; i<j; i++) {
		if (arr[i] == selectOption_.replace(/\s+/g, String.fromCharCode(32))) {
			return i;
		}
	}
	return -1;
}

function changeFilters(){
	var showRemoveAll = false;
	var innerHTML = "<table><tr><td width='120' valign='top'><a href='#' class='unuo-btn-xs' onclick=removeAll()>移除全部[X]</a></td><td></td></tr>";
	//var innerHTML_ = "";
	var strArray = null;
	if (document.getElementById("brand_").value != "") {
		strArray = document.getElementById("brand_").value.split(",");
		innerHTML += "<tr><td width='120' valign='top'><a href='#' class='unuo-btn-xs' onclick=removeOnes('brand_')>移除全部品牌 [X]:</a></td><td>";
		for (var i=0, j=strArray.length; i<j; i++) {
			if (i!=0) {
				innerHTML += "&nbsp;";
			}
			innerHTML += "<a href='#' class='unuo-btn-xs' onclick=removeBrand('" + strArray[i].replace(/\s+/g, "&nbsp;") + "')>" + strArray[i] + " [X]</a>";
		}
		innerHTML += "</td></tr>"
		showRemoveAll = true;
		//innerHTML_ = "<br /><button onclick=removeBrands()>移除品牌</button>" + innerHTML;
	}
	if (document.getElementById("size_").value != "") {
		strArray = document.getElementById("size_").value.split(",");
		innerHTML += "<tr><td width='120' valign='top'><a href='#' class='unuo-btn-xs' onclick=removeOnes('size_')>移除全部尺码 [X]:</a></td><td>";
		for (var i=0, j=strArray.length; i<j; i++) {
			if (i!=0) {
				innerHTML += "&nbsp;";
			}
			innerHTML += "<a href='#' class='unuo-btn-xs' onclick=removeSize('" + strArray[i].replace(/\s+/g, "&nbsp;") + "')>" + strArray[i] + " [X]</a>";
		}
		innerHTML += "</td></tr>"
		showRemoveAll = true;
		//innerHTML_ += "<br /><button onclick=removeSizes()>移除尺码</button>" + innerHTML;
	}
	if (document.getElementById("color_").value != "") {
		strArray = document.getElementById("color_").value.split(",");
		innerHTML += "<tr><td width='120' valign='top'><a href='#' class='unuo-btn-xs' onclick=removeOnes('color_')>移除全部颜色 [X]:</a></td><td>";
		for (var i=0, j=strArray.length; i<j; i++) {
			if (i!=0) {
				innerHTML += "&nbsp;";
			}
			innerHTML += "<a href='#' class='unuo-btn-xs' onclick=removeColor('" + strArray[i].replace(/\s+/g, "&nbsp;") + "')>" + strArray[i] + " [X]</a>";
		}
		innerHTML += "</td></tr>"
		showRemoveAll = true;
		//innerHTML_ += "<br /><button onclick=removeColors()>移除颜色</button>" + innerHTML;
	}
	innerHTML += "</table>";
	/*if (innerHTML != "") {
		innerHTML = "<button class='btn-xs' style='color: black; border: 1px solid #bbbbbb;' onclick=removeAll()>移除全部 X</button>" + innerHTML;
	}*/
	if (showRemoveAll) {
		document.getElementById("filter").innerHTML = innerHTML;
	}
}

changeFilters();

function removeBrand(brand) {
	var tmp = brand.replace(/\s+/g, String.fromCharCode(32));
	var str = "";
	var arr = document.getElementById("brand_").value.split(",");
	var flag = false;
	for (var i=0, j=arr.length; i<j; i++) {
		if (i == 0 && tmp == arr[i]) {
			flag = true;
		} else if (i == 0 && tmp != arr[i]) {
			str += arr[i];
		} else if (i != 0 && tmp == arr[i]) {
			continue;
		} else {
			if (flag) {
				str += arr[i];
				flag = false;
			} else {
				str += "," + arr[i];
			}
		}
	}
	document.getElementById("brand_").value = str;
	submitForm();
}

function removeSize(size) {
	var tmp = size.replace(/\s+/g, String.fromCharCode(32));
	var str = "";
	var arr = document.getElementById("size_").value.split(",");
	var flag = false;
	for (var i=0, j=arr.length; i<j; i++) {
		if (i == 0 && tmp == arr[i]) {
			flag = true;
		} else if (i == 0 && tmp != arr[i]) {
			str += arr[i];
		} else if (i != 0 && tmp == arr[i]) {
			continue;
		} else {
			if (flag) {
				str += arr[i];
				flag = false;
			} else {
				str += "," + arr[i];
			}
		}
	}
	document.getElementById("size_").value = str;
	submitForm();
}

function removeColor(color) {
	var tmp = color.replace(/\s+/g, String.fromCharCode(32));
	var str = "";
	var arr = document.getElementById("color_").value.split(",");
	var flag = false;
	for (var i=0, j=arr.length; i<j; i++) {
		if (i == 0 && tmp == arr[i]) {
			flag = true;
		} else if (i == 0 && tmp != arr[i]) {
			str += arr[i];
		} else if (i != 0 && tmp == arr[i]) {
			continue;
		} else {
			if (flag) {
				str += arr[i];
				flag = false;
			} else {
				str += "," + arr[i];
			}
		}
	}
	document.getElementById("color_").value = str;
	submitForm();
}

function removeOnes(option) {
	document.getElementById(option).value = "";
	submitForm();
}

function removeAll() {
	document.getElementById("brand_").value = "";
	document.getElementById("size_").value = "";
	document.getElementById("color_").value = "";
	submitForm();
}

function addSort(param,direction) {
	document.getElementById("param_").value = param;
	document.getElementById("direction_").value = direction;
	submitForm();
}

function submitForm() {
	var strArray = null;
	var json = {
		filters:{
			brands: document.getElementById("brand_").value.split(","),
			colors: document.getElementById("color_").value.split(","),
			sizes: document.getElementById("size_").value.split(",")
		},
		sortParam:{
			param: document.getElementById("param_").value,
			direction: document.getElementById("direction_").value
		}
	};
	var returnJson = JSON.stringify(json);
	document.getElementById("data").value = returnJson;
	document.getElementById("filterForm").submit();
}

/* 按照拼音首字母分类 */
var PIL = [
	{"i": "A", "n": ""},
	{"i": "B", "n": ""},
	{"i": "C", "n": ""},
	{"i": "D", "n": ""},
	{"i": "E", "n": ""},
	{"i": "F", "n": ""},
	{"i": "G", "n": ""},
	{"i": "H", "n": ""},
	{"i": "I", "n": ""},
	{"i": "J", "n": ""},
	{"i": "K", "n": ""},
	{"i": "L", "n": ""},
	{"i": "M", "n": ""},
	{"i": "N", "n": ""},
	{"i": "O", "n": ""},
	{"i": "P", "n": ""},
	{"i": "Q", "n": ""},
	{"i": "R", "n": ""},
	{"i": "S", "n": ""},
	{"i": "T", "n": ""},
	{"i": "U", "n": ""},
	{"i": "V", "n": ""},
	{"i": "W", "n": ""},
	{"i": "X", "n": ""},
	{"i": "Y", "n": ""},
	{"i": "Z", "n": ""},
	{"i": "0", "n": ""},
	{"i": "1", "n": ""},
	{"i": "2", "n": ""},
	{"i": "3", "n": ""},
	{"i": "4", "n": ""},
	{"i": "5", "n": ""},
	{"i": "6", "n": ""},
	{"i": "7", "n": ""},
	{"i": "8", "n": ""},
	{"i": "9", "n": ""},
	{"i": "其它字母", "n": ""}
]

function getPIL() {
	var BIL = filterOptions.brandPinyinInitial;
	for (var i=0, j=BIL.length; i<j; i++) {
		switch (BIL[i].toUpperCase().charAt(0)) {
			case "A".charAt(0): {
				if (""!=PIL[0].n)
					PIL[0].n += ",";
				PIL[0].n += i;
				break;
			}
			case "B".charAt(0) : {
				if (""!=PIL[1].n)
					PIL[1].n += ",";
				PIL[1].n += i;
				break;
			}
			case "C".charAt(0) : {
				if (""!=PIL[2].n)
					PIL[2].n += ",";
				PIL[2].n += i;
				break;
			}
			case "D".charAt(0) : {
				if (""!=PIL[3].n)
					PIL[3].n += ",";
				PIL[3].n += i;
				break;
			}
			case "E".charAt(0) : {
				if (""!=PIL[4].n)
					PIL[4].n += ",";
				PIL[4].n += i;
				break;
			}
			case "F".charAt(0) : {
				if (""!=PIL[5].n)
					PIL[5].n += ",";
				PIL[5].n += i;
				break;
			}
			case "G".charAt(0) : {
				if (""!=PIL[6].n)
					PIL[6].n += ",";
				PIL[6].n += i;
				break;
			}
			case "H".charAt(0) : {
				if (""!=PIL[7].n)
					PIL[7].n += ",";
				PIL[7].n += i;
				break;
			}
			case "I".charAt(0) : {
				if (""!=PIL[8].n)
					PIL[8].n += ",";
				PIL[8].n += i;
				break;
			}
			case "J".charAt(0) : {
				if (""!=PIL[9].n)
					PIL[9].n += ",";
				PIL[9].n += i;
				break;
			}
			case "K".charAt(0) : {
				if (""!=PIL[10].n)
					PIL[10].n += ",";
				PIL[10].n += i;
				break;
			}
			case "L".charAt(0) : {
				if (""!=PIL[11].n)
					PIL[11].n += ",";
				PIL[11].n += i;
				break;
			}
			case "M".charAt(0) : {
				if (""!=PIL[12].n)
					PIL[12].n += ",";
				PIL[12].n += i;
				break;
			}
			case "N".charAt(0) : {
				if (""!=PIL[13].n)
					PIL[13].n += ",";
				PIL[13].n += i;
				break;
			}
			case "O".charAt(0) : {
				if (""!=PIL[14].n)
					PIL[14].n += ",";
				PIL[14].n += i;
				break;
			}
			case "P".charAt(0) : {
				if (""!=PIL[15].n)
					PIL[15].n += ",";
				PIL[15].n += i;
				break;
			}
			case "Q".charAt(0) : {
				if (""!=PIL[16].n)
					PIL[16].n += ",";
				PIL[16].n += i;
				break;
			}
			case "R".charAt(0) : {
				if (""!=PIL[17].n)
					PIL[17].n += ",";
				PIL[17].n += i;
				break;
			}
			case "S".charAt(0) : {
				if (""!=PIL[18].n)
					PIL[18].n += ",";
				PIL[18].n += i;
				break;
			}
			case "T".charAt(0) : {
				if (""!=PIL[19].n)
					PIL[19].n += ",";
				PIL[19].n += i;
				break;
			}
			case "U".charAt(0) : {
				if (""!=PIL[20].n)
					PIL[20].n += ",";
				PIL[20].n += i;
				break;
			}
			case "V".charAt(0) : {
				if (""!=PIL[21].n)
					PIL[21].n += ",";
				PIL[21].n += i;
				break;
			}
			case "W".charAt(0) : {
				if (""!=PIL[22].n)
					PIL[22].n += ",";
				PIL[22].n += i;
				break;
			}
			case "X".charAt(0) : {
				if (""!=PIL[23].n)
					PIL[23].n += ",";
				PIL[23].n += i;
				break;
			}
			case "Y".charAt(0) : {
				if (""!=PIL[24].n)
					PIL[24].n += ",";
				PIL[24].n += i;
				break;
			}
			case "Z".charAt(0) : {
				if (""!=PIL[25].n)
					PIL[25].n += ",";
				PIL[25].n += i;
				break;
			}
			case "0".charAt(0) : {
				if (""!=PIL[26].n)
					PIL[26].n += ",";
				PIL[26].n += i;
				break;
			}
			case "1".charAt(0) : {
				if (""!=PIL[27].n)
					PIL[27].n += ",";
				PIL[27].n += i;
				break;
			}
			case "2".charAt(0) : {
				if (""!=PIL[28].n)
					PIL[28].n += ",";
				PIL[28].n += i;
				break;
			}
			case "3".charAt(0) : {
				if (""!=PIL[29].n)
					PIL[29].n += ",";
				PIL[29].n += i;
				break;
			}
			case "4".charAt(0) : {
				if (""!=PIL[30].n)
					PIL[30].n += ",";
				PIL[30].n += i;
				break;
			}
			case "5".charAt(0) : {
				if (""!=PIL[31].n)
					PIL[31].n += ",";
				PIL[31].n += i;
				break;
			}
			case "6".charAt(0) : {
				if (""!=PIL[32].n)
					PIL[32].n += ",";
				PIL[32].n += i;
				break;
			}
			case "7".charAt(0) : {
				if (""!=PIL[33].n)
					PIL[33].n += ",";
				PIL[33].n += i;
				break;
			}
			case "8".charAt(0) : {
				if (""!=PIL[34].n)
					PIL[34].n += ",";
				PIL[34].n += i;
				break;
			}
			case "9".charAt(0) : {
				if (""!=PIL[35].n)
					PIL[35].n += ",";
				PIL[35].n += i;
				break;
			}
			default : {
				if (""!=PIL[36].n)
					PIL[36].n += ",";
				PIL[36].n += i;
			}
		}
	}
}

getPIL();

function separateBrandList() {
	var brands = filterOptions.brands;
	var brand = "";
	var subList = [];
	var tab1 = "";
	for (var i=0; i<7; i++) {
		if (""!=PIL[i].n) {
			subList = PIL[i].n.split(",");
		} else {
			continue;
		}
		for (var o=0, p=subList.length; o<p; o++) {
			brand = brands[parseInt(subList[o])];
			if (getIndexOf("brand_", brand)!=-1) {
				tab1 += ("<a href='#' class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;margin-right:10px;' onclick=addBrand('" + brand.replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + brand + "</a>&nbsp;");
			} else {
				tab1 += ("<a href='#' class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;' onclick=addBrand('" + brand.replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + brand + "</a>&nbsp;");
			}
		}
	}
	if (""==tab1) tab1 = "&nbsp;";
	document.getElementById("tab1").innerHTML = tab1;
	var tab2 = "";
	for (var i=7; i<14; i++) {
		if (""!=PIL[i].n) {
			subList = PIL[i].n.split(",");
		} else {
			continue;
		}
		for (var o=0, p=subList.length; o<p; o++) {
			brand = brands[parseInt(subList[o])];
			if (getIndexOf("brand_", brand)!=-1) {
				tab2 += ("<a href='#' class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;margin-right:10px;' onclick=addBrand('" + brand.replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + brand + "</a>&nbsp;");
			} else {
				tab2 += ("<a href='#' class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;' onclick=addBrand('" + brand.replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + brand + "</a>&nbsp;");
			}
		}
	}
	if (""==tab2) tab2 = "&nbsp;";
	document.getElementById("tab2").innerHTML = tab2;
	var tab3 = "";
	for (var i=14; i<20; i++) {
		if (""!=PIL[i].n) {
			subList = PIL[i].n.split(",");
		} else {
			continue;
		}
		for (var o=0, p=subList.length; o<p; o++) {
			brand = brands[parseInt(subList[o])];
			if (getIndexOf("brand_", brand)!=-1) {
				tab3 += ("<a href='#' class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;margin-right:10px;' onclick=addBrand('" + brand.replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + brand + "</a>&nbsp;");
			} else {
				tab3 += ("<a href='#' class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;' onclick=addBrand('" + brand.replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + brand + "</a>&nbsp;");
			}
		}
	}
	if (""==tab3) tab3 = "&nbsp;";
	document.getElementById("tab3").innerHTML = tab3;
	var tab4 = "";
	for (var i=20; i<26; i++) {
		if (""!=PIL[i].n) {
			subList = PIL[i].n.split(",");
		} else {
			continue;
		}
		for (var o=0, p=subList.length; o<p; o++) {
			brand = brands[parseInt(subList[o])];
			if (getIndexOf("brand_", brand)!=-1) {
				tab4 += ("<a href='#' class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;margin-right:10px;' onclick=addBrand('" + brand.replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + brand + "</a>&nbsp;");
			} else {
				tab4 += ("<a href='#' class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;' onclick=addBrand('" + brand.replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + brand + "</a>&nbsp;");
			}
		}
	}
	if (""==tab4) tab4 = "&nbsp;";
	document.getElementById("tab4").innerHTML = tab4;
	var tab5 = "";
	for (var i=26; i<36; i++) {
		if (""!=PIL[i].n) {
			subList = PIL[i].n.split(",");
		} else {
			continue;
		}
		for (var o=0, p=subList.length; o<p; o++) {
			brand = brands[parseInt(subList[o])];
			if (getIndexOf("brand_", brand)!=-1) {
				tab5 += ("<a href='#' class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;margin-right:10px;' onclick=addBrand('" + brand.replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + brand + "</a>&nbsp;");
			} else {
				tab5 += ("<a href='#' class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;' onclick=addBrand('" + brand.replace(/\s+/g, "&nbsp;") + "') class='unuo-btn-xs' style='margin-bottom:5px;margin-right:10px;'>" + brand + "</a>&nbsp;");
			}
		}
	}
	if (""==tab5) tab5 = "&nbsp;";
	document.getElementById("tab5").innerHTML = tab5;
}

// 请保留下段注释代码
/*var PILButtons = "<button class='unuo-btn-xs unuo-btn-xs-selected' style='margin-bottom:5px;' class='unuo-btn-xs' onclick=showBrandList(-1)>全部</button>&nbsp;";
var notFirstPI = true;
for (var i=0, j=PIL.length; i<j; i++) {
	if (""!=PIL[i].n) {
		PILButtons += ("<button class='unuo-btn-xs' style='margin-bottom:5px;' class='unuo-btn-xs' onclick=showBrandList(" + i + ")>" + PIL[i].i + "</button>&nbsp;");
	}
}
document.getElementById("initials").innerHTML = PILButtons;

function showBrandList(i) {
	alert(0);
	if (-1==i) {
		showBrands();
		document.getElementById("initial_").value = "";
	} else {
		if (""==document.getElementById("initial_").value) {
			document.getElementById("initial_").value += PIL[i].n;
		} else if (-1==document.getElementById("initial_").value.indexOf(PIL[i].n)) {
			document.getElementById("initial_").value += "," + PIL[i].n;
		} else {
			return;
		}
		showSubBrands();
	}
}

function showSubBrands() {
	alert(document.getElementById("initial_").value);

alert(JSON.stringify(PIL));
}*/

separateBrandList();

showMoreSizes();