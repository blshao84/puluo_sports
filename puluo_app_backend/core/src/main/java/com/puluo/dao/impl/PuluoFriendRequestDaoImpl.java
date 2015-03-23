package com.puluo.dao.impl;

import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.entity.FriendRequestStatus;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.jdbc.DalTemplate;

public class PuluoFriendRequestDaoImpl extends DalTemplate implements PuluoFriendRequestDao {

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveNewRequest(String fromUser, String toUser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateRequestStatus(String requestUUID,
			FriendRequestStatus newStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PuluoFriendRequest findByUUID(String requestUUID) {
		// TODO Auto-generated method stub
		return null;
	}

}
