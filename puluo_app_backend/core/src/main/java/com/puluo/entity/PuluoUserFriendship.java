package com.puluo.entity;

import java.util.List;

public interface PuluoUserFriendship {
	public String  userUUID();
	
	public List<PuluoFriendInfo> friends();
	
	public PuluoFriendRequest request();
	public PuluoPrivateMessage[] pastMessages(); // TODO 我的理解就是除了msg_id外都为空，否则需要再定义一个message的interface
}
