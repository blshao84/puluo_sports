package com.puluo.result.user;

import java.util.List;

import com.puluo.result.message.SingleFriendRequestResult;
import com.puluo.util.HasJSON;

public class ListFriendRequestsResult extends HasJSON {
	public List<SingleFriendRequestResult> requests;

	public ListFriendRequestsResult(List<SingleFriendRequestResult> requests) {
		super();
		this.requests = requests;
	}
	
}
