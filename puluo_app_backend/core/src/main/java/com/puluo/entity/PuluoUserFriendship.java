package com.puluo.entity;

import java.util.List;

import com.puluo.dao.PuluoDSI;
import com.puluo.entity.impl.PuluoFriendInfo;

public interface PuluoUserFriendship {
	public String userUUID();
	public List<PuluoFriendInfo> friends();
	public void setDSI(PuluoDSI dsi);
}
