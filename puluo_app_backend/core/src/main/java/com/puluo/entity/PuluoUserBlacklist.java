package com.puluo.entity;

import java.util.List;

import com.puluo.entity.impl.PuluoUserInfo;

public interface PuluoUserBlacklist {
	public String userUUID();
	public List<PuluoUserInfo> bannedUsers();
	public List<String> bannedUUIDs();
}
