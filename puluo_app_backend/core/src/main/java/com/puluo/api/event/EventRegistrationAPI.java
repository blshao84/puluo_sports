package com.puluo.api.event;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.EventRegistrationResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.OrderEvent;
import com.puluo.entity.payment.impl.OrderEventImpl;
import com.puluo.entity.payment.impl.OrderEventType;
import com.puluo.entity.payment.impl.PuluoOrderStateMachine;
import com.puluo.entity.payment.impl.PuluoOrderStatus;
import com.puluo.payment.alipay.AlipayUtil;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class EventRegistrationAPI extends
		PuluoAPI<PuluoDSI, EventRegistrationResult> {
	public static Log log = LogFactory.getLog(EventRegistrationAPI.class);

	public String event_uuid;
	public String user_uuid;

	public EventRegistrationAPI(String event_uuid) {
		this(event_uuid, DaoApi.getInstance());
	}

	public EventRegistrationAPI(String event_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_uuid = event_uuid;
	}

	@Override
	public void execute() {
		PuluoPaymentOrder order = dsi.paymentDao().getOrderByEvent(event_uuid);
		if (order == null) {
			EventRegistrationResult result = createNewOrder();
			this.rawResult = result;
		} else {
			if (order.userId() == user_uuid) {
				PuluoOrderStatus status = order.status();
				if (status.isCancel()) {
					log.error("订单(uuid={})已经被取消", order.orderUUID(),
							order.userId(), user_uuid);
					createErrorResult("系统支付错误", "订单已取消");
					this.rawResult = null;
				} else if (status.isPaid()) {
					this.rawResult = new EventRegistrationResult("",
							order.orderUUID(), true);
				} else {
					EventRegistrationResult result = updateOrder(order);
					this.rawResult = result;
				}
			} else {
				log.error("订单中的用户id={}与该用户id={}不匹配", order.userId(), user_uuid);
				createErrorResult("系统支付错误", "订单中的用户id与该用户不匹配");
				this.rawResult = null;
			}
		}
	}

	private EventRegistrationResult updateOrder(PuluoPaymentOrder order) {
		EventRegistrationResult result = null;
		PuluoOrderStatus status = order.status();
		String paymentLink;
		switch (status) {
		case Undefined:
			paymentLink = AlipayUtil.generateLink(order);
			OrderEvent event1 = new OrderEventImpl(order.orderUUID(),
					OrderEventType.PlaceOrderEvent);
			dsi.orderEventDao().saveOrderEvent(event1);
			OrderEvent event2 = new OrderEventImpl(order.orderUUID(),
					OrderEventType.PayOrderEvent);
			dsi.orderEventDao().saveOrderEvent(event2);
			List<OrderEvent> events = new ArrayList<OrderEvent>();
			events.add(event1);
			events.add(event2);
			PuluoOrderStatus nextStatus = PuluoOrderStateMachine.nextState(
					order, events);
			dsi.paymentDao().updateOrderStatus(order, nextStatus);
			result = new EventRegistrationResult(paymentLink,
					order.orderUUID(), false);
			break;
		case New:
			paymentLink = AlipayUtil.generateLink(order);
			OrderEvent event3 = new OrderEventImpl(order.orderUUID(),
					OrderEventType.PayOrderEvent);
			dsi.orderEventDao().saveOrderEvent(event3);
			PuluoOrderStatus nextStatus2 = PuluoOrderStateMachine.nextState(
					order, event3);
			dsi.paymentDao().updateOrderStatus(order, nextStatus2);
			result = new EventRegistrationResult(paymentLink,
					order.orderUUID(), false);
			break;
		case Paying:
			paymentLink = AlipayUtil.generateLink(order);
			result = new EventRegistrationResult(paymentLink,
					order.orderUUID(), false);
			break;
		default:
			break;
		}
		return result;
	}

	
	private EventRegistrationResult createNewOrder() {
		//PuluoPaymentOrder order = new P
		return null;
	}

	private void createErrorResult(String errorType, String errorDetail) {
		this.error = new ApiErrorResult(errorType, errorDetail, "");
	}
}