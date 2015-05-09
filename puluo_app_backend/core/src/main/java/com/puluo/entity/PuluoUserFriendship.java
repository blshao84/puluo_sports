package com.puluo.entity;

import java.util.List;

import com.puluo.dao.PuluoDSI;
import com.puluo.entity.impl.PuluoUserInfo;

public interface PuluoUserFriendship {
	public String userUUID();
	public List<PuluoUserInfo> friends();
	public void setDSI(PuluoDSI dsi);
}
