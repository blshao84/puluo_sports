package com.puluo.api.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.payment.PuluoAlipayAPI;
import com.puluo.config.Configurations;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoCoupon;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoCouponImpl;
import com.puluo.entity.impl.PuluoPaymentOrderImpl;
import com.puluo.entity.payment.OrderEvent;
import com.puluo.entity.payment.impl.OrderEventImpl;
import com.puluo.entity.payment.impl.PuluoOrderStateMachine;
import com.puluo.enumeration.OrderEventType;
import com.puluo.enumeration.PuluoOrderStatus;
import com.puluo.payment.alipay.AlipayUtil;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.event.EventRegistrationResult;
import com.puluo.util.EventPriceCalculator;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class EventRegistrationAPI extends
		PuluoAPI<PuluoDSI, EventRegistrationResult> {
	public static Log log = LogFactory.getLog(EventRegistrationAPI.class);

	public final String event_uuid;
	public final String user_uuid;
	public final String coupon_uuid;
	public final boolean mock;

	public EventRegistrationAPI(String event_uuid, String user_uuid,
			boolean mock,PuluoDSI dsi) {
		this(event_uuid, user_uuid, null, mock, dsi);
	}
	
	public EventRegistrationAPI(String event_uuid, String user_uuid,
			boolean mock) {
		this(event_uuid, user_uuid, null, mock, DaoApi.getInstance());
	}

	public EventRegistrationAPI(String event_uuid, String user_uuid,
			String coupon_uuid, boolean mock) {
		this(event_uuid, user_uuid, coupon_uuid, mock, DaoApi.getInstance());
	}

	public EventRegistrationAPI(String event_uuid, String user_uuid,
			String coupon_uuid, boolean mock, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_uuid = event_uuid;
		this.user_uuid = user_uuid;
		this.coupon_uuid = coupon_uuid;
		this.mock = mock;
	}

	@Override
	public void execute() {
		PuluoPaymentOrder order = dsi.paymentDao().getOrderByEvent(event_uuid,
				user_uuid);
		if (order == null) {
			EventRegistrationResult result = createNewOrder();
			this.rawResult = result;
		} else {
			PuluoOrderStatus status = order.status();
			if (status.isCancel()) {
				log.error(String.format("订单(uuid is %s)已经被取消",
						order.orderUUID()));
				this.error = ApiErrorResult.getError(2);
				this.rawResult = null;
			} else if (status.isPaid()) {
				this.rawResult = new EventRegistrationResult("",
						order.orderUUID(), true);
			} else {
				EventRegistrationResult result = updateOrder(order);
				this.rawResult = result;
			}
		}
	}

	private EventRegistrationResult updateOrder(PuluoPaymentOrder order) {
		PuluoEvent event = dsi.eventDao().getEventByUUID(order.eventId());
		if (event.registeredUsers() >= event.capatcity()) {
			this.error = ApiErrorResult.getError(51);
			return null;
		}
		EventRegistrationResult result = null;
		PuluoOrderStatus status = order.status();
		String paymentLink;
		try {
			switch (status) {
			case Undefined:
				paymentLink = AlipayUtil.generateDirectWAPLink(order, mock);
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
				paymentLink = AlipayUtil.generateDirectWAPLink(order, mock);
				OrderEvent event3 = new OrderEventImpl(order.orderUUID(),
						OrderEventType.PayOrderEvent);
				dsi.orderEventDao().saveOrderEvent(event3);
				PuluoOrderStatus nextStatus2 = PuluoOrderStateMachine
						.nextState(order, event3);
				dsi.paymentDao().updateOrderStatus(order, nextStatus2);
				result = new EventRegistrationResult(paymentLink,
						order.orderUUID(), false);
				break;
			case Paying:
				paymentLink = AlipayUtil.generateDirectWAPLink(order, mock);
				result = new EventRegistrationResult(paymentLink,
						order.orderUUID(), false);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			log.error("更新订单状态发生错误");
		}
		return result;
	}

	private EventRegistrationResult createNewOrder() {
		PuluoEvent event = dsi.eventDao().getEventByUUID(event_uuid);
		if (event == null) {
			this.error = ApiErrorResult.getError(1);
			return null;
		} else if (event.registeredUsers() >= event.capatcity()) {
			this.error = ApiErrorResult.getError(51);
			return null;
		} else if (event.eventTime().isBefore(DateTime.now())){
			this.error = ApiErrorResult.getError(52);
			return null;
		}else {
			try {
				PuluoUser user = dsi.userDao().getByUUID(user_uuid);
				PuluoCoupon coupon = dsi.couponDao().getByCouponUUID(coupon_uuid);
				Double amount = EventPriceCalculator.calculate(event, coupon, user);
				DateTime paymentTime = DateTime.now();
				PuluoPaymentOrder order = new PuluoPaymentOrderImpl("", amount,
						paymentTime, user_uuid, event_uuid,
						PuluoOrderStatus.New);
				OrderEvent placeOrderEvent = new OrderEventImpl(
						order.orderUUID(), OrderEventType.PlaceOrderEvent);
				dsi.paymentDao().saveOrder(order);
				dsi.orderEventDao().saveOrderEvent(placeOrderEvent);
				PuluoPaymentOrder savedOrder = dsi.paymentDao().getOrderByUUID(
						order.orderUUID());
				OrderEvent payOrderEvent = new OrderEventImpl(
						order.orderUUID(), OrderEventType.PayOrderEvent);
				dsi.orderEventDao().saveOrderEvent(payOrderEvent);
				PuluoOrderStatus nextStatus2 = PuluoOrderStateMachine
						.nextState(savedOrder, payOrderEvent);
				dsi.paymentDao().updateOrderStatus(order, nextStatus2);
				if(coupon!=null){
					dsi.couponDao().updateCoupon(new PuluoCouponImpl(
							coupon.uuid(), 
							coupon.couponType(), 
							coupon.amount(),
							coupon.ownerUUID(), 
							savedOrder.orderUUID(), 
							coupon.locationUUID(),
							coupon.validUntil()));
				}
				if (amount != 0.0) {
					log.info(String.format("generating alipay order amount =%s",amount));
					String paymentLink = AlipayUtil.generateDirectWAPLink(
							savedOrder, mock);
					return new EventRegistrationResult(paymentLink,
							order.orderUUID(), false);
				} else {
					log.info(String.format("skipping alipay order amount =%s",amount));
					String out_trade_no = AlipayUtil.generateOrderID(
							savedOrder, Configurations.orderIDBase);
					PuluoAlipayAPI alipay = new PuluoAlipayAPI(
							new HashMap<String, String>(), null, out_trade_no,
							"DO NOT NEED A PAYMENT!", mock);
					alipay.processOrderWithZero();
					return new EventRegistrationResult("", order.orderUUID(),
							true);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("生成订单时发生未知错误");
				this.error = ApiErrorResult.getError(1);
				return null;
			}
		}
	}

	public String paymentLink() {
		if (rawResult == null) {
			return "";
		} else
			return rawResult.link;
	}
	
	public String orderUUID() {
		if (rawResult == null) {
			return "";
		} else
			return rawResult.order_uuid;
	}
}