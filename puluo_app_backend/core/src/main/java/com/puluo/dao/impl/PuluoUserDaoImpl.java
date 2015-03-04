package com.puluo.dao.impl;

import com.puluo.dao.PuluoUserDao;
import com.puluo.entity.impl.PuluoUserImpl;
import com.puluo.jdbc.DalTemplate;

public class PuluoUserDaoImpl extends DalTemplate implements PuluoUserDao {

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean save(String mobile, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updatePassword(PuluoUserImpl user, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProfile(PuluoUserImpl olduser, PuluoUserImpl newuser) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PuluoUserImpl getByMobile(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoUserImpl getByUUID(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoUserImpl findUser(String first_name, String last_name,
			String email, String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

}
