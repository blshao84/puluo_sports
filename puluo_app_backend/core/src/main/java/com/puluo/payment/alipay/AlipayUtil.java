package com.puluo.payment.alipay;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.puluo.api.event.EventRegistrationAPI;
import com.puluo.config.Configurations;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.payment.config.AlipayConfig;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class AlipayUtil {
	public static Log log = LogFactory.getLog(EventRegistrationAPI.class);

	public static String generateOrderID(PuluoPaymentOrder order, long base) {
		return String.valueOf((order.orderNumericID() + base));
	}

	public static long parseOrderID(String orderID, long base) {
		return (Long.parseLong(orderID) - base);
	}

	public static String generateDirectPayLink(PuluoPaymentOrder order) {
		String ALIPAY_GATEWAY_NEW = "https://mapi.alipay.com/gateway.do?";

		String outTradeNo = generateOrderID(order, Configurations.orderIDBase);
		String payment_type = "1";
		String subject = outTradeNo;
		String body = outTradeNo;
		String notify_url = "http://" + Configurations.webServer + "/payment/alipay/notify";
		String return_url = "https://" + Configurations.webServer + "/payment/alipay/notify";
		String seller_email = AlipayConfig.seller_email;
		String total_fee = Strs.prettyDouble(order.amount(), 2);
		String show_url = Configurations.webServer;
		HashMap<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("payment_type", payment_type);// 支付类型
		sParaTemp.put("notify_url", notify_url);// 必填，不能修改,服务器异步通知页面路径
		sParaTemp.put("return_url", return_url);// 页面跳转同步通知页面路径
		sParaTemp.put("seller_email", seller_email);// 卖家支付宝帐户
		sParaTemp.put("out_trade_no", outTradeNo);// 必填,商户订单号
		sParaTemp.put("subject", subject);
		sParaTemp.put("total_fee", total_fee);// 付款金额
		sParaTemp.put("body", body);
		sParaTemp.put("show_url", show_url);// 商品展示地址
		String link = AlipaySubmit.buildRequestLink(ALIPAY_GATEWAY_NEW,
				sParaTemp);
		log.info(String.format("支付宝支付链接:\n %s", link));
		return link;
	}

	public static String generateDirectWAPLink(PuluoPaymentOrder order,
			boolean mock) throws Exception {
		String out_trade_no = generateOrderID(order, Configurations.orderIDBase);
		String subject = out_trade_no;
		String notify_url = "http://" + Configurations.webServer + "/payment/alipay/notify";
		String return_url = "https://" + Configurations.webServer + "/payment/alipay/notify";
		String merchant_url = "";
		String total_fee = Strs.prettyDouble(order.amount(), 2);

		// 支付宝网关地址
		String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";

		// //////////////////////////////////调用授权接口alipay.wap.trade.create.direct获取授权码token//////////////////////////////////////

		// 返回格式
		String format = "xml";
		// 必填，不需要修改

		// 返回格式
		String v = "2.0";
		// 必填，不需要修改

		// 请求号
		String req_id = UtilDate.getOrderNum();
		// 必填，须保证每次请求都是唯一

		// req_data详细信息
		// 商户网站订单系统中唯一订单号，必填

		// 必填

		// 请求业务参数详细
		String req_dataToken = "<direct_trade_create_req><notify_url>"
				+ notify_url + "</notify_url><call_back_url>" + return_url
				+ "</call_back_url><seller_account_name>"
				+ AlipayConfig.seller_email
				+ "</seller_account_name><out_trade_no>" + out_trade_no
				+ "</out_trade_no><subject>" + subject
				+ "</subject><total_fee>" + total_fee
				+ "</total_fee><merchant_url>" + merchant_url
				+ "</merchant_url></direct_trade_create_req>";
		// 必填

		// ////////////////////////////////////////////////////////////////////////////////

		// 把请求参数打包成数组
		Map<String, String> sParaTempToken = new HashMap<String, String>();
		sParaTempToken.put("service", "alipay.wap.trade.create.direct");
		sParaTempToken.put("partner", AlipayConfig.partner);
		sParaTempToken.put("_input_charset", AlipayConfig.input_charset);
		sParaTempToken.put("sec_id", AlipayConfig.sign_type);
		sParaTempToken.put("format", format);
		sParaTempToken.put("v", v);
		sParaTempToken.put("req_id", req_id);
		sParaTempToken.put("req_data", req_dataToken);

		// 建立请求
		String sHtmlTextToken = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW,
				"", "", sParaTempToken);
		// URLDECODE返回的信息
		sHtmlTextToken = URLDecoder.decode(sHtmlTextToken,
				AlipayConfig.input_charset);
		// 获取token
		String request_token;
		if (mock)
			request_token = "";
		else
			request_token = AlipaySubmit.getRequestToken(sHtmlTextToken);
		// out.println(request_token);

		// //////////////////////////////////根据授权码token调用交易接口alipay.wap.auth.authAndExecute//////////////////////////////////////

		// 业务详细
		String req_data = "<auth_and_execute_req><request_token>"
				+ request_token + "</request_token></auth_and_execute_req>";
		// 必填

		// 把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
		sParaTemp.put("partner", AlipayConfig.partner);
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("sec_id", AlipayConfig.sign_type);
		sParaTemp.put("format", format);
		sParaTemp.put("v", v);
		sParaTemp.put("req_data", req_data);

		String link = AlipaySubmit.buildRequestLink(ALIPAY_GATEWAY_NEW,
				sParaTemp);
		log.info(String.format("支付宝支付链接:\n %s", link));
		return link;
	}
}
