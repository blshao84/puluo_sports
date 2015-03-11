package com.puluo.api.event;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.EventRegistrationResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.impl.PuluoPaymentOrderImpl;
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

	public EventRegistrationAPI(String event_uuid, String user_uuid) {
		this(event_uuid, user_uuid, DaoApi.getInstance());
	}

	public EventRegistrationAPI(String event_uuid, String user_uuid,
			PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_uuid = event_uuid;
		this.user_uuid = user_uuid;
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
					log.error("订单(uuid is {})已经被取消", order.orderUUID(),
							order.userId(), user_uuid);
					this.error = ApiErrorResult.getError(2);
					this.rawResult = null;
				} else if (status.isPaid()) {
					this.rawResult = new EventRegistrationResult("",
							order.orderUUID(), true);
				} else {
					EventRegistrationResult result = updateOrder(order);
					this.rawResult = result;
				}
			} else {
				log.error("订单中的用户id is {}与该用户id is {}不匹配", order.userId(),
						user_uuid);
				this.error = ApiErrorResult.getError(3);
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
		PuluoEvent event = dsi.eventDao().getEventByUUID(event_uuid);
		if (event == null) {
			this.error =  ApiErrorResult.getError(1);
			return null;
		} else {
			Double amount = event.price();
			DateTime paymentTime = DateTime.now();
			PuluoPaymentOrder order = new PuluoPaymentOrderImpl("", amount,
					paymentTime, user_uuid, event_uuid, PuluoOrderStatus.New);
			OrderEvent placeOrderEvent = new OrderEventImpl(order.orderUUID(),
					OrderEventType.PlaceOrderEvent);
			dsi.paymentDao().saveOrder(order);
			dsi.orderEventDao().saveOrderEvent(placeOrderEvent);
			PuluoPaymentOrder savedOrder = dsi.paymentDao().getOrderByUUID(
					order.orderUUID());
			String paymentLink = AlipayUtil.generateLink(savedOrder);
			OrderEvent payOrderEvent = new OrderEventImpl(order.orderUUID(),
					OrderEventType.PayOrderEvent);
			dsi.orderEventDao().saveOrderEvent(payOrderEvent);
			PuluoOrderStatus nextStatus2 = PuluoOrderStateMachine.nextState(
					savedOrder, payOrderEvent);
			dsi.paymentDao().updateOrderStatus(order, nextStatus2);
			return new EventRegistrationResult(paymentLink, order.orderUUID(),
					false);
		}
	}
}