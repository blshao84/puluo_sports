package com.puluo.api.event;

import java.util.ArrayList;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventDetailResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoEventDaoImpl;
import com.puluo.entity.PuluoEvent;
import com.puluo.util.TimeUtils;


public class EventDetailAPI extends PuluoAPI<PuluoDSI,EventDetailResult> {

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
		PuluoEventDaoImpl event_dao = new PuluoEventDaoImpl();
		PuluoEvent event = event_dao.getEventByUUID(event_uuid);
		ArrayList<String> thumbnails = new ArrayList<String>();
		ArrayList<String> memories = new ArrayList<String>();
		for(int i=0;i<event.eventInfo().poster().size();i++)
			thumbnails.add(event.eventInfo().poster().get(i).imageURL());
		for(int j=0;j<event.memory().size();j++)
			memories.add(event.memory().get(j).imageURL());
		EventDetailResult result = new EventDetailResult(event.status(),event.eventInfo().name(),
				TimeUtils.formatDate(event.eventTime()),event.eventLocation().address(),event.eventLocation().city(),
				event.eventLocation().phone(),event.eventInfo().coachName(),event.eventInfo().coachUuid(),thumbnails, 
				event.registeredUsers(),event.capatcity(),event.eventInfo().likes(),event.eventLocation().lattitude(), 
				event.eventLocation().longitude(),event.eventInfo().details(),memories);
		rawResult = result;
	}
}