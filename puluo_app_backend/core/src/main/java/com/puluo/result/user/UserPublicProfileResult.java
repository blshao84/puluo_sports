package com.puluo.result.user;

public class UserPublicProfileResult {
	public final String first_name;
	public final String last_name;
	public final String thumbnail;
	public final String large_image;
	public final String saying;
	public final int likes;
	public final boolean banned;
	public final String following;
	public final boolean is_coach;
	public final long last_login;

	public UserPublicProfileResult(String first_name, String last_name,
			String thumbnail, String large_image, String saying, int likes,
			boolean banned, String following, boolean is_coach,long last_login) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.thumbnail = thumbnail;
		this.large_image = large_image;
		this.saying = saying;
		this.likes = likes;
		this.banned = banned;
		this.following = following;
		this.is_coach = is_coach;
		this.last_login = last_login;
	}

	public static UserPublicProfileResult dummy() {

		return new UserPublicProfileResult("Tracy", "Boyd",
				"http://upyun/puluo/userThumb.jpg!200",
				"http://upyun/puluo/userThumb.jpg",
				"I’ve got an app for that.", 2, false, "true", false,10000000);
	}
}