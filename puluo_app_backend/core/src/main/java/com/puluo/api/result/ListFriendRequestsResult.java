package com.puluo.api.result;

import java.util.List;

import com.puluo.util.HasJSON;

public class ListFriendRequestsResult extends HasJSON {
	public List<SingleFriendRequestResult> requests;

	public ListFriendRequestsResult(List<SingleFriendRequestResult> requests) {
		super();
		this.requests = requests;
	}
	
}
