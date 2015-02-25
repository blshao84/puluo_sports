package com.puluo.entity;

import org.joda.time.LocalDate;


public interface PuluoUser {

	String idUser();
	String type();
	String username();
	String iconurl();
	String name();
	String phone();
	String email();
	LocalDate birthday();
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
