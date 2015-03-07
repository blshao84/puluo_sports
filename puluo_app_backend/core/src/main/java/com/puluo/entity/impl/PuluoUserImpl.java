package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoUser;


public class PuluoUserImpl implements PuluoUser {

	private String iduser;
	private String type;
	private String username;
	private String iconurl;
	private String name;
	private String phone;
	private String email;
	private DateTime birthday;
	private char sex;
	private String zip;
	private String country; //added by Xuyang
	private String state;
	private String city;
	private String occupation; //added by Xuyang
	private String address;
	private String[] interests;
	private String description;
	private String[] friends;
	private String privacy;
	private int status;
	
	
	public PuluoUserImpl() {}
	
	public PuluoUserImpl(String iduser, String type, String username,
			String iconurl, String name, String phone, String email,
			DateTime birthday, char sex, String zip, String country, String state, String city,
			String occupation, String address, String[] interests, String description,
			String[] friends, String privacy, int status) {
		this.iduser = iduser;
		this.type = type;
		this.username = username;
		this.iconurl = iconurl;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.birthday = birthday;
		this.sex = sex;
		this.zip = zip;
		this.country = country; //added by Xuyang
		this.state = state;
		this.city = city;
		this.occupation = occupation; //added by Xuyang
		this.address = address;
		this.interests = interests;
		this.description = description;
		this.friends = friends;
		this.privacy = privacy;
		this.status = status;
	}
	
	@Override
	public String idUser() {
		// TODO Auto-generated method stub
		return iduser;
	}

	@Override
	public String type() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String username() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public String iconurl() {
		// TODO Auto-generated method stub
		return iconurl;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String phone() {
		// TODO Auto-generated method stub
		return phone;
	}

	@Override
	public String email() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public DateTime birthday() {
		// TODO Auto-generated method stub
		return birthday;
	}

	@Override
	public char sex() {
		// TODO Auto-generated method stub
		return sex;
	}

	@Override
	public String zip() {
		// TODO Auto-generated method stub
		return zip;
	}

	@Override
	public String country() { //added by Xuyang
		// TODO Auto-generated method stub
		return country;
	}

	@Override
	public String state() {
		// TODO Auto-generated method stub
		return state;
	}

	@Override
	public String city() {
		// TODO Auto-generated method stub
		return city;
	}
	
	@Override
	public String occupation() { //added by Xuyang
		// TODO Auto-generated method stub
		return occupation;
	}

	@Override
	public String address() {
		// TODO Auto-generated method stub
		return address;
	}

	@Override
	public String[] interests() {
		// TODO Auto-generated method stub
		return interests;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return description;
	}
	
	@Override
	public String[] friends() {
		// TODO Auto-generated method stub
		return friends;
	}

	@Override
	public String privacy() {
		// TODO Auto-generated method stub
		return privacy;
	}

	@Override
	public int status() {
		// TODO Auto-generated method stub
		return status;
	}

	protected String getIdUser() {
		return iduser;
	}
	
	protected String getType() {
		return type;
	}

	protected String getUsername() {
		return username;
	}

	protected String getIconUrl() {
		return iconurl;
	}


	protected String getName() {
		return name;
	}

	protected String getPhone() {
		return phone;
	}


	protected String getEmail() {
		return email;
	}


	protected DateTime getBirthday() {
		return birthday;
	}

	protected char getSex() {
		return sex;
	}

	protected String getZip() {
		return zip;
	}

	protected String getState() {
		return state;
	}



	protected String getCity() {
		return city;
	}


	protected String getAddress() {
		return address;
	}


	protected String[] getInterests() {
		return interests;
	}


	protected String getDescription() {
		return description;
	}

	protected String[] getFriends() {
		return friends;
	}
	protected String getPrivacy() {
		return privacy;
	}

	protected int getStatus() {
		return status;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public String getIconurl() {
		return iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setBirthday(DateTime birthday) {
		this.birthday = birthday;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setInterests(String[] interests) {
		this.interests = interests;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFriends(String[] friends) {
		this.friends = friends;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	public void setStatus(int status) {
		this.status = status;
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
		return null;
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
