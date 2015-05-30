package com.puluo.app;

import java.util.List;

import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoWechatBindingDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoWechatBinding;

public class DBDataTool {

	public static void main(String[] args) {
		fixUserThumbnail();
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
					String head = tmp[tmp.length -1];
					System.out.println("**************"+head);
					userDao.updateProfile(user, null,null, head, null, null, null, null, null, null, null, null);
				}
		}
	}

}
