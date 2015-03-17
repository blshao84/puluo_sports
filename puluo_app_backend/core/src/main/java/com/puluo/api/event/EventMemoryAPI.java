package com.puluo.api.event;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventMemoryResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.impl.PuluoEventMemoryImpl;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class EventMemoryAPI extends PuluoAPI<PuluoDSI,EventMemoryResult>{
	public static Log log = LogFactory.getLog(EventMemoryAPI.class);	
	public String event_uuid;
	public int max_count;

	public EventMemoryAPI(String event_uuid, int max_count){
		this(event_uuid, max_count, DaoApi.getInstance());
	}
	public EventMemoryAPI(String event_uuid, int max_count, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_uuid = event_uuid;
		this.max_count = max_count;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找活动%s的回忆图片",event_uuid));
		PuluoEventMemoryDao memory_dao = dsi.eventMemoryDao();
		List<PuluoEventMemory> memories = memory_dao.getEventMemoryByUUID(event_uuid);
		List<String> memories_url = new ArrayList<String>();
		for(int i=0;i<memories.size();i++) 
			memories_url.add(memories.get(i).imageURL());
		EventMemoryResult result = new EventMemoryResult(memories_url);
		rawResult = result;
	}
}
