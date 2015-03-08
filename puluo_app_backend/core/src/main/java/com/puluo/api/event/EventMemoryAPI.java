package com.puluo.api.event;

import java.util.ArrayList;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventMemoryResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoEventMemoryDaoImpl;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.impl.PuluoEventMemoryImpl;


public class EventMemoryAPI extends PuluoAPI<PuluoDSI,EventMemoryResult>{
	
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
		PuluoEventMemoryDaoImpl memory_dao = new PuluoEventMemoryDaoImpl();
		ArrayList<String> memories_url = new ArrayList<String>();
		ArrayList<PuluoEventMemory> memories = memory_dao.getEventMemoryByUUID(event_uuid);
		for(int i=0;i<memories.size();i++) 
			memories_url.add(((PuluoEventMemoryImpl)memories.get(i)).imageURL());
		EventMemoryResult result = new EventMemoryResult(memories_url);
		rawResult = result;
	}
}
