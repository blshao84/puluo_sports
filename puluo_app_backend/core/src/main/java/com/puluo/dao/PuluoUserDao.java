package com.puluo.dao;

import com.puluo.entity.impl.PuluoUserImpl;


public interface PuluoUserDao {
	
	public boolean createTable();
	
	public boolean save(String mobile, String password);
	
	public boolean updatePassword(PuluoUserImpl user, String newPassword);
	
	public boolean updateProfile(PuluoUserImpl olduser, PuluoUserImpl newuser); 
	
	public PuluoUserImpl getByMobile(String mobile);
	
	public PuluoUserImpl getByUUID(String uuid);
	
	public PuluoUserImpl findUser(String first_name, String last_name, String email, String mobile);
}
