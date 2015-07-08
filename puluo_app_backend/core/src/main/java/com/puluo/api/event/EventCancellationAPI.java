package com.puluo.api.event;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.OrderEvent;
import com.puluo.entity.payment.impl.OrderEventImpl;
import com.puluo.enumeration.OrderEventType;
import com.puluo.enumeration.PuluoOrderStatus;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.event.EventCancellationResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class EventCancellationAPI extends
		PuluoAPI<PuluoDSI, EventCancellationResult> {
	public static Log log = LogFactory.getLog(EventRegistrationAPI.class);

	public final String event_uuid;
	public final String user_uuid;
	
	public EventCancellationAPI(String event_uuid, String user_uuid) {
		this(event_uuid,user_uuid,DaoApi.getInstance());
	}
	public EventCancellationAPI(String event_uuid, String user_uuid,PuluoDSI dsi) {
		super();
		this.event_uuid = event_uuid;
		this.user_uuid = user_uuid;
		this.dsi = dsi;
	}

	@Override
	public void execute() {	
		PuluoPaymentDao paymentDao = dsi.paymentDao();
		PuluoPaymentOrder order = paymentDao.getOrderByEvent(event_uuid, user_uuid);
		if(order==null){
			this.error = ApiErrorResult.getError(53);
		} else if(!order.status().isPaid()) {
			this.error = ApiErrorResult.getError(54);
		} else {
			OrderEvent orderEvent = new OrderEventImpl(order.orderUUID(), OrderEventType.CancelOrderEvent);
			paymentDao.updateOrderStatus(order, PuluoOrderStatus.Cancel);
			dsi.orderEventDao().saveOrderEvent(orderEvent);
			this.rawResult = new EventCancellationResult(order.orderUUID(), true);
		}
	}
}
