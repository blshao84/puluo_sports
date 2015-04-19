/* 必须在导入该脚本前导入public.js */

$(document).ready(function (){

	/* 追踪商品图片缩放事件
	var $pro_large_img_zoom = true; // 切换大图片源地址后缩放状态为未查看
	function trackImgZoom() {
		var $pro_large_img = $("#product_image_enlarge_link");
		$pro_large_img.mouseout(function() {
			if ($pro_large_img_zoom) {
				$pro_large_img_zoom = false; // 在不切换大图片源地址情况下，再次缩放查看，不会追踪事件
				trackingInfo.a = "image zoom: " + getImageFileName($('#product_image_enlarge_link').attr('href'));
				trackingInfo.at = "mouseover";
				submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
			}
		});
	}
	trackImgZoom(); */

	/* 追踪商品相册点击事件 开始 */
	var $pro_img_list = $("#spec-list a"); // 获取相册列表
	var $pro_large_img_link_1 = "";
	var $pro_large_img_link_2 = "";
	$pro_img_list.hover(function() {
		$pro_large_img_link_1 = $('#product_image_enlarge_link').attr('href'); // 获取鼠标移入相册前大图片源地址
	}, function() {
		$pro_large_img_link_2 = $('#product_image_enlarge_link').attr('href'); // 获取鼠标移出相册后大图片源地址
		if ($pro_large_img_link_1!=$pro_large_img_link_2) { // 若大图片源地址发生了改变
			//$pro_large_img_zoom = true; // 设定切换大图片源地址后缩放状态为未查看
			trackingInfo.a = "image select: " + getImageFileName($('#product_image_enlarge_link').attr('href'));
			trackingInfo.at = "click";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
			trackImgZoom();

		}
	});
	/* 追踪商品相册点击事件 结束 */

	/* 追踪商品详情切换事件 开始 */
	var $pro_intro_tab_imgs = $("#unuo-product-imgs-tab");
	var $pro_intro_tab_imgs_select = false;
	$pro_intro_tab_imgs.click(function() {
		if ($pro_intro_tab_imgs_select) {
			$pro_intro_tab_imgs_select = false;
			$pro_intro_tab_props_select = true;
			$pro_intro_tab_comments_select = true;
			trackingInfo.a = "product information: " + $pro_intro_tab_imgs.text();
			trackingInfo.at = "click";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
		}
	});

	var $pro_intro_tab_props = $("#unuo-product-props-tab");
	var $pro_intro_tab_props_select = true;
	$pro_intro_tab_props.click(function() {
		if ($pro_intro_tab_props_select) {
			$pro_intro_tab_imgs_select = true;
			$pro_intro_tab_props_select = false;
			$pro_intro_tab_comments_select = true;
			trackingInfo.a = "product information: " + $pro_intro_tab_props.text();
			trackingInfo.at = "click";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
		}
	});

	var $pro_intro_tab_comments = $("#unuo-product-comments-tab");
	var $pro_intro_tab_comments_select = true;
	$pro_intro_tab_comments.click(function() {
		if ($pro_intro_tab_comments_select) {
			$pro_intro_tab_imgs_select = true;
			$pro_intro_tab_props_select = true;
			$pro_intro_tab_comments_select = false;
			trackingInfo.a = "product information: " + $pro_intro_tab_comments.text();
			trackingInfo.at = "click";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
		}
	});
	/* 追踪商品详情切换事件 结束 */
	
	/* 追踪商品二维码点击事件
	var $product_qrcode = $("#product_qrcode a:eq(0)"); // 获取二维码链接
	var $product_qrcode_flag = true;
	$product_qrcode.click(function() {
		if ($product_qrcode_flag) {
			$product_qrcode_flag = false;
			trackingInfo.a = "qrcode: " + "二维码";
			trackingInfo.at = "click";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
		}
	}); */


	/* 追踪商品分享点击事件
	var $product_share_qzone = $("#product_share a:eq(1)"); // 获取分享列表
	var $product_share_qzone_flag = true;
	$product_share_qzone.click(function() {
		if ($product_share_qzone_flag) {
			$product_share_qzone_flag = false;
			trackingInfo.a = "share: 分享到QQ空间";
			trackingInfo.at = "click";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
		}
	});

	var $product_share_sinaminiblog = $("#product_share a:eq(2)"); // 获取分享列表
	var $product_share_sinaminiblog_flag = true;
	$product_share_sinaminiblog.click(function() {
		if ($product_share_sinaminiblog_flag) {
			$product_share_sinaminiblog_flag = false;
			trackingInfo.a = "share: 分享到新浪微博";
			trackingInfo.at = "click";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
		}
	});

	var $product_share_renren = $("#product_share a:eq(3)"); // 获取分享列表
	var $product_share_renren_flag = true;
	$product_share_renren.click(function() {
		if ($product_share_renren_flag) {
			$product_share_renren_flag = false;
			trackingInfo.a = "share: 分享到人人网";
			trackingInfo.at = "click";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
		}
	});

	var $product_share_qqmb = $("#product_share a:eq(4)"); // 获取分享列表
	var $product_share_qqmb_flag = true;
	$product_share_qqmb.click(function() {
		if ($product_share_qqmb_flag) {
			$product_share_qqmb_flag = false;
			trackingInfo.a = "share: 分享到腾讯微博";
			trackingInfo.at = "click";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
		}
	});
	
	var $product_share_neteasemb = $("#product_share a:eq(5)"); // 获取分享列表
	var $product_share_neteasemb_flag = true;
	$product_share_neteasemb.click(function() {
		if ($product_share_neteasemb_flag) {
			$product_share_neteasemb_flag = false;
			trackingInfo.a = "share: 分享到网易微博";
			trackingInfo.at = "click";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
		}
	});
	
	var $product_share_more_text = $("#product_share a:eq(0)"); // 获取分享列表
	var $product_share_more_text_flag = true;
	$product_share_more_text.mouseout(function() {
		if ($product_share_more_text_flag) {
			$product_share_more_text_flag = false;
			trackingInfo.a = "share: 分享到更多平台（文字）";
			trackingInfo.at = "mouseover";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
		}
	});
	
	var $product_share_more_icon = $("#product_share a:eq(6)"); // 获取分享列表
	var $product_share_more_icon_flag = true;
	$product_share_more_icon.mouseout(function() {
		if ($product_share_more_icon_flag) {
			$product_share_more_icon_flag = false;
			trackingInfo.a = "share: 分享到更多平台（图标）";
			trackingInfo.at = "mouseover";
			submitTracking(); // 将该事件认定为需追踪事件，提交至服务器
		}
	}); */

});