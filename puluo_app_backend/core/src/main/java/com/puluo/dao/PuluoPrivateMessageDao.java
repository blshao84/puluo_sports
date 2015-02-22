package com.puluo.dao;

import com.puluo.entity.PuluoPrivateMessage;

public interface PuluoPrivateMessageDao {
	
	public boolean saveMessage(PuluoPrivateMessage message);
	
	public boolean updateMessage(PuluoPrivateMessage message);
	
	public PuluoPrivateMessage getFriendRequestMessage(String userUUID);
	
	
}
