package com.puluo.entity.impl;

import java.util.Calendar;
import java.util.List;

import org.joda.time.DateTime;

import com.puluo.config.Configurations;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoUser;
import com.puluo.enumeration.FriendRequestStatus;
import com.puluo.enumeration.PuluoUserType;
import com.puluo.util.Strs;

public class PuluoUserImpl implements PuluoUser {

	private final String user_uuid;
	private final String mobile;
	private final String[] interests;
	private final String password;
	private final String firstName; // 名
	private final String lastName; // 姓
	private final String thumbnail; // 头像
	private final PuluoUserType user_type;
	private final String email;
	char sex;
	private final String zip;
	private final String country; // added by Xuyang
	private final String state;
	private final String city;
	private final String occupation; // added by Xuyang
	private final String address;
	private final String saying; // 只是saying而已
	DateTime birthday;
	DateTime created_at; // 用户创建时间
	DateTime updated_at; // 用户信息最后一次更新时间
	boolean banned;
	PuluoDSI dsi;

	public PuluoUserImpl(String user_uuid, String mobile, String[] interests,
			String password, String firstName, String lastName,
			String thumbnail, PuluoUserType user_type,
			String email, char sex, String zip, String country, String state,
			String city, String occupation, String address, String saying,
			DateTime birthday, DateTime created_at, DateTime updated_at,
			boolean banned, PuluoDSI dsi) {
		super();
		this.user_uuid = user_uuid;
		this.mobile = mobile;
		this.interests = interests;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.thumbnail = thumbnail;
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
		this.dsi = dsi;
	}

	public PuluoUserImpl(String user_uuid, String mobile, String[] interests,
			String password, String firstName, String lastName,
			String thumbnail, PuluoUserType user_type,
			String email, char sex, String zip, String country, String state,
			String city, String occupation, String address, String saying,
			DateTime birthday, DateTime created_at, DateTime updated_at,
			boolean banned) {
		this(user_uuid, mobile, interests, password, firstName, lastName,
				thumbnail, user_type, email, sex, zip, country,
				state, city, occupation, address, saying, birthday, created_at,
				updated_at, banned, DaoApi.getInstance());
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
		if (firstName == null)
			return "";
		else
			return firstName;
	}

	@Override
	public String lastName() {
		if (lastName == null)
			return "";
		else
			return lastName;
	}

	@Override
	public String thumbnail() {
		if (thumbnail == null)
			// FIXME: should have a default img!!!
			return "";
		else
			return thumbnail;
	}
	
	@Override
	public String thumbnailURL() {
		return Configurations.imgHttpLink(thumbnail(),"");
	}


	@Override
	public PuluoUserType userType() {
		return user_type;
	}

	@Override
	public String email() {
		if (email == null)
			return "";
		else
			return email;
	}

	@Override
	public DateTime birthday() {
		if (birthday == null)
			return new DateTime(0);
		else
			return birthday;
	}

	@Override
	public char sex() {
		return sex;
	}

	@Override
	public String zip() {
		if (zip == null)
			return "";
		else
			return zip;
	}

	@Override
	public String country() {
		if (country == null)
			return "";
		else
			return country;
	}

	@Override
	public String state() {
		if (state == null)
			return "";
		else
			return state;
	}

	@Override
	public String city() {
		if (city == null)
			return "";
		else
			return city;
	}

	@Override
	public String occupation() {
		if (occupation == null)
			return "";
		else
			return occupation;
	}

	@Override
	public String address() {
		if (address == null)
			return "";
		else
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
		if (saying == null)
			return "";
		else
			return saying;
	}

	@Override
	public boolean banned() {

		return banned;
	}

	@Override
	public String name() {
		return Strs.join(lastName," ",firstName);
	}

	@Override
	public int likes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String following(String other_uuid) {
		return following(other_uuid, DaoApi.getInstance());
	}

	public String following(String other_uuid, PuluoDSI dsi) {
		if (other_uuid.equals(user_uuid)) {
			return "true";
		} else {
			if (dsi.friendshipDao().isFriend(user_uuid, other_uuid)) {
				return "true";
			} else if (dsi.friendRequestDao().getFriendRequestByUsers(user_uuid,other_uuid,FriendRequestStatus.Requested).size()>0
					|| dsi.friendRequestDao().getFriendRequestByUsers(other_uuid,user_uuid,FriendRequestStatus.Requested).size()>0) {
				return "pending";
			} else {
				return "false";
			}
		}
	}

	@Override
	public boolean isCoach() {

		if (user_type.equals(PuluoUserType.Coach))
			return true;
		return false;
	}

	@Override
	public DateTime lastLogin() {

		return dsi.sessionDao().getByMobile(mobile).createdAt();
	}

	@Override
	public long lastDuration() {

		return Calendar.getInstance().getTimeInMillis()
				- lastLogin().getMillis();
	}

	@Override
	public boolean autoAddFriend() {

		return dsi.userSettingDao().getByUserUUID(user_uuid).autoAddFriend();
	}

	@Override
	public boolean allowStrangerViewTimeline() {

		return dsi.userSettingDao().getByUserUUID(user_uuid).isTimelinePublic();
	}

	@Override
	public boolean allowSearched() {

		return dsi.userSettingDao().getByUserUUID(user_uuid).isSearchable();
	}

	@Override
	public List<PuluoFriendRequest> pending() {
		return pending(DaoApi.getInstance());
	}
	
	public List<PuluoFriendRequest> pending(PuluoDSI dsi) {
		return dsi.friendRequestDao().getPendingFriendRequestsByUserUUID(user_uuid,0,0);
	}

}
