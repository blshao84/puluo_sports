package com.puluo.result.event;

import com.puluo.util.HasJSON;

public class EventCancellationResult extends HasJSON {
	public String order_uuid;
	public boolean success;

	public EventCancellationResult(String order_uuid, boolean success) {
		this.order_uuid = order_uuid;
		this.success = success;
	}

	public static EventCancellationResult dummy() {
		return new EventCancellationResult(
				"de305d54-75b4-431b-adb2-eb6b9e546015", true);
	}
}
