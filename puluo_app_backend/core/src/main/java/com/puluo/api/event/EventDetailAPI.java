package com.puluo.api.event;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.EventDetailResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class EventDetailAPI extends PuluoAPI<PuluoDSI, EventDetailResult> {
	public static Log log = LogFactory.getLog(EventDetailAPI.class);
	public String event_uuid;
	public String user_uuid;

	public EventDetailAPI(String event_uuid, String user_uuid) {
		this(event_uuid, user_uuid, DaoApi.getInstance());
	}

	public EventDetailAPI(String event_uuid, String user_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_uuid = event_uuid;
		this.user_uuid = user_uuid;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找活动%s信息", event_uuid));
		List<String> thumbnails = new ArrayList<String>();
		List<String> images = new ArrayList<String>();
		PuluoEventDao event_dao = dsi.eventDao();
		PuluoEvent event = event_dao.getEventByUUID(event_uuid);
		if (event != null) {
			PuluoEventInfo info = event.eventInfo();
			PuluoEventLocation location = event.eventLocation();
			if (info != null & location != null) {
				List<PuluoEventPoster> posters = info.poster();
				List<PuluoEventMemory> memories = event.memory();
				
				for (int i = 0; i < posters.size(); i++)
					thumbnails.add(posters.get(i).thumbnailURL());
				for (int j = 0; j < memories.size(); j++)
					images.add(memories.get(j).thumbnailURL());
				
				EventDetailResult result = new EventDetailResult(
						event.statusName(), info.name(),
						TimeUtils.dateTime2Millis(event.eventTime()),
						location.address(), location.city(), location.phone(),
						info.coachName(), info.coachUUID(), thumbnails,
						event.registeredUsers(), event.capatcity(),
						info.likes(), location.latitude(),
						location.longitude(), info.details(), images,
						event.originalPrice(), event.attendees(),
						event.registered(user_uuid), info.duration());
				rawResult = result;
			} else {
				log.error(String.format(
						"event %s info or location doesn't exist", event_uuid));
				this.error = ApiErrorResult.getError(43);
			}
		} else {
			log.error(String.format("活动%s信息不存在", event_uuid));
			this.error = ApiErrorResult.getError(28);
		}
	}
}