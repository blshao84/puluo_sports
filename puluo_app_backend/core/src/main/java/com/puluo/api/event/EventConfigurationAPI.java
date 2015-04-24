package com.puluo.api.event;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventConfigurationResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class EventConfigurationAPI extends PuluoAPI<PuluoDSI, EventConfigurationResult> {
	public static Log log = LogFactory.getLog(EventConfigurationAPI.class);

	@Override
	public void execute() {
		this.rawResult = EventConfigurationResult.getInstance();
	}

}
