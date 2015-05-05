package com.puluo.test.functional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.enumeration.EventStatus;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.EventFunctionalTestRunner;
import com.puluo.test.functional.util.EventTestDataSource;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class EventSearchFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(EventSearchFunctionalTest.class);
	private static EventTestDataSource dataSource = new EventTestDataSource(
			"event_memory");

	@BeforeClass
	public static void setupDB() {
		dataSource.setupDB();
	}

	@AfterClass
	public static void cleanupDB() {
		dataSource.cleanupDB();
	}

	@Test
	public void testDateSearchWithFrom() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {
			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"event_from_date\":%s,\"limit\":200}", session,
						TimeUtils.parseDateTime("2015-06-01 14:00:00").getMillis());
			}

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/search", inputs(session));
				Set<String> expectedEvents = new HashSet<String>();
				expectedEvents.add(dataSource.eventID1);
				expectedEvents.add(dataSource.eventID2);
				expectedEvents.add(dataSource.eventID3);
				Set<String> actualEvents = extractUUID(json, new HashSet<String>());
				Assert.assertEquals(expectedEvents, actualEvents);
			}
			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
	}

	@Test
	public void testDateSearchWithTo() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {
			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"event_to_date\":%s,\"limit\":200}", session,
						TimeUtils.parseDateTime("2015-06-01 10:00:00").getMillis());
			}

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/search", inputs(session));
				Set<String> expectedEvents = new HashSet<String>();
				expectedEvents.add(dataSource.eventID1);
				expectedEvents.add(dataSource.eventID2);
				Set<String> actualEvents = extractUUID(json, new HashSet<String>());
				Assert.assertEquals(expectedEvents, actualEvents);
			}
			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
	}

	@Test
	public void testDateSearchWithFromAndTo() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {
			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"event_from_date\":%s,"+ "\"event_to_date\":%s,\"limit\":200}", session,
						TimeUtils.parseDateTime("2015-06-02 14:00:00").getMillis(),
						TimeUtils.parseDateTime("2015-06-02 12:00:00").getMillis());
			}

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/search", inputs(session));
				Set<String> expectedEvents = new HashSet<String>();
				expectedEvents.add(dataSource.eventID3);
				Set<String> actualEvents = extractUUID(json, new HashSet<String>());
				Assert.assertEquals(expectedEvents, actualEvents);
			}
			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
	}

	@Test
	public void testDateSearchWithClosedEvent() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {
			
			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"event_date\":%s,"
						+ "\"status\":\"%s\",\"limit\":200}", session,
						dataSource.event_date_0602.getMillis(),
						EventStatus.Closed.name());
			}

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/search", inputs(session));
				Set<String> expectedEvents = new HashSet<String>();
				expectedEvents.add(dataSource.eventID4);
				Set<String> actualEvents = extractUUID(json, new HashSet<String>());
				Assert.assertEquals(expectedEvents, actualEvents);
			}

			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
	}
	
	@Test
	public void testKeywordSearchInName() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {
			
			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"keyword\":%s,\"limit\":200}", session,"臀部炸弹");
			}

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/search", inputs(session));
				Set<String> expectedEvents = new HashSet<String>();
				expectedEvents.add(dataSource.eventID1);
				expectedEvents.add(dataSource.eventID2);
				Set<String> actualEvents = extractUUID(json, new HashSet<String>());
				Assert.assertEquals(expectedEvents, actualEvents);
			}

			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
	}
	
	@Test
	public void testKeywordSearchInDescription() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {
			
			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"keyword\":%s,\"limit\":200}", session,"瘦臀");
			}

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/search", inputs(session));
				Set<String> expectedEvents = new HashSet<String>();
				expectedEvents.add(dataSource.eventID1);
				expectedEvents.add(dataSource.eventID2);
				Set<String> actualEvents = extractUUID(json, new HashSet<String>());
				Assert.assertEquals(expectedEvents, actualEvents);
			}

			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
	}
	
	@Test
	public void testKeywordSearchInDescriptionAndName() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {
			
			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"keyword\":%s,\"limit\":200}", session,"减脂");
			}

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/search", inputs(session));
				Set<String> expectedEvents = new HashSet<String>();
				expectedEvents.add(dataSource.eventID1);
				expectedEvents.add(dataSource.eventID2);
				expectedEvents.add(dataSource.eventID3);
				Set<String> actualEvents = extractUUID(json, new HashSet<String>());
				Assert.assertEquals(expectedEvents, actualEvents);
			}

			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
	}
	
	@Test
	public void testKeywordPriceSort() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {
			
			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"keyword\":%s,"
						+ "\"sort\":\"Price\",\"limit\":200}", session,"减脂");
			}

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/search", inputs(session));
				ArrayList<String> expectedEvents = new ArrayList<String>();
				expectedEvents.add(dataSource.eventID1);
				expectedEvents.add(dataSource.eventID3);
				expectedEvents.add(dataSource.eventID2);
				ArrayList<String> actualEvents = extractUUID(json, new ArrayList<String>());
				Assert.assertEquals(expectedEvents, actualEvents);
			}

			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
	}
	
	@Test
	public void testKeywordPriceSortDesc() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {
			
			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"keyword\":%s,"
						+ "\"sort\":\"Price\","
						+ "\"sort_direction\":\"Desc\",\"limit\":200}", session,"减脂");
			}

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/search", inputs(session));
				ArrayList<String> expectedEvents = new ArrayList<String>();
				expectedEvents.add(dataSource.eventID2);
				expectedEvents.add(dataSource.eventID3);
				expectedEvents.add(dataSource.eventID1);
				ArrayList<String> actualEvents = extractUUID(json, new ArrayList<String>());
				Assert.assertEquals(expectedEvents, actualEvents);
			}

			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
	}
	
	

	private <T extends Collection<String>> T extractUUID(JsonNode json, T uuids) {
		JSONObject jo = json.getObject();
		JSONArray joArray = jo.getJSONArray("events");
		for (int i = 0; i < joArray.length(); i++) {
			String eventUUID = joArray.getJSONObject(i).getString("event_uuid");
			if(eventUUID.startsWith("event")){
				uuids.add(eventUUID);
			}
		}
		return uuids;
	}
}
