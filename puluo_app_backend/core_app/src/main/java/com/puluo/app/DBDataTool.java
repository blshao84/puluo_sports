package com.puluo.app;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.config.Configurations;
import com.puluo.dao.PuluoCouponDao;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.dao.impl.PuluoWechatBindingDaoImpl;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoWechatBinding;
import com.puluo.entity.impl.PuluoCouponImpl;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.enumeration.CouponType;
import com.puluo.enumeration.EventStatus;

public class DBDataTool {

	public static void main(String[] args) {
		//fixUserThumbnail();
		//grantCoupons();
		createWeeklyEvents();
	}

	@SuppressWarnings("unused")
	private static void fixUserThumbnail() {
		List<PuluoWechatBinding> bindings = ((PuluoWechatBindingDaoImpl) DaoApi
				.getInstance().wechatBindingDao()).getAll();
		PuluoUserDao userDao = DaoApi.getInstance().userDao();
		for (PuluoWechatBinding bd : bindings) {
			PuluoUser user = userDao.getByMobile(bd.mobile());
			if (user != null)
				if (user.thumbnail().contains("img.puluosports.com")) {
					System.out.println(user.mobile() + ":" + user.thumbnail());
					String[] tmp = user.thumbnail().split("/");
					String head = tmp[tmp.length - 1];
					System.out.println("**************" + head);
					userDao.updateProfile(user, null, null, head, null, null,
							null, null, null, null, null, null);
				}
		}
	}

	@SuppressWarnings("unused")
	private static void grantCoupons() {
		List<PuluoUser> users = ((PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao()).getAll();
		PuluoCouponDao couponDao = DaoApi.getInstance().couponDao();
		for (PuluoUser user : users) {
			for (int i = 0; i < 5; i++)
				couponDao.insertCoupon(new PuluoCouponImpl(UUID.randomUUID()
						.toString(), CouponType.Deduction,
						Configurations.registrationAwardAmount,
						user.userUUID(), null, Configurations.puluoLocation
								.locationId(), DateTime.now().plusDays(14)));
		}
	}
	
	private static void createWeeklyEvents() {
		PuluoEventDao eventDao = DaoApi.getInstance().eventDao();
		List<PuluoEvent> events = eventDao.findEvents(
				DateTime.now().minusDays(7), 
				DateTime.now(), null, null,null, null, 0, 0, 0, null, null,100,0);
		System.out.println(events.size());
		for(PuluoEvent e:events){
			String uuid = UUID.randomUUID().toString();
			DateTime newDate= e.eventTime().plusWeeks(1);
			PuluoEvent newEvent = new PuluoEventImpl(
					uuid, 
					newDate, 
					EventStatus.Open, 
					e.registeredUsers(), 
					e.capatcity(), 
					e.originalPrice(), 
					e.discountedPrice(), 
					e.eventInfoUUID(),
					e.eventLocationUUID(),
					e.hottest());
			eventDao.saveEvent(newEvent);
		}
	}
}
