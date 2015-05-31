package com.puluo.api.payment;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.config.Configurations;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoRegistrationInvitationDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoRegistrationInvitation;
import com.puluo.entity.impl.PuluoCouponImpl;
import com.puluo.enumeration.CouponType;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoCouponAPI {
	private static Log log = LogFactory.getLog(PuluoCouponAPI.class);
	
	public static void grantNewCoupon(String receivedUserUUID){
		PuluoDSI dsi = DaoApi.getInstance();
		PuluoRegistrationInvitationDao invitationDao = dsi.invitationDao();
		List<PuluoRegistrationInvitation> invitations = 
				invitationDao.getUserReceivedInvitations(receivedUserUUID);
		for(PuluoRegistrationInvitation i:invitations){
			if(i.fromUUID()!=null){
				log.info("give coupon to user "+i.fromUUID());
			String couponUUID =UUID.randomUUID().toString();
			dsi.couponDao().insertCoupon(new PuluoCouponImpl(
					couponUUID,
					CouponType.Deduction, 
					Configurations.registrationAwardAmount, 
					i.fromUUID(), 
					null,
					Configurations.puluoLocation.locationId(), 
					DateTime.now().plusDays(14)));
			invitationDao.updateCoupon(i.uuid(), couponUUID);
			
			}
		}
	}
}
