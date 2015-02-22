package com.puluo.entity;

import java.util.List;

public interface PuluoUserFriendship {
	public String  userUUID();
	
	public List<PuluoFriendInfo> friends();
}
