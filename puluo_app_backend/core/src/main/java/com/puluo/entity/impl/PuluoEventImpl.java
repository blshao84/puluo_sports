package com.puluo.entity.impl;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoEventMemory;
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
	public Double price() {
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
}
