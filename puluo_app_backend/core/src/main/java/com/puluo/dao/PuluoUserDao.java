package com.puluo.dao;

import com.puluo.entity.PuluoUser;

public interface PuluoUserDao {
	
	public boolean createTable();
	
	public boolean save(String mobile, String password);
	
	public boolean updatePassword(PuluoUser user, String newPassword);
	
	public boolean updateProfile(PuluoUser olduser, PuluoUser newuser); 
	
	public PuluoUser getByMobile(String mobile);
	
	public PuluoUser getByUUID(String uuid);	
}
