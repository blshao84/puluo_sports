package com.puluo.entity.impl;

import java.sql.Date;

import com.puluo.entity.PuluoUser;


public class PuluoUserImpl implements PuluoUser {

	private String iduser;
	private String type;
	private String username;
	private String iconurl;
	private String name;
	private String phone;
	private String email;
	private Date birthday;
	private char sex;
	private String zip;
	private String province;
	private String city;
	private String address;
	private String[] interests;
	private String description;
	private String[] friends;
	private String privacy;
	private int status;
	
	
	public PuluoUserImpl() {}
	
	public PuluoUserImpl(String iduser, String type, String username,
			String iconurl, String name, String phone, String email,
			Date birthday, char sex, String zip, String province, String city,
			String address, String[] interests, String description,
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
		this.province = province;
		this.city = city;
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
	public Date birthday() {
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
	public String province() {
		// TODO Auto-generated method stub
		return province;
	}

	@Override
	public String city() {
		// TODO Auto-generated method stub
		return city;
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

	public void setIdUser(String iduser) {
		this.iduser = iduser;
	}

	protected String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	protected String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	protected String getIconUrl() {
		return iconurl;
	}

	public void setIconUrl(String iconurl) {
		this.iconurl = iconurl;
	}

	protected String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	protected String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	protected Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	protected char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	protected String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	protected String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	protected String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	protected String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	protected String[] getInterests() {
		return interests;
	}

	public void setInterests(String[] interests) {
		this.interests = interests;
	}

	protected String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	protected String[] getFriends() {
		return friends;
	}

	public void setFriends(String[] friends) {
		this.friends = friends;
	}

	protected String getPrivacy() {
		return privacy;
	}

	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}

	protected int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
