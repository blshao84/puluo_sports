package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;

public class ListMessageResult extends HasJSON {
	public List<MessageResult> messages;

	public ListMessageResult(List<MessageResult> messages) {
		super();
		this.messages = messages;
	}

	public static ListMessageResult dummy() {
		List<MessageResult> messages = new ArrayList<MessageResult>();
		messages.add(new MessageResult(
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"http://upyun.com/puluo/xxxx", "http://upyun.com/puluo/xxxx",
				"hi, this is Tracy!", "2012-01-01 12:00:00"));
		messages.add(new MessageResult(
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"http://upyun.com/puluo/xxxx", "http://upyun.com/puluo/xxxx",
				"hi, this is Tracy!", "2012-01-01 12:00:00"));
		return new ListMessageResult(messages);
	}
}
