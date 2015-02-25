package com.puluo.entity.impl;

import org.joda.time.LocalDate;

import com.puluo.entity.PuluoUser;


public class PuluoUserImpl implements PuluoUser {

	private String iduser;
	private String type;
	private String username;
	private String iconurl;
	private String name;
	private String phone;
	private String email;
	private LocalDate birthday;
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
			LocalDate birthday, char sex, String zip, String province, String city,
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
	public LocalDate birthday() {
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


	protected LocalDate getBirthday() {
		return birthday;
	}

	protected char getSex() {
		return sex;
	}

	protected String getZip() {
		return zip;
	}

	protected String getProvince() {
		return province;
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
}
