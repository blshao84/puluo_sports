package com.puluo.dao;

import org.joda.time.DateTime;
import com.puluo.entity.PuluoPrivateMessage;


public interface PuluoPrivateMessageDao {
	
	public boolean createTable();
	
	public boolean saveMessage(PuluoPrivateMessage message);

	public String sendMessage(PuluoPrivateMessage message, String to_user);
	
	public PuluoPrivateMessage getFriendRequestMessage(String userUUID);
	
	public PuluoPrivateMessage[] getMessagesByUser(String userUUID, DateTime time_from, DateTime time_to);
	
	public PuluoPrivateMessage[] getMessagesByUser(String from_user_uuid, String to_user_uuid, DateTime time_from, DateTime time_to);
}
