package com.puluo.payment.alipay;

import java.util.HashMap;

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

	public static String generateLink(PuluoPaymentOrder order) {
		String outTradeNo = generateOrderID(order, Configurations.orderIDBase);
		String payment_type = "1";
		String subject = outTradeNo;
		String body = outTradeNo;
		String notify_url = Configurations.webServer + "/payment/alipay/notify";
		String return_url = Configurations.webServer +"/payment/alipay/return";
		String seller_email = AlipayConfig.email;
		String total_fee = Strs.prettyDouble(order.amount(),1);
		String show_url = Configurations.webServer;
	    HashMap<String,String> sParaTemp = new HashMap<String, String>();
	    sParaTemp.put("service", "create_direct_pay_by_user"); 
	    sParaTemp.put("partner", AlipayConfig.partner);
	    sParaTemp.put("_input_charset", AlipayConfig.input_charset);
	    sParaTemp.put("payment_type", payment_type);//支付类型
	    sParaTemp.put("notify_url", notify_url);//必填，不能修改,服务器异步通知页面路径
	    sParaTemp.put("return_url", return_url);//页面跳转同步通知页面路径
	    sParaTemp.put("seller_email", seller_email);//卖家支付宝帐户
	    sParaTemp.put("out_trade_no", outTradeNo);//必填,商户订单号
	    sParaTemp.put("subject", subject);
	    sParaTemp.put("total_fee", total_fee);//付款金额
	    sParaTemp.put("body", body);
	    sParaTemp.put("show_url", show_url);//商品展示地址
	    String link = AlipaySubmit.buildRequestLink(sParaTemp);
		log.info(String.format("支付宝支付链接:\n %s",link)); 		 
		return link;
	}
}
