package com.puluo.api.social;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ListFriendsResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class ListFriendsAPI extends PuluoAPI<PuluoDSI,ListFriendsResult> {
	
	public ListFriendsAPI(){
		this(DaoApi.getInstance());
	}
	public ListFriendsAPI(PuluoDSI dsi) {
		this.dsi = dsi;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
