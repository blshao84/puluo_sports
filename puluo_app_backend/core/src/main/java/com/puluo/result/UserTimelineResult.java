package com.puluo.result;

import java.util.ArrayList;
import java.util.List;

import com.puluo.entity.PuluoTimelinePost;
import com.puluo.entity.impl.PuluoTimelinePostImpl;
import com.puluo.util.HasJSON;
import com.puluo.util.TimeUtils;

public class UserTimelineResult extends HasJSON {

	public List<UserTimeline> timelines;

	public UserTimelineResult() {
		super();
	}

	public UserTimelineResult(List<UserTimeline> timelines) {
		super();
		this.timelines = timelines;
	}

	public boolean setTimelinePosts(List<PuluoTimelinePost> posts) {

		timelines = new ArrayList<UserTimeline>();
		for (int i = 0; i < posts.size(); i++) {
			PuluoTimelinePostImpl post_impl = (PuluoTimelinePostImpl) posts.get(i);
			// create timelineLikes array
			ArrayList<TimelineLikes> tl_likes = new ArrayList<TimelineLikes>();
			for (int j = 0; j < post_impl.likes().size(); j++) {
				TimelineLikes like = new TimelineLikes(post_impl.likes().get(j)
						.userUUID(), post_impl.likes().get(j).userName(),
						TimeUtils.dateTime2Millis(post_impl.likes().get(j)
								.createdAt()));
				tl_likes.add(like);
			}
			// create timelineComments array
			ArrayList<TimelineComments> tl_comments = new ArrayList<TimelineComments>();
			for (int k = 0; k < post_impl.comments().size(); k++) {
				TimelineComments comment = new TimelineComments(post_impl
						.comments().get(k).commentUUID(), post_impl.comments()
						.get(k).toUser().userUUID(), post_impl.comments()
						.get(k).fromUser().userUUID(), post_impl.comments()
						.get(k).fromUser().name(), post_impl.comments().get(k)
						.content(), String.valueOf(post_impl.comments().get(k)
						.isRead()), 
						TimeUtils.dateTime2Millis(post_impl.comments().get(k).createdAt()));
				tl_comments.add(comment);
			}
			// create userTimeline array
			UserTimeline tmp = new UserTimeline(post_impl.timelineUUID(),
					new TimelineEvent(post_impl.event().eventUUID(), post_impl
							.event().eventInfo().name(), 
							TimeUtils.dateTime2Millis(post_impl.event().eventTime())), 
							post_impl.content(),tl_likes, tl_comments, 
					TimeUtils.dateTime2Millis(post_impl.createdAt()),
					TimeUtils.dateTime2Millis(post_impl.createdAt()));
			timelines.add(tmp);
		}
		return true;
	}

	public static UserTimelineResult dummy() {

		List<UserTimeline> details = new ArrayList<UserTimeline>();

		List<TimelineLikes> likes = new ArrayList<TimelineLikes>();
		List<TimelineComments> comments = new ArrayList<TimelineComments>();

		likes.add(new TimelineLikes("de305d54-75b4-431b-adb2-eb6b9e546013",
				"Bob", 1427007059034L));
		likes.add(new TimelineLikes("de305d54-75b4-431b-adb2-eb6b9e546013",
				"Bob", 1427007059034L));

		comments.add(new TimelineComments(
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013", "Bob", "abc", "false",
				1427007059034L));

		details.add(new UserTimeline("de305d54-75b4-431b-adb2-eb6b9e546013",
				new TimelineEvent("de305d54-75b4-431b-adb2-eb6b9e546013",
						"Weapon of big ass reduction", 1427007059034L),
				"This is an awesome event", likes, comments, 1427007059034L,
				1427007059034L));
		return new UserTimelineResult(details);
	}
}

class UserTimeline {
	public String timeline_uuid;
	public TimelineEvent event;
	public String my_words;
	public List<TimelineLikes> likes;
	public List<TimelineComments> comments;
	public long create_at;
	public long updated_at;

	public UserTimeline(String timeline_uuid, TimelineEvent event,
			String my_words, List<TimelineLikes> likes,
			List<TimelineComments> comments, long create_at, long updated_at) {
		super();
		this.timeline_uuid = timeline_uuid;
		this.event = event;
		this.my_words = my_words;
		this.likes = likes;
		this.comments = comments;
		this.create_at = create_at;
		this.updated_at = updated_at;
	}
}

class TimelineEvent {
	public String event_uuid;
	public String event_name;
	public long created_at;

	public TimelineEvent(String event_uuid, String event_name, long created_at) {
		super();
		this.event_uuid = event_uuid;
		this.event_name = event_name;
		this.created_at = created_at;
	}

}

class TimelineLikes {
	public String user_uuid;
	public String user_name;
	public long created_at;

	public TimelineLikes(String event_uuid, String event_name, long created_at) {
		super();
		this.user_uuid = event_uuid;
		this.user_name = event_name;
		this.created_at = created_at;
	}

}

class TimelineComments {
	public String comment_uuid;
	public String reply_to_uuid;
	public String user_uuid;
	public String user_name;
	public String content;
	public String read;
	public long created_at;

	public TimelineComments(String comment_uuid, String reply_to_uuid,
			String user_uuid, String user_name, String content, String read,
			long created_at) {
		super();
		this.comment_uuid = comment_uuid;
		this.reply_to_uuid = reply_to_uuid;
		this.user_uuid = user_uuid;
		this.user_name = user_name;
		this.content = content;
		this.read = read;
		this.created_at = created_at;
	}
}
