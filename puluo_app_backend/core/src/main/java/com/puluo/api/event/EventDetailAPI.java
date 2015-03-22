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
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;


public class EventDetailAPI extends PuluoAPI<PuluoDSI,EventDetailResult> {
	public static Log log = LogFactory.getLog(EventDetailAPI.class);
	public String event_uuid;
	
	public EventDetailAPI(String event_uuid){
		this(event_uuid, DaoApi.getInstance());
	}

	public EventDetailAPI(String event_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_uuid = event_uuid;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找活动%s信息",event_uuid));
		List<String> thumbnails = new ArrayList<String>();
		List<String> images = new ArrayList<String>();
		PuluoEventDao event_dao = dsi.eventDao();
		PuluoEvent event = event_dao.getEventByUUID(event_uuid);
		if(event!=null) {
			for(int i=0;i<event.eventInfo().poster().size();i++) {
				thumbnails.add(event.eventInfo().poster().get(i).thumbnail());
				images.add(event.eventInfo().poster().get(i).imageURL());
			}
			EventDetailResult result = new EventDetailResult(event.status(),event.eventInfo().name(),
					TimeUtils.formatDate(event.eventTime()),event.eventLocation().address(),event.eventLocation().city(),
					event.eventLocation().phone(),event.eventInfo().coachName(),event.eventInfo().coachUUID(),thumbnails, 
					event.registeredUsers(),event.capatcity(),event.eventInfo().likes(),event.eventLocation().latitude(), 
					event.eventLocation().longitude(),event.eventInfo().details(),images);
			rawResult = result;
		} else {
			log.error(String.format("活动%s信息不存在",event_uuid));
			this.error = ApiErrorResult.getError(28);
		}
	}
}