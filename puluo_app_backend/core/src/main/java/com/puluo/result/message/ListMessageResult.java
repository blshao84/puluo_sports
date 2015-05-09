package com.puluo.result.message;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;


public class ListMessageResult extends HasJSON {
	public List<MessageResult> messages;
	public int total_count;

	public ListMessageResult(List<MessageResult> messages,int total_count) {
		super();
		this.total_count = total_count;
		this.messages = messages;
	}

	public static ListMessageResult dummy() {
		List<MessageResult> messages = new ArrayList<MessageResult>();
		messages.add(new MessageResult(
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"Baolin","Shao",
				"Tracey","Boydston",
				"http://upyun.com/puluo/xxxx", "http://upyun.com/puluo/xxxx",
				"hi, this is Tracy!", 1427007059034L));
		messages.add(new MessageResult(
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"Baolin","Shao",
				"Tracey","Boydston",
				"http://upyun.com/puluo/xxxx", "http://upyun.com/puluo/xxxx",
				"hi, this is Tracy!", 1427007059034L));
		return new ListMessageResult(messages,2);
	}
}
