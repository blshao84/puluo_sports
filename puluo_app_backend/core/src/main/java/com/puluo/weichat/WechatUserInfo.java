package com.puluo.weichat;

public class WechatUserInfo {
	public int subscribe;
	public String openid;
	public String nickname;
	public int sex;
	public String language;
	public String city;
	public String province;
	public String country;
	public String headimgurl;
	public String unionid;
	public String remark;
	public long subscribe_time;
	public int groupid;
	
	public WechatUserInfo(int subscribe, String openid, String nickname,
			int sex, String language, String city, String province,
			String country, String headimgurl, String unionid, String remark,
			long subscribe_time, int groupid) {
		super();
		this.subscribe = subscribe;
		this.openid = openid;
		this.nickname = nickname;
		this.sex = sex;
		this.language = language;
		this.city = city;
		this.province = province;
		this.country = country;
		this.headimgurl = headimgurl;
		this.unionid = unionid;
		this.remark = remark;
		this.subscribe_time = subscribe_time;
		this.groupid = groupid;
	}

	@Override
	public String toString() {
		return "WechatUserInfo [subscribe=" + subscribe + ", openid=" + openid
				+ ", nickname=" + nickname + ", sex=" + sex + ", language="
				+ language + ", city=" + city + ", province=" + province
				+ ", country=" + country + ", headimgurl=" + headimgurl
				+ ", unionid=" + unionid + ", remark=" + remark
				+ ", subscribe_time=" + subscribe_time + ", groupid=" + groupid
				+ "]";
	}
	
	
}
