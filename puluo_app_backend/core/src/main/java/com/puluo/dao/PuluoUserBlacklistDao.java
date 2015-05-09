package com.puluo.dao;

import com.puluo.entity.PuluoUserBlacklist;


public interface PuluoUserBlacklistDao {
	
	public boolean createTable();

	public PuluoUserBlacklist getBlacklistByUUID(String userUUID);
	
	public PuluoUserBlacklist freeUser(String userUUID, String bannedUUID);
	
	public PuluoUserBlacklist banUser(String userUUID, String bannedUUID);
	
	public boolean isBanned(String oneUserUUID, String theOtherUUID);
}
