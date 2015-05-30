package com.puluo.app;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.config.Configurations;
import com.puluo.dao.PuluoCouponDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.dao.impl.PuluoWechatBindingDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoWechatBinding;
import com.puluo.entity.impl.PuluoCouponImpl;
import com.puluo.enumeration.CouponType;

public class DBDataTool {

	public static void main(String[] args) {
		//fixUserThumbnail();
		grantCoupons();
	}

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
}
