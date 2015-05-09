package com.puluo.result.message;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;


public class DeleteFriendResult extends HasJSON {
	public List<String> friends;

	public DeleteFriendResult(List<String> friends) {
		super();
		this.friends = friends;
	}

	public static DeleteFriendResult dummy() {
		List<String> dummyFriends = new ArrayList<String>();
		dummyFriends.add("de305d54-75b4-431b-adb2-eb6b9e546012");
		dummyFriends.add("de305d54-75b4-431b-adb2-eb6b9e546014");
		return new DeleteFriendResult(dummyFriends);
	}
}
