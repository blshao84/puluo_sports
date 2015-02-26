package com.puluo.entity;

import java.sql.Time;

public interface PuluoFriendRequest {

	String requestId();
	String status();
	PuluoPrivateMessage[] messages();
	Time create();
	Time update();
}
