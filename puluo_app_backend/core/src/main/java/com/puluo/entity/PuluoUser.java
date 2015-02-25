package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoUser {

	String idUser();
	String type(); // TODO fix me
	String username(); // TODO fix me
	String iconurl(); // TODO fix me
	String name(); // TODO fix me
	String phone(); // TODO fix me
	String email();
	Date birthday();
	char sex();
	String zip();
	String province();
	String city();
	String address();
	String[] interests(); // TODO fix me
	String description(); // TODO fix me
	String[] friends(); // TODO fix me
	String privacy(); // TODO fix me
	int status(); // TODO fix me
	
	Time lastLogin(); //最后一次登录时间
	long lastDuration(); //最后一次登录时长
	Time create(); //用户创建时间
	Time update(); //用户信息最后一次更新时间
	String firstName(); //名
	String lastName(); //姓
	String thumbnail(); //头像
	String largeImage(); //大图
	String mobile(); //手机
	boolean autoAddFriend(); //自动添加好友
	boolean allowStrangerViewTimeline(); //允许陌生人查看
	boolean allowSearched(); //允许被搜索到
	String saying(); //只是saying而已
	int likes();
	boolean banned();
	int following();
	boolean isCoach();
}
