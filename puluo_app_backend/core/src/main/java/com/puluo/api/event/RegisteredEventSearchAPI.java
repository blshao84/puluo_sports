package com.puluo.api.event;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.result.event.EventSearchResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class RegisteredEventSearchAPI extends PuluoAPI<PuluoDSI, EventSearchResult> {
	public static Log log = LogFactory.getLog(RegisteredEventSearchAPI.class);
	public String userUUID;
	public List<PuluoEvent> searchedEvents;
	
	public RegisteredEventSearchAPI(String userUUID) {
		this(userUUID, DaoApi.getInstance());
	}

	public RegisteredEventSearchAPI(String userUUID, PuluoDSI dsi) {
		this.userUUID = userUUID;
		this.dsi = dsi;
	}

	@Override
	public void execute() {
		log.info(String.format("开始根据用户%s已注册的活动", userUUID));
		PuluoPaymentDao payment_dao = dsi.paymentDao();
		List<PuluoPaymentOrder> orders = payment_dao.getPaidOrdersByUserUUID(userUUID);
		searchedEvents = new ArrayList<PuluoEvent>();
		PuluoEventDao event_dao = dsi.eventDao();
		for (PuluoPaymentOrder order: orders) {
			searchedEvents.add(event_dao.getEventByUUID(order.eventId()));
		}
		log.info(String.format("找到%d个已注册活动", searchedEvents.size()));
		EventSearchResult result = new EventSearchResult();
		result.setSearchResult(searchedEvents);
		rawResult = result;
	}
}
