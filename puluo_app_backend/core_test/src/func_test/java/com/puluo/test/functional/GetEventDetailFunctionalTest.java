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
import com.puluo.dao.impl.PuluoOrderEventDaoImpl;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.entity.impl.PuluoEventAttendee;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.EventFunctionalTestRunner;
import com.puluo.test.functional.util.EventTestDataSource;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class GetEventDetailFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory
			.getLog(GetEventDetailFunctionalTest.class);
	private static String order_uuid;
	private static EventTestDataSource dataSource = new EventTestDataSource(
			"event_detail");

	@BeforeClass
	public static void setupDB() {
		dataSource.setupDB();
	}

	@AfterClass
	public static void cleanupDB() {
		dataSource.cleanupDB();
		PuluoOrderEventDaoImpl orderEventDao = (PuluoOrderEventDaoImpl) DaoApi
				.getInstance().orderEventDao();
		orderEventDao.deleteByOrderUUID(order_uuid);
	}

	@Test
	public void testGetRegisteredEvent() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("/events/payment/"
						+ dataSource.eventID1, inputs(session));
				log.info(json);
				order_uuid = getStringFromJson(json, "order_uuid");
				String paid = getStringFromJson(json, "paid");
				Assert.assertTrue("paid should be true", Boolean.valueOf(paid));
				JsonNode json2 = callAPI("/events/registered", inputs(session));
				log.info(json2);
				List<JsonNode> events = getJsonArrayFromJson(json2, "events");
				Assert.assertEquals(1, events.size());
				String event_uuid = getStringFromJson(events.get(0), "event_uuid");
				Assert.assertEquals(dataSource.eventID1, event_uuid);
				
			}

			@Override
			public String inputs(String session) {
				return String.format("{\"token\":\"%s\",\"mock\":true}", session);
			}

			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
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

	private void testEvent(final String eventID) {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/detail/" + eventID,
						inputs(session));
				PuluoEvent event = DaoApi.getInstance().eventDao()
						.getEventByUUID(eventID);
				compareEvent(json, event);
			}

			@Override
			public String inputs(String session) {
				return String.format("{\"token\":\"%s\"}", session);
			}

			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
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
		Set<String> posters = new HashSet<String>(
				super.getStringArrayFromJson(json, "posters"));
		Set<String> memories = new HashSet<String>(super.getStringArrayFromJson(
				json, "memories"));
		String longitude = super.getStringFromJson(json, "geo_location",
				"longitude");
		String latitude = super.getStringFromJson(json, "geo_location",
				"latitude");
		String actualRegistered = super.getStringFromJson(json, "registered");
		Set<String> actualAttendees = new HashSet<String>();
		for (JsonNode node : super.getJsonArrayFromJson(json, "attendees")) {
			actualAttendees.add("{" + "\"name\":\""
					+ super.getStringFromJson(node, "name") + "\","
					+ "\"uuid\":\"" + super.getStringFromJson(node, "uuid")
					+ "\"," + "\"thumbnail\":\""
					+ super.getStringFromJson(node, "thumbnail") + "\"" + "}");
		}

		log.info(String.format("values extracted from json:\n" + "phone:%s,\n"
				+ "status:%s,\n" + "city:%s\n" + "details:%s\n"
				+ "address:%s\n" + "coach_uuid:%s\n" + "capacity:%s\n"
				+ "registered_users:%s\n" + "event_name:%s\n"
				+ "coach_name:%s\n" + "posters:%s\n" + "memories:%s\n"
				+ "longitude:%s\n" + "lattitude:%s\n" + "registered:%s\n"
				+ "attendees:%s\n", phone, status, city, details, address,
				coach_uuid, capacity, registered_users, event_name, coach_name,
				posters, memories, longitude, latitude, actualRegistered,
				actualAttendees));
		PuluoDSI dsi = DaoApi.getInstance();
		PuluoEventInfo info = event.eventInfo();
		PuluoEventLocation loc = event.eventLocation();
		List<PuluoEventPoster> eventPosters = dsi.eventPosterDao()
				.getEventPosterByInfoUUID(info.eventInfoUUID());
		List<PuluoEventMemory> eventMemories = dsi.eventMemoryDao()
				.getEventMemoryByEventUUID(event.eventUUID());
		List<PuluoEventAttendee> attendees = event.attendees();
		Set<String> posterThumbnails = new HashSet<String>();
		Set<String> memoryThumbnails = new HashSet<String>();
		Set<String> expectedAttendees = new HashSet<String>();
		//starting from 1, because the EventDetaiAPI returns posters from the second one
		for (int i=1;i<eventPosters.size();i++) {
			posterThumbnails.add(eventPosters.get(i).thumbnailURL());
		}
		for (PuluoEventMemory m : eventMemories) {
			memoryThumbnails.add(m.thumbnailURL());
		}
		for (PuluoEventAttendee a : attendees) {
			expectedAttendees.add("{" + "\"name\":\"" + a.name() + "\","
					+ "\"uuid\":\"" + a.uuid() + "\"," + "\"thumbnail\":\""
					+ a.thumbnail() + "\"" + "}");
		}
		String expectedRregistered = String.valueOf(event
				.registered(dataSource.userID));
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
		Assert.assertEquals(posters, posterThumbnails);
		Assert.assertEquals(memories, memoryThumbnails);
		Assert.assertEquals(actualRegistered, expectedRregistered);
		Assert.assertEquals(actualAttendees, expectedAttendees);

	}

}
