package com.puluo.api.result;

public class UserPublicProfileResult {
	public String first_name;
	public String last_name;
	public String thumbnail;
	public String large_image;
	public String saying;
	public int likes;
	public boolean banned;
	public boolean following;
	public boolean is_coach;

	public UserPublicProfileResult(String first_name, String last_name,
			String thumbnail, String large_image, String saying, int likes,
			boolean banned, boolean following, boolean is_coach) {
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
	}

	public static UserPublicProfileResult dummy() {

		return new UserPublicProfileResult("Tracy", "Boyd",
				"http://upyun/puluo/userThumb.jpg!200",
				"http://upyun/puluo/userThumb.jpg",
				"I’ve got an app for that.", 2, false, true, false);
	}
}