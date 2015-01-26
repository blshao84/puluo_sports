package com.puluo.entity;

import java.sql.Date;


public interface PuluoUser {

	int getType(String iduser);
	
	String setType(String iduser, int type);
	
	String getUsername(String iduser);
	
	String setUsername(String iduser, String username);
	
	String getIconURL(String iduser);
	
	String setIconURL(String iduser, String iconurl);
	
	String getName(String iduser);
	
	String setName(String iduser, String name);
	
	String getPhone(String iduser);
	
	String setPhone(String iduser, String phone);
	
	String getEmail(String iduser);
	
	String setEmail(String iduser, String email);
	
	Date getBirthday(String iduser);
	
	String setBirthday(String iduser, Date birthday);
	
	char getSex(String iduser);
	
	String setSex(String iduser, char sex);
	
	String getZip(String iduser);
	
	String setZip(String iduser, String zip);
	
	String getProvince(String iduser);
	
	String setProvince(String iduser, String province);
	
	String getCity(String iduser);
	
	String setCity(String iduser, String city);
	
	String getAddress(String iduser);
	
	String setAddress(String iduser, String address);
	
	String[] getInterests(String iduser);
	
	String setInterests(String iduser, String[] interests);
	
	String[] getFriends(String iduser);
	
	String setFriends(String iduser, String[] friends);
	
	String getPrivacy(String iduser);
	
	String setPrivacy(String iduser, String privacy);
	
	int getStatus(String iduser);
	
	String setStatus(String iduser, int status);
	
	String findUserId(String username, String name, String phone, String email, Date birthday);

}
