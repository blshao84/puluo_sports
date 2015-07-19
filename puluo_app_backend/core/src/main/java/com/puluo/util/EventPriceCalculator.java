package com.puluo.util;

import com.puluo.config.Configurations;
import com.puluo.entity.PuluoCoupon;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoUser;

public class EventPriceCalculator {
	// TODO: should write test for this class
	private static final Log log = LogFactory
			.getLog(EventPriceCalculator.class);

	public static Double calculate(PuluoEvent event, PuluoCoupon coupon,
			PuluoUser user) {
		if (event == null) {
			log.warn("event is null, ignore it and return 0.0");
			return 0.0;
		} else {
			if (coupon != null && user != null) {
				Double eventPrice = calculate(event, user);
				Double couponDiscount = coupon.amount();
				String couponUser = coupon.ownerUUID();
				if (couponUser.equals(user.userUUID()) && coupon.isValid()) {
					Double discounted = eventPrice - couponDiscount;
					if (discounted < Configurations.minPrice) {
						log.info(Strs.join("event ", event.eventUUID(),
								" after discounting, values at ", discounted,
								" less than min price ",
								Configurations.minPrice));
						return Configurations.minPrice;
					} else
						return discounted;
				} else {
					if (coupon.isValid()) {
						log.warn(Strs.join("try to use coupon ", coupon.uuid(),
								" belonging to user ", couponUser, " on user ",
								user.userUUID(),
								" user doesn't match so ignore the coupon"));
					} else {
						log.warn(Strs.join("coupon ", coupon.uuid(),
								" is not valid after ",
								TimeUtils.formatBirthday(coupon.validUntil())));
					}
					return eventPrice;
				}
			} else if (coupon == null && user != null) {
				return calculate(event, user);
			} else if (coupon != null && user == null) {
				log.warn(String.format(
						"can't verify coupon belongs to the user,"
								+ " ignore coupont=%s", coupon.uuid()));
				return event.price();
			} else {
				log.info("no coupon or user, use event's own price:"
						+ event.price());
				return event.price();
			}
		}
	}

	// TODO: in the future we might apply user's specific promotions to event
	public static Double calculate(PuluoEvent event, PuluoUser user) {
		return event.price();
	}
}
