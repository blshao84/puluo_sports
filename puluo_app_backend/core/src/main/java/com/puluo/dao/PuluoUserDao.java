package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoUser;
import com.puluo.enumeration.PuluoUserType;


public interface PuluoUserDao {
	
	public boolean createTable();
	
	public boolean save(String mobile, String password);
	
	public PuluoUser getByMobile(String mobile);
	
	public PuluoUser getByUUID(String uuid);
	
	public boolean updatePassword(PuluoUser user, String newPassword);
	
	public PuluoUser updateProfile(
			PuluoUser curuser, 
			String first_name,
			String last_name, 
			String thumbnail, 
			String saying, 
			String email, 
			String sex, 
			String birthday,
			String country, 
			String state, 
			String city, 
			String zip); 
	
	public PuluoUser updateAdminProfile(
			PuluoUser profile,
			PuluoUserType userType, 
			Boolean banned);
//	public List<PuluoUser> findUser(String first_name, String last_name, String email, String mobile);
//	March 19, 2015 Luke 增加参数and_or，类型为boolean，and为true，or为false，暂时不会一次take一个变量
	public List<PuluoUser> findUser(String first_name, String last_name, String email, String mobile, boolean and_or);
	
	//public List<PuluoUser> findUserByFirstName(String first_name);
	
	//public List<PuluoUser> findUserByLastName(String last_name);
	
	//public List<PuluoUser> findUserByEmail(String email);
	
	//public List<PuluoUser> findUserByMobile(String mobile);
}
