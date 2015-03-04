package com.puluo.dao.impl;

import java.util.List;

import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.entity.PuluoUserFriendship;
import com.puluo.jdbc.DalTemplate;

public class PuluoUserFriendshipDaoImpl extends DalTemplate implements
		PuluoUserFriendshipDao {

	@Override
	public List<PuluoUserFriendship> getFriendListByUUID(String userUUID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PuluoUserFriendship> deleteOneFriend(String userUUID,
			String frendUUID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PuluoUserFriendship> addOneFriend(String userUUID,
			String frendUUID) {
		// TODO Auto-generated method stub
		return null;
	}

}
