package com.puluo.test.functional;

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
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.EventFunctionalTestRunner;
import com.puluo.test.functional.util.EventTestDataSource;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

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
	public void testDateSearch() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {
			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"event_date\":%s," + "}", session,
						dataSource.event_date_0601.getMillis());
			}

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/search", inputs(session));
				Set<String> expectedEvents = new HashSet<String>();
				expectedEvents.add(dataSource.eventID1);
				expectedEvents.add(dataSource.eventID2);
				Set<String> actualEvents = extractUUID(json);
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
						+ "\"event_date\":%s," + "}", session,
						dataSource.event_date_0602.getMillis());
			}

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/search", inputs(session));
				Set<String> expectedEvents = new HashSet<String>();
				expectedEvents.add(dataSource.eventID3);
				Set<String> actualEvents = extractUUID(json);
				Assert.assertEquals(expectedEvents, actualEvents);
			}

			@Override
			public EventTestDataSource dataSource() {
				return dataSource;
			}
		});
	}

	private Set<String> extractUUID(JsonNode json) {
		Set<String> uuids = new HashSet<String>();
		JSONObject jo = json.getObject();
		JSONArray joArray = jo.getJSONArray("events");
		for (int i = 0; i < joArray.length(); i++) {
			uuids.add(joArray.getJSONObject(i).getString("event_uuid"));
		}
		return uuids;
	}
}
