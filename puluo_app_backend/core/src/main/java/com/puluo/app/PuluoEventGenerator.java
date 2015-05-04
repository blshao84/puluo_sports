package com.puluo.app;

import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.dao.PuluoEventPosterDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.entity.impl.PuluoEventInfoImpl;
import com.puluo.entity.impl.PuluoEventPosterImpl;
import com.puluo.enumeration.EventStatus;
import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;

public class PuluoEventGenerator {
	public static PuluoUser coach1 = DaoApi.getInstance().userDao().getByMobile("18646655333");
	public static PuluoUser coach2 = DaoApi.getInstance().userDao().getByMobile("18521564305");
	public static PuluoEventLocation location1 = DaoApi.getInstance().eventLocationDao().getEventLocationByUUID("89512a40-d6cc-468e-aa50-b037405f8e3b");
	public static PuluoEventLocation location2 = DaoApi.getInstance().eventLocationDao().getEventLocationByUUID("dfc60ac8-0638-44fc-9f54-9ff84f582a41");
	
	public static void main(String[] args) {
		generateWeeklyEvents(new DateTime(2014,4,27,0,0,0,0));
		generateWeeklyEvents(new DateTime(2014,4,27,0,0,0,0).plusWeeks(1));
		generateWeeklyEvents(new DateTime(2014,4,27,0,0,0,0).plusWeeks(2));

	}
	
	public static void generateWeeklyEvents(DateTime dt) {

		
		PuluoEventInfo info1 = new PuluoEventInfoImpl(
				UUID.randomUUID().toString(), 
				"cycling", 
				"crazy cycling", 
				coach1.name(), 
				coach1.userUUID(), 
				coach1.thumbnailURL(), 
				"cycling", 
				60, 
				PuluoEventLevel.Level1, 
				PuluoEventCategory.Others);
		PuluoEventInfo info2 = new PuluoEventInfoImpl(
				UUID.randomUUID().toString(), 
				"kendo", 
				"interesting kendo", 
				coach2.name(), 
				coach2.userUUID(), 
				coach2.thumbnailURL(), 
				"kendo", 
				45, 
				PuluoEventLevel.Level2, 
				PuluoEventCategory.Others);
		PuluoEventInfo info3 = new PuluoEventInfoImpl(
				UUID.randomUUID().toString(), 
				"pilates", 
				"funny pilates", 
				coach1.name(), 
				coach1.userUUID(), 
				coach1.thumbnailURL(), 
				"pilates", 
				30, 
				PuluoEventLevel.Level1, 
				PuluoEventCategory.Others);
		PuluoEventInfo info4 = new PuluoEventInfoImpl(
				UUID.randomUUID().toString(), 
				"taekwondo", 
				"boring taekwondo", 
				coach2.name(), 
				coach2.userUUID(), 
				coach2.thumbnailURL(), 
				"taekwondo", 
				60, 
				PuluoEventLevel.Level2, 
				PuluoEventCategory.Others);
		PuluoEventInfo info5 = new PuluoEventInfoImpl(
				UUID.randomUUID().toString(), 
				"trx", 
				"strong trx", 
				coach1.name(), 
				coach1.userUUID(), 
				coach1.thumbnailURL(), 
				"trx", 
				45, 
				PuluoEventLevel.Level1, 
				PuluoEventCategory.Others);
		PuluoEventInfo info6 = new PuluoEventInfoImpl(
				UUID.randomUUID().toString(), 
				"yoga", 
				"beautiful yoga", 
				coach2.name(), 
				coach2.userUUID(), 
				coach2.thumbnailURL(), 
				"yoga", 
				30, 
				PuluoEventLevel.Level2, 
				PuluoEventCategory.Others);
		PuluoEventInfo info7 = new PuluoEventInfoImpl(
				UUID.randomUUID().toString(), 
				"zumba", 
				"lovely zumba", 
				coach1.name(), 
				coach1.userUUID(), 
				coach1.thumbnailURL(), 
				"zumba", 
				60, 
				PuluoEventLevel.Level1, 
				PuluoEventCategory.Others);
		PuluoEventInfoDao infoDao = DaoApi.getInstance().eventInfoDao();
		//infoDao.createTable();
		infoDao.saveEventInfo(info1);
		infoDao.saveEventInfo(info2);
		infoDao.saveEventInfo(info3);
		infoDao.saveEventInfo(info4);
		infoDao.saveEventInfo(info5);
		infoDao.saveEventInfo(info6);
		infoDao.saveEventInfo(info7);
		
		PuluoEvent event1 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getAM(dt), 
				EventStatus.Open, 
				0, 
				20, 
				80.0, 
				50.0, 
				info1.eventInfoUUID(), 
				location1.locationId(), 
				0);
		PuluoEvent event2 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getPM(dt), 
				EventStatus.Open, 
				0, 
				15, 
				80.0, 
				45.0, 
				info2.eventInfoUUID(), 
				location2.locationId(), 
				0);
		
		PuluoEvent event3 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getAM(dt.plusDays(1)), 
				EventStatus.Open, 
				0, 
				20, 
				80.0, 
				55.0, 
				info3.eventInfoUUID(), 
				location1.locationId(), 
				0);
		PuluoEvent event4 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getPM(dt.plusDays(1)), 
				EventStatus.Open, 
				0, 
				15, 
				80.0, 
				60.0, 
				info4.eventInfoUUID(), 
				location2.locationId(), 
				0);
		
		PuluoEvent event5 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getAM(dt.plusDays(2)), 
				EventStatus.Open, 
				0, 
				20, 
				80.0, 
				65.0, 
				info5.eventInfoUUID(), 
				location1.locationId(), 
				0);
		PuluoEvent event6 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getPM(dt.plusDays(2)), 
				EventStatus.Open, 
				0, 
				15, 
				80.0, 
				70.0, 
				info6.eventInfoUUID(), 
				location2.locationId(), 
				0);
		
		PuluoEvent event7 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getAM(dt.plusDays(3)), 
				EventStatus.Open, 
				0, 
				20, 
				80.0, 
				75.0, 
				info7.eventInfoUUID(), 
				location1.locationId(), 
				0);
		PuluoEvent event8 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getPM(dt.plusDays(3)), 
				EventStatus.Open, 
				0, 
				15, 
				80.0, 
				40.0, 
				info1.eventInfoUUID(), 
				location2.locationId(), 
				0);
		
		PuluoEvent event9 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getAM(dt.plusDays(4)), 
				EventStatus.Open, 
				0, 
				20, 
				80.0, 
				40.0, 
				info2.eventInfoUUID(), 
				location1.locationId(), 
				0);
		PuluoEvent event10 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getPM(dt.plusDays(4)), 
				EventStatus.Open, 
				0, 
				15, 
				80.0, 
				35.0, 
				info3.eventInfoUUID(), 
				location2.locationId(), 
				0);
		
		PuluoEvent event11 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getAM(dt.plusDays(5)), 
				EventStatus.Open, 
				0, 
				20, 
				80.0, 
				30.0, 
				info4.eventInfoUUID(), 
				location1.locationId(), 
				0);
		PuluoEvent event12 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getPM(dt.plusDays(5)), 
				EventStatus.Open, 
				0, 
				15, 
				80.0, 
				50.0, 
				info6.eventInfoUUID(), 
				location2.locationId(), 
				0);
		
		PuluoEvent event13 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getAM(dt.plusDays(6)), 
				EventStatus.Open, 
				0, 
				20, 
				80.0, 
				50.0, 
				info7.eventInfoUUID(), 
				location1.locationId(), 
				0);
		PuluoEvent event14 = new PuluoEventImpl(
				UUID.randomUUID().toString(), 
				getPM(dt.plusDays(6)), 
				EventStatus.Open, 
				0, 
				15, 
				80.0, 
				50.0, 
				info1.eventInfoUUID(), 
				location2.locationId(), 
				0);
		PuluoEventDao eventDao = DaoApi.getInstance().eventDao();
		//eventDao.createTable();
		eventDao.saveEvent(event1);
		eventDao.saveEvent(event2);
		eventDao.saveEvent(event3);
		eventDao.saveEvent(event4);
		eventDao.saveEvent(event5);
		eventDao.saveEvent(event6);
		eventDao.saveEvent(event7);
		eventDao.saveEvent(event8);
		eventDao.saveEvent(event9);
		eventDao.saveEvent(event10);
		eventDao.saveEvent(event11);
		eventDao.saveEvent(event12);
		eventDao.saveEvent(event13);
		eventDao.saveEvent(event14);
		
		PuluoEventPoster poster11 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "dcddcc53-c348-4568-9274-97c400f29c5b.jpg", info1.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster12 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "134c059a-7437-4de5-ba3d-f61839628cea.jpg", info1.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster13 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "c95bbdaa-34df-433f-a2f9-ed6c8c77df61.jpg", info1.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster14 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "78255c46-6aeb-4aa7-9378-8bcc011e94e5.jpg", info1.eventInfoUUID(), DateTime.now());
		
		PuluoEventPoster poster21 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "6181964f-ed5e-4c3a-82de-90dd94dbba30.jpg", info2.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster22 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "de769d22-aa0f-4869-9921-37b1375ef752.JPG", info2.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster23 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "a8eecd74-cc0e-498d-a872-1faa6a6380f1.jpg", info2.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster24 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "08134564-8f4c-425f-8c7c-b5faee9d367a.jpg", info2.eventInfoUUID(), DateTime.now());
		
		PuluoEventPoster poster31 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "0b6d9f58-4017-4e3c-8397-b497d8dbde81.jpeg", info3.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster32 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "ec780169-2970-419c-87e1-44f30685cc78.jpg", info3.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster33 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "f61fa44a-f583-4cc2-93ee-e3818c2d2671.jpg", info3.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster34 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "9b5a7285-8111-4f97-88b0-bc869dd887de.jpg", info3.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster35 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "4a8be1e2-889d-4845-8968-9682807af68f.jpg", info3.eventInfoUUID(), DateTime.now());
		
		PuluoEventPoster poster41 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "152b7961-5905-4b73-81cb-8fbee3267794.jpg", info4.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster42 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "d078c73e-0863-45f3-bd20-594891b57722.jpg", info4.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster43 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "ba30569e-d65a-4ca9-8bcd-527cecd85a48.jpg", info4.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster44 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "ec7233d8-4486-4692-ac7e-dcd3eda7b68e.jpg", info4.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster45 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "912786df-7396-42b4-9801-ee010309fd3c.JPG", info4.eventInfoUUID(), DateTime.now());

		PuluoEventPoster poster51 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "7e8f086c-ede1-4243-af96-cd4427611ad3.jpeg", info5.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster52 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "c478355e-ce1b-443b-92c3-ab5c79f1f632.jpg", info5.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster53 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "fe89067b-b153-4952-b35b-ff97aecf3b87.jpg", info5.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster54 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "d2b4a9cf-2a01-4457-86a3-5cd1f4a030f0.jpg", info5.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster55 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "b1d11084-be0c-47bb-9b0f-ca7efdf4693f.jpg", info5.eventInfoUUID(), DateTime.now());
		
		PuluoEventPoster poster61 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "4fc064b5-adb0-47f5-a0a0-ebca6b4ea68b.jpg", info6.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster62 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "8950aac6-b6cc-4660-be6a-28f888d49985.jpeg", info6.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster63 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "d0ca2c84-4f3c-41f2-a0a7-d44830d3c6be.jpg", info6.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster64 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "e9c633a3-75ad-4d00-a010-b5119be6ec6d.jpg", info6.eventInfoUUID(), DateTime.now());
		
		PuluoEventPoster poster71 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "4fc074b5-adb0-47f5-a0a0-ebca7b4ea78b.jpg", info7.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster72 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "8950aac7-b7cc-4770-be7a-28f888d49985.jpeg", info7.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster73 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "d0ca2c84-4f3c-41f2-a0a7-d44830d3c7be.jpg", info7.eventInfoUUID(), DateTime.now());
		PuluoEventPoster poster74 = new PuluoEventPosterImpl(
				UUID.randomUUID().toString(), "e9c733a3-75ad-4d00-a010-b5119be7ec7d.jpg", info7.eventInfoUUID(), DateTime.now());
		
		PuluoEventPosterDao posterDao = DaoApi.getInstance().eventPosterDao();
		//posterDao.createTable();
		posterDao.saveEventPhoto(poster11);
		posterDao.saveEventPhoto(poster12);
		posterDao.saveEventPhoto(poster13);
		posterDao.saveEventPhoto(poster14);
		
		posterDao.saveEventPhoto(poster21);
		posterDao.saveEventPhoto(poster22);
		posterDao.saveEventPhoto(poster23);
		posterDao.saveEventPhoto(poster24);
		
		posterDao.saveEventPhoto(poster31);
		posterDao.saveEventPhoto(poster32);
		posterDao.saveEventPhoto(poster33);
		posterDao.saveEventPhoto(poster34);
		posterDao.saveEventPhoto(poster35);
		
		posterDao.saveEventPhoto(poster41);
		posterDao.saveEventPhoto(poster42);
		posterDao.saveEventPhoto(poster43);
		posterDao.saveEventPhoto(poster44);
		posterDao.saveEventPhoto(poster45);
		
		posterDao.saveEventPhoto(poster51);
		posterDao.saveEventPhoto(poster52);
		posterDao.saveEventPhoto(poster53);
		posterDao.saveEventPhoto(poster54);
		posterDao.saveEventPhoto(poster55);
		
		posterDao.saveEventPhoto(poster61);
		posterDao.saveEventPhoto(poster62);
		posterDao.saveEventPhoto(poster63);
		posterDao.saveEventPhoto(poster64);
		
		posterDao.saveEventPhoto(poster71);
		posterDao.saveEventPhoto(poster72);
		posterDao.saveEventPhoto(poster73);
		posterDao.saveEventPhoto(poster74);
		
		

	}
	
	public static DateTime getAM(DateTime dt) {
		return new DateTime(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),12,30,0,0);
	}
	
	public static DateTime getPM(DateTime dt) {
		return new DateTime(dt.getYear(),dt.getMonthOfYear(),dt.getDayOfMonth(),7,30,0,0);
	}

}
