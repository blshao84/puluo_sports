package com.puluo.dao.impl;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.jdbc.DalTemplate;

public class PuluoPrivateMessageDaoImpl extends DalTemplate implements
		PuluoPrivateMessageDao {

	@Override
	public boolean saveMessage(PuluoPrivateMessage message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String sendMessage(PuluoPrivateMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoPrivateMessage getFriendRequestMessage(String userUUID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoPrivateMessage[] getMessagesByUser(String userUUID,
			DateTime time_from, DateTime time_to) {
		// TODO Auto-generated method stub
		return null;
	}

}
