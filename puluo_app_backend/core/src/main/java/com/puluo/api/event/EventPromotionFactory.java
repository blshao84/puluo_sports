package com.puluo.api.event;

import java.util.List;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoRegistrationInvitation;
import com.puluo.entity.PuluoUser;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Pair;

public class EventPromotionFactory {
	private static Log log = LogFactory.getLog(EventPromotionFactory.class);
	private static int MIN_REFERED_USERS = 5;
	private static PuluoDSI dsi = DaoApi.getInstance();

	public static Pair<String, Boolean> checkReferalCount(PuluoEvent event,
			PuluoUser user) {
		if (event == null || user == null)
			return new Pair<String, Boolean>("系统错误，请稍后再试", false);
		if (event.hottest() == 3) {
			List<PuluoRegistrationInvitation> invitations = dsi.invitationDao()
					.getUserSentInvitations(user.userUUID());
			log.info(String.format("find %ss invitations from user %s",
					invitations.size(),user.userUUID()));
			if (invitations.size() >= MIN_REFERED_USERS) {
				return new Pair<String, Boolean>("", true);
			} else {
				return new Pair<String, Boolean>(
						String.format("已经有%s位小伙伴在您的邀请下加入普罗了，"
								+ "加油再邀请%s位您即可免费体验本次课程！",
								invitations.size(),
								(MIN_REFERED_USERS-invitations.size())), false);
			}
		}else{
			return new Pair<String, Boolean>("", true);
		}
	}
}
