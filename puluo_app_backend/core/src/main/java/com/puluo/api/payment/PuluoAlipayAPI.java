package com.puluo.api.payment;

import java.util.HashMap;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.AlipaymentResult;
import com.puluo.config.Configurations;
import com.puluo.dao.PuluoDSI;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.payment.OrderEvent;
import com.puluo.entity.payment.impl.OrderEventImpl;
import com.puluo.entity.payment.impl.OrderEventType;
import com.puluo.entity.payment.impl.PuluoOrderStatus;
import com.puluo.payment.alipay.AlipayNotify;
import com.puluo.payment.alipay.AlipayUtil;
import com.puluo.service.PuluoService;
import com.puluo.service.util.JuheSMSResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class PuluoAlipayAPI extends PuluoAPI<PuluoDSI, AlipaymentResult> {
	public static Log log = LogFactory.getLog(PuluoAlipayAPI.class);
	private final HashMap<String, String> params;
	private final String trade_status;
	private final String trade_id;
	private final String paymentRef;

	public PuluoAlipayAPI(HashMap<String, String> params, String trade_status,
			String trade_id, String paymentRef) {
		super();
		this.params = params;
		this.trade_status = trade_status;
		this.trade_id = trade_id;
		this.paymentRef = paymentRef;
	}

	@Override
	public void execute() {
		if (verifyRequest()) {
			processValidRequest();
		}
		return;
	}
	
	public void processOrderWithZero() {
		processValidRequest();
	}

	private void processValidRequest() {
		log.info(String.format("订单PaymentOrder=%s状态正常", orderNumericID()));
		// verifyRequest ensures order exists
		PuluoPaymentOrder order = dsi.paymentDao().getOrderByNumericID(
				orderNumericID());
		PuluoOrderStatus status = order.status();
		if (!status.isPaid()) {
			if (status != PuluoOrderStatus.Paying) {
				logErrorMessage(String.format("订单PaymentOrder=%s状态=%s异常，",
						orderNumericID(), status));
				sendSystemWarningEmail(order);
			} else {
				boolean successUpdate = updateOrderStatus(order);
				if (successUpdate) {
					if (Configurations.enableSMSNotification) {
						sendNotification(order);
					}
					setSuccessfulPaymentResult();
				} else {
					sendNotification(order);
					sendSystemWarningEmail(order);
					setSuccessfulPaymentResult();
				}
			}

		} else {
			setSuccessfulPaymentResult();
		}
	}

	private void sendSystemWarningEmail(PuluoPaymentOrder order) {
		String subject = "订单状态错误";
		String content = String.format("订单%s收到支付宝的通知，支付宝订单号为：%s,更新订单是发生错误。",
				order.orderUUID(), paymentRef);
		PuluoService.email.sendTextEmailToSupport("contact@puluosports.com",
				subject, content);
	}

	public AlipaymentResult getPaymentResult() {
		return this.rawResult;
	}

	private boolean updateOrderStatus(PuluoPaymentOrder order) {
		// FIXME: should check update status ...
		OrderEvent event = new OrderEventImpl(order.orderUUID(),
				OrderEventType.ConfirmOrderEvent);
		boolean successOrderEvent = dsi.orderEventDao().saveOrderEvent(event);
		boolean successOrderStatus = dsi.paymentDao().updateOrderStatus(order,
				PuluoOrderStatus.Paid);
		boolean successOrderPayment = dsi.paymentDao().updateOrderPaymentInfo(
				order, paymentRef);
		return successOrderEvent && successOrderPayment && successOrderStatus;
	}

	private void setSuccessfulPaymentResult() {
		this.rawResult = new AlipaymentResult(true, "");
	}

	private void sendNotification(PuluoPaymentOrder order) {
		log.info(String.format("为PaymentOrder=%s生成短信信息", order.orderUUID()));
		PuluoEvent event = dsi.eventDao().getEventByUUID(order.eventId());
		PuluoUser user = dsi.userDao().getByUUID(order.userId());
		JuheSMSResult smsResult = null;
		if (event != null && user != null) {
			String time = TimeUtils.formatDate(event.eventTime());
			String location = event.eventLocation().address();
			String name = event.eventInfo().name();
			smsResult = PuluoService.sms.sendConfirmationMessage(user.mobile(),
					time, location, name);
		} else {
			log.error(String.format("活动不存在,uuid=%s", order.eventId()));
			smsResult = PuluoService.sms.sendWarningConfirmationMessage(user
					.mobile());
		}
		if (!smsResult.isSuccess()) {
			log.error(String.format("发送短信至失败:%s:%s", user.mobile(),
					smsResult.toString()));
		}
	}

	private boolean verifyRequest() {
		boolean isParamValid;
		try {
			isParamValid = AlipayNotify.verifyNotify(params);
			if (isParamValid) {
				try {

					PuluoPaymentOrder order = dsi.paymentDao()
							.getOrderByNumericID(orderNumericID());
					if (order == null) {
						return logErrorMessage("无法根据支付宝返回的订单号找到系统中的订单");
					} else {
						if (!trade_status.equals("TRADE_FINISHED")
								&& !trade_status.equals("TRADE_SUCCESS")) {
							return logErrorMessage(String.format(
									"PaymentOrder的状态错误(id=%s,status-%s)",
									orderNumericID(), trade_status));
						} else {
							return true;
						}
					}
				} catch (Exception e) {
					return logErrorMessage("支付宝返回的trade id错误");
				}
			} else {
				return logErrorMessage("支付宝返回参数没有通过验证");
			}
		} catch (Exception e1) {
			logErrorMessage("支付宝返回参数没有通过验证");
			return false;
		}
	}

	private boolean logErrorMessage(String msg) {
		log.error(msg);
		log.error(params);
		rawResult = new AlipaymentResult(false, msg);
		return false;
	}

	private long orderNumericID() {
		return AlipayUtil.parseOrderID(trade_id, Configurations.orderIDBase);
	}
}
