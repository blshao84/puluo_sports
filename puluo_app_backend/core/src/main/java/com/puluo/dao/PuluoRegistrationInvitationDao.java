package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoRegistrationInvitation;


public interface PuluoRegistrationInvitationDao {
	public boolean createTable();
	public PuluoRegistrationInvitation getByUUID(String uuid);
	public List<PuluoRegistrationInvitation> getUserSentInvitations(String fromUUID);
	public List<PuluoRegistrationInvitation> getUserReceivedInvitations(String fromUUID);
	public boolean updateCoupon(String invitationUUID,String couponUUID);
	public boolean insertInvitation(PuluoRegistrationInvitation invitation);
}
