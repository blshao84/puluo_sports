package com.puluo.dao;

import org.joda.time.DateTime;
import com.puluo.entity.PuluoPrivateMessage;


public interface PuluoPrivateMessageDao {
	
	public boolean createTable();
	
	public boolean saveMessage(PuluoPrivateMessage message);

	public String sendMessage(PuluoPrivateMessage message);
	
	public PuluoPrivateMessage getFriendRequestMessage(String userUUID);
	
	public PuluoPrivateMessage[] getMessagesByUser(String userUUID, DateTime time_from, DateTime time_to);
}
