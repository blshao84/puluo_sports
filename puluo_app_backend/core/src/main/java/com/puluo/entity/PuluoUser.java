package com.puluo.entity;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.puluo.entity.impl.PuluoUserType;

public interface PuluoUser {

	String userUUID();
	String mobile(); //手机
	String password();
	String firstName(); //名
	String lastName(); //姓
	String thumbnail(); //头像
	String largeImage(); //大图
	PuluoUserType userType(); 
	String email();
	LocalDate birthday();
	char sex();
	String zip();
	String country(); //added by Xuyang
	String state();
	String city();
	String occupation(); //added by Xuyang
	String address();
	String[] interests(); 
	DateTime createdAt(); //用户创建时间
	DateTime updatedAt(); //用户信息最后一次更新时间
	String saying(); //只是saying而已
	boolean banned();
	
	/**
	 * The following methods should be implememened by joining other tables
	 * @return
	 */
	String name();
	int likes();
	int following();
	boolean isCoach();
	DateTime lastLogin(); //最后一次登录时间
	long lastDuration(); //最后一次登录时长
	boolean autoAddFriend(); //自动添加好友
	boolean allowStrangerViewTimeline(); //允许陌生人查看
	boolean allowSearched(); //允许被搜索到
	 
}
