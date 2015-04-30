package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;

import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;
import com.puluo.util.HasJSON;

public class PuluoConfigurationResult extends HasJSON {
	public List<String> categories;
	public List<String> levels;
	
	private PuluoConfigurationResult(){
		categories = new ArrayList<String>();
		levels = new ArrayList<String>();
		for(PuluoEventCategory c:PuluoEventCategory.values()){
			categories.add(c.name());
		}
		for(PuluoEventLevel l:PuluoEventLevel.values()){
			levels.add(l.name());
		}
		
	}
	
	private static class SingletonHolder {
		private static final PuluoConfigurationResult INSTANCE = new PuluoConfigurationResult();
	}

	public static PuluoConfigurationResult getInstance() {
		return SingletonHolder.INSTANCE;
	}
	public static PuluoConfigurationResult dummy() {
		return getInstance();
	}
}
