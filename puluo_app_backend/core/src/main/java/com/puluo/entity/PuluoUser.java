package com.puluo.entity;

import java.sql.Date;


public interface PuluoUser {

	String idUser();
	String type();
	String username();
	String iconurl();
	String name();
	String phone();
	String email();
	Date birthday();
	char sex();
	String zip();
	String province();
	String city();
	String address();
	String[] interests();
	String description();
	String[] friends();
	String privacy();
	int status();	
}
