package com.puluo.app;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import com.mashape.unirest.http.Unirest;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoWechatBindingDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoWechatBinding;
import com.puluo.result.ImageUploadServiceResult;
import com.puluo.service.PuluoService;
import com.puluo.util.FileIOUtil;
import com.puluo.util.Strs;
import com.puluo.weichat.PuluoWechatTokenCache;
import com.puluo.weichat.WechatUserInfo;
import com.puluo.weichat.WechatUtil;

public class WechatUserInfoUpdator {
	public static void main(String[] args) {
		String token = PuluoWechatTokenCache.token();
		PuluoWechatBindingDaoImpl bindingDao = (PuluoWechatBindingDaoImpl) DaoApi
				.getInstance().wechatBindingDao();
		PuluoUserDao userDao = DaoApi.getInstance().userDao();
		List<PuluoWechatBinding> bindings = bindingDao.getAll();
		for (PuluoWechatBinding bd : bindings) {

			try {
				WechatUserInfo info = WechatUtil
						.getUserInfo(token, bd.openId());
				PuluoUser user = null;
				if (bd.mobile() != null) {
					user = userDao.getByMobile(bd.mobile());
				}
				if (user != null && info != null) {
					String firstName = null;
					String thumbnail = null;
					String sex = null;
					String country = null;
					String province = null;
					String city = null;
					if (Strs.isEmpty(user.firstName()) && !Strs.isEmpty(info.nickname))
						firstName = info.nickname;
					if (Strs.isEmpty(user.thumbnail()) && !Strs.isEmpty(info.headimgurl))
						thumbnail = saveThumbnail(info.headimgurl);
					if (Strs.isEmptyChar(user.sex())) {
						if (info.sex == 1) {
							sex = "男";
						} else if (info.sex == 2) {
							sex = "女";
						} else
							sex = null;
					}
					if (Strs.isEmpty(user.country())&& !Strs.isEmpty(info.country))
						country = info.country;
					if (Strs.isEmpty(user.state())&& !Strs.isEmpty(info.province))
						province = info.province;
					if (Strs.isEmpty(user.city())&& !Strs.isEmpty(info.city))
						city = info.city;
					if (firstName != null || thumbnail != null || sex != null
							|| country != null || province != null
							|| city != null) {
						System.out.println("update user " + user.mobile()
								+ "\n" + info.toString());
						userDao.updateProfile(user, info.nickname, null,
								thumbnail, null, null, sex, null, info.country,
								info.province, info.city, null);
					} else {
						System.out.println("no update for user "
								+ user.mobile());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("unable to update user " + bd.mobile());
			}
		}
	}

	public static String saveThumbnail(String link) {
		try {
			InputStream in = Unirest.get(link).asBinary().getBody();
			byte[] data = FileIOUtil.readBytes(in);
			ImageUploadServiceResult res = PuluoService.image.saveImage(data,
					UUID.randomUUID().toString());
			return res.image_link;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
