package com.puluo.test.functional;

import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.EventTestDataSource;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class EventMemoryFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory
			.getLog(EventMemoryFunctionalTest.class);
	private static EventTestDataSource dataSource = new EventTestDataSource("event_memory");

	@BeforeClass
	public static void setupDB() {
		dataSource.setupDB();
	}

	@AfterClass
	public static void cleanupDB() {
		dataSource.cleanupDB();
	}
	
	@Test
	public void testEventWithMemory(){
		String session = login(dataSource.mobile, dataSource.password);
		if (session != null) {
			String inputs = String.format("{\"token\":\"%s\"}", session);
			try {
				JsonNode json = callAPI("events/memory/" + dataSource.eventID1, inputs);
				Set<String> mem =  new HashSet<String>();
				mem.add("http://upyun.com/puluo/1234.jpg");
				mem.add("http://upyun.com/puluo/5678.jpg");
				compareMemory(json, mem);
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
	
	@Test
	public void testEventWithoutMemory(){
		String session = login(dataSource.mobile, dataSource.password);
		if (session != null) {
			String inputs = String.format("{\"token\":\"%s\"}", session);
			try {
				JsonNode json = callAPI("events/memory/" + dataSource.eventID3, inputs);
				Set<String> mem =  new HashSet<String>();
				compareMemory(json, mem);
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
	
	@Test
	public void testNonExistingEvent(){
		String session = login(dataSource.mobile, dataSource.password);
		if (session != null) {
			String inputs = String.format("{\"token\":\"%s\"}", session);
			try {
				JsonNode json = callAPI("events/memory/123456", inputs);
				Set<String> mem =  new HashSet<String>();
				compareMemory(json, mem);
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

	private void compareMemory(JsonNode json, Set<String> mem) {
		Set<String> images = new HashSet<String>(super.getStringArrayFromJson(
				json, "memories"));
		Assert.assertEquals(images, mem);
		
	}

	

}
