package com.puluo.dao;

import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoUserImpl;


public interface PuluoUserDao {
	
	public boolean createTable();
	
	public boolean save(String mobile, String password);
	
	public boolean updatePassword(PuluoUserImpl user, String newPassword);
	
	public boolean updateProfile(PuluoUserImpl olduser, PuluoUserImpl newuser); 
	
	public PuluoUser getByMobile(String mobile);
	
	public PuluoUser getByUUID(String uuid);
	
	public PuluoUser findUser(String first_name, String last_name, String email, String mobile);
}
