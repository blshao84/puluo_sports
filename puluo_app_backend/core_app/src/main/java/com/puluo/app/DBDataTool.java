package com.puluo.app;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.config.Configurations;
import com.puluo.dao.PuluoCouponDao;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventPosterDao;
import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoEventInfoDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.dao.impl.PuluoWechatBindingDaoImpl;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoWechatBinding;
import com.puluo.entity.impl.PuluoCouponImpl;
import com.puluo.entity.impl.PuluoEventAttendee;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.entity.impl.PuluoPaymentOrderImpl;
import com.puluo.enumeration.CouponType;
import com.puluo.enumeration.EventStatus;
import com.puluo.enumeration.PuluoOrderStatus;
import com.puluo.enumeration.PuluoPartner;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class DBDataTool {

	public static void main(String[] args) {
		// fixUserThumbnail();
		// grantCoupons();
		createWeeklyEvents();
		// registerEvent();
		// fixPosterRank();
	}
	
	private static void fakeOrders() {
		String idevent = "4a131632-ae98-4e5e-9f42-65d45a32451c";
		PuluoPaymentOrder order1 = new PuluoPaymentOrderImpl(
				311,UUID.randomUUID().toString(),"",0, DateTime.now(),"af48bfb1-36eb-4304-be95-e5f4fb08bc1e", idevent,PuluoOrderStatus.Paid , PuluoPartner.PuluoWeb);
		PuluoPaymentOrder order2 = new PuluoPaymentOrderImpl(
				312,UUID.randomUUID().toString(),"",0, DateTime.now(),"0d874681-934c-4df1-a935-d07195f0d5e7", idevent,PuluoOrderStatus.Paid , PuluoPartner.PuluoWeb);
		PuluoPaymentOrder order3 = new PuluoPaymentOrderImpl(
				313,UUID.randomUUID().toString(),"",0, DateTime.now(),"630eaa80-7dde-4919-b55b-6dd8add30ea5", idevent,PuluoOrderStatus.Paid , PuluoPartner.PuluoWeb);
		PuluoPaymentOrder order4 = new PuluoPaymentOrderImpl(
				314,UUID.randomUUID().toString(),"",0, DateTime.now(),"dcfa8988-7f98-45bb-b114-a609f19d3365", idevent,PuluoOrderStatus.Paid , PuluoPartner.PuluoWeb);
		PuluoPaymentDao dao = DaoApi.getInstance().paymentDao();
		dao.saveOrder(order1);
		dao.saveOrder(order2);
		dao.saveOrder(order3);
		dao.saveOrder(order4);
	}

	private static void fixPosterRank() {
		PuluoEventInfoDaoImpl eventInfoDaoImpl = (PuluoEventInfoDaoImpl) DaoApi
				.getInstance().eventInfoDao();
		PuluoEventPosterDao posterDao = DaoApi.getInstance().eventPosterDao();
		List<PuluoEventInfo> infos = eventInfoDaoImpl.getAll();
		for (PuluoEventInfo info : infos) {
			List<PuluoEventPoster> posters = posterDao
					.getEventPosterByInfoUUID(info.eventInfoUUID());
			for (int i = 0; i < posters.size(); i++) {
				posterDao.updatePosterRank(posters.get(i).imageId(), i + 1);
			}
		}
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

	@SuppressWarnings("unused")
	private static void createWeeklyEvents() {
		PuluoEventDao eventDao = DaoApi.getInstance().eventDao();
		DateTime from = TimeUtils.parseDateTime("2015-07-04 00:00:00");
		DateTime to = TimeUtils.parseDateTime("2015-07-04 23:59:59");
		List<PuluoEvent> events = eventDao.findEvents(
				from,to, null, null, null,
				null, 0, 0, 0, EventStatus.Open, null, 100, 0);
		System.out.println(events.size());
		for (PuluoEvent e : events) {
			String uuid = UUID.randomUUID().toString();
			DateTime newDate = e.eventTime().plusWeeks(1);
			PuluoEvent newEvent = new PuluoEventImpl(uuid, newDate,
					EventStatus.Open, e.registeredUsers(), e.capatcity(),
					e.originalPrice(), e.discountedPrice(), e.eventInfoUUID(),
					e.eventLocationUUID(), e.hottest());
			eventDao.saveEvent(newEvent);
		}
	}

	private static void registerEvent() {
		String eventID = "69078a59-e913-46d2-aa53-c00a8c4d67cf";
		PuluoEvent event = DaoApi.getInstance().eventDao()
				.getEventByUUID(eventID);
		List<PuluoEventAttendee> attendees = event.attendees();
		Set<String> attendeeUUIDs = new HashSet<String>();
		for (PuluoEventAttendee a : attendees) {
			attendeeUUIDs.add(a.uuid());
		}
		PuluoUserDaoImpl userDaoImpl = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		List<PuluoUser> users = userDaoImpl.getAll();
		int cnt = 0;
		int index = 0;
		int size = users.size();
		while (cnt < 1 && index < size) {
			PuluoUser user = users.get(index);
			index++;
			if (!attendeeUUIDs.contains(user.userUUID())
					&& Strs.isEmpty(user.firstName())
					&& Strs.isEmpty(user.lastName())) {
				PuluoPaymentOrder order = new PuluoPaymentOrderImpl(
						"DO NOT NEED A PAYMENT!", 0, DateTime.now(),
						user.userUUID(), eventID, PuluoOrderStatus.Paid,
						PuluoPartner.PuluoWeb);
				DaoApi.getInstance().paymentDao().saveOrder(order);
				cnt++;
			}
		}
	}
}
