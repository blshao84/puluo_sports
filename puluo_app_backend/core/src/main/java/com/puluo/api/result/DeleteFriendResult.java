package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;

public class DeleteFriendResult extends HasJSON {
	public List<PastMessagesResult> past_messages;

	public DeleteFriendResult(List<PastMessagesResult> past_messages) {
		super();
		this.past_messages = past_messages;
	}

	public static DeleteFriendResult dummy() {
		return new DeleteFriendResult(PastMessagesResult.dummy());
	}
}

class PastMessagesResult {
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