package com.puluo.dao.impl;

import java.util.ArrayList;
import com.puluo.dao.PuluoUserDao;
import com.puluo.entity.PuluoUser;
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
	public boolean updatePassword(PuluoUser user, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PuluoUser updateProfile(PuluoUser curuser, String first_name, String last_name, 
			String thumbnail, String large_image, String saying, String email, String sex, 
			String birthday, String country, String state, String city, String zip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoUser getByMobile(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoUser getByUUID(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PuluoUser> findUser(String first_name, String last_name,
			String email, String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

}
