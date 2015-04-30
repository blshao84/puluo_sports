package com.puluo.test.dao;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.dao.PuluoEventLocationDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.entity.impl.PuluoEventInfoImpl;
import com.puluo.entity.impl.PuluoEventLocationImpl;
import com.puluo.enumeration.EventSortType;
import com.puluo.enumeration.EventStatus;
import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;
import com.puluo.enumeration.SortDirection;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class PuluoEventDaoTest {
	
	public static Log log = LogFactory.getLog(PuluoEventDaoTest.class);

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoEventInfoDao infoDao = DaoTestApi.eventInfoDevDao;
		infoDao.createTable();
		PuluoEventInfo info0 = new PuluoEventInfoImpl("event_info_uuid_0", "name_0", "description_0",
				"coach_name_0", "coach_uuid_0", "thumbnail_uuid_0", "details_0", 0, PuluoEventLevel.Level1, PuluoEventCategory.Others);
		infoDao.upsertEventInfo(info0);
		PuluoEventInfo info1 = new PuluoEventInfoImpl("event_info_uuid_1", "name_1", "description_1",
				"coach_name_0", "coach_uuid_0", "thumbnail_uuid_0", "details_0", 0, PuluoEventLevel.Level1, PuluoEventCategory.Others);
		infoDao.upsertEventInfo(info1);
		PuluoEventInfo info2 = new PuluoEventInfoImpl("event_info_uuid_2", "name_2", "description_1",
				"coach_name_0", "coach_uuid_0", "thumbnail_uuid_0", "details_0", 0, PuluoEventLevel.Level2, PuluoEventCategory.Others);
		infoDao.upsertEventInfo(info2);
		PuluoEventLocationDao locationDao = DaoTestApi.eventLocationDevDao;
		locationDao.createTable();
		PuluoEventLocation location0 = new PuluoEventLocationImpl("uuid_0",
				"address_0", "zip_0", "name_0", "phone_0", "city_0",
				1.0, 1.0, 0, 0, 0);
		locationDao.upsertEventLocation(location0);
		PuluoEventLocation location1 = new PuluoEventLocationImpl("uuid_1",
				"address_0", "zip_0", "name_0", "phone_0", "city_0",
				2.0, 1.0, 0, 0, 0);
		locationDao.upsertEventLocation(location1);
		PuluoEventLocation location2 = new PuluoEventLocationImpl("uuid_2",
				"address_0", "zip_0", "name_0", "phone_0", "city_0",
				4.0, 2.0, 0, 0, 0);
		locationDao.upsertEventLocation(location2);

		PuluoEventDao eventDao = DaoTestApi.eventDevDao;
		eventDao.createTable();
		PuluoEvent event0 = new PuluoEventImpl("event_uuid_0", TimeUtils.parseDateTime("2015-03-02 20:35:59"), EventStatus.Open, 
				0, 0, 300.00, 299.99, 
				"event_info_uuid_0", "uuid_0", 0, DaoTestApi.getInstance());
		eventDao.upsertEvent(event0);
		PuluoEvent event1 = new PuluoEventImpl("event_uuid_1", TimeUtils.parseDateTime("2015-03-01 20:35:59"), EventStatus.Open, 
				0, 0, 300.00, 199.99, 
				"event_info_uuid_1", "uuid_2", 1, DaoTestApi.getInstance());
		eventDao.upsertEvent(event1);
		PuluoEvent event2 = new PuluoEventImpl("event_uuid_2", TimeUtils.parseDateTime("2015-03-02 20:35:59"), EventStatus.Closed, 
				0, 0, 300.00, 99.99, 
				"event_info_uuid_2", "uuid_1", 2, DaoTestApi.getInstance());
		eventDao.upsertEvent(event2);
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.eventInfoDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		dao = (DalTemplate) DaoTestApi.eventLocationDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		dao = (DalTemplate) DaoTestApi.eventDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testFindEvents() {
		log.info("testFindEvents start!");
		PuluoEventDao eventDao = DaoTestApi.eventDevDao;
		
		List<PuluoEvent> events = eventDao.findEvents(null,null, null, null, null, null, 2.0, 2.0, 2, null, PuluoEventCategory.Others);
		Assert.assertEquals("距离在2以内的event应该有3个!", 3 , events.size());
		events = eventDao.findEvents(null,null, null, null, null, null, 2.0, 2.0, 1.5, null, PuluoEventCategory.Others);
		Assert.assertEquals("距离在1.5以内的event应该有2个!", 2 , events.size());
		events = eventDao.findEvents(null,null, null, null, null, null, 2.0, 2.0, 1.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("距离在1以内的event应该有1个!", 1 , events.size());
		events = eventDao.findEvents(null,null, null, null, null, null, 2.0, 2.0, 0.5, null, PuluoEventCategory.Others);
		Assert.assertEquals("距离在0.5以内的event应该有0个!", 0 , events.size());
		
		events = eventDao.findEvents(null,null, "1", null, null, null, 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("keyword包含1的event应该有2个!", 2 , events.size());
		events = eventDao.findEvents(null,null, "2", null, null, null, 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("keyword包含2的event应该有1个!", 1 , events.size());
		events = eventDao.findEvents(null,null, "3", null, null, null, 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("keyword包含3的event应该有0个!", 0 , events.size());
		//from date
		events = eventDao.findEvents(TimeUtils.parseDateTime("2015-02-01 20:35:59"),null, null, null, null, null, 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("time在2015-03-01 20:35:59的event应该有3个!", 3 , events.size());
		//to date
		events = eventDao.findEvents(null,TimeUtils.parseDateTime("2015-03-03 20:35:59"), null, null, null, null, 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("time在2015-03-02 20:35:59的event应该有2个!", 3 , events.size());
		
		//from and to date
		events = eventDao.findEvents(
				TimeUtils.parseDateTime("2015-02-27 20:35:59"),
				TimeUtils.parseDateTime("2015-03-02 10:35:59"), null, null, null, null, 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("time在2015-03-02 20:35:59的event应该有1个!", 1 , events.size());
		
		events = eventDao.findEvents(null,null, null, null, null, null, 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("所有event应该有3个!", 3 , events.size());

		events = eventDao.findEvents(null,null, null, null,EventSortType.Distance, SortDirection.valueOf("Asc"), 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("距离最近的event的id应该为event_uuid_0!", "event_uuid_0" , events.get(0).eventUUID());
		events = eventDao.findEvents(null,null, null, null, EventSortType.Distance, SortDirection.valueOf("Desc"), 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("距离最远的event的id应该为event_uuid_1!", "event_uuid_1" , events.get(0).eventUUID());
		
		events = eventDao.findEvents(null,null, null, null, EventSortType.Time, SortDirection.valueOf("Asc"), 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("时间最近的event的time应该为2015-03-01 20:35:59!", TimeUtils.parseDateTime("2015-03-01 20:35:59") , events.get(0).eventTime());
		events = eventDao.findEvents(null,null, null, null, EventSortType.Time, SortDirection.valueOf("Desc"), 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("时间最远的event的time应该为2015-03-02 20:35:59!", TimeUtils.parseDateTime("2015-03-02 20:35:59") , events.get(0).eventTime());
		
		events = eventDao.findEvents(null,null, null, null, EventSortType.Price, SortDirection.valueOf("Asc"), 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("价格最便宜的event的id应该为event_uuid_2!", "event_uuid_2" , events.get(0).eventUUID());
		events = eventDao.findEvents(null,null, null, null, EventSortType.Price, SortDirection.valueOf("Desc"), 0.0, 0.0, 0.0, null, PuluoEventCategory.Others);
		Assert.assertEquals("价格最昂贵的event的id应该为event_uuid_0!", "event_uuid_0" , events.get(0).eventUUID());

		events = eventDao.findEvents(TimeUtils.parseDateTime("2015-03-02 20:35:59"),null, "0", null, EventSortType.Price, SortDirection.valueOf("Asc"), 2.0, 2.0, 2, null, PuluoEventCategory.Others);
		Assert.assertEquals("符合条件event应该有1个!", 1 , events.size());
		events = eventDao.findEvents(TimeUtils.parseDateTime("2015-03-01 20:35:59"),null, "0", null, EventSortType.Distance, SortDirection.valueOf("Desc"), 2.0, 2.0, 2, null, PuluoEventCategory.Others);
		Assert.assertEquals("符合条件event应该有1个!", 1 , events.size());

		events = eventDao.findEvents(null,null, null, null, EventSortType.Price, SortDirection.Asc, 0.0, 0.0, 0.0, EventStatus.Open, PuluoEventCategory.Others);
		Assert.assertEquals("符合条件event应该有2个!", 2 , events.size());
		events = eventDao.findEvents(null,null, null, null, EventSortType.Price, SortDirection.Desc, 0.0, 0.0, 0.0, EventStatus.Full, PuluoEventCategory.Others);
		Assert.assertEquals("符合条件event应该有3个!", 3 , events.size());

		events = eventDao.findEvents(null,null, null, PuluoEventLevel.Level2, EventSortType.Price, SortDirection.Desc, 0.0, 0.0, 0.0, EventStatus.Full, PuluoEventCategory.Others);
		Assert.assertEquals("符合条件event应该有1个!", 1 , events.size());
		
		log.info("testFindEvents done!");
	}
	
	@Test
	public void testGetEventByUUID() {
		log.info("testGetEventByUUID start!");
		PuluoEventDao eventDao = DaoTestApi.eventDevDao;
		PuluoEvent event0 = new PuluoEventImpl("event_uuid_0", TimeUtils.parseDateTime("2015-03-02 20:35:59"), EventStatus.Open, 
				1, 1, 340.00, 249.99, 
				"event_info_uuid_0", "uuid_0", 0, DaoTestApi.getInstance());
		eventDao.upsertEvent(event0);
		event0 = eventDao.getEventByUUID("event_uuid_0");
		Assert.assertEquals("status应该为Open!", "Open" , event0.statusName());
		Assert.assertTrue("capatcity应该等于1!", 1==event0.capatcity());
		Assert.assertTrue("originalPrice应该等于340!", 340.00==event0.originalPrice());
		Assert.assertTrue("price应该等于249.99", 249.99==event0.price());
		log.info("testGetEventByUUID done!");
	}
	
	@Test
	public void testGetPopularEvents() {
		List<PuluoEvent> recommendations = DaoTestApi.eventDevDao.findPopularEvent(0);
		Assert.assertEquals("should be 1 events that has hottest greater than 0 and open", 1, recommendations.size());
		Assert.assertEquals(1, recommendations.get(0).hottest());
	}
}
