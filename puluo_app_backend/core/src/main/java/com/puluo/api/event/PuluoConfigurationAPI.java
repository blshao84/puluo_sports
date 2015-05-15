package com.puluo.api.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.result.PuluoConfigurationResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoConfigurationAPI extends PuluoAPI<PuluoDSI, PuluoConfigurationResult> {
	public static Log log = LogFactory.getLog(PuluoConfigurationAPI.class);

	@Override
	public void execute() {
		this.rawResult = PuluoConfigurationResult.getInstance();
	}
	
	public void setGeoLocations(Map<String,List<String>> geo) {
		Map<String,Map<String,List<String>>> geos = new HashMap<String, Map<String,List<String>>>();
		geos.put("China", geo);
		this.rawResult.geo = geos;
	}

}
