package com.puluo.result.message;

import java.util.ArrayList;
import java.util.List;


public class PastMessagesResult {
	public String msg_uuid;

	public PastMessagesResult(String msg_uuid) {
		super();
		this.msg_uuid = msg_uuid;
	}
	
	public static List<PastMessagesResult> dummy() {
		List<PastMessagesResult> result = new ArrayList<PastMessagesResult>();
		result.add(new PastMessagesResult("de305d54-75b4-431b-adb2-eb6b9e546013"));
		result.add(new PastMessagesResult("de305d54-75b4-431b-adb2-eb6b9e546013"));
		return result;
	}
}