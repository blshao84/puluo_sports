package com.puluo.dao;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoPrivateMessage;


public interface PuluoPrivateMessageDao {
	
	public boolean createTable();
	
	public boolean saveMessage(PuluoPrivateMessage message);

//	public String sendMessage(PuluoPrivateMessage message, String to_user);
	
	public List<PuluoPrivateMessage> getFriendRequestMessage(String fromUserUUID, String toUserUUID);
	
	public PuluoPrivateMessage[] getMessagesByFromUser(String userUUID, DateTime time_from, DateTime time_to);
	
	public PuluoPrivateMessage[] getMessagesByUser(String from_user_uuid, String to_user_uuid, DateTime time_from, DateTime time_to);
}
