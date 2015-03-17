package com.puluo.dao;

import java.util.List;
import com.puluo.entity.PuluoUser;


public interface PuluoUserDao {
	
	public boolean createTable();
	
	public boolean save(String mobile, String password);
	
	public PuluoUser getByMobile(String mobile);
	
	public PuluoUser getByUUID(String uuid);
	
	public boolean updatePassword(PuluoUser user, String newPassword);
	
	public PuluoUser updateProfile(PuluoUser curuser, String first_name, String last_name, 
			String thumbnail, String large_image, String saying, String email, String sex, 
			String birthday, String country, String state, String city, String zip); 
	
	public List<PuluoUser> findUser(String first_name, String last_name, String email, String mobile);
	
	//public List<PuluoUser> findUserByFirstName(String first_name);
	
	//public List<PuluoUser> findUserByLastName(String last_name);
	
	//public List<PuluoUser> findUserByEmail(String email);
	
	//public List<PuluoUser> findUserByMobile(String mobile);
}
