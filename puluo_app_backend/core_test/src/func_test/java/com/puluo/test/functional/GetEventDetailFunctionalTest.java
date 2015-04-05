package com.puluo.test.functional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.EventTestDataSource;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class GetEventDetailFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory
			.getLog(GetEventDetailFunctionalTest.class);
	private static EventTestDataSource dataSource = new EventTestDataSource("event_detail");

	@BeforeClass
	public static void setupDB() {
		dataSource.setupDB();
	}

	@AfterClass
	public static void cleanupDB() {
		dataSource.cleanupDB();
	}

	@Test
	public void testExistingEvent1() {
		testEvent(dataSource.eventID1);
	}
	@Test
	public void testExistingEvent2() {
		testEvent(dataSource.eventID2);
	}
	@Test
	public void testExistingEvent3() {
		testEvent(dataSource.eventID3);
	}
	@Test
	public void testExistingEvent4() {
		testEvent(dataSource.eventID4);
	}

	@Test
	public void testNonExistingEvent() {
		String session = login(dataSource.mobile, dataSource.password);
		if (session != null) {
			String inputs = String.format("{\"token\":\"%s\"}", session);
			try {
				JsonNode json2 = callAPI("events/detail/1234567", inputs);
				String error = super.getStringFromJson(json2, "id");
				Assert.assertEquals(
						"get non existing event should return error id 28",
						"28", error);

			} catch (UnirestException e) {
				e.printStackTrace();
				Assert.fail("fail to logout");
			}
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));
		} else
			Assert.fail("fail to login");
	}

	private void testEvent(String eventID){
		String session = login(dataSource.mobile, dataSource.password);
		if (session != null) {
			String inputs = String.format("{\"token\":\"%s\"}", session);
			try {
				JsonNode json = callAPI("events/detail/" + eventID, inputs);
				PuluoEvent event = DaoApi.getInstance().eventDao()
						.getEventByUUID(eventID);
				compareEvent(json, event);
			} catch (UnirestException e) {
				e.printStackTrace();
				Assert.fail("fail to logout");
			}
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));
		} else
			Assert.fail("fail to login");

	}

	private void compareEvent(JsonNode json, PuluoEvent event) {
		String phone = super.getStringFromJson(json, "phone");
		String status = super.getStringFromJson(json, "status");
		String city = super.getStringFromJson(json, "city");
		String details = super.getStringFromJson(json, "details");
		String address = super.getStringFromJson(json, "address");
		String coach_uuid = super.getStringFromJson(json, "coach_uuid");
		String capacity = super.getStringFromJson(json, "capacity");
		String registered_users = super.getStringFromJson(json,
				"registered_users");
		String event_name = super.getStringFromJson(json, "event_name");
		String coach_name = super.getStringFromJson(json, "coach_name");
		Set<String> thumbnail = new HashSet<String>(
				super.getStringArrayFromJson(json, "thumbnail"));
		Set<String> images = new HashSet<String>(super.getStringArrayFromJson(
				json, "images"));
		String longitude = super.getStringFromJson(json, "geo_location",
				"longitude");
		String latitude = super.getStringFromJson(json, "geo_location",
				"latitude");

		log.info(String.format("values extracted from json:\n" + "phone:%s,\n"
				+ "status:%s,\n" + "city:%s\n" + "details:%s\n"
				+ "address:%s\n" + "coach_uuid:%s\n" + "capacity:%s\n"
				+ "registered_users:%s\n" + "event_name:%s\n"
				+ "coach_name:%s\n" + "thumbnail:%s\n" + "images:%s\n"
				+ "longitude:%s\n" + "lattitude:%s\n", phone, status, city,
				details, address, coach_uuid, capacity, registered_users,
				event_name, coach_name, thumbnail, images, longitude, latitude));
		PuluoDSI dsi = DaoApi.getInstance();
		PuluoEventInfo info = event.eventInfo();
		PuluoEventLocation loc = event.eventLocation();
		List<PuluoEventPoster> posters = dsi.eventPosterDao()
				.getEventPosterByInfoUUID(info.eventInfoUUID());
		List<PuluoEventMemory> memories = dsi.eventMemoryDao()
				.getEventMemoryByEventUUID(event.eventUUID());
		Set<String> posterThumbnails = new HashSet<String>();
		Set<String> memoryImages = new HashSet<String>();
		for (PuluoEventPoster p : posters) {
			posterThumbnails.add(p.thumbnail());
		}
		for (PuluoEventMemory m : memories) {
			memoryImages.add(m.imageURL());
		}
		Assert.assertEquals(phone, loc.phone());
		Assert.assertEquals(status, event.statusName());
		Assert.assertEquals(city, loc.city());
		Assert.assertEquals(details, info.details());
		Assert.assertEquals(address, loc.address());
		Assert.assertEquals(coach_uuid, info.coachUUID());
		Assert.assertEquals(capacity, "" + event.capatcity());
		Assert.assertEquals(registered_users, "" + event.registeredUsers());
		Assert.assertEquals(event_name, info.name());
		Assert.assertEquals(coach_name, info.coachName());
		Assert.assertEquals(longitude, "" + loc.longitude());
		Assert.assertEquals(latitude, "" + loc.latitude());
		Assert.assertEquals(thumbnail, posterThumbnails);
		Assert.assertEquals(images, memoryImages);

	}

}
