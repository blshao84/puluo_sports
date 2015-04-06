package com.puluo.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.puluo.service.util.JuheSMSResult;

public class SMSResultTest {
	@Test
	public void testJsonDeserialization() {
		String json = "{\"reason\":\"短信发送成功\",\"result\":{\"count\":1,\"fee\":1,\"sid\":1288911649},\"error_code\":0}";
		JuheSMSResult expected = new JuheSMSResult("短信发送成功", 1, 1, 1288911649,
				0);
		JuheSMSResult res = JuheSMSResult.fromJson(json);
		assertEquals(String.format(
				"expected deserialized result %s should equal to result %s",
				expected, res), expected, res);
	}
	
	@Test
	public void testJuheResultSerialization() {
		String expected = "{\"reason\":\"短信发送成功\",\"result\":{\"count\":1,\"fee\":1,\"sid\":1288911649},\"error_code\":0}";
		JuheSMSResult result = new JuheSMSResult("短信发送成功", 1, 1, 1288911649,
				0);
		String json = result.toJson();
		assertEquals(String.format(
				"expected serialized string \n%s \nshould equal to result \n%s",
				expected, json), expected, json);
	}

}
