package com.puluo.entity.impl;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;


public class PuluoUserImpl implements PuluoUser {

	private String uuid;
//	private String type;
//	private String username;
//	private String iconurl;
//	private String name;
	private String mobile;
//	private String email;
//	private DateTime birthday;
//	private char sex;
//	private String zip;
//	private String country; //added by Xuyang
//	private String state;
//	private String city;
//	private String occupation; //added by Xuyang
//	private String address;
	private String[] interests;
//	private String description;
//	private String[] friends;
//	private String privacy;
//	private int status;
	private String password;
	
	public PuluoUserImpl(String uuid, String mobile, String[] interests,
		String password) {
		super();
		this.uuid = uuid;
		this.mobile = mobile;
		this.interests = interests;
		this.password = password;
	}
	@Override
	public String idUser() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String type() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String username() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String iconurl() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String phone() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String email() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DateTime birthday() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public char sex() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String zip() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String country() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String state() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String city() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String occupation() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String address() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] interests() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] friends() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String privacy() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int status() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public DateTime lastLogin() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long lastDuration() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public DateTime create() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public DateTime update() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String firstName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String lastName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String thumbnail() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String largeImage() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String mobile() {
		// TODO Auto-generated method stub
		return mobile;
	}
	@Override
	public boolean autoAddFriend() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean allowStrangerViewTimeline() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean allowSearched() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String saying() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int likes() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean banned() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int following() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isCoach() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String password() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
