package com.puluo.test.functional;

import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.config.Configurations;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.EventFunctionalTestRunner;
import com.puluo.test.functional.util.EventTestDataSource;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class EventMemoryFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(EventMemoryFunctionalTest.class);
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
	public void testEventWithMemory() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/memory/" + dataSource.eventID1,
						inputs(session));
				Set<String> mem = new HashSet<String>();
				mem.add(Strs.join(Configurations.imageServer,"1234.jpg"));
				mem.add(Strs.join(Configurations.imageServer,"5678.jpg"));
				compareMemory(json, mem);
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

	@Test
	public void testEventWithoutMemory() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/memory/" + dataSource.eventID3,
						inputs(session));
				Set<String> mem = new HashSet<String>();
				compareMemory(json, mem);
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

	@Test
	public void testNonExistingEvent() {
		super.runAuthenticatedTest(new EventFunctionalTestRunner() {
			
			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/memory/123456", inputs(session));
				Set<String> mem = new HashSet<String>();
				compareMemory(json, mem);
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

	private void compareMemory(JsonNode json, Set<String> mem) {
		Set<String> images = new HashSet<String>(super.getStringArrayFromJson(
				json, "memories"));
		Assert.assertEquals(images, mem);

	}

}
