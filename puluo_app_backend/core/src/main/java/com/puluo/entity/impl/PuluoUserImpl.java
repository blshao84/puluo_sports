package com.puluo.entity.impl;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.puluo.entity.PuluoUser;
import com.puluo.util.Strs;

public class PuluoUserImpl implements PuluoUser {

	private String user_uuid;
	private String mobile;
	private String[] interests;
	private String password;
	private String firstName; // 名
	private String lastName; // 姓
	private String thumbnail; // 头像
	private String largeImage; // 大图
	private PuluoUserType user_type;
	private String email;
	char sex;
	private String zip;
	private String country; // added by Xuyang
	private String state;
	private String city;
	private String occupation; // added by Xuyang
	private String address;
	private String saying; // 只是saying而已
	DateTime birthday;
	DateTime created_at; // 用户创建时间
	DateTime updated_at; // 用户信息最后一次更新时间
	boolean banned;

	public PuluoUserImpl(String user_uuid, String mobile, String[] interests,
			String password, String firstName, String lastName,
			String thumbnail, String largeImage, PuluoUserType user_type,
			String email, char sex, String zip, String country, String state,
			String city, String occupation, String address, String saying,
			LocalDate birthday, DateTime created_at, DateTime updated_at,
			boolean banned) {
		super();
		this.user_uuid = user_uuid;
		this.mobile = mobile;
		this.interests = interests;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.thumbnail = thumbnail;
		this.largeImage = largeImage;
		this.user_type = user_type;
		this.email = email;
		this.sex = sex;
		this.zip = zip;
		this.country = country;
		this.state = state;
		this.city = city;
		this.occupation = occupation;
		this.address = address;
		this.saying = saying;
		this.birthday = birthday;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.banned = banned;
	}

	@Override
	public String userUUID() {
		
		return user_uuid;
	}

	@Override
	public String mobile() {
		
		return mobile;
	}

	@Override
	public String password() {
		
		return password;
	}

	@Override
	public String firstName() {
		
		return firstName;
	}

	@Override
	public String lastName() {
		
		return lastName;
	}

	@Override
	public String thumbnail() {
		
		return thumbnail;
	}

	@Override
	public String largeImage() {
		
		return largeImage;
	}

	@Override
	public PuluoUserType userType() {
		
		return user_type;
	}

	@Override
	public String email() {
		
		return email;
	}

	@Override
	public DateTime birthday() {
		
		return birthday;
	}

	@Override
	public char sex() {
		
		return sex;
	}

	@Override
	public String zip() {
		
		return zip;
	}

	@Override
	public String country() {
		
		return country;
	}

	@Override
	public String state() {
		
		return state;
	}

	@Override
	public String city() {
		
		return city;
	}

	@Override
	public String occupation() {
		
		return occupation;
	}

	@Override
	public String address() {
		
		return address;
	}

	@Override
	public String[] interests() {
		
		return interests;
	}

	@Override
	public DateTime createdAt() {
		
		return created_at;
	}

	@Override
	public DateTime updatedAt() {
		
		return updated_at;
	}

	@Override
	public String saying() {
		
		return saying;
	}

	@Override
	public boolean banned() {
		
		return banned;
	}
	
	@Override
	public String name() {
		return Strs.join(firstName," ",lastName);
	}

	@Override
	public int likes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int following() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isCoach() {
		if (user_type.equals(PuluoUserType.Coach))
			return true;
		return false;
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

}
