package com.puluo.entity.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.PuluoUser;
import com.puluo.enumeration.EventStatus;

public class PuluoEventImpl implements PuluoEvent {
	private final String uuid;
	private final DateTime event_time;
	private final EventStatus status; // TODO: should be strong typed
	// private final String[] images;
	// private final String[] memories;
	private final int registeredUsers;
	private final int capatcity;
	private final Double price;
	private final Double discountedPrice;
	private final String info_uuid;
	private final String location_uuid;
	private final int hottest;

	private final PuluoDSI dsi;

	public PuluoEventImpl(String uuid) {

		this.uuid = uuid;
		this.event_time = null;
		this.status = null;
		this.registeredUsers = 0;
		this.capatcity = 0;
		this.price = 0.0;
		this.discountedPrice = 0.0;
		this.info_uuid = null;
		this.location_uuid = null;
		this.hottest = 1;
		this.dsi = null;
	}
	
	public PuluoEventImpl(String uuid, DateTime event_time, EventStatus status,
			int registeredUsers, int capatcity, Double price,
			Double discountedPrice, String info_uuid, String location_uuid, int hottest,
			PuluoDSI dsi) {

		this.uuid = uuid;
		this.event_time = event_time;
		this.status = status;
		// this.images = images;
		// this.memories = memories;
		this.registeredUsers = registeredUsers;
		this.capatcity = capatcity;
		this.price = price;
		this.discountedPrice = discountedPrice;
		this.info_uuid = info_uuid;
		this.location_uuid = location_uuid;
		this.hottest = hottest;
		this.dsi = dsi;
	}

	public PuluoEventImpl(String uuid, DateTime event_time, EventStatus status,
			int registeredUsers, int capatcity, Double price,
			Double discountedPrice, String info_uuid, String location_uuid, int hottest) {

		this(uuid, event_time, status, registeredUsers, capatcity, price,
				discountedPrice, info_uuid, location_uuid, hottest, DaoApi.getInstance());
	}

	@Override
	public String eventUUID() {
		return uuid;
	}

	@Override
	public DateTime eventTime() {
		return event_time;
	}

	@Override
	public String statusName() {
		return status == null ? "" : status.name();
	}

	@Override
	public int registeredUsers() {
		return registeredUsers;
	}

	@Override
	public int capatcity() {
		return capatcity;
	}

	@Override
	public Double price(){
		if(discountedPrice < price && discountedPrice>0) {
			return discountedPrice;
		} else return price;
	}
	@Override
	public Double originalPrice() {
		return price;
	}

	@Override
	public Double discount() {
		return discountedPrice / price;
	}

	@Override
	public Double discountedPrice() {
		return discountedPrice;
	}

	@Override
	public PuluoEventInfo eventInfo() {
		return dsi.eventInfoDao().getEventInfoByUUID(info_uuid);
	}

	@Override
	public PuluoEventLocation eventLocation() {
		return dsi.eventLocationDao().getEventLocationByUUID(location_uuid);
	}

	@Override
	public List<PuluoEventMemory> memory() {
		return dsi.eventMemoryDao().getEventMemoryByEventUUID(uuid);
	}

	@Override
	public int hottest() {
		return hottest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PuluoEventImpl other = (PuluoEventImpl) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	@Override
	public List<PuluoEventAttendee> attendees() {
		List<PuluoPaymentOrder> orders = dsi.paymentDao().getOrderByEvent(uuid);
		List<PuluoEventAttendee> attendees = new ArrayList<PuluoEventAttendee>();
		PuluoUser user;
		for (PuluoPaymentOrder order: orders) {
			if (order.status().isPaid()) {
				user = dsi.userDao().getByUUID(order.userId());
				attendees.add(new PuluoEventAttendee(user.name(), user.userUUID(), user.thumbnail()));
			}
		}
		return attendees;
	}

	@Override
	public boolean registered(String userUUID) {
		PuluoPaymentOrder order = dsi.paymentDao().getOrderByEvent(uuid, userUUID);
		if (order!=null && order.status().isPaid()) {
			return true;
		}
		return false;
	}
}
