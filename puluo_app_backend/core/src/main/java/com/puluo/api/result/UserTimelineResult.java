package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

import com.google.gson.Gson;
import com.puluo.util.HasJSON;


public class UserTimelineResult extends HasJSON{
	public String timeline_uuid;
	public TimelineEvent event;
	public String my_words;
	public List<TimelineLikes> likes;
	public List<TimelineComments> comments;
	public DateTime create_at;
	public DateTime updated_at;

	public UserTimelineResult(String timeline_uuid, TimelineEvent event,
			String my_words, List<TimelineLikes> likes,
			List<TimelineComments> comments, DateTime create_at,
			DateTime updated_at) {
		super();
		this.timeline_uuid = timeline_uuid;
		this.event = event;
		this.my_words = my_words;
		this.likes = likes;
		this.comments = comments;
		this.create_at = create_at;
		this.updated_at = updated_at;
	}
	
	public static List<UserTimelineResult> dummy() {
		List<UserTimelineResult> details = new ArrayList<UserTimelineResult>();
		
		List<TimelineLikes> likes = new ArrayList<TimelineLikes>();
		List<TimelineComments> comments = new ArrayList<TimelineComments>();
			
		likes.add(new TimelineLikes("de305d54-75b4-431b-adb2-eb6b9e546013", "Bob", DateTime.parse("2012-01-01 12:00:00")));
		likes.add(new TimelineLikes("de305d54-75b4-431b-adb2-eb6b9e546013", "Bob", DateTime.parse("2012-01-01 12:00:00")));
		
		comments.add(new TimelineComments("", "", "de305d54-75b4-431b-adb2-eb6b9e546013", "Bob", "", "false", DateTime.parse("2012-01-01 12:00:00")));
		
		details.add(new UserTimelineResult("", new TimelineEvent("de305d54-75b4-431b-adb2-eb6b9e546013", 
				"Weapon of big ass reduction", DateTime.parse("2012-01-01 12:00:00")), "This is an awesome event", likes, 
				comments, DateTime.parse("2012-01-02 12:00:00"), DateTime.parse("2012-01-03 12:00:00")));
		
		return details;
	}
}

class TimelineEvent {
	public String event_uuid;
	public String event_name;
	public DateTime created_at;

	public TimelineEvent(String event_uuid, String event_name,
			DateTime created_at) {
		super();
		this.event_uuid = event_uuid;
		this.event_name = event_name;
		this.created_at = created_at;
	}

	public static TimelineEvent dummy() {
		// TODO
		return null;
	}
}

class TimelineLikes {
	public String user_uuid;
	public String user_name;
	public DateTime created_at;

	public TimelineLikes(String event_uuid, String event_name,
			DateTime created_at) {
		super();
		this.user_uuid = event_uuid;
		this.user_name = event_name;
		this.created_at = created_at;
	}

	public static TimelineLikes dummy() {
		// TODO
		return null;
	}
}

class TimelineComments {
	public String comment_uuid;
	public String reply_to_uuid;
	public String user_uuid;
	public String user_name;
	public String content;
	public String read;
	public DateTime created_at;

	public TimelineComments(String comment_uuid, String reply_to_uuid,
			String user_uuid, String user_name, String content, String read,
			DateTime created_at) {
		super();
		this.comment_uuid = comment_uuid;
		this.reply_to_uuid = reply_to_uuid;
		this.user_uuid = user_uuid;
		this.user_name = user_name;
		this.content = content;
		this.read = read;
		this.created_at = created_at;
	}

	public static TimelineComments dummy() {
		// TODO
		return null;
	}
}
