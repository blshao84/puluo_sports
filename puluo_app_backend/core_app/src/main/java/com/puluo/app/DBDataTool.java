package com.puluo.app;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.config.Configurations;
import com.puluo.dao.PuluoCouponDao;
import com.puluo.dao.PuluoDSI;
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
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoWechatBinding;
import com.puluo.entity.impl.PuluoCouponImpl;
import com.puluo.entity.impl.PuluoEventAttendee;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.entity.impl.PuluoEventInfoImpl;
import com.puluo.entity.impl.PuluoEventPosterImpl;
import com.puluo.entity.impl.PuluoPaymentOrderImpl;
import com.puluo.enumeration.CouponType;
import com.puluo.enumeration.EventStatus;
import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;
import com.puluo.enumeration.PuluoEventPosterType;
import com.puluo.enumeration.PuluoOrderStatus;
import com.puluo.enumeration.PuluoPartner;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class DBDataTool {

	public static void main(String[] args) {
		// fixUserThumbnail();
		// grantCoupons();
		//createWeeklyEvents();
		// registerEvent();
		// fixPosterRank();
		PuluoDSI dsi = DaoApi.getInstance();
		PuluoEventLocation loc = Configurations.puluoLocation;
		PuluoEventInfo zunbaInfo = dsi.eventInfoDao().getEventInfoByUUID(
				"c514fe75-4b85-4fc9-b8f9-ac8003743ad4");
		PuluoEventInfo pulatiInfo = new PuluoEventInfoImpl(UUID.randomUUID()
				.toString(), "普拉提", // name
				"", // desc
				"", // coach name
				"", // coach uuid
				"", // thumbnail
				"", // detail
				60, PuluoEventLevel.Level1, PuluoEventCategory.Yoga);

		PuluoEventPoster pulatiPoster1 = new PuluoEventPosterImpl(
				"bb59230e-74e8-4b51-babc-8c5bd8bc3390.png", "pulati200",
				pulatiInfo.eventInfoUUID(), DateTime.now(),
				PuluoEventPosterType.POSTER, 1);
		PuluoEventPoster pulatiPoster2 = new PuluoEventPosterImpl(
				"9b8a695a-a3e3-4b88-89ae-5a5042029705.jpg", "pulati",
				pulatiInfo.eventInfoUUID(), DateTime.now(),
				PuluoEventPosterType.POSTER, 2);
		dsi.eventPosterDao().saveEventPhoto(pulatiPoster1);
		dsi.eventPosterDao().saveEventPhoto(pulatiPoster2);

		PuluoEventInfo hataInfo = dsi.eventInfoDao().getEventInfoByUUID(
				"ddc12abe-6ea9-44d3-9abb-de57fc7f158f");
		PuluoEventInfo coreInfo = dsi.eventInfoDao().getEventInfoByUUID(
				"679c3d67-3a3d-4072-8007-15f14966df24");

		PuluoEventInfo trxInfo = new PuluoEventInfoImpl(UUID.randomUUID()
				.toString(), "身体平衡+TRX", // name
				"", // desc
				"", // coach name
				"", // coach uuid
				"", // thumbnail
				"", // detail
				60, PuluoEventLevel.Level1, PuluoEventCategory.Stamina);
		PuluoEventPoster trxPoster1 = new PuluoEventPosterImpl(
				"8bf84367-73db-470f-b322-f1c36537ef84.png", "trx200",
				trxInfo.eventInfoUUID(), DateTime.now(),
				PuluoEventPosterType.POSTER, 1);
		PuluoEventPoster trxPoster2 = new PuluoEventPosterImpl(
				"703d74c6-52f2-401c-baf1-4aac595a1728.jpg", "trx",
				trxInfo.eventInfoUUID(), DateTime.now(),
				PuluoEventPosterType.POSTER, 2);
		dsi.eventPosterDao().saveEventPhoto(trxPoster1);
		dsi.eventPosterDao().saveEventPhoto(trxPoster2);

		PuluoEventInfo xiaobanInfo = dsi.eventInfoDao().getEventInfoByUUID(
				"c007a3a8-6b78-4aa4-b537-881876b810df");
		PuluoEventInfo yaofuInfo = dsi.eventInfoDao().getEventInfoByUUID(
				"243613ef-a18e-4bdc-921f-b82fb661089c");
		
		
		PuluoEventInfo hiitInfo = new PuluoEventInfoImpl(UUID.randomUUID()
				.toString(), "HITT训练", // name
				"", // desc
				"", // coach name
				"", // coach uuid
				"", // thumbnail
				"", // detail
				60, PuluoEventLevel.Level1, PuluoEventCategory.Stamina);
		PuluoEventPoster hiitPoster1 = new PuluoEventPosterImpl(
				"b8eaba76-967a-4e00-917f-8b69548fedbb.png", "hiit200",
				hiitInfo.eventInfoUUID(), DateTime.now(),
				PuluoEventPosterType.POSTER, 1);
		PuluoEventPoster hiitPoster2 = new PuluoEventPosterImpl(
				"f7880943-0f1c-461e-bdda-23a557ac0b9f.jpeg", "hiit",
				hiitInfo.eventInfoUUID(), DateTime.now(),
				PuluoEventPosterType.POSTER, 2);
		dsi.eventPosterDao().saveEventPhoto(hiitPoster1);
		dsi.eventPosterDao().saveEventPhoto(hiitPoster2);
		
		
		
		PuluoEventInfo minzuInfo = new PuluoEventInfoImpl(UUID.randomUUID()
				.toString(), "民族舞", // name
				"", // desc
				"", // coach name
				"", // coach uuid
				"", // thumbnail
				"", // detail
				60, PuluoEventLevel.Level1, PuluoEventCategory.Dance);
		PuluoEventPoster minzuPoster1 = new PuluoEventPosterImpl(
				"b49b4bc6-e7ec-4267-873b-152252a8375b.png", "minzu200",
				minzuInfo.eventInfoUUID(), DateTime.now(),
				PuluoEventPosterType.POSTER, 1);
		PuluoEventPoster minzuPoster2 = new PuluoEventPosterImpl(
				"b3372cfb-2ee6-4f79-88f3-383d04892ea1.jpg", "minzu",
				minzuInfo.eventInfoUUID(), DateTime.now(),
				PuluoEventPosterType.POSTER, 2);
		dsi.eventPosterDao().saveEventPhoto(minzuPoster1);
		dsi.eventPosterDao().saveEventPhoto(minzuPoster2);
		
		
		PuluoEventInfo pilaoInfo = dsi.eventInfoDao().getEventInfoByUUID(
				"3db9e9ba-07b7-488a-ae7c-d33e534803bf");
		PuluoEventInfo pbInfo = dsi.eventInfoDao().getEventInfoByUUID(
				"313c60c2-e889-4a7f-beb9-e4d7593a402c");
		
		PuluoEventInfo dupiInfo = new PuluoEventInfoImpl(UUID.randomUUID()
				.toString(), "肚皮舞", // name
				"", // desc
				"", // coach name
				"", // coach uuid
				"", // thumbnail
				"", // detail
				60, PuluoEventLevel.Level1, PuluoEventCategory.Dance);
		PuluoEventPoster dupiPoster1 = new PuluoEventPosterImpl(
				"80a00d89-0d10-4337-ba9c-7a26b02fb1dd.png", "dupi200",
				dupiInfo.eventInfoUUID(), DateTime.now(),
				PuluoEventPosterType.POSTER, 1);
		PuluoEventPoster dupiPoster2 = new PuluoEventPosterImpl(
				"455e7aa6-39b4-4fab-a214-414d15e3dff6.png", "dupi",
				dupiInfo.eventInfoUUID(), DateTime.now(),
				PuluoEventPosterType.POSTER, 2);
		dsi.eventPosterDao().saveEventPhoto(dupiPoster1);
		dsi.eventPosterDao().saveEventPhoto(dupiPoster2);
		
		dsi.eventInfoDao().saveEventInfo(pulatiInfo);
		dsi.eventInfoDao().saveEventInfo(trxInfo);
		dsi.eventInfoDao().saveEventInfo(hiitInfo);
		dsi.eventInfoDao().saveEventInfo(minzuInfo);
		dsi.eventInfoDao().saveEventInfo(dupiInfo);

		PuluoEvent zunba = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-20 19:00:00"),
				EventStatus.Open, 0, 20, 1.0, 1.0, zunbaInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(zunba);

		PuluoEvent pulati = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-20 20:00:00"),
				EventStatus.Open, 0, 20, 1.0, 1.0, pulatiInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(pulati);

		PuluoEvent hata = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-21 19:00:00"),
				EventStatus.Open, 0, 20, 1.0, 1.0, hataInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(hata);

		PuluoEvent core = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-21 20:00:00"),
				EventStatus.Open, 0, 5, 1.0, 1.0, coreInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(core);

		PuluoEvent coreRK = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-21 20:00:00"),
				EventStatus.Open, 0, 15, 1.0, 1.0, coreInfo.eventInfoUUID(),
				loc.locationId(), -1);
		dsi.eventDao().saveEvent(coreRK);

		PuluoEvent trx = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-22 19:00:00"),
				EventStatus.Open, 0, 20, 1.0, 1.0, trxInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(trx);

		PuluoEvent xiaoban = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-22 20:00:00"),
				EventStatus.Open, 0, 10, 1.0, 1.0, xiaobanInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(xiaoban);

		PuluoEvent yaofu = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-23 19:00:00"),
				EventStatus.Open, 0, 20, 1.0, 1.0, yaofuInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(yaofu);

		PuluoEvent hiit = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-23 20:00:00"),
				EventStatus.Open, 0, 5, 1.0, 1.0, hiitInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(hiit);

		PuluoEvent hiitRK = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-23 20:00:00"),
				EventStatus.Open, 0, 15, 1.0, 1.0, hiitInfo.eventInfoUUID(),
				loc.locationId(), -1);
		dsi.eventDao().saveEvent(hiitRK);

		PuluoEvent minzu = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-24 18:30:00"),
				EventStatus.Open, 0, 20, 1.0, 1.0, minzuInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(minzu);

		PuluoEvent pilao = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-24 19:30:00"),
				EventStatus.Open, 0, 20, 1.0, 1.0, pilaoInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(pilao);

		PuluoEvent pb = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-25 14:00:00"),
				EventStatus.Open, 0, 5, 1.0, 1.0, pbInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(pb);
		
		PuluoEvent pbPB = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-25 14:00:00"),
				EventStatus.Open, 0, 15, 1.0, 1.0, pbInfo.eventInfoUUID(),
				loc.locationId(), -1);
		dsi.eventDao().saveEvent(pbPB);

		PuluoEvent dupi = new PuluoEventImpl(UUID.randomUUID().toString(),
				TimeUtils.parseDateTime("2015-07-25 15:15:00"),
				EventStatus.Open, 0, 20, 1.0, 1.0, dupiInfo.eventInfoUUID(),
				loc.locationId(), 0);
		dsi.eventDao().saveEvent(dupi);

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
