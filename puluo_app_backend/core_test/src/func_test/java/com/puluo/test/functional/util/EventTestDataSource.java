package com.puluo.test.functional.util;

import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoEventDaoImpl;
import com.puluo.dao.impl.PuluoEventInfoDaoImpl;
import com.puluo.dao.impl.PuluoEventLocationDaoImpl;
import com.puluo.dao.impl.PuluoEventMemoryDaoImpl;
import com.puluo.dao.impl.PuluoEventPosterDaoImpl;
import com.puluo.dao.impl.PuluoPaymentDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.entity.impl.PuluoEventInfoImpl;
import com.puluo.entity.impl.PuluoEventLocationImpl;
import com.puluo.entity.impl.PuluoEventMemoryImpl;
import com.puluo.entity.impl.PuluoEventPosterImpl;
import com.puluo.entity.impl.PuluoPaymentOrderImpl;
import com.puluo.enumeration.EventStatus;
import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;
import com.puluo.enumeration.PuluoEventPosterType;
import com.puluo.enumeration.PuluoOrderStatus;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class EventTestDataSource {
	public static Log log = LogFactory.getLog(EventTestDataSource.class);
	public String locID1 = "loc_1";
	public String locID2 = "loc_2";
	public String infoID1 = "event_info_1";
	public String infoID2 = "event_info_2";
	public String eventID1 = "event_1";
	public String eventID2 = "event_2";
	public String eventID3 = "event_3";
	public String eventID4 = "event_4";
	public String eventID5 = "event_5";
	public String eventID6 = "event_6";
	public String eventID7 = "event_7";
	public String memID1 = "mem_1";
	// public String memID2 = "mem_2";
	public String memID3 = "mem_3";
	public String memID4 = "mem_4";
	public String memID5 = "mem_5";
	public String posterID1 = "poster_1";
	public String posterID2 = "poster_2";
	public String mobile = "1234567";
	public String password = "abcdefg";
	public DateTime event_date_0601 = TimeUtils
			.parseDateTime("2016-06-01 13:30:00");
	public DateTime event_date_0602 = TimeUtils
			.parseDateTime("2016-06-02 10:30:00");
	public DateTime event_date_21000601 = TimeUtils
			.parseDateTime("2100-06-01 13:30:00");
	public DateTime event_date_21000602 = TimeUtils
			.parseDateTime("2100-06-02 10:30:00");
	public String userID;
	public String orderID;

	public EventTestDataSource(String prefix) {
		this.locID1 = prefix + "_" + prefix + "_" + "loc_1";
		this.locID2 = prefix + "_" + "loc_2";
		this.infoID1 = prefix + "_" + "event_info_1";
		this.infoID2 = prefix + "_" + "event_info_2";
		this.eventID1 = prefix + "_" + "event_1";
		this.eventID2 = prefix + "_" + "event_2";
		this.eventID3 = prefix + "_" + "event_3";
		this.eventID4 = prefix + "_" + "event_4";
		this.eventID5 = prefix + "_" + "event_5";
		this.eventID6 = prefix + "_" + "event_6";
		this.eventID7 = prefix + "_" + "event_7";
		this.memID1 = prefix + "_" + "mem_1";
		// this.memID2 = prefix + "_" + "mem_2";
		this.memID3 = prefix + "_" + "mem_3";
		this.memID4 = prefix + "_" + "mem_4";
		this.memID5 = prefix + "_" + "mem_5";
		this.posterID1 = prefix + "_" + "poster_1";
		this.posterID2 = prefix + "_" + "poster_2";
		this.mobile = prefix + "_" + "1234567";
		this.password = prefix + "_" + "abcdefg";
	}

	public void setupDB() {
		cleanupDB();
		PuluoDSI dsi = DaoApi.getInstance();
		dsi.userDao().save(mobile, password);
		PuluoUser user = dsi.userDao().getByMobile(mobile);
		dsi.userDao().updateProfile(user, "Lei", "Shi", "http://www.puluosports.com/logo.jpg", null, null, null, null, null, null, null, null);
		userID = user.userUUID();

		PuluoEventLocation location1 = new PuluoEventLocationImpl(locID1,
				"北京市国贸大厦250号", "110110", "国贸大厦3-1-2", "123456789", "北京",
				1234.567, 7654.321, 0, 15, 0);

		PuluoEventLocation location2 = new PuluoEventLocationImpl(locID2,
				"北京市尚都sohou 3-2-1", "110110", "普罗总部", "123456789", "北京",
				1234.537, 7654.341, 0, 20, 0);

		PuluoEventInfo eventInfo1 = new PuluoEventInfoImpl(infoID1, "臀部炸弹（测试）",
				"瘦臀（测试）、减脂（测试）", "Jerry Ass", "123456", "", "详细信息", 60, PuluoEventLevel.Level1, PuluoEventCategory.Others);

		PuluoEventInfo eventInfo2 = new PuluoEventInfoImpl(infoID2, "减脂集中营（测试）",
				"减脂（测试）", "Tom Fat", "7080234", "", "详细信息", 45, PuluoEventLevel.Level1, PuluoEventCategory.Others);

		PuluoEvent event1 = new PuluoEventImpl(eventID1, event_date_0601,
				EventStatus.Open, 3, 15, 80.0, 50.0,
				eventInfo1.eventInfoUUID(), location1.locationId(), 0);

		PuluoEvent event2 = new PuluoEventImpl(eventID2, event_date_0601,
				EventStatus.Open, 3, 15, 90.0, 90.0,
				eventInfo1.eventInfoUUID(), location2.locationId(), 0);

		PuluoEvent event3 = new PuluoEventImpl(eventID3, event_date_0602,
				EventStatus.Open, 3, 15, 100.0, 60.0,
				eventInfo2.eventInfoUUID(), location1.locationId(), 0);

		PuluoEvent event4 = new PuluoEventImpl(eventID4, event_date_0602,
				EventStatus.Closed, 3, 15, 150.0, 50.0,
				eventInfo2.eventInfoUUID(), location2.locationId(), 0);

		PuluoEvent event5 = new PuluoEventImpl(eventID5, event_date_21000602,
				EventStatus.Open, 3, 15, 150.0, 100.0,
				eventInfo1.eventInfoUUID(), location1.locationId(), 0);

		PuluoEvent event6 = new PuluoEventImpl(eventID6, event_date_21000602,
				EventStatus.Open, 3, 15, 150.0, 110.0,
				eventInfo1.eventInfoUUID(), location2.locationId(), 0);

		PuluoEvent event7 = new PuluoEventImpl(eventID7, event_date_21000602,
				EventStatus.Open, 3, 15, 150.0, 120.0,
				eventInfo2.eventInfoUUID(), location1.locationId(), 0);

		PuluoEventMemory mem1 = new PuluoEventMemoryImpl(memID1,
				"123.jpg", eventID4, user.userUUID(),
				null);
		PuluoEventMemory mem3 = new PuluoEventMemoryImpl(memID3,
				"789.jpg", eventID2, user.userUUID(),
				null);
		PuluoEventMemory mem4 = new PuluoEventMemoryImpl(memID4,
				"1234.jpg", eventID1, user.userUUID(),
				null);
		PuluoEventMemory mem5 = new PuluoEventMemoryImpl(memID5,
				"5678.jpg", eventID1, user.userUUID(),
				null);

		PuluoEventPoster poster1 = new PuluoEventPosterImpl(posterID1,
				"xyz.jpg", infoID1, DateTime.now(),PuluoEventPosterType.POSTER,1);

		PuluoEventPoster poster2 = new PuluoEventPosterImpl(posterID2,
				"abc.jpg", infoID2, DateTime.now(),PuluoEventPosterType.POSTER,2);
		
		PuluoPaymentOrder payment = new PuluoPaymentOrderImpl(UUID.randomUUID().toString(), 0.0,
				DateTime.now(), user.userUUID(), eventID1, PuluoOrderStatus.Paid);
		orderID = payment.orderUUID();

		dsi.eventInfoDao().saveEventInfo(eventInfo1);
		dsi.eventInfoDao().saveEventInfo(eventInfo2);
		dsi.eventLocationDao().saveEventLocation(location1);
		dsi.eventLocationDao().saveEventLocation(location2);
		dsi.eventDao().saveEvent(event1);
		dsi.eventDao().saveEvent(event2);
		dsi.eventDao().saveEvent(event3);
		dsi.eventDao().saveEvent(event4);
		dsi.eventDao().saveEvent(event5);
		dsi.eventDao().saveEvent(event6);
		dsi.eventDao().saveEvent(event7);
		dsi.eventMemoryDao().saveEventMemory(mem1);
		// dsi.eventMemoryDao().saveEventMemory(mem2);
		dsi.eventMemoryDao().saveEventMemory(mem3);
		dsi.eventMemoryDao().saveEventMemory(mem4);
		dsi.eventMemoryDao().saveEventMemory(mem5);
		dsi.eventPosterDao().saveEventPhoto(poster1);
		dsi.eventPosterDao().saveEventPhoto(poster2);
		dsi.paymentDao().saveOrder(payment);
		dump();

	}

	public void cleanupDB() {
		PuluoDSI dsi = DaoApi.getInstance();
		PuluoUserDaoImpl userDao = (PuluoUserDaoImpl) dsi.userDao();
		PuluoEventInfoDaoImpl eventInfoDao = (PuluoEventInfoDaoImpl) dsi
				.eventInfoDao();
		PuluoEventLocationDaoImpl eventLocationDao = (PuluoEventLocationDaoImpl) dsi
				.eventLocationDao();
		PuluoEventMemoryDaoImpl eventMemoryDao = (PuluoEventMemoryDaoImpl) dsi
				.eventMemoryDao();
		PuluoEventPosterDaoImpl eventPosterDao = (PuluoEventPosterDaoImpl) dsi
				.eventPosterDao();
		PuluoEventDaoImpl eventDao = (PuluoEventDaoImpl) dsi.eventDao();
		PuluoPaymentDaoImpl paymentDao = (PuluoPaymentDaoImpl) dsi.paymentDao();

		eventInfoDao.deleteByEventInfoUUID(infoID1);
		eventInfoDao.deleteByEventInfoUUID(infoID2);
		eventLocationDao.deleteByLocationUUID(locID1);
		eventLocationDao.deleteByLocationUUID(locID2);
		eventDao.deleteByEventUUID(eventID1);
		eventDao.deleteByEventUUID(eventID2);
		eventDao.deleteByEventUUID(eventID3);
		eventDao.deleteByEventUUID(eventID4);
		eventDao.deleteByEventUUID(eventID5);
		eventDao.deleteByEventUUID(eventID6);
		eventDao.deleteByEventUUID(eventID7);
		eventMemoryDao.deleteByMemoryUUID(memID1);
		// eventMemoryDao.deleteByMemoryUUID(memID2);
		eventMemoryDao.deleteByMemoryUUID(memID3);
		eventMemoryDao.deleteByMemoryUUID(memID4);
		eventMemoryDao.deleteByMemoryUUID(memID5);
		eventPosterDao.deleteByPosterUUID(posterID1);
		eventPosterDao.deleteByPosterUUID(posterID2);
		PuluoUser user = userDao.getByMobile(mobile);
		if (user != null)
			userDao.deleteByUserUUID(user.userUUID());
		paymentDao.deleteByOrderUUID(orderID);
	}

	public void dump() {
		PuluoDSI dsi = DaoApi.getInstance();
		PuluoEventInfoDaoImpl eventInfoDao = (PuluoEventInfoDaoImpl) dsi
				.eventInfoDao();
		PuluoEventLocationDaoImpl eventLocationDao = (PuluoEventLocationDaoImpl) dsi
				.eventLocationDao();
		PuluoEventMemoryDaoImpl eventMemoryDao = (PuluoEventMemoryDaoImpl) dsi
				.eventMemoryDao();
		PuluoEventPosterDaoImpl eventPosterDao = (PuluoEventPosterDaoImpl) dsi
				.eventPosterDao();
		PuluoEventDaoImpl eventDao = (PuluoEventDaoImpl) dsi.eventDao();
		PuluoPaymentDaoImpl paymentDao = (PuluoPaymentDaoImpl) dsi.paymentDao();
		log.info(dsi.userDao().getByMobile(mobile));
		log.info(eventInfoDao.getEventInfoByUUID(infoID1));
		log.info(eventInfoDao.getEventInfoByUUID(infoID2));
		log.info(eventLocationDao.getEventLocationByUUID(locID1));
		log.info(eventLocationDao.getEventLocationByUUID(locID2));
		log.info(eventDao.getEventByUUID(eventID1));
		log.info(eventDao.getEventByUUID(eventID2));
		log.info(eventDao.getEventByUUID(eventID3));
		log.info(eventDao.getEventByUUID(eventID4));
		log.info(eventDao.getEventByUUID(eventID5));
		log.info(eventDao.getEventByUUID(eventID6));
		log.info(eventDao.getEventByUUID(eventID7));
		log.info(eventMemoryDao.getEventMemoryByUUID(memID1));
		// log.info(eventMemoryDao.getEventMemoryByUUID(memID2));
		log.info(eventMemoryDao.getEventMemoryByUUID(memID3));
		log.info(eventMemoryDao.getEventMemoryByUUID(memID4));
		log.info(eventMemoryDao.getEventMemoryByUUID(memID5));
		log.info(eventPosterDao.getEventPosterByUUID(posterID1));
		log.info(eventPosterDao.getEventPosterByUUID(posterID2));
		log.info(paymentDao.getOrderByUUID(orderID));
	}

}
