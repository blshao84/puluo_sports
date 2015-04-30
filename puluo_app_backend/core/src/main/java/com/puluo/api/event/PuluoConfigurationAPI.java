package com.puluo.api.event;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.PuluoConfigurationResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoConfigurationAPI extends PuluoAPI<PuluoDSI, PuluoConfigurationResult> {
	public static Log log = LogFactory.getLog(PuluoConfigurationAPI.class);

	@Override
	public void execute() {
		this.rawResult = PuluoConfigurationResult.getInstance();
	}

}
